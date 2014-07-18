/**
 * 
 */
package cn.wensiqun.asmsupport.block;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.objectweb.asm.Label;
import org.objectweb.asm.Type;

import cn.wensiqun.asmsupport.AbstractExecuteable;
import cn.wensiqun.asmsupport.Crementable;
import cn.wensiqun.asmsupport.Executable;
import cn.wensiqun.asmsupport.Parameterized;
import cn.wensiqun.asmsupport.asm.InstructionHelper;
import cn.wensiqun.asmsupport.block.control.Catch;
import cn.wensiqun.asmsupport.block.control.DoWhileLoop;
import cn.wensiqun.asmsupport.block.control.Finally;
import cn.wensiqun.asmsupport.block.control.ForEachLoop;
import cn.wensiqun.asmsupport.block.control.IF;
import cn.wensiqun.asmsupport.block.control.ILoop;
import cn.wensiqun.asmsupport.block.control.Try;
import cn.wensiqun.asmsupport.block.control.WhileLoop;
import cn.wensiqun.asmsupport.block.method.GenericMethodBody;
import cn.wensiqun.asmsupport.clazz.AClass;
import cn.wensiqun.asmsupport.clazz.ArrayClass;
import cn.wensiqun.asmsupport.clazz.NewMemberClass;
import cn.wensiqun.asmsupport.definition.method.AMethod;
import cn.wensiqun.asmsupport.definition.variable.ExplicitVariable;
import cn.wensiqun.asmsupport.definition.variable.GlobalVariable;
import cn.wensiqun.asmsupport.definition.variable.IVariable;
import cn.wensiqun.asmsupport.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.definition.variable.SuperVariable;
import cn.wensiqun.asmsupport.definition.variable.ThisVariable;
import cn.wensiqun.asmsupport.definition.variable.meta.LocalVariableMeta;
import cn.wensiqun.asmsupport.exception.ASMSupportException;
import cn.wensiqun.asmsupport.exception.MethodInvokeException;
import cn.wensiqun.asmsupport.exception.UnreachableCode;
import cn.wensiqun.asmsupport.exception.VerifyErrorException;
import cn.wensiqun.asmsupport.operators.BlockEndFlag;
import cn.wensiqun.asmsupport.operators.InstanceofOperator;
import cn.wensiqun.asmsupport.operators.NoneOperator;
import cn.wensiqun.asmsupport.operators.Return;
import cn.wensiqun.asmsupport.operators.StringAppender;
import cn.wensiqun.asmsupport.operators.Throw;
import cn.wensiqun.asmsupport.operators.array.ArrayLength;
import cn.wensiqun.asmsupport.operators.array.ArrayLoader;
import cn.wensiqun.asmsupport.operators.array.ArrayStorer;
import cn.wensiqun.asmsupport.operators.array.ArrayValue;
import cn.wensiqun.asmsupport.operators.asmdirect.GOTO;
import cn.wensiqun.asmsupport.operators.assign.Assigner;
import cn.wensiqun.asmsupport.operators.assign.GlobalVariableAssigner;
import cn.wensiqun.asmsupport.operators.assign.LocalVariableAssigner;
import cn.wensiqun.asmsupport.operators.checkcast.CheckCast;
import cn.wensiqun.asmsupport.operators.logical.LogicalAnd;
import cn.wensiqun.asmsupport.operators.logical.LogicalOr;
import cn.wensiqun.asmsupport.operators.logical.LogicalXor;
import cn.wensiqun.asmsupport.operators.logical.Not;
import cn.wensiqun.asmsupport.operators.logical.ShortCircuitAnd;
import cn.wensiqun.asmsupport.operators.logical.ShortCircuitOr;
import cn.wensiqun.asmsupport.operators.method.CommonMethodInvoker;
import cn.wensiqun.asmsupport.operators.method.ConstructorInvoker;
import cn.wensiqun.asmsupport.operators.method.MethodInvoker;
import cn.wensiqun.asmsupport.operators.method.StaticMethodInvoker;
import cn.wensiqun.asmsupport.operators.numerical.arithmetic.Addition;
import cn.wensiqun.asmsupport.operators.numerical.arithmetic.Division;
import cn.wensiqun.asmsupport.operators.numerical.arithmetic.Modulus;
import cn.wensiqun.asmsupport.operators.numerical.arithmetic.Multiplication;
import cn.wensiqun.asmsupport.operators.numerical.arithmetic.Subtraction;
import cn.wensiqun.asmsupport.operators.numerical.bitwise.BitAnd;
import cn.wensiqun.asmsupport.operators.numerical.bitwise.BitOr;
import cn.wensiqun.asmsupport.operators.numerical.bitwise.BitXor;
import cn.wensiqun.asmsupport.operators.numerical.bitwise.Inverts;
import cn.wensiqun.asmsupport.operators.numerical.bitwise.LeftShift;
import cn.wensiqun.asmsupport.operators.numerical.bitwise.RightShift;
import cn.wensiqun.asmsupport.operators.numerical.bitwise.UnsignedRightShift;
import cn.wensiqun.asmsupport.operators.numerical.crement.AfterDecrement;
import cn.wensiqun.asmsupport.operators.numerical.crement.AfterIncrement;
import cn.wensiqun.asmsupport.operators.numerical.crement.BeforeDecrement;
import cn.wensiqun.asmsupport.operators.numerical.crement.BeforeIncrement;
import cn.wensiqun.asmsupport.operators.numerical.posinegative.Negative;
import cn.wensiqun.asmsupport.operators.relational.Equal;
import cn.wensiqun.asmsupport.operators.relational.GreaterEqual;
import cn.wensiqun.asmsupport.operators.relational.GreaterThan;
import cn.wensiqun.asmsupport.operators.relational.LessEqual;
import cn.wensiqun.asmsupport.operators.relational.LessThan;
import cn.wensiqun.asmsupport.operators.relational.NotEqual;
import cn.wensiqun.asmsupport.operators.ternary.TernaryOperator;
import cn.wensiqun.asmsupport.operators.util.OperatorFactory;
import cn.wensiqun.asmsupport.operators.util.ThrowExceptionContainer;
import cn.wensiqun.asmsupport.operators.variable.LocalVariableCreator;
import cn.wensiqun.asmsupport.utils.ASConstant;
import cn.wensiqun.asmsupport.utils.memory.Scope;
import cn.wensiqun.asmsupport.utils.memory.ScopeLogicVariable;

