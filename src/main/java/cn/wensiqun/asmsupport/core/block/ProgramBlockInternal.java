/**
 * 
 */
package cn.wensiqun.asmsupport.core.block;

import cn.wensiqun.asmsupport.core.Crementable;
import cn.wensiqun.asmsupport.core.Parameterized;
import cn.wensiqun.asmsupport.core.asm.InstructionHelper;
import cn.wensiqun.asmsupport.core.block.control.condition.IFInternal;
import cn.wensiqun.asmsupport.core.block.control.exception.ExceptionSerialBlock;
import cn.wensiqun.asmsupport.core.block.control.exception.TryInternal;
import cn.wensiqun.asmsupport.core.block.control.loop.DoWhileInternal;
import cn.wensiqun.asmsupport.core.block.control.loop.ForEachInternal;
import cn.wensiqun.asmsupport.core.block.control.loop.Loop;
import cn.wensiqun.asmsupport.core.block.control.loop.WhileInternal;
import cn.wensiqun.asmsupport.core.block.method.AbstractMethodBody;
import cn.wensiqun.asmsupport.core.block.sync.SynchronizedInternal;
import cn.wensiqun.asmsupport.core.clazz.AClass;
import cn.wensiqun.asmsupport.core.clazz.AClassFactory;
import cn.wensiqun.asmsupport.core.clazz.ArrayClass;
import cn.wensiqun.asmsupport.core.clazz.NewMemberClass;
import cn.wensiqun.asmsupport.core.definition.method.AMethod;
import cn.wensiqun.asmsupport.core.definition.value.Value;
import cn.wensiqun.asmsupport.core.definition.variable.ExplicitVariable;
import cn.wensiqun.asmsupport.core.definition.variable.GlobalVariable;
import cn.wensiqun.asmsupport.core.definition.variable.IVariable;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.core.definition.variable.NonStaticGlobalVariable;
import cn.wensiqun.asmsupport.core.definition.variable.StaticGlobalVariable;
import cn.wensiqun.asmsupport.core.definition.variable.SuperVariable;
import cn.wensiqun.asmsupport.core.definition.variable.ThisVariable;
import cn.wensiqun.asmsupport.core.definition.variable.meta.LocalVariableMeta;
import cn.wensiqun.asmsupport.core.exception.ASMSupportException;
import cn.wensiqun.asmsupport.core.exception.MethodInvokeException;
import cn.wensiqun.asmsupport.core.exception.VerifyErrorException;
import cn.wensiqun.asmsupport.core.operator.BlockEndFlag;
import cn.wensiqun.asmsupport.core.operator.InstanceofOperator;
import cn.wensiqun.asmsupport.core.operator.Return;
import cn.wensiqun.asmsupport.core.operator.StringAppender;
import cn.wensiqun.asmsupport.core.operator.Throw;
import cn.wensiqun.asmsupport.core.operator.array.ArrayLength;
import cn.wensiqun.asmsupport.core.operator.array.ArrayLoader;
import cn.wensiqun.asmsupport.core.operator.array.ArrayStorer;
import cn.wensiqun.asmsupport.core.operator.array.ArrayValue;
import cn.wensiqun.asmsupport.core.operator.asmdirect.GOTO;
import cn.wensiqun.asmsupport.core.operator.assign.Assigner;
import cn.wensiqun.asmsupport.core.operator.assign.LocalVariableAssigner;
import cn.wensiqun.asmsupport.core.operator.assign.NonStaticGlobalVariableAssigner;
import cn.wensiqun.asmsupport.core.operator.assign.StaticGlobalVariableAssigner;
import cn.wensiqun.asmsupport.core.operator.checkcast.CheckCast;
import cn.wensiqun.asmsupport.core.operator.logical.LogicalAnd;
import cn.wensiqun.asmsupport.core.operator.logical.LogicalOr;
import cn.wensiqun.asmsupport.core.operator.logical.LogicalXor;
import cn.wensiqun.asmsupport.core.operator.logical.Not;
import cn.wensiqun.asmsupport.core.operator.logical.ShortCircuitAnd;
import cn.wensiqun.asmsupport.core.operator.logical.ShortCircuitOr;
import cn.wensiqun.asmsupport.core.operator.method.CommonMethodInvoker;
import cn.wensiqun.asmsupport.core.operator.method.ConstructorInvoker;
import cn.wensiqun.asmsupport.core.operator.method.MethodInvoker;
import cn.wensiqun.asmsupport.core.operator.method.StaticMethodInvoker;
import cn.wensiqun.asmsupport.core.operator.numerical.OperatorFactory;
import cn.wensiqun.asmsupport.core.operator.numerical.arithmetic.Addition;
import cn.wensiqun.asmsupport.core.operator.numerical.arithmetic.Division;
import cn.wensiqun.asmsupport.core.operator.numerical.arithmetic.Modulus;
import cn.wensiqun.asmsupport.core.operator.numerical.arithmetic.Multiplication;
import cn.wensiqun.asmsupport.core.operator.numerical.arithmetic.Subtraction;
import cn.wensiqun.asmsupport.core.operator.numerical.bit.BitAnd;
import cn.wensiqun.asmsupport.core.operator.numerical.bit.BitOr;
import cn.wensiqun.asmsupport.core.operator.numerical.bit.BitXor;
import cn.wensiqun.asmsupport.core.operator.numerical.bit.Reverse;
import cn.wensiqun.asmsupport.core.operator.numerical.bit.ShiftLeft;
import cn.wensiqun.asmsupport.core.operator.numerical.bit.ShiftRight;
import cn.wensiqun.asmsupport.core.operator.numerical.bit.UnsignedShiftRight;
import cn.wensiqun.asmsupport.core.operator.numerical.crement.PostposeDecrment;
import cn.wensiqun.asmsupport.core.operator.numerical.crement.PostposeIncrment;
import cn.wensiqun.asmsupport.core.operator.numerical.crement.PreposeDecrment;
import cn.wensiqun.asmsupport.core.operator.numerical.crement.PreposeIncrment;
import cn.wensiqun.asmsupport.core.operator.numerical.posinegative.Negative;
import cn.wensiqun.asmsupport.core.operator.numerical.relational.Equal;
import cn.wensiqun.asmsupport.core.operator.numerical.relational.GreaterEqual;
import cn.wensiqun.asmsupport.core.operator.numerical.relational.GreaterThan;
import cn.wensiqun.asmsupport.core.operator.numerical.relational.LessEqual;
import cn.wensiqun.asmsupport.core.operator.numerical.relational.LessThan;
import cn.wensiqun.asmsupport.core.operator.numerical.relational.NotEqual;
import cn.wensiqun.asmsupport.core.operator.numerical.ternary.TernaryOperator;
import cn.wensiqun.asmsupport.core.operator.numerical.variable.LocalVariableCreator;
import cn.wensiqun.asmsupport.core.utils.ASConstant;
import cn.wensiqun.asmsupport.core.utils.common.ThrowExceptionContainer;
import cn.wensiqun.asmsupport.core.utils.memory.Scope;
import cn.wensiqun.asmsupport.core.utils.memory.ScopeLogicVariable;
import cn.wensiqun.asmsupport.org.apache.commons.lang3.ArrayUtils;
import cn.wensiqun.asmsupport.org.apache.commons.lang3.StringUtils;
import cn.wensiqun.asmsupport.org.objectweb.asm.Label;
import cn.wensiqun.asmsupport.org.objectweb.asm.Type;
import cn.wensiqun.asmsupport.standard.action.ActionSet;

