package cn.wensiqun.asmsupport.block.control;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.objectweb.asm.Label;

import cn.wensiqun.asmsupport.Executable;
import cn.wensiqun.asmsupport.Parameterized;
import cn.wensiqun.asmsupport.block.ProgramBlock;
import cn.wensiqun.asmsupport.block.body.Body;
import cn.wensiqun.asmsupport.block.method.GenericMethodBody;
import cn.wensiqun.asmsupport.clazz.AClass;
import cn.wensiqun.asmsupport.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.exception.ASMSupportException;
import cn.wensiqun.asmsupport.exception.UnreachableCode;
import cn.wensiqun.asmsupport.operators.NoneOperator;
import cn.wensiqun.asmsupport.operators.Throw;
import cn.wensiqun.asmsupport.operators.asmdirect.GOTO;
import cn.wensiqun.asmsupport.operators.asmdirect.Marker;
import cn.wensiqun.asmsupport.operators.asmdirect.Store;
import cn.wensiqun.asmsupport.operators.util.OperatorFactory;
import cn.wensiqun.asmsupport.operators.util.ThrowExceptionContainer;
import cn.wensiqun.asmsupport.utils.memory.Stack;

/**
 * Try语句块
 * 
 * @author 温斯群(Joe Wen)
 *
 */
public abstract class Try extends SeriesBlock implements Body {

    private static Log log = LogFactory.getLog(Try.class);

    /**下一个catch语句块 */
    private Catch catchEntity;

    /**当前语句块的起始位置 */
    private Label start;

    /**当前语句块的其实位置 */    
    private Label end;
    
    /**当前语句块所有catch的异常集合*/
    private ThrowExceptionContainer catchedExceptions;

    /** 该程序块中所有可执行的指令 */
    private List<Executable> parentExes;
    
    /** Finally Block*/
    private Finally finallyBlock;
    
    public Try() {
        super();
        start = new Label();
        end = new Label();
        implicitCatchStartLbl = new Label();
        catchedExceptions = new ThrowExceptionContainer();
    }
    
    /**
     * 添加异常到catch的异常集合中
     * @param exception
     */
    public void addCatchedException(AClass exception){
        //添加到catch的异常
    	catchedExceptions.add(exception);
        //移除方法签名中对应的异常
        removeException(exception);
    }
    
    @Override
	public void addException(AClass exception){
    	//如果catchedExceptions不包含当前的异常,则添加到方法签名的异常中。
    	if(!catchedExceptions.contains(exception)){
    		super.addException(exception);
    	}
    }
	
	@Override
	public final void generateInsn() {
		body();
		
		boolean returnInTry = false;
        
		try{
        	/*
        	 * 创建空操作，其作用是通过newOperator方法判断是否抛出UnreachableCode异常判断当前
        	 * 操作是否可到达。如果不可到达则不执行以下指令
        	 */
            OperatorFactory.newOperator(NoneOperator.class, new Class<?>[]{ProgramBlock.class}, getExecuteBlock());
            //创建GOTO指令。跳到整个try catch的结束部分 如果存在finally 则是finally起始部分
            new GOTO(getExecuteBlock(), getTerminalEndLabel()); 
        }catch(UnreachableCode uc){
            log.debug("unreachable code");
            returnInTry = true;
        }catch(RuntimeException e){
        	throw e;
        }
        
        if(finallyBlock != null){
            generateThrowableCatch();
            if(returnInTry){
                finallyBlock.getOwnerBlock().removeExe(finallyBlock);
            }
        }
	}

	@Override
    public void executing() {
    	insnHelper.nop();
        insnHelper.mark(start);
        insnHelper.nop();
        //boolean endMarked = false;
        Stack stack = insnHelper.getMv().getStack();
        for(Executable exe : getExecuteQueue()){
            if(exe.equals(implicitCatchThrowableStore)){
                insnHelper.mark(end);
                stack.push(AClass.THROWABLE_ACLASS.getType());
            }
            exe.execute();
        }
        try{
        	end.getOffset();
        }catch(IllegalStateException e){
            insnHelper.mark(end);	
        }
    }