/**
 * 
 * 
 * 
 * @author wensiqun(at)gmail
 * 
 */
public abstract class ProgramBlock extends AbstractExecuteable implements IBlockOperators, Cloneable  {

    private static Log log = LogFactory.getLog(ProgramBlock.class);

    /**执行Block, 通过当前Block所创建的操作，实际是executeBlock的代理*/
	private   ProgramBlock                 executeBlock = this;
    protected Scope                        scope;
    private   Label                        start;
    private   Label                        end;
    /** 属于哪个block。父block */
    protected ProgramBlock                 ownerBlock;
    /** 该程序块中所有可执行的指令 */
    private   List<Executable>             executeQueue;
    protected InstructionHelper            insnHelper;
    protected AMethod                      method;
    /** 当前block是否已经返回 或者已经抛出异常了 */
    protected boolean                      returned;
    /** 是否需要检测UnreachableCode, 即在创建一个操作的时候是否需要检测程序能够成功的运行到该操作 */
    private   boolean                      needCheckUnreachableCode = true;
    private   ThrowExceptionContainer      throwExceptions;

    /******************* Getter Setter ************************/
    
    public void setExecuteBlock(ProgramBlock exeBlock) {
        this.executeBlock = exeBlock;
    }
    
    protected ProgramBlock getExecuteBlock(){
        return executeBlock;
    }
    
    public ThrowExceptionContainer getThrowExceptions() {
		return throwExceptions;
	}
    
    

    /******************* Getter Setter ************************/
    
    /**
     * 添加抛出的异常到方法签名中
     * @param exception
     */
	public void addException(AClass exception){
		if(throwExceptions == null){
			throwExceptions = new ThrowExceptionContainer();
		}
		throwExceptions.add(exception);
    }
    
    public void removeException(AClass exception){
    	if(throwExceptions != null){
        	throwExceptions.remove(exception);
    	}
    }
    
	public boolean isNeedCheckUnreachableCode() {
		return needCheckUnreachableCode;
	}

	public void setNeedCheckUnreachableCode(boolean whetherCheckUnreachableCode) {
		this.needCheckUnreachableCode = whetherCheckUnreachableCode;
	}

	@Override
	public boolean equals(Object obj) {
    	if(this == obj){
    		return true;
    	}
    	
    	if(obj instanceof ProgramBlock){
    		return this.scope == ((ProgramBlock)obj).scope;
    	}
		return false;
	}

	public boolean isReturned() {
		return returned;
	}
    
	public void setReturned(boolean returned){
		if(this.returned && returned == false){
		}else{
			this.returned = returned;
		}
	}

    public List<Executable> getExecuteQueue(){
    	return this.executeQueue;
    }
    
    protected abstract void init();

    /**
     * 获取当前程序块的克隆拷贝
     * @return
     */
    public ProgramBlock getCopy(){
        try {
            return (ProgramBlock) clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            throw new ASMSupportException();
        }
    }
    
    /**
     * 克隆当前的程序块的执行队列到给定程序块执行队列中
     * @param owner 克隆至此
     */
	public void clonerGenerate(ProgramBlock cloneTo){
        ProgramBlock clone = getCopy();
        clone.setExecuteBlock(cloneTo);
        clone.generateInsn();
	}
    
    /**
     * create a logic program block
     * 
     * @param mv
     * @param scope
     * @param parent
     */
    protected ProgramBlock() {
    	executeQueue = new ArrayList<Executable>();
    	start = new Label();
        end = new Label();
    }

    /**
     * override this method if want create a new block
     * 生成操作到执行队列中去。
     */
    public abstract void generateInsn();
    
    @Override
    public void prepare() {
        init();
        generateInsn();
        new BlockEndFlag(getExecuteBlock());
    }
    
    protected void subBlockPrepare(ProgramBlock pb){
    	subBlockPrepare(pb, this);
    }
    