/**
 * 
 * 
 * 
 * @author wensiqun(at)163.com
 * 
 */
public abstract class ProgramBlockInternal extends AbstractBlockInternal implements ActionSet<IFInternal , WhileInternal, DoWhileInternal, ForEachInternal, TryInternal, SynchronizedInternal>  {

    /**执行Block, 通过当前Block所创建的操作，实际是executeBlock的代理*/
    private   ProgramBlockInternal        executor = this;
    
    private   ProgramBlockInternal        parent;
    
    private   Scope                       scope;
    
    protected InstructionHelper           insnHelper;
    
    private   ThrowExceptionContainer     throwExceptions;
    
    /** 当前block是否已经返回 或者已经抛出异常了 */
    private   boolean                     finish = false;
    
    /*<<<<<<<<<<<<<<<<<<< Getter Setter <<<<<<<<<<<<<<<<<<<<<<<<*/
    
    public void setExecutor(ProgramBlockInternal exeBlock) {
        executor = exeBlock;
    }
    
    protected ProgramBlockInternal getExecutor(){
        return executor;
    }
    
    public ThrowExceptionContainer getThrowExceptions() {
        return throwExceptions;
    }

    public boolean isFinish() {
        return finish;
    }

    public void setFinish(boolean finish) {
        this.finish = finish;
    }
    
    /* >>>>>>>>>>>>>>>>>> Getter Setter >>>>>>>>>>>>>>>>>>>>>>>*/

    public ProgramBlockInternal getParent() {
        return parent;
    }

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

    @Override
    public boolean equals(Object obj) {
        if(this == obj){
            return true;
        }
        
        if(obj instanceof ProgramBlockInternal){
            return this.scope == ((ProgramBlockInternal)obj).scope;
        }
        return false;
    }

    /**
     * 获取当前程序块的克隆拷贝
     * @return
     */
    public ProgramBlockInternal getCopy(){
        try {
            return (ProgramBlockInternal) clone();
        } catch (CloneNotSupportedException e) {
            throw new ASMSupportException(e);
        }
    }
    
    /**
     * 克隆当前的程序块的执行队列到给定程序块执行队列中
     * @param targetBlock 克隆至此
     */
    public void generateTo(ProgramBlockInternal targetBlock){
        ProgramBlockInternal clone = getCopy();
        clone.setExecutor(targetBlock);
        clone.generate();
        //just trigger if the last is SerialBlock in the queue of cloneTo
        OperatorFactory.newOperator(BlockEndFlag.class, new Class[]{ProgramBlockInternal.class}, targetBlock);
    }

    protected void init(){};
    
    /**
     * override this method if want create a new block
     * 生成操作到执行队列中去。
     */
    public abstract void generate();
    
    @Override
    public void prepare() {
        init();
        scope.getStart().setName(this.getClass().toString() + " start");
        scope.getEnd().setName(this.getClass().toString() + " end");
        
        generate();
        
        //just trigger if the last is SerialBlock in the queue
        OperatorFactory.newOperator(BlockEndFlag.class, 
                new Class[]{ProgramBlockInternal.class}, 
                getExecutor());
    }
    
