package cn.wensiqun.asmsupport.block.control;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.objectweb.asm.Label;

import cn.wensiqun.asmsupport.Executable;
import cn.wensiqun.asmsupport.Parameterized;
import cn.wensiqun.asmsupport.block.ProgramBlock;
import cn.wensiqun.asmsupport.block.body.LocalVariableBody;
import cn.wensiqun.asmsupport.block.method.GenericMethodBody;
import cn.wensiqun.asmsupport.clazz.AClass;
import cn.wensiqun.asmsupport.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.exception.ASMSupportException;
import cn.wensiqun.asmsupport.exception.UnreachableCode;
import cn.wensiqun.asmsupport.operators.NoneOperator;
import cn.wensiqun.asmsupport.operators.Throw;
import cn.wensiqun.asmsupport.operators.asmdirect.GOTO;
import cn.wensiqun.asmsupport.operators.asmdirect.Marker;
import cn.wensiqun.asmsupport.operators.asmdirect.NOP;
import cn.wensiqun.asmsupport.operators.asmdirect.Store;
import cn.wensiqun.asmsupport.operators.util.OperatorFactory;
import cn.wensiqun.asmsupport.utils.memory.Stack;

/**
 * 
 * @author 温斯群(Joe Wen)
 *
 */
public abstract class Catch extends SeriesBlock implements LocalVariableBody {

    private static Log log = LogFactory.getLog(Catch.class);
    
    private AClass exception;
    
    private Try entityTry;
    
    private Catch nextCatch;
    
    private Finally finallyBlock;
    
    private Label catchLbl;
    
    /** Catch块结束的位置1*/
    private Label endCatchLbl1;
    
    private Label endCatchLbl2;
    
    private Label implicitCatchStartLbl;
    
    private Store implicitCatchThrowableStore;
    
    /** 该程序块中所有可执行的指令 */
    private List<Executable> parentExes;
    
    public Catch(AClass exception) {
        super();
        this.exception = exception;
        catchLbl = new Label();
        endCatchLbl1 = new Label();
        endCatchLbl2 = new Label();
        implicitCatchStartLbl = new Label();
    }
    
    void setFinallyBlock(Finally finallyBlock) {
    	this.finallyBlock = finallyBlock;
	}

    public Finally getFinallyBlock() {
		return finallyBlock;
	}

	@Override
    public void executing() {
        insnHelper.mark(catchLbl);
        Stack stack = insnHelper.getMv().getStack();
        stack.push(exception.getType());
        insnHelper.nop();
        
        for(Executable exe : getExecuteQueue()){
            if(exe.equals(implicitCatchThrowableStore)){
                stack.push(AClass.THROWABLE_ACLASS.getType());
            }
            exe.execute();
        }
        
        if(finallyBlock == null){
        	//insnHelper.goTo(getTerminalEndLabelInCatch());
            insnHelper.mark(endCatchLbl2);
        }

    }

    @Override
    protected void init() {
    	GenericMethodBody mb = getMethodBody();
        mb.addTryCatchInfo(entityTry.getStart(), entityTry.getEnd(), catchLbl, exception);
        
        //是最后一个Catch 并且存在Finally Block 则对try程序每个catch程序
        //添加一个隐藏的catch块(此catch程序块将catch throwable异常)
        if(finallyBlock != null){
            mb.addTryCatchInfo(entityTry.getStart(), entityTry.getEnd(), implicitCatchStartLbl, null);
        	
            Catch nextCatch = entityTry.getCatchEntity();
            while(nextCatch != null){
            	mb.addTryCatchInfo(nextCatch.catchLbl, endCatchLbl1, implicitCatchStartLbl, null);
            	nextCatch = nextCatch.getNextCatch();
            }
        }
        
    }
    
    @Override
    public final void generateInsn() {
    	
        Label exceptionLbl = new Label();
        LocalVariable lv = getLocalVariableModel("e", exception);
        new Store(getExecuteBlock(), lv);
        
        lv.getScopeLogicVar().setSpecifiedStartLabel(exceptionLbl);
        new Marker(getExecuteBlock(), exceptionLbl);
        
        body(lv);
        
        new Marker(getExecuteBlock(), endCatchLbl1);
        new NOP(getExecuteBlock());
        
        Finally finallyOfCurrentTrySeries = getFinally();
        
        
        if(finallyOfCurrentTrySeries != null){
            new Marker(getExecuteBlock(), endCatchLbl2);
            try{
            	//空操作 判读程序是否可以继续执行下去
        	    OperatorFactory.newOperator(NoneOperator.class, new Class<?>[]{ProgramBlock.class}, getExecuteBlock());
        	    //如果程序能够走到这里 表示之前没有return或者throw操作，则将finally内容copy至当前catch块的末尾
        	    finallyBlock.clonerGenerate(getExecuteBlock());
            }catch(UnreachableCode uc){
                log.debug(uc.getMessage());
            }catch(RuntimeException e){
            	throw e;
            }
        }
        
        try{
        	//空操作 判读程序是否可以继续执行下去
            OperatorFactory.newOperator(NoneOperator.class, new Class<?>[]{ProgramBlock.class}, getExecuteBlock());
            //如果程序能够走到这里 表示之前没有return或者throw操作，跳转到finally block的起始位置
            new GOTO(this.getExecuteBlock(), getTerminalEndLabelInCatch()); 
        }catch(UnreachableCode uc){
            log.debug("unreachable code");	
        }catch(RuntimeException e){
        	throw e;
        }
        
        //the finally block of current block
        Finally finallyOfCurrentCatch = finallyBlock;
        //if finally block is not null, indicate this is a last block
        if(finallyOfCurrentCatch != null){
            /*generate a hidden catch block for other uncatch exception and the block code is same to finally block */
            generateThrowableCatch();
        }
    }
    
    
    /**
     *  generate a hidden catch block for other uncatch exception and the block code is same to finally block 
     *  
     */
    private void generateThrowableCatch(){
        boolean currentCheckUnrechableCode = getExecuteBlock().isNeedCheckUnreachableCode();
        getExecuteBlock().setNeedCheckUnreachableCode(false);
        
        new Marker(this.getExecuteBlock(), implicitCatchStartLbl);
        
        LocalVariable exception = getLocalAnonymousVariableModel(AClass.THROWABLE_ACLASS);
        implicitCatchThrowableStore = new Store(getExecuteBlock(), exception);
        
        finallyBlock.clonerGenerate(getExecuteBlock());
        //throwException(exception);
        OperatorFactory.newOperator(Throw.class, 
                new Class<?>[]{ProgramBlock.class, Parameterized.class, boolean.class}, getExecuteBlock(), exception, true);
        
        getExecuteBlock().setNeedCheckUnreachableCode(currentCheckUnrechableCode);
    }
    