    /**
     * 通常情况下的prepare
     * 
     * @param pb
     * @param parentBlock
     */
    protected void subBlockPrepare(ProgramBlock pb, ProgramBlock parentBlock){
    	pb.setInsnHelper(insnHelper);
    	pb.setScope(new Scope(method.getLocals(), parentBlock.getScope()));
    	//设置父类的Block
    	pb.setOwnerBlock(parentBlock.getExecuteBlock());
    	if(pb instanceof Try || pb instanceof Catch || pb instanceof Finally){
    		if(pb instanceof Try){
    			this.getMethod().setNearlyTryBlock((Try)pb);
    		}
    	}else{
    		tiggerTryCatchPrepare();
        	pb.prepare();
    	}
        new BlockEndFlag(pb);
    }
    
    public void tiggerTryCatchPrepare(){
    	Try nearlyTryBlock = getMethod().getNearlyTryBlock();
    	//获取离当操作前最近try程序块
		if(nearlyTryBlock != null){
            
			getMethod().setNearlyTryBlock(null);
			//try prepare
			
			nearlyTryBlock.prepare();
			
			//catch prepare
			Catch catchBlock = nearlyTryBlock.getCatchEntity();
			
			Finally finallyBlock = nearlyTryBlock.getFinallyBlock();
			
			while(catchBlock != null){
				catchBlock.prepare();
				catchBlock = catchBlock.getNextCatch();
			}
			
			if(finallyBlock != null){
		        try{
		            OperatorFactory.newOperator(NoneOperator.class, new Class<?>[]{ProgramBlock.class}, getExecuteBlock());
					finallyBlock.prepare();
		        }catch(UnreachableCode uc){
		            log.debug("unreachable code");	
		        }catch(RuntimeException e){
		        	throw e;
		        }
			}
			
		}
    }
    
    @Override
    public final void execute() {
        insnHelper.mark(getScope().getStart());
        insnHelper.nop();
        executing();
        insnHelper.mark(getScope().getEnd());
        insnHelper.nop();
    }
    
    public abstract void executing();

    /**
     * 添加一个Executeable
     * 
     * @param exe
     */
    public void addExe(Executable exe) {
        getExecuteQueue().add(exe);
    }
    
    public void addAllExe(int index, List<Executable> exes) {
        getExecuteQueue().addAll(index, exes);
    }
    
    /**
     * 
     * @param exe
     */
    public void removeExe(Executable exe) {
        for (int i = getExecuteQueue().size() - 1; i >= 0; i--) {
            if (getExecuteQueue().get(i).equals(exe)) {
                getExecuteQueue().remove(i);
                break;
            }
        }
    }

    /**
     * 替换
     * @param old
     * @param newp
     */
    public void replaceExe(Executable old, Executable newp){
        for (int i = getExecuteQueue().size() - 1; i >= 0; i--) {
            if (getExecuteQueue().get(i).equals(old)) {
                getExecuteQueue().remove(i);
                getExecuteQueue().add(i, newp);
                break;
            }
        }
    }

    public void setScope(Scope scope) {
        this.scope = scope;
    }

    public Scope getScope() {
        return this.scope;
    }
    
    public void setInsnHelper(InstructionHelper insnHelper) {
        this.insnHelper = insnHelper;
        this.method = insnHelper.getMethod();
    }

    public void setOwnerBlock(ProgramBlock block) {
        this.ownerBlock = block;
    }

    public ProgramBlock getOwnerBlock() {
        return this.ownerBlock;
    }
    
    public AMethod getMethod() {
        return method;
    }
    
    public LocalVariable[] getMethodArguments(){
    	return method.getArguments();
    }
    
    protected GenericMethodBody getMethodBody(){
        if(this instanceof GenericMethodBody){
            return (GenericMethodBody) this;
        }
        return ownerBlock.getMethodBody();
    }
    
    /**
     * get current method owner.
     * 
     * @return
     */
    public NewMemberClass getMethodOwner() {
        return method.getMethodOwner();
    }

    public InstructionHelper getInsnHelper() {
        return insnHelper;
    }
    
    //*******************************************************************************************//
    //                                Variable Operator                                          //
    //*******************************************************************************************//
	private final LocalVariable createOnlyVariable(final AClass aClass, final String name, boolean anonymous){
		if(!anonymous && StringUtils.isBlank(name)){
			throw new IllegalArgumentException("variable must be non-null if 'anonymous' is false");
		}
		LocalVariableMeta lve = new LocalVariableMeta(anonymous ? "anonymous" : name, 0, aClass);
        LocalVariableCreator lvc = OperatorFactory.newOperator(LocalVariableCreator.class, 
        		new Class<?>[]{ProgramBlock.class, String.class, Type.class, Type.class}, 
        		getExecuteBlock(), anonymous ? null : name, aClass.getType(), aClass.getType());
        ScopeLogicVariable slv = lvc.getScopeLogicVariable();
        slv.setCompileOrder(insnHelper.getMethod().nextInsNumber());
        LocalVariable lv = new LocalVariable(lve);
        lv.setScopeLogicVar(slv);
        return lv;
	}

    protected final LocalVariable getLocalAnonymousVariableModel(final AClass aClass){
    	return createOnlyVariable(aClass, "anonymous", true);
    }
    
    protected final LocalVariable getLocalVariableModel(final String name, final AClass aClass){
        return createOnlyVariable(aClass, name, false);
    }