    @Override
    public final void execute()
    {
        getInsnHelper().mark(scope.getStart());
        //getInsnHelper().nop();
        doExecute();
        getInsnHelper().mark(scope.getEnd());
        //getInsnHelper().nop();
    }
    
    protected abstract void doExecute();

    public void setScope(Scope scope) {
        this.scope = scope;
    }

    public Scope getScope() {
        return this.scope;
    }
    
    public Label getStart()
    {
        return scope.getStart();
    }

    public Label getEnd()
    {
        return scope.getEnd();
    }

    public void setInsnHelper(InstructionHelper insnHelper) {
        this.insnHelper = insnHelper;
    }

    public void setParent(ProgramBlockInternal block) {
        parent = block;
        setInsnHelper(block.insnHelper);
        setScope(new Scope(getMethod().getLocals(), block.getScope()));
    }
    
    public AMethod getMethod() {
        return insnHelper.getMethod();
    }
    
    public LocalVariable[] getMethodArguments(){
        return getMethod().getArguments();
    }
    
    protected AbstractMethodBody getMethodBody(){
        return getMethod().getMethodBody();
    }
    
    /**
     * get current method owner.
     * 
     * @return
     */
    public NewMemberClass getMethodOwner() {
        return getMethod().getMethodOwner();
    }

    /**
     * Get the {@link InstructionHelper}
     * 
     * @return
     */
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
        
        LocalVariableMeta lve = new LocalVariableMeta(anonymous || getMethod().isCreatingImplicitFinally() ? "anonymous" : name, 0, aClass);
        
        LocalVariableCreator lvc = OperatorFactory.newOperator(LocalVariableCreator.class, 
                new Class<?>[]{ProgramBlockInternal.class, String.class, Type.class, Type.class}, 
                getExecutor(), anonymous ? null : name, aClass.getType(), aClass.getType());
        ScopeLogicVariable slv = lvc.getScopeLogicVariable();
        slv.setCompileOrder(getMethod().nextInsNumber());
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
    public LocalVariable _var(String name, Class<?> type, Parameterized para) {
        return _var(name, AClassFactory.getProductClass(type), false, para);
    }

    @Override
    public LocalVariable _var(Class<?> type, Parameterized para) {
        return _var("", AClassFactory.getProductClass(type), true, para);
    }

    @Override
    public LocalVariable _var(String name, AClass type, Parameterized para) {
        return _var(name, type, false, para);
    }

    @Override
    public LocalVariable _var(AClass type, Parameterized para) {
        return _var("", type, true, para);
    }
    
    @Override
    public final LocalVariable _var(final String name, final AClass aClass, boolean anonymous, final Parameterized para) {
        if(aClass.isArray()){
            throw new IllegalArgumentException(aClass + " is Array type exchange to createArrayVariable to create the array variable");
        }
        LocalVariable lv = createOnlyVariable(aClass, name, anonymous);
        if(para == null){
            _assign(lv, aClass.getDefaultValue());
        }else{
            _assign(lv, para);
        }
        return lv;
    }
    
    @Override
    public final LocalVariable _createArrayVariableWithAllocateDimension(final String name, final ArrayClass aClass, boolean anonymous, Parameterized... allocateDim) {
        LocalVariable lv = createOnlyVariable(aClass, name, anonymous);
        if(allocateDim == null){
            _assign(lv, aClass.getDefaultValue());
        }else{
            _assign(lv, _newArray(aClass, allocateDim));
        }
        return lv;
    }
    
    @Override
    public final LocalVariable _createArrayVariable(final String name, final ArrayClass aClass, boolean anonymous, Parameterized value) {
        LocalVariable lv = createOnlyVariable(aClass, name, anonymous);
        if(value == null){
            _assign(lv, aClass.getDefaultValue());
        }else{
            _assign(lv, value);
        }
        return lv;
    }

    @Override
    public LocalVariable _createArrayVariable(String name, ArrayClass aClass, boolean anonymous, Object parameterizedArray) {
        LocalVariable lv = createOnlyVariable(aClass, name, anonymous);
        if(parameterizedArray == null){
            _assign(lv, aClass.getDefaultValue());
        }else{
            _assign(lv, getExecutor()._newArrayWithValue(aClass, parameterizedArray));
        }
        return lv;
    }
    
    
    @Override
    public final Assigner _assign(ExplicitVariable variable, Parameterized val){
        if(variable instanceof LocalVariable){
            return OperatorFactory.newOperator(LocalVariableAssigner.class,
                    new Class<?>[]{ProgramBlockInternal.class, LocalVariable.class, Parameterized.class}, 
                    getExecutor(), variable, val);
        }else if(variable instanceof StaticGlobalVariable){
            return OperatorFactory.newOperator(StaticGlobalVariableAssigner.class,
                            new Class<?>[]{ProgramBlockInternal.class, StaticGlobalVariable.class, Parameterized.class}, 
                            getExecutor(), variable, val);
        }else if(variable instanceof NonStaticGlobalVariable){
            return OperatorFactory.newOperator(NonStaticGlobalVariableAssigner.class,
                    new Class<?>[]{ProgramBlockInternal.class, NonStaticGlobalVariable.class, Parameterized.class}, 
                    getExecutor(),  variable, val);
        }
        return null;
    }
    
    //*******************************************************************************************//
    //                                Value Operator                                             //
    //*******************************************************************************************//
    