    @Override
    protected void init() {
    	GenericMethodBody mb = getMethodBody();
    	//如果存在try之后直接存在finally添加一个隐藏的catch块(此catch程序块将catch throwable异常)
        if(finallyBlock != null){
            mb.addTryCatchInfo(getStart(), getEnd(), implicitCatchStartLbl, null);
        }
    }
    
    /**
     * 判断是否已经catch过了异常或者catch过了其父类
     * @param exception
     * @return
     */
    public boolean checkCatchBlockException(AClass exception){
        return catchEntity.checkCatch(exception);
    }
    
    /**
     * 
     * @param ca
     * @return
     */
    public Catch catchException(Catch ca){
    	if(this.finallyBlock != null){
            throw new ASMSupportException("exist finally block. please create catch before finally block");
    	}
    	
        this.catchEntity = ca;
        ca.setEntityTry(this);
        parentExes.add(ca);
        ca.setParentExes(parentExes);
        ca.setPrevious(this);
        addCatchedException(ca.getException());
        subBlockPrepare(ca, getOwnerBlock());
        return ca;
    }
    
    /**
     * 
     * @param fly
     * @return
     */
    public Finally finallyThan(Finally fny){
    	//如果已经catch了异常则不能直接通过try创建finally块
        if(this.catchEntity != null){
            throw new ASMSupportException("has been catch exception. please create finally block by Catch");
        }
        setFinallyBlock(fny);
        fny.setPrevious(this);

        subBlockPrepare(fny, getOwnerBlock());
        
        parentExes.add(fny);
        return fny;
    }

    public void setParentExes(List<Executable> parentExes) {
        this.parentExes = parentExes;
    }
    
    public void setFinallyBlock(Finally finallyBlock) {
		this.finallyBlock = finallyBlock;
	}

    
    /**
     * 返回finally块
     * @return
     */
	public Finally getFinallyBlock(){
		if(finallyBlock != null){
			return finallyBlock;
		}
		
    	if(catchEntity != null){
    		return catchEntity.getFinally();
    	}
    	return null;
    }

	public Catch getCatchEntity() {
		return catchEntity;
	}
    
    Label getTerminalEndLabel(){
        
        if(catchEntity != null){
        	return catchEntity.getTerminalEndLabel();
        }
        
        if(finallyBlock != null){
        	return finallyBlock.startLbl;
        }
    	
        throw new ASMSupportException("insert \"Finally\"  or \"Catch\" to complete TryStatement");
        
    }
    
    private Label implicitCatchStartLbl;
    
    /**
     * 当存在finally的时候存储 会有一个隐士的catch块 catch的是Throwable的异常.
     * 此Store存储的就是Throwable异常
     */
    private Store implicitCatchThrowableStore;
    
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
        
        OperatorFactory.newOperator(UnAddExceptionThrow.class, 
                new Class<?>[]{ProgramBlock.class, Parameterized.class}, getExecuteBlock(), exception);
        
        getExecuteBlock().setNeedCheckUnreachableCode(currentCheckUnrechableCode);
    }
    
    /**
     * throw操作
     * 区别于Throw的是，当创建隐式的catch块的时候，不将该catch捕获的Throwable类型的异常添加到方法签名中
     * 即方法上向上添加异常抛出声明
     * @author 
     *
     */
    public static class UnAddExceptionThrow extends Throw {

		public UnAddExceptionThrow(ProgramBlock block,
				Parameterized exception) {
			super(block, exception);
		}

		@Override
		protected void beforeInitProperties() {
		    //don't add exception
		}
    }
    
    
    
    public Label getStart() {
		return start;
	}

	public Label getEnd() {
		return end;
	}
	
	public ThrowExceptionContainer getCatchedExceptions() {
		return catchedExceptions;
	}

	@Override
	public String toString() {
		return "Try Block:" + super.toString();
	}
}