	@Override
    public final LocalVariable createVariable(final String name, final AClass aClass, boolean anonymous, final Parameterized para) {
		if(aClass.isArray()){
			throw new IllegalArgumentException(aClass + " is Array type exchange to createArrayVariable to create the array variable");
		}
        LocalVariable lv = createOnlyVariable(aClass, name, anonymous);
        if(para == null){
            assign(lv, aClass.getDefaultValue());
        }else{
            assign(lv, para);
        }
        return lv;
    }
	
    @Override
    public final LocalVariable createArrayVariableWithAllocateDimension(final String name, final ArrayClass aClass, boolean anonymous, Parameterized... allocateDim) {
        LocalVariable lv = createOnlyVariable(aClass, name, anonymous);
        if(allocateDim == null){
            assign(lv, aClass.getDefaultValue());
        }else{
            assign(lv, newArray(aClass, allocateDim));
        }
        return lv;
    }
    
    @Override
	public final LocalVariable createArrayVariable(final String name, final ArrayClass aClass, boolean anonymous, Parameterized value) {
        LocalVariable lv = createOnlyVariable(aClass, name, anonymous);
        if(value == null){
            assign(lv, aClass.getDefaultValue());
        }else{
            assign(lv, value);
        }
        return lv;
	}

	@Override
	public LocalVariable createArrayVariable(String name, ArrayClass aClass, boolean anonymous, Object parameterizedArray) {
        LocalVariable lv = createOnlyVariable(aClass, name, anonymous);
        if(parameterizedArray == null){
            assign(lv, aClass.getDefaultValue());
        }else{
            assign(lv, getExecuteBlock().newArrayWithValue(aClass, parameterizedArray));
        }
        return lv;
	}
    
    
    @Override
    public final Assigner assign(ExplicitVariable variable, Parameterized val){
        if(variable instanceof LocalVariable){
        	return OperatorFactory.newOperator(LocalVariableAssigner.class,
	    			new Class<?>[]{ProgramBlock.class, LocalVariable.class, Parameterized.class}, 
	    			getExecuteBlock(), (LocalVariable) variable, val);
        }else if(variable instanceof GlobalVariable){
        	return OperatorFactory.newOperator(GlobalVariableAssigner.class,
        	    			new Class<?>[]{ProgramBlock.class, GlobalVariable.class, Parameterized.class}, 
        	    			getExecuteBlock(), (GlobalVariable) variable, val);
        }
        return null;
    }
    
    //*******************************************************************************************//
    //                                Value Operator                                             //
    //*******************************************************************************************//
    
    @Override
    public final ArrayValue newArray(final ArrayClass aClass, final Parameterized... allocateDims){
    	return OperatorFactory.newOperator(ArrayValue.class, 
    			new Class<?>[]{ProgramBlock.class, ArrayClass.class, Parameterized[].class}, 
    			getExecuteBlock(), aClass, allocateDims);
    }
    
    @Override
    public final ArrayValue newArrayWithValue(final ArrayClass aClass, final Object arrayObject){
    	return OperatorFactory.newOperator(ArrayValue.class, 
    			new Class<?>[]{ProgramBlock.class, ArrayClass.class, Object.class}, 
    			getExecuteBlock(), aClass, arrayObject);
    }

    @Override
    public final ArrayValue newArrayWithValue(final ArrayClass aClass, final Parameterized[] values){
    	return newArrayWithValue(aClass, (Object)values);
    }

    @Override
    public final ArrayValue newArrayWithValue(final ArrayClass aClass, final Parameterized[][] values){
    	return newArrayWithValue(aClass, (Object)values);
    }

    @Override
    public final ArrayValue newArrayWithValue(final ArrayClass aClass, final Parameterized[][][] values){
    	return newArrayWithValue(aClass, (Object)values);
    }

    @Override
    public final ArrayValue newArrayWithValue(final ArrayClass aClass, final Parameterized[][][][] values){
    	return newArrayWithValue(aClass, (Object)values);
    }
    
    
    //*******************************************************************************************//
    //                                Array Operator                                             //
    //*******************************************************************************************//  
    
    @Override
    public final ArrayLoader arrayLoad(IVariable arrayReference, Parameterized pardim, Parameterized... parDims){
        return OperatorFactory.newOperator(ArrayLoader.class, 
        		new Class<?>[]{ProgramBlock.class, Parameterized.class, Parameterized.class, Parameterized[].class},
        		getExecuteBlock(), arrayReference, pardim, parDims);
    }
    
    @Override
    public final ArrayLoader arrayLoad(MethodInvoker arrayReference, Parameterized pardim, Parameterized... parDims){
        return OperatorFactory.newOperator(ArrayLoader.class, 
        		new Class<?>[]{ProgramBlock.class, Parameterized.class, Parameterized.class, Parameterized[].class},
        		getExecuteBlock(), arrayReference, pardim, parDims);
    }
    
    @Override
	public ArrayLoader arrayLoad(ArrayLoader arrayReference, Parameterized pardim, Parameterized... parDims) {
        return OperatorFactory.newOperator(ArrayLoader.class, 
        		new Class<?>[]{ProgramBlock.class, Parameterized.class, Parameterized.class, Parameterized[].class},
        		getExecuteBlock(), arrayReference, pardim, parDims);
	}

	@Override
	public ArrayLoader arrayLoad(ArrayValue arrayReference, Parameterized pardim, Parameterized... parDims) {
        return OperatorFactory.newOperator(ArrayLoader.class, 
        		new Class<?>[]{ProgramBlock.class, Parameterized.class, Parameterized.class, Parameterized[].class},
        		getExecuteBlock(), arrayReference, pardim, parDims);
	}