    @Override
    public final ArrayValue _newArray(final ArrayClass aClass, final Parameterized... allocateDims){
        return OperatorFactory.newOperator(ArrayValue.class, 
                new Class<?>[]{ProgramBlockInternal.class, ArrayClass.class, Parameterized[].class}, 
                getExecutor(), aClass, allocateDims);
    }
    
    @Override
    public final ArrayValue _newArrayWithValue(final ArrayClass aClass, final Object arrayObject){
        return OperatorFactory.newOperator(ArrayValue.class, 
                new Class<?>[]{ProgramBlockInternal.class, ArrayClass.class, Object.class}, 
                getExecutor(), aClass, arrayObject);
    }

    @Override
    public final ArrayValue _newArrayWithValue(final ArrayClass aClass, final Parameterized[] values){
        return _newArrayWithValue(aClass, (Object)values);
    }

    @Override
    public final ArrayValue _newArrayWithValue(final ArrayClass aClass, final Parameterized[][] values){
        return _newArrayWithValue(aClass, (Object)values);
    }

    @Override
    public final ArrayValue _newArrayWithValue(final ArrayClass aClass, final Parameterized[][][] values){
        return _newArrayWithValue(aClass, (Object)values);
    }

    @Override
    public final ArrayValue _newArrayWithValue(final ArrayClass aClass, final Parameterized[][][][] values){
        return _newArrayWithValue(aClass, (Object)values);
    }
    
    
    //*******************************************************************************************//
    //                                Array Operator                                             //
    //*******************************************************************************************//  
    
    @Override
    public final ArrayLoader _arrayLoad(IVariable arrayReference, Parameterized pardim, Parameterized... parDims){
        return OperatorFactory.newOperator(ArrayLoader.class, 
                new Class<?>[]{ProgramBlockInternal.class, Parameterized.class, Parameterized.class, Parameterized[].class},
                getExecutor(), arrayReference, pardim, parDims);
    }
    
    @Override
    public final ArrayLoader _arrayLoad(MethodInvoker arrayReference, Parameterized pardim, Parameterized... parDims){
        return OperatorFactory.newOperator(ArrayLoader.class, 
                new Class<?>[]{ProgramBlockInternal.class, Parameterized.class, Parameterized.class, Parameterized[].class},
                getExecutor(), arrayReference, pardim, parDims);
    }
    
    @Override
    public ArrayLoader _arrayLoad(ArrayLoader arrayReference, Parameterized pardim, Parameterized... parDims) {
        return OperatorFactory.newOperator(ArrayLoader.class, 
                new Class<?>[]{ProgramBlockInternal.class, Parameterized.class, Parameterized.class, Parameterized[].class},
                getExecutor(), arrayReference, pardim, parDims);
    }

    @Override
    public ArrayLoader _arrayLoad(ArrayValue arrayReference, Parameterized pardim, Parameterized... parDims) {
        return OperatorFactory.newOperator(ArrayLoader.class, 
                new Class<?>[]{ProgramBlockInternal.class, Parameterized.class, Parameterized.class, Parameterized[].class},
                getExecutor(), arrayReference, pardim, parDims);
    }

    @Override
    public ArrayLoader _arrayLoad(Assigner arrayReference, Parameterized pardim, Parameterized... parDims) {
        return OperatorFactory.newOperator(ArrayLoader.class, 
                new Class<?>[]{ProgramBlockInternal.class, Parameterized.class, Parameterized.class, Parameterized[].class},
                getExecutor(), arrayReference, pardim, parDims);
    }

    @Override
    public final ArrayStorer _arrayStore(IVariable arrayReference, Parameterized value,
            Parameterized dim, Parameterized... dims) {
        return OperatorFactory.newOperator(ArrayStorer.class, 
                new Class<?>[]{ProgramBlockInternal.class, Parameterized.class, Parameterized.class, Parameterized.class, Parameterized[].class},
                getExecutor(), arrayReference, value, dim, dims);
    }

    @Override
    public final  ArrayStorer _arrayStore(MethodInvoker arrayReference, Parameterized value,
            Parameterized dim, Parameterized... dims) {
        return OperatorFactory.newOperator(ArrayStorer.class, 
                new Class<?>[]{ProgramBlockInternal.class, Parameterized.class, Parameterized.class, Parameterized.class, Parameterized[].class},
                getExecutor(), arrayReference, value, dim, dims);
    }

    
    @Override
    public ArrayStorer _arrayStore(ArrayLoader arrayReference, Parameterized value, Parameterized dim, Parameterized... dims) {
        return OperatorFactory.newOperator(ArrayStorer.class, 
                new Class<?>[]{ProgramBlockInternal.class, Parameterized.class, Parameterized.class, Parameterized.class, Parameterized[].class},
                getExecutor(), arrayReference, value, dim, dims);
    }

    @Override
    public ArrayStorer _arrayStore(ArrayValue arrayReference, Parameterized value, Parameterized dim, Parameterized... dims) {
        return OperatorFactory.newOperator(ArrayStorer.class, 
                new Class<?>[]{ProgramBlockInternal.class, Parameterized.class, Parameterized.class, Parameterized.class, Parameterized[].class},
                getExecutor(), arrayReference, value, dim, dims);
    }