    void setEntityTry(Try t){
        this.entityTry = t;
    }

	/**
     * 判断当前catch块或者之后的catch块是否已经catch过了异常或者catch过了其父类
     * @param exception
     * @return
     */
    public boolean checkCatch(AClass excep){
        if(excep.isChildOrEqual(exception)){
            return true;
        }else{
            if(nextCatch != null){
                return nextCatch.checkCatch(excep);
            }
        }
        return false;
    }
    
    /**
     * 
     * @param ca
     * @return
     */
    public Catch catchException(Catch ca){
    	
        if(this.finallyBlock != null){
            throw new ASMSupportException("cannot declare catch after finally block");
        }
        
        if(entityTry.checkCatchBlockException(ca.exception)){
            throw new ASMSupportException("the exception type " + ca.exception + " has been catch by previously catch block");
        }

        entityTry.addCatchedException(ca.getException());
        /*entityTry.getCatchedExceptions().add(ca.getException());
    	entityTry.removeException(ca.getException());*/
        
        this.nextCatch = ca;
        //执行队列添加ca
        parentExes.add(ca);
        
        //设置ca的父执行队列
        ca.setParentExes(parentExes);
        //设置当前catch对应的try
        ca.setEntityTry(this.entityTry);
        ca.setPrevious(this);
        
        subBlockPrepare(ca, getOwnerBlock());
        
        return ca;
    }
    
    public Finally finallyThan(Finally fly){
        if(this.nextCatch != null){
            throw new ASMSupportException("cannot declare finally block before catch");
        }
        setFinallyBlock(fly);
        fly.setPrevious(this);

        subBlockPrepare(fly, getOwnerBlock());
        
        parentExes.add(fly);
        
        return fly;
    }
    
    @Override
	public void setReturned(boolean returned) {
		super.setReturned(returned);
		
    	boolean superReturned = true;
    	SeriesBlock previous = getPrevious();
    	while(previous != null){
    		if(!previous.isReturned()){
    			superReturned = false;
    			break;
    		}
    		previous = previous.getPrevious();
    	}
    	if(superReturned){
    		getOwnerBlock().setReturned(returned);
    	}
    	
	}

	/**
     * get finally block
     * @return
     */
    public Finally getFinally(){
        if(finallyBlock != null){
            return finallyBlock;
        }else{
            if(nextCatch != null){
                return nextCatch.getFinally();
            }
        }
        return null;
    }
    
    /**
     * 设置父类
     * @param parentExes
     */
    public void setParentExes(List<Executable> parentExes) {
        this.parentExes = parentExes;
    }
    
    /**
     * 获取try catch系列的结束的位置Label，此方法不同于getTerminalEndLabel()的地方在于:
     * 如果存在finally block的时候跳转的位置。getTerminalEndLabel跳转至finally的起始位置
     * 而catch跳转至finally的结束位置。 getTerminalEndLabel方法将会在Try中使用，
     * getTerminalEndLabelInCatch在Catch中使用的。 finally程序块无论是try结束的
     * 时候还是catch结束的时候都会执行，但如果try中没有return的时候这两个程序块如何执行
     * finally程序块的方式是不同的， try采用跳转至finally的起始位置的方式， 而catch则是将finally
     * 中的指令复制到catch内的指令的最后(如果是catch中存在return指令则会复制在catch中的
     * return指令之前)
     * @return
     */
    private Label getTerminalEndLabelInCatch(){
        if(nextCatch == null){
            if(finallyBlock != null){
                return finallyBlock.endLbl;
            }else {
                return endCatchLbl2;
            }
        }
        return nextCatch.getTerminalEndLabelInCatch();
    }
    
    Label getTerminalEndLabel(){
        if(nextCatch == null){
            if(finallyBlock != null){
                return finallyBlock.startLbl;
            }else {
                return endCatchLbl2;
            }
        }
        return nextCatch.getTerminalEndLabel();
    }

	public Catch getNextCatch() {
		return nextCatch;
	}

	public AClass getException() {
		return exception;
	}

	@Override
	public String toString() {
		return "Catch Block:" + super.toString();
	}
	
	
}