	@Override
	public ArrayLoader arrayLoad(Assigner arrayReference, Parameterized pardim, Parameterized... parDims) {
        return OperatorFactory.newOperator(ArrayLoader.class, 
        		new Class<?>[]{ProgramBlock.class, Parameterized.class, Parameterized.class, Parameterized[].class},
        		getExecuteBlock(), arrayReference, pardim, parDims);
	}

	@Override
    public final ArrayStorer arrayStore(IVariable arrayReference, Parameterized value,
            Parameterized dim, Parameterized... dims) {
        return OperatorFactory.newOperator(ArrayStorer.class, 
        		new Class<?>[]{ProgramBlock.class, Parameterized.class, Parameterized.class, Parameterized.class, Parameterized[].class},
        		getExecuteBlock(), arrayReference, value, dim, dims);
    }

    @Override
    public final  ArrayStorer arrayStore(MethodInvoker arrayReference, Parameterized value,
            Parameterized dim, Parameterized... dims) {
        return OperatorFactory.newOperator(ArrayStorer.class, 
        		new Class<?>[]{ProgramBlock.class, Parameterized.class, Parameterized.class, Parameterized.class, Parameterized[].class},
        		getExecuteBlock(), arrayReference, value, dim, dims);
    }

    
    @Override
	public ArrayStorer arrayStore(ArrayLoader arrayReference, Parameterized value, Parameterized dim, Parameterized... dims) {
        return OperatorFactory.newOperator(ArrayStorer.class, 
        		new Class<?>[]{ProgramBlock.class, Parameterized.class, Parameterized.class, Parameterized.class, Parameterized[].class},
        		getExecuteBlock(), arrayReference, value, dim, dims);
	}

	@Override
	public ArrayStorer arrayStore(ArrayValue arrayReference, Parameterized value, Parameterized dim, Parameterized... dims) {
        return OperatorFactory.newOperator(ArrayStorer.class, 
        		new Class<?>[]{ProgramBlock.class, Parameterized.class, Parameterized.class, Parameterized.class, Parameterized[].class},
        		getExecuteBlock(), arrayReference, value, dim, dims);
	}

	@Override
	public ArrayStorer arrayStore(Assigner arrayReference, Parameterized value, Parameterized dim, Parameterized... dims) {
        return OperatorFactory.newOperator(ArrayStorer.class, 
        		new Class<?>[]{ProgramBlock.class, Parameterized.class, Parameterized.class, Parameterized.class, Parameterized[].class},
        		getExecuteBlock(), arrayReference, value, dim, dims);
	}

	@Override
    public final ArrayLength arrayLength(IVariable arrayReference, Parameterized... dims) {
        return OperatorFactory.newOperator(ArrayLength.class, 
        		new Class<?>[]{ProgramBlock.class, Parameterized.class, Parameterized[].class}, 
        		getExecuteBlock(), arrayReference, dims);
    }


	@Override
	public ArrayLength arrayLength(MethodInvoker arrayReference, Parameterized... dims) {
        return OperatorFactory.newOperator(ArrayLength.class, 
        		new Class<?>[]{ProgramBlock.class, Parameterized.class, Parameterized[].class}, 
        		getExecuteBlock(), arrayReference, dims);
	}

	@Override
	public ArrayLength arrayLength(ArrayLoader arrayReference, Parameterized... dims) {
        return OperatorFactory.newOperator(ArrayLength.class, 
        		new Class<?>[]{ProgramBlock.class, Parameterized.class, Parameterized[].class}, 
        		getExecuteBlock(), arrayReference, dims);
	}

	@Override
	public ArrayLength arrayLength(ArrayValue arrayReference, Parameterized... dims) {
        return OperatorFactory.newOperator(ArrayLength.class, 
        		new Class<?>[]{ProgramBlock.class, Parameterized.class, Parameterized[].class}, 
        		getExecuteBlock(), arrayReference, dims);
	}

	@Override
	public ArrayLength arrayLength(Assigner arrayReference, Parameterized... dims) {
        return OperatorFactory.newOperator(ArrayLength.class, 
        		new Class<?>[]{ProgramBlock.class, Parameterized.class, Parameterized[].class}, 
        		getExecuteBlock(), arrayReference, dims);
	}	
    
    //*******************************************************************************************//
    //                                 Check Cast                                                //
    //*******************************************************************************************//


	@Override
    public final CheckCast checkCast(Parameterized cc, AClass to){
        if(to.isPrimitive()){
    		    throw new IllegalArgumentException("cannot cache cast to type : " + to);
    	  }
        return OperatorFactory.newOperator(CheckCast.class, 
        		new Class<?>[]{ProgramBlock.class, Parameterized.class, AClass.class}, 
        		getExecuteBlock(), cc, to);
        //return new CheckCast(getExecuteBlock(), cc, to);
    }
    
    //*******************************************************************************************//
    //                                Arithmetic Operator                                        //
    //*******************************************************************************************//

    @Override
    public final Addition add(Parameterized factor1, Parameterized factor2){
        return OperatorFactory.newOperator(Addition.class, 
        		new Class<?>[]{ProgramBlock.class, Parameterized.class, Parameterized.class},
        		getExecuteBlock(), factor1, factor2);
    }