    @Override
    public ArrayStorer _arrayStore(Assigner arrayReference, Parameterized value, Parameterized dim, Parameterized... dims) {
        return OperatorFactory.newOperator(ArrayStorer.class, 
                new Class<?>[]{ProgramBlockInternal.class, Parameterized.class, Parameterized.class, Parameterized.class, Parameterized[].class},
                getExecutor(), arrayReference, value, dim, dims);
    }

    @Override
    public final ArrayLength _arrayLength(IVariable arrayReference, Parameterized... dims) {
        return OperatorFactory.newOperator(ArrayLength.class, 
                new Class<?>[]{ProgramBlockInternal.class, Parameterized.class, Parameterized[].class}, 
                getExecutor(), arrayReference, dims);
    }


    @Override
    public ArrayLength _arrayLength(MethodInvoker arrayReference, Parameterized... dims) {
        return OperatorFactory.newOperator(ArrayLength.class, 
                new Class<?>[]{ProgramBlockInternal.class, Parameterized.class, Parameterized[].class}, 
                getExecutor(), arrayReference, dims);
    }

    @Override
    public ArrayLength _arrayLength(ArrayLoader arrayReference, Parameterized... dims) {
        return OperatorFactory.newOperator(ArrayLength.class, 
                new Class<?>[]{ProgramBlockInternal.class, Parameterized.class, Parameterized[].class}, 
                getExecutor(), arrayReference, dims);
    }

    @Override
    public ArrayLength _arrayLength(ArrayValue arrayReference, Parameterized... dims) {
        return OperatorFactory.newOperator(ArrayLength.class, 
                new Class<?>[]{ProgramBlockInternal.class, Parameterized.class, Parameterized[].class}, 
                getExecutor(), arrayReference, dims);
    }

    @Override
    public ArrayLength _arrayLength(Assigner arrayReference, Parameterized... dims) {
        return OperatorFactory.newOperator(ArrayLength.class, 
                new Class<?>[]{ProgramBlockInternal.class, Parameterized.class, Parameterized[].class}, 
                getExecutor(), arrayReference, dims);
    }    
    
    //*******************************************************************************************//
    //                                 Check Cast                                                //
    //*******************************************************************************************//


    @Override
    public final CheckCast _checkcast(Parameterized cc, AClass to){
        if(to.isPrimitive()){
            throw new IllegalArgumentException("Cannot check cast to type " + to + " from " + cc.getParamterizedType());
        }
        return OperatorFactory.newOperator(CheckCast.class, 
                new Class<?>[]{ProgramBlockInternal.class, Parameterized.class, AClass.class}, 
                getExecutor(), cc, to);
    }

    @Override
    public final CheckCast _checkcast(Parameterized cc, Class<?> to){
        return _checkcast(cc, AClassFactory.getProductClass(to));
    }
    
    //*******************************************************************************************//
    //                                Arithmetic Operator                                        //
    //*******************************************************************************************//

    @Override
    public final Addition _add(Parameterized factor1, Parameterized factor2){
        return OperatorFactory.newOperator(Addition.class, 
                new Class<?>[]{ProgramBlockInternal.class, Parameterized.class, Parameterized.class},
                getExecutor(), factor1, factor2);
    }

    @Override
    public final Subtraction _sub(Parameterized factor1, Parameterized factor2){
        return OperatorFactory.newOperator(Subtraction.class, 
                new Class<?>[]{ProgramBlockInternal.class, Parameterized.class, Parameterized.class},
                getExecutor(), factor1, factor2);
    }

    @Override
    public final Multiplication _mul(Parameterized factor1, Parameterized factor2){
        return OperatorFactory.newOperator(Multiplication.class, 
                new Class<?>[]{ProgramBlockInternal.class, Parameterized.class, Parameterized.class},
                getExecutor(), factor1, factor2);
    }

    @Override
    public final Division _div(Parameterized factor1, Parameterized factor2){
        return OperatorFactory.newOperator(Division.class, 
                new Class<?>[]{ProgramBlockInternal.class, Parameterized.class, Parameterized.class},
                getExecutor(), factor1, factor2);
    }

    @Override
    public final Modulus _mod(Parameterized factor1, Parameterized factor2){
        return OperatorFactory.newOperator(Modulus.class, 
                new Class<?>[]{ProgramBlockInternal.class, Parameterized.class, Parameterized.class},
                getExecutor(), factor1, factor2);
    }

    @Override
    public final Negative _neg(Parameterized factor){
        return OperatorFactory.newOperator(Negative.class, 
                new Class<?>[]{ProgramBlockInternal.class, Parameterized.class},
                getExecutor(), factor);
    }
    
    //*******************************************************************************************//
    //                                       Bit Operator                                        //
    //*******************************************************************************************//

    @Override
    public final Reverse _reverse(Parameterized factor){
        return OperatorFactory.newOperator(Reverse.class, 
                new Class<?>[]{ProgramBlockInternal.class, Parameterized.class},
                getExecutor(), factor);
    }

    @Override
    public final BitAnd _band(Parameterized factor1, Parameterized factor2){
        return OperatorFactory.newOperator(BitAnd.class, 
                new Class<?>[]{ProgramBlockInternal.class, Parameterized.class, Parameterized.class},
                getExecutor(), factor1, factor2);
    } 

    @Override
    public final BitOr _bor(Parameterized factor1, Parameterized factor2){
        return OperatorFactory.newOperator(BitOr.class, 
                new Class<?>[]{ProgramBlockInternal.class, Parameterized.class, Parameterized.class},
                getExecutor(), factor1, factor2);
    } 

    @Override
    public final BitXor _bxor(Parameterized factor1, Parameterized factor2){
        return OperatorFactory.newOperator(BitXor.class, 
                new Class<?>[]{ProgramBlockInternal.class, Parameterized.class, Parameterized.class},
                getExecutor(), factor1, factor2);
    } 

    @Override
    public final ShiftLeft _shl(Parameterized factor1, Parameterized factor2){
        return OperatorFactory.newOperator(ShiftLeft.class, 
                new Class<?>[]{ProgramBlockInternal.class, Parameterized.class, Parameterized.class},
                getExecutor(), factor1, factor2);
    }
    
    public final ShiftRight _shr(Parameterized factor1, Parameterized factor2){
        return OperatorFactory.newOperator(ShiftRight.class, 
                new Class<?>[]{ProgramBlockInternal.class, Parameterized.class, Parameterized.class},
                getExecutor(), factor1, factor2);
    }

    @Override
    public final UnsignedShiftRight _ushr(Parameterized factor1, Parameterized factor2){
        return OperatorFactory.newOperator(UnsignedShiftRight.class, 
                new Class<?>[]{ProgramBlockInternal.class, Parameterized.class, Parameterized.class},
                getExecutor(), factor1, factor2);
    }
    
    //*******************************************************************************************//
    //                    Decrement or Increment Operator                                        //
    //*******************************************************************************************//

    @Override
    public final PreposeDecrment _preDec(Crementable crement){
        return OperatorFactory.newOperator(PreposeDecrment.class, 
                new Class<?>[]{ProgramBlockInternal.class, Crementable.class},
                getExecutor(), crement);
    }

    @Override
    public final PostposeDecrment _postDec(Crementable crement){
        return OperatorFactory.newOperator(PostposeDecrment.class, 
                new Class<?>[]{ProgramBlockInternal.class, Crementable.class},
                getExecutor(), crement);
    }

    @Override
    public final PreposeIncrment _preInc(Crementable crement){
        return OperatorFactory.newOperator(PreposeIncrment.class, 
                new Class<?>[]{ProgramBlockInternal.class, Crementable.class},
                getExecutor(), crement);
    }

    @Override
    public final PostposeIncrment _postInc(Crementable crement){
        return OperatorFactory.newOperator(PostposeIncrment.class, 
                new Class<?>[]{ProgramBlockInternal.class, Crementable.class},
                getExecutor(), crement);
    }

    //*******************************************************************************************//
    //                            Relational Operator                                            //
    //*******************************************************************************************//

    @Override
    public final GreaterThan _gt(Parameterized factor1, Parameterized factor2){
        return OperatorFactory.newOperator(GreaterThan.class, 
                new Class<?>[]{ProgramBlockInternal.class, Parameterized.class, Parameterized.class},
                getExecutor(), factor1, factor2);
    }

    @Override
    public final GreaterEqual _ge(Parameterized factor1, Parameterized factor2){
        return OperatorFactory.newOperator(GreaterEqual.class, 
                new Class<?>[]{ProgramBlockInternal.class, Parameterized.class, Parameterized.class},
                getExecutor(), factor1, factor2);
    }

    @Override
    public final LessThan _lt(Parameterized factor1, Parameterized factor2){
        return OperatorFactory.newOperator(LessThan.class, 
                new Class<?>[]{ProgramBlockInternal.class, Parameterized.class, Parameterized.class},
                getExecutor(), factor1, factor2);
    }

    @Override
    public final LessEqual _le(Parameterized factor1, Parameterized factor2){
        return OperatorFactory.newOperator(LessEqual.class, 
                new Class<?>[]{ProgramBlockInternal.class, Parameterized.class, Parameterized.class},
                getExecutor(), factor1, factor2);
    }

    @Override
    public final Equal _eq(Parameterized factor1, Parameterized factor2){
        return OperatorFactory.newOperator(Equal.class, 
                new Class<?>[]{ProgramBlockInternal.class, Parameterized.class, Parameterized.class},
                getExecutor(), factor1, factor2);
    }

    @Override
    public final NotEqual _ne(Parameterized factor1, Parameterized factor2){
        return OperatorFactory.newOperator(NotEqual.class, 
                new Class<?>[]{ProgramBlockInternal.class, Parameterized.class, Parameterized.class},
                getExecutor(), factor1, factor2);
    }


    //*******************************************************************************************//
    //                            Logic Operator                                                 //
    //*******************************************************************************************//

    @Override
    public final LogicalAnd _logicalAnd(Parameterized factor1, Parameterized factor2){
        return OperatorFactory.newOperator(LogicalAnd.class, 
                new Class<?>[]{ProgramBlockInternal.class, Parameterized.class, Parameterized.class}, 
                getExecutor(), factor1, factor2);
    }

    @Override
    public final LogicalOr _logicalOr(Parameterized factor1, Parameterized factor2){
        return OperatorFactory.newOperator(LogicalOr.class, 
                new Class<?>[]{ProgramBlockInternal.class, Parameterized.class, Parameterized.class}, 
                getExecutor(), factor1, factor2);
    }

    @Override
    public final LogicalXor _logicalXor(Parameterized factor1, Parameterized factor2){
        return OperatorFactory.newOperator(LogicalXor.class, 
                new Class<?>[]{ProgramBlockInternal.class, Parameterized.class, Parameterized.class}, 
                getExecutor(), factor1, factor2);
    }