    @Override
    public final Subtraction sub(Parameterized factor1, Parameterized factor2){
        return OperatorFactory.newOperator(Subtraction.class, 
        		new Class<?>[]{ProgramBlock.class, Parameterized.class, Parameterized.class},
        		getExecuteBlock(), factor1, factor2);
    }

    @Override
    public final Multiplication mul(Parameterized factor1, Parameterized factor2){
        return OperatorFactory.newOperator(Multiplication.class, 
        		new Class<?>[]{ProgramBlock.class, Parameterized.class, Parameterized.class},
        		getExecuteBlock(), factor1, factor2);
    }

    @Override
    public final Division div(Parameterized factor1, Parameterized factor2){
        return OperatorFactory.newOperator(Division.class, 
        		new Class<?>[]{ProgramBlock.class, Parameterized.class, Parameterized.class},
        		getExecuteBlock(), factor1, factor2);
    }

    @Override
    public final Modulus mod(Parameterized factor1, Parameterized factor2){
        return OperatorFactory.newOperator(Modulus.class, 
        		new Class<?>[]{ProgramBlock.class, Parameterized.class, Parameterized.class},
        		getExecuteBlock(), factor1, factor2);
    }

    @Override
    public final Negative neg(Parameterized factor){
        return OperatorFactory.newOperator(Negative.class, 
        		new Class<?>[]{ProgramBlock.class, Parameterized.class},
        		getExecuteBlock(), factor);
    }
    
    //*******************************************************************************************//
    //                                       Bit Operator                                        //
    //*******************************************************************************************//

    @Override
    public final Inverts inverts(Parameterized factor){
        return OperatorFactory.newOperator(Inverts.class, 
        		new Class<?>[]{ProgramBlock.class, Parameterized.class},
        		getExecuteBlock(), factor);
    }

    @Override
    public final BitAnd bitAnd(Parameterized factor1, Parameterized factor2){
        return OperatorFactory.newOperator(BitAnd.class, 
        		new Class<?>[]{ProgramBlock.class, Parameterized.class, Parameterized.class},
        		getExecuteBlock(), factor1, factor2);
    } 

    @Override
    public final BitOr bitOr(Parameterized factor1, Parameterized factor2){
        return OperatorFactory.newOperator(BitOr.class, 
        		new Class<?>[]{ProgramBlock.class, Parameterized.class, Parameterized.class},
        		getExecuteBlock(), factor1, factor2);
    } 

    @Override
    public final BitXor bitXor(Parameterized factor1, Parameterized factor2){
        return OperatorFactory.newOperator(BitXor.class, 
        		new Class<?>[]{ProgramBlock.class, Parameterized.class, Parameterized.class},
        		getExecuteBlock(), factor1, factor2);
    } 

    @Override
    public final LeftShift leftShift(Parameterized factor1, Parameterized factor2){
        return OperatorFactory.newOperator(LeftShift.class, 
        		new Class<?>[]{ProgramBlock.class, Parameterized.class, Parameterized.class},
        		getExecuteBlock(), factor1, factor2);
    }
    
    public final RightShift rightShift(Parameterized factor1, Parameterized factor2){
        return OperatorFactory.newOperator(RightShift.class, 
        		new Class<?>[]{ProgramBlock.class, Parameterized.class, Parameterized.class},
        		getExecuteBlock(), factor1, factor2);
    }

    @Override
    public final UnsignedRightShift unsignedRightShift(Parameterized factor1, Parameterized factor2){
        return OperatorFactory.newOperator(UnsignedRightShift.class, 
        		new Class<?>[]{ProgramBlock.class, Parameterized.class, Parameterized.class},
        		getExecuteBlock(), factor1, factor2);
    }
    
    //*******************************************************************************************//
    //                    Decrement or Increment Operator                                        //
    //*******************************************************************************************//

    @Override
    public final BeforeDecrement beforeDec(Crementable crement){
        return OperatorFactory.newOperator(BeforeDecrement.class, 
        		new Class<?>[]{ProgramBlock.class, Crementable.class},
        		getExecuteBlock(), crement);
    }

    @Override
    public final AfterDecrement afterDec(Crementable crement){
        return OperatorFactory.newOperator(AfterDecrement.class, 
        		new Class<?>[]{ProgramBlock.class, Crementable.class},
        		getExecuteBlock(), crement);
    }

    @Override
    public final BeforeIncrement beforeInc(Crementable crement){
        return OperatorFactory.newOperator(BeforeIncrement.class, 
        		new Class<?>[]{ProgramBlock.class, Crementable.class},
        		getExecuteBlock(), crement);
    }

    @Override
    public final AfterIncrement afterInc(Crementable crement){
        return OperatorFactory.newOperator(AfterIncrement.class, 
        		new Class<?>[]{ProgramBlock.class, Crementable.class},
        		getExecuteBlock(), crement);
    }

    //*******************************************************************************************//
    //                            Relational Operator                                            //
    //*******************************************************************************************//

    @Override
    public final GreaterThan greaterThan(Parameterized factor1, Parameterized factor2){
        return OperatorFactory.newOperator(GreaterThan.class, 
        		new Class<?>[]{ProgramBlock.class, Parameterized.class, Parameterized.class},
        		getExecuteBlock(), factor1, factor2);
    }