    @Override
    public final ShortCircuitAnd _and(Parameterized factor1, Parameterized factor2, Parameterized... otherFactors){
        ShortCircuitAnd sca = OperatorFactory.newOperator(ShortCircuitAnd.class, 
                new Class<?>[]{ProgramBlockInternal.class, Parameterized.class, Parameterized.class}, 
                getExecutor(), factor1, factor2);
        if(ArrayUtils.isNotEmpty(otherFactors)) {
            for(Parameterized factor : otherFactors) {
                sca = OperatorFactory.newOperator(ShortCircuitAnd.class, 
                        new Class<?>[]{ProgramBlockInternal.class, Parameterized.class, Parameterized.class}, 
                        getExecutor(), sca, factor);
            }
        }
        return sca;
    }

    @Override
    public final ShortCircuitOr _or(Parameterized factor1, Parameterized factor2, Parameterized... otherFactors){
        ShortCircuitOr sco = OperatorFactory.newOperator(ShortCircuitOr.class, 
                new Class<?>[]{ProgramBlockInternal.class, Parameterized.class, Parameterized.class}, 
                getExecutor(), factor1, factor2);
        if(ArrayUtils.isNotEmpty(otherFactors)) {
            for(Parameterized factor : otherFactors) {
                sco = OperatorFactory.newOperator(ShortCircuitOr.class, 
                        new Class<?>[]{ProgramBlockInternal.class, Parameterized.class, Parameterized.class}, 
                        getExecutor(), sco, factor);
            }
        }
        return sco;
    }

    @Override
    public final Not _not(Parameterized factor){
        return OperatorFactory.newOperator(Not.class, 
                new Class<?>[]{ProgramBlockInternal.class, Parameterized.class}, 
                getExecutor(), factor);
    }

    @Override
    public final TernaryOperator _ternary(Parameterized exp1, Parameterized exp2, Parameterized exp3){
        return OperatorFactory.newOperator(TernaryOperator.class, 
                new Class<?>[]{ProgramBlockInternal.class, Parameterized.class, Parameterized.class, Parameterized.class}, 
                getExecutor(), exp1, exp2, exp3);
    }
    

    //*******************************************************************************************//
    //                                  String Operator                                          //
    //*******************************************************************************************//

    @Override
    public final Parameterized _append(Parameterized par1, Parameterized... pars){
        return OperatorFactory.newOperator(StringAppender.class, 
                new Class<?>[]{ProgramBlockInternal.class, Parameterized.class, Parameterized[].class}, 
                getExecutor(), par1, pars);
    }
    
    //*******************************************************************************************//
    //                                  instanceof Operator                                      //
    //*******************************************************************************************//

    @Override
    public final Parameterized _instanceof(Parameterized obj, AClass type){
        return OperatorFactory.newOperator(InstanceofOperator.class, 
                new Class<?>[]{ProgramBlockInternal.class, Parameterized.class, AClass.class}, 
                getExecutor(), obj, type);
    }

    
    //*******************************************************************************************//
    //                                  method invoke Operator                                      //
    //*******************************************************************************************//

    @Override
    public final MethodInvoker _invoke(Parameterized caller, String methodName, Parameterized... arguments){
        return OperatorFactory.newOperator(CommonMethodInvoker.class, 
                new Class<?>[]{ProgramBlockInternal.class, Parameterized.class, String.class, Parameterized[].class}, 
                getExecutor(), caller, methodName, arguments);
    }
    

    @Override
	public MethodInvoker _invoke(String methodName, Parameterized... args) {
		return _invoke(_this(), methodName, args);
	}

	protected final void invokeVerify(AClass a){
        if(a.isInterface()){
            throw new MethodInvokeException("the class " + getExecutor().getMethodOwner() + " is a interface and interfaces have no static methods");
        }
        
        if(a.isPrimitive()){
            throw new MethodInvokeException("the class " + getExecutor().getMethodOwner() + " is a primitive and primitive cannot as a method invoker owner");
        }
    }

    @Override
    public final MethodInvoker _invoke(AClass owner, String methodName, Parameterized... arguments) {
        invokeVerify(owner);
        return OperatorFactory.newOperator(StaticMethodInvoker.class, 
                new Class<?>[]{ProgramBlockInternal.class, AClass.class, String.class, Parameterized[].class}, 
                getExecutor(), owner, methodName, arguments);
    }

    @Override
    public final MethodInvoker _new(AClass owner, Parameterized... arguments){
        invokeVerify(owner);
        return OperatorFactory.newOperator(ConstructorInvoker.class, 
                new Class<?>[]{ProgramBlockInternal.class, AClass.class, Parameterized[].class}, 
                getExecutor(), owner, arguments);
    }

    @Override
    public final MethodInvoker _new(Class<?> owner, Parameterized... arguments){
        return this._new(AClassFactory.getProductClass(owner), arguments);
    }
    
    //*******************************************************************************************//
    //                                  control Operator                                      //
    //*******************************************************************************************//

    @Override
    public final IFInternal _if(IFInternal ifBlock){
        ifBlock.setParent(getExecutor());
        getQueue().add(ifBlock);
        ifBlock.prepare();
        return ifBlock;
    }

    @Override
    public final WhileInternal _while(WhileInternal whileLoop){
        whileLoop.setParent(getExecutor());
        getQueue().add(whileLoop);
        whileLoop.prepare();
        return whileLoop;
    }

    @Override
    public final DoWhileInternal _dowhile(DoWhileInternal dowhile){
        dowhile.setParent(getExecutor());
        getQueue().add(dowhile);
        dowhile.prepare();
        return dowhile;
    }

    @Override
    public final ForEachInternal _for(final ForEachInternal forEach){
        forEach.setParent(getExecutor());
        getQueue().add(forEach);
        forEach.prepare();
        return forEach;
    }

    @Override
    public final void _break(){
        ProgramBlockInternal pb = getExecutor();
        while(pb != null){
            if(pb instanceof Loop){
                OperatorFactory.newOperator(GOTO.class, 
                        new Class<?>[]{ProgramBlockInternal.class, Label.class}, 
                        getExecutor(), ((Loop)pb).getBreakLabel());
                //new GOTO(getExecutor(), ((ILoop)pb).getBreakLabel());
                return;
            }
            pb = pb.getParent();
        }
        throw new InternalError("there is on loop!");
    }

    @Override
    public final void _continue(){
        ProgramBlockInternal pb = getExecutor();
        while(pb != null){
            if(pb instanceof Loop){
                OperatorFactory.newOperator(GOTO.class, 
                        new Class<?>[]{ProgramBlockInternal.class, Label.class}, 
                        getExecutor(), ((Loop)pb).getContinueLabel());
                //new GOTO(getExecutor(), ((ILoop)pb).getContinueLabel());
                return;
            }
            pb = pb.getParent();
        }
        throw new InternalError("there is on loop!");
    }

    @Override
    public final void _throw(Parameterized exception){
        OperatorFactory.newOperator(Throw.class, 
                new Class<?>[]{ProgramBlockInternal.class, Parameterized.class}, getExecutor(), exception);
     }
    
    @Override
    public final TryInternal _try(final TryInternal t){
        new ExceptionSerialBlock(getExecutor(), t);
        return t;
     }

    @Override
    public final SynchronizedInternal _sync(SynchronizedInternal s){
       s.setParent(getExecutor());
       getQueue().add(s);
       s.prepare();
       return s;
    }
    
    @Override
    public final ThisVariable _this() {
        if(getMethod().isStatic()){
            throw new ASMSupportException("cannot use \"this\" keyword in static block");
        }
        return getMethodOwner().getThisVariable();
    }
    
    @Override
    public final GlobalVariable _this(String name) {
        return _this().getGlobalVariable(name);
    }
    
    @Override
    public final SuperVariable _super() {
        if(getMethod().isStatic()){
            throw new ASMSupportException("cannot use \"super\" keyword in static block");
        }
        return getMethodOwner().getSuperVariable();
    }
    
    @Override
    public final MethodInvoker _invokeOriginal(){
        if(getMethod().getMode() == ASConstant.METHOD_CREATE_MODE_MODIFY){
            String originalMethodName = getMethod().getMethodMeta().getName();
            if(originalMethodName.equals(ASConstant.CLINIT)){
                originalMethodName = ASConstant.CLINIT_PROXY;
            }else if(originalMethodName.equals(ASConstant.INIT)){
                originalMethodName = ASConstant.INIT_PROXY;
            }
            originalMethodName += ASConstant.METHOD_PROXY_SUFFIX;
            if(getMethod().isStatic()){
                return _invoke(getMethod().getMethodOwner(), originalMethodName, getMethodArguments());
            }else{
                return _invoke(_this(), originalMethodName, getMethodArguments());
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
    public final Return _return() {
        if (!getMethod().getMethodMeta().getReturnType().equals(Type.VOID_TYPE)) {
            throw new VerifyErrorException("Do not specify a return type! ");
        }
        return OperatorFactory.newOperator(Return.class, 
                new Class<?>[]{ProgramBlockInternal.class, Parameterized.class}, getExecutor(), null);
    }

    /**
     * run return statement with return value
     * 
     * @param parame return value
     */
    @Override
    public final Return _return(Parameterized parame) {
        return OperatorFactory.newOperator(Return.class, 
                new Class<?>[]{ProgramBlockInternal.class, Parameterized.class}, getExecutor(), parame);
    }

    @Override
    public Value val(Integer val) {
        return Value.value(val);
    }

    @Override
    public Value val(Short val) {
        return Value.value(val);
    }

    @Override
    public Value val(Byte val) {
        return Value.value(val);
    }

    @Override
    public Value val(Boolean val) {
        return Value.value(val);
    }

    @Override
    public Value val(Long val) {
        return Value.value(val);
    }

    @Override
    public Value val(Double val) {
        return Value.value(val);
    }

    @Override
    public Value val(Character val) {
        return Value.value(val);
    }

    @Override
    public Value val(Float val) {
        return Value.value(val);
    }

    @Override
    public Value val(AClass val) {
        return Value.value(val);
    }

    @Override
    public Value val(Class<?> val) {
        return Value.value(val);
    }

    @Override
    public Value val(String val) {
        return Value.value(val);
    }

    @Override
    public Value _null(AClass type) {
        return Value.getNullValue(type);
    }

    @Override
    public Value _null(Class<?> type) {
        return Value.getNullValue(AClassFactory.getProductClass(type));
    }
    
    
}