    @Override
    public final GreaterEqual greaterEqual(Parameterized factor1, Parameterized factor2){
        return OperatorFactory.newOperator(GreaterEqual.class, 
        		new Class<?>[]{ProgramBlock.class, Parameterized.class, Parameterized.class},
        		getExecuteBlock(), factor1, factor2);
    }

    @Override
    public final LessThan lessThan(Parameterized factor1, Parameterized factor2){
        return OperatorFactory.newOperator(LessThan.class, 
        		new Class<?>[]{ProgramBlock.class, Parameterized.class, Parameterized.class},
        		getExecuteBlock(), factor1, factor2);
    }

    @Override
    public final LessEqual lessEqual(Parameterized factor1, Parameterized factor2){
        return OperatorFactory.newOperator(LessEqual.class, 
        		new Class<?>[]{ProgramBlock.class, Parameterized.class, Parameterized.class},
        		getExecuteBlock(), factor1, factor2);
    }

    @Override
    public final Equal equal(Parameterized factor1, Parameterized factor2){
        return OperatorFactory.newOperator(Equal.class, 
        		new Class<?>[]{ProgramBlock.class, Parameterized.class, Parameterized.class},
        		getExecuteBlock(), factor1, factor2);
    }

    @Override
    public final NotEqual notEqual(Parameterized factor1, Parameterized factor2){
        return OperatorFactory.newOperator(NotEqual.class, 
        		new Class<?>[]{ProgramBlock.class, Parameterized.class, Parameterized.class},
        		getExecuteBlock(), factor1, factor2);
    }


    //*******************************************************************************************//
    //                            Logic Operator                                                 //
    //*******************************************************************************************//

    @Override
    public final LogicalAnd logicalAnd(Parameterized factor1, Parameterized factor2){
        return OperatorFactory.newOperator(LogicalAnd.class, 
        		new Class<?>[]{ProgramBlock.class, Parameterized.class, Parameterized.class}, 
        		getExecuteBlock(), factor1, factor2);
    }

    @Override
    public final LogicalOr logicalOr(Parameterized factor1, Parameterized factor2){
        return OperatorFactory.newOperator(LogicalOr.class, 
        		new Class<?>[]{ProgramBlock.class, Parameterized.class, Parameterized.class}, 
        		getExecuteBlock(), factor1, factor2);
    }

    @Override
    public final LogicalXor logicalXor(Parameterized factor1, Parameterized factor2){
        return OperatorFactory.newOperator(LogicalXor.class, 
        		new Class<?>[]{ProgramBlock.class, Parameterized.class, Parameterized.class}, 
        		getExecuteBlock(), factor1, factor2);
    }

    @Override
    public final ShortCircuitAnd conditionalAnd(Parameterized factor1, Parameterized factor2){
        return OperatorFactory.newOperator(ShortCircuitAnd.class, 
        		new Class<?>[]{ProgramBlock.class, Parameterized.class, Parameterized.class}, 
        		getExecuteBlock(), factor1, factor2);
    }

    @Override
    public final ShortCircuitOr conditionalOr(Parameterized factor1, Parameterized factor2){
    	return OperatorFactory.newOperator(ShortCircuitOr.class, 
        		new Class<?>[]{ProgramBlock.class, Parameterized.class, Parameterized.class}, 
        		getExecuteBlock(), factor1, factor2);
    }

    @Override
    public final Not not(Parameterized factor){
    	return OperatorFactory.newOperator(Not.class, 
        		new Class<?>[]{ProgramBlock.class, Parameterized.class}, 
        		getExecuteBlock(), factor);
    }

    @Override
    public final TernaryOperator ternary(Parameterized exp1, Parameterized exp2, Parameterized exp3){
    	return OperatorFactory.newOperator(TernaryOperator.class, 
        		new Class<?>[]{ProgramBlock.class, Parameterized.class, Parameterized.class, Parameterized.class}, 
        		getExecuteBlock(), exp1, exp2, exp3);
    }
    

    //*******************************************************************************************//
    //                                  String Operator                                          //
    //*******************************************************************************************//

    @Override
    public final Parameterized append(Parameterized par1, Parameterized... pars){
    	return OperatorFactory.newOperator(StringAppender.class, 
        		new Class<?>[]{ProgramBlock.class, Parameterized.class, Parameterized[].class}, 
        		getExecuteBlock(), par1, pars);
    }
    
    //*******************************************************************************************//
    //                                  instanceof Operator                                      //
    //*******************************************************************************************//

    @Override
    public final Parameterized instanceOf(Parameterized obj, AClass type){
        return OperatorFactory.newOperator(InstanceofOperator.class, 
        		new Class<?>[]{ProgramBlock.class, Parameterized.class, AClass.class}, 
        		getExecuteBlock(), obj, type);
    }

    
    //*******************************************************************************************//
    //                                  method invoke Operator                                      //
    //*******************************************************************************************//

    @Override
    public final MethodInvoker invoke(Parameterized caller, String methodName, Parameterized... arguments){
        return OperatorFactory.newOperator(CommonMethodInvoker.class, 
        		new Class<?>[]{ProgramBlock.class, Parameterized.class, String.class, Parameterized[].class}, 
        		getExecuteBlock(), caller, methodName, arguments);
    }

    protected final void invokeVerify(AClass a){
        if(a.isInterface()){
            throw new MethodInvokeException("the class " + getExecuteBlock().getMethodOwner() + " is a interface and interfaces have no static methods");
        }
        
        if(a.isPrimitive()){
            throw new MethodInvokeException("the class " + getExecuteBlock().getMethodOwner() + " is a primitive and primitive cannot as a method invoker owner");
        }
    }

    @Override
    public final MethodInvoker invokeStatic(AClass owner, String methodName, Parameterized... arguments) {
        invokeVerify(owner);
        return OperatorFactory.newOperator(StaticMethodInvoker.class, 
        		new Class<?>[]{ProgramBlock.class, AClass.class, String.class, Parameterized[].class}, 
        		getExecuteBlock(), owner, methodName, arguments);
    }
    
    public final MethodInvoker invokeConstructor(AClass owner, Parameterized... arguments){
        invokeVerify(owner);
        return OperatorFactory.newOperator(ConstructorInvoker.class, 
        		new Class<?>[]{ProgramBlock.class, AClass.class, Parameterized[].class}, 
        		getExecuteBlock(), owner, arguments);
    }
    
    //*******************************************************************************************//
    //                                  control Operator                                      //
    //*******************************************************************************************//

    @Override
    public final IF ifthan(IF ifs){
        addExe(ifs);
        ifs.setParentExes(getExecuteQueue());
        subBlockPrepare(ifs);
        return ifs;
    }

    @Override
    public final WhileLoop whileloop(WhileLoop wl){
        addExe(wl);
        subBlockPrepare(wl);
        return wl;
    }

    @Override
    public final WhileLoop dowhile(DoWhileLoop dwl){
        addExe(dwl);
        subBlockPrepare(dwl);
        return dwl;
    }

    @Override
    public final ForEachLoop forEach(final ForEachLoop forEach){
        addExe(forEach);
        subBlockPrepare(forEach);
        return forEach;
    }

    @Override
    public final void breakOut(){
        ProgramBlock pb = getExecuteBlock();
        while(pb != null){
            if(pb instanceof ILoop){
                new GOTO(getExecuteBlock(), ((ILoop)pb).getBreakLabel());
                return;
            }
            pb = pb.ownerBlock;
        }
        throw new InternalError("there is on loop!");
    }

    @Override
    public final void continueOut(){
        ProgramBlock pb = getExecuteBlock();
        while(pb != null){
            if(pb instanceof ILoop){
                new GOTO(getExecuteBlock(), ((ILoop)pb).getContinueLabel());
                return;
            }
            pb = pb.ownerBlock;
        }
        throw new InternalError("there is on loop!");
    }

    @Override
    public final void throwException(Parameterized exception){
    	//returned = true;
        OperatorFactory.newOperator(Throw.class, 
                new Class<?>[]{ProgramBlock.class, Parameterized.class}, getExecuteBlock(), exception);
    }
    
    @Override
    public final Try tryDo(final Try t){
        t.setParentExes(getExecuteQueue());
        addExe(t);
        subBlockPrepare(t);
        return t;
    }

    @Override
	public final Synchronized syn(Synchronized s){
		addExe(s);
        subBlockPrepare(s);
		return s;
	}
    
    @Override
    public final ThisVariable getThis() {
    	if(getMethod().isStatic()){
    		throw new ASMSupportException("cannot use \"this\" keyword in static block");
    	}
        return method.getMethodOwner().getThisVariable();
    }
    
    @Override
    public final SuperVariable getSuper() {
    	if(getMethod().isStatic()){
    		throw new ASMSupportException("cannot use \"super\" keyword in static block");
    	}
        return method.getMethodOwner().getSuperVariable();
    }
    
    @Override
    public final MethodInvoker invokeOriginalMethod(){
    	if(getMethod().getMode() == ASConstant.METHOD_CREATE_MODE_MODIFY){
    		String originalMethodName = getMethod().getMethodMeta().getName();
    		if(originalMethodName.equals(ASConstant.CLINIT)){
    			originalMethodName = ASConstant.CLINIT_PROXY;
    		}else if(originalMethodName.equals(ASConstant.INIT)){
    			originalMethodName = ASConstant.INIT_PROXY;
    		}
			originalMethodName += ASConstant.METHOD_PROXY_SUFFIX;
    		if(getMethod().isStatic()){
    			return invokeStatic(getMethod().getMethodOwner(), originalMethodName, getMethodArguments());
    		}else{
        		return invoke(getThis(), originalMethodName, getMethodArguments());
    		}
    	}else{
    		throw new ASMSupportException("This method is new and not modify!");
    	}
    }
    
    /**
     * run return statement
     * @return
     */
    @Override
    public final Return runReturn() {
        if (!method.getMethodMeta().getReturnType().equals(Type.VOID_TYPE)) {
            throw new VerifyErrorException("Do not specify a return type! ");
        }
        return OperatorFactory.newOperator(Return.class, 
                new Class<?>[]{ProgramBlock.class, Parameterized.class}, getExecuteBlock(), null);
    }

    /**
     * run return statement with return value
     * 
     * @param parame return value
     */
    @Override
    public final Return runReturn(Parameterized parame) {
        return OperatorFactory.newOperator(Return.class, 
                new Class<?>[]{ProgramBlock.class, Parameterized.class}, getExecuteBlock(), parame);
    }
    
}
