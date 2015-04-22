/**    
 *  Asmsupport is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
/**
 * 
 */
package cn.wensiqun.asmsupport.core.block;

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
import cn.wensiqun.asmsupport.core.clazz.AClassFactory;
import cn.wensiqun.asmsupport.core.clazz.ArrayClass;
import cn.wensiqun.asmsupport.core.clazz.MutableClass;
import cn.wensiqun.asmsupport.core.definition.KernelParameterized;
import cn.wensiqun.asmsupport.core.definition.method.AMethod;
import cn.wensiqun.asmsupport.core.definition.value.Value;
import cn.wensiqun.asmsupport.core.definition.variable.GlobalVariable;
import cn.wensiqun.asmsupport.core.definition.variable.IVariable;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.core.definition.variable.NonStaticGlobalVariable;
import cn.wensiqun.asmsupport.core.definition.variable.StaticGlobalVariable;
import cn.wensiqun.asmsupport.core.definition.variable.SuperVariable;
import cn.wensiqun.asmsupport.core.definition.variable.ThisVariable;
import cn.wensiqun.asmsupport.core.exception.MethodInvokeException;
import cn.wensiqun.asmsupport.core.exception.VerifyErrorException;
import cn.wensiqun.asmsupport.core.operator.BlockEndFlag;
import cn.wensiqun.asmsupport.core.operator.array.KernelArrayLength;
import cn.wensiqun.asmsupport.core.operator.array.KernelArrayLoad;
import cn.wensiqun.asmsupport.core.operator.array.KernelArrayStore;
import cn.wensiqun.asmsupport.core.operator.array.KernelArrayValue;
import cn.wensiqun.asmsupport.core.operator.asmdirect.GOTO;
import cn.wensiqun.asmsupport.core.operator.assign.KernelAssign;
import cn.wensiqun.asmsupport.core.operator.assign.LocalVariableAssigner;
import cn.wensiqun.asmsupport.core.operator.assign.NonStaticGlobalVariableAssigner;
import cn.wensiqun.asmsupport.core.operator.assign.StaticGlobalVariableAssigner;
import cn.wensiqun.asmsupport.core.operator.common.KernelCast;
import cn.wensiqun.asmsupport.core.operator.common.KernelInstanceof;
import cn.wensiqun.asmsupport.core.operator.common.KernelReturn;
import cn.wensiqun.asmsupport.core.operator.common.KernelStrAdd;
import cn.wensiqun.asmsupport.core.operator.common.KernelTernary;
import cn.wensiqun.asmsupport.core.operator.common.KernelThrow;
import cn.wensiqun.asmsupport.core.operator.common.LocalVariableCreator;
import cn.wensiqun.asmsupport.core.operator.logical.KernelLogicalAnd;
import cn.wensiqun.asmsupport.core.operator.logical.KernelLogicalOr;
import cn.wensiqun.asmsupport.core.operator.logical.KernelLogicalXor;
import cn.wensiqun.asmsupport.core.operator.logical.KernelNot;
import cn.wensiqun.asmsupport.core.operator.logical.KernelShortCircuitAnd;
import cn.wensiqun.asmsupport.core.operator.logical.KernelShortCircuitOr;
import cn.wensiqun.asmsupport.core.operator.method.CommonMethodInvoker;
import cn.wensiqun.asmsupport.core.operator.method.ConstructorInvoker;
import cn.wensiqun.asmsupport.core.operator.method.MethodInvoker;
import cn.wensiqun.asmsupport.core.operator.method.StaticMethodInvoker;
import cn.wensiqun.asmsupport.core.operator.numerical.OperatorFactory;
import cn.wensiqun.asmsupport.core.operator.numerical.arithmetic.KernelAdd;
import cn.wensiqun.asmsupport.core.operator.numerical.arithmetic.KernelDiv;
import cn.wensiqun.asmsupport.core.operator.numerical.arithmetic.KernelMod;
import cn.wensiqun.asmsupport.core.operator.numerical.arithmetic.KernelMul;
import cn.wensiqun.asmsupport.core.operator.numerical.arithmetic.KernelSub;
import cn.wensiqun.asmsupport.core.operator.numerical.bit.KernelBitAnd;
import cn.wensiqun.asmsupport.core.operator.numerical.bit.KernelBitOr;
import cn.wensiqun.asmsupport.core.operator.numerical.bit.KernelBitXor;
import cn.wensiqun.asmsupport.core.operator.numerical.bit.KernelReverse;
import cn.wensiqun.asmsupport.core.operator.numerical.bit.KernelShiftLeft;
import cn.wensiqun.asmsupport.core.operator.numerical.bit.KernelShiftRight;
import cn.wensiqun.asmsupport.core.operator.numerical.bit.KernelUnsignedShiftRight;
import cn.wensiqun.asmsupport.core.operator.numerical.crement.KernelPostDecrment;
import cn.wensiqun.asmsupport.core.operator.numerical.crement.KernelPostIncrment;
import cn.wensiqun.asmsupport.core.operator.numerical.crement.KernelPreDecrment;
import cn.wensiqun.asmsupport.core.operator.numerical.crement.KernelPreIncrment;
import cn.wensiqun.asmsupport.core.operator.numerical.posinegative.KernelNeg;
import cn.wensiqun.asmsupport.core.operator.numerical.relational.KernelEqual;
import cn.wensiqun.asmsupport.core.operator.numerical.relational.KernelGreaterEqual;
import cn.wensiqun.asmsupport.core.operator.numerical.relational.KernelGreaterThan;
import cn.wensiqun.asmsupport.core.operator.numerical.relational.KernelLessEqual;
import cn.wensiqun.asmsupport.core.operator.numerical.relational.KernelLessThan;
import cn.wensiqun.asmsupport.core.operator.numerical.relational.KernelNotEqual;
import cn.wensiqun.asmsupport.core.utils.ASConstant;
import cn.wensiqun.asmsupport.core.utils.common.ThrowExceptionContainer;
import cn.wensiqun.asmsupport.core.utils.lang.ArrayUtils;
import cn.wensiqun.asmsupport.core.utils.lang.StringUtils;
import cn.wensiqun.asmsupport.core.utils.memory.Scope;
import cn.wensiqun.asmsupport.core.utils.memory.ScopeLogicVariable;
import cn.wensiqun.asmsupport.org.objectweb.asm.Label;
import cn.wensiqun.asmsupport.org.objectweb.asm.Type;
import cn.wensiqun.asmsupport.standard.action.ActionSet;
import cn.wensiqun.asmsupport.standard.def.clazz.AClass;
import cn.wensiqun.asmsupport.standard.def.var.meta.LocalVariableMeta;
import cn.wensiqun.asmsupport.standard.exception.ASMSupportException;

/**
 * 
 * 
 * 
 * @author wensiqun(at)163.com
 * 
 */
public abstract class ProgramBlockInternal extends AbstractBlockInternal implements 
ActionSet<KernelParameterized, IVariable, GlobalVariable,
IFInternal, WhileInternal, DoWhileInternal, ForEachInternal, TryInternal, SynchronizedInternal> {

	/** the actually executor.*/
    private ProgramBlockInternal executor = this;

    private ProgramBlockInternal parent;

    private Scope scope;

    protected InstructionHelper insnHelper;

    private ThrowExceptionContainer throwExceptions;
    
    private boolean finish;

    /**
     * Set executor
     * 
     * @param exeBlock
     */
    public void setExecutor(ProgramBlockInternal exeBlock) {
        executor = exeBlock;
    }

    /**
     * Get the actually executor.
     * 
     * @return
     */
    protected ProgramBlockInternal getExecutor() {
        return executor;
    }

    /**
     * Check the block has already finish generated.
     * 
     * @return
     */
    public boolean isFinish() {
		return finish;
	}

    /**
     * Set the block has already finish generated.
     * 
     * @param finish
     */
	public void setFinish(boolean finish) {
		this.finish = finish;
	}

	/**
     * The all throw exception container.
     * 
     * @return
     */
    public ThrowExceptionContainer getThrowExceptions() {
        return throwExceptions;
    }

    /**
     * Add exception it's throw in current method body.
     * @param exceptionType the exception type.
     */
    public void addException(AClass exceptionType) {
        if (throwExceptions == null) {
            throwExceptions = new ThrowExceptionContainer();
        }
        throwExceptions.add(exceptionType);
    }
    
    /**
     * Remove exception type from current exception container.
     * 
     * @param exception
     */
    public void removeException(AClass exceptionType) {
        if (throwExceptions != null) {
            throwExceptions.remove(exceptionType);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj instanceof ProgramBlockInternal) {
            return this.scope == ((ProgramBlockInternal) obj).scope;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return scope.hashCode();
    }

    /**
     * Set scope
     * 
     * @param scope
     */
    public void setScope(Scope scope) {
        this.scope = scope;
    }

    /**
     * Get scope
     * 
     * @return
     */
    public Scope getScope() {
        return this.scope;
    }

    /**
     * Get block start label
     * 
     * @return
     */
    public Label getStart() {
        return scope.getStart();
    }

    /**
     * Get block end label
     * 
     * @return
     */
    public Label getEnd() {
        return scope.getEnd();
    }

    /**
     * Set {@link InstructionHelper}
     * 
     * @param insnHelper
     */
    public void setInsnHelper(InstructionHelper insnHelper) {
        this.insnHelper = insnHelper;
    }

    /**
     * Get the parent of current block
     * 
     * @return
     */
    public ProgramBlockInternal getParent() {
        return parent;
    }

    public void setParent(ProgramBlockInternal block) {
        parent = block;
        setInsnHelper(block.insnHelper);
        setScope(new Scope(getMethod().getLocals(), block.getScope()));
    }

    /**
     * Geth the method of current block bellow.
     * 
     * @return
     */
    public AMethod getMethod() {
        return insnHelper.getMethod();
    }

    /**
     * Get the method argument that's corresponding to current block.
     * 
     * @return
     */
    public LocalVariable[] getMethodArguments() {
        return getMethod().getArguments();
    }

    /**
     * Get the MethodBody that's corresponding to current block.
     * 
     * @return
     */
    protected AbstractMethodBody getMethodBody() {
        return getMethod().getMethodBody();
    }

    /**
     * Get current method owner, generally is the SemiClass it's
     * a class which you want geneate.
     * 
     * @return
     */
    public MutableClass getMethodOwner() {
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
    
    /**
     * Generate current body instruction to other program block.
     * 
     * @param targetBlock the target block it you want clone to.
     */
    public void generateTo(ProgramBlockInternal targetBlock) {
		try {
			ProgramBlockInternal clone = (ProgramBlockInternal) clone();
	        clone.setExecutor(targetBlock);
	        clone.generate();
	        // just trigger if the last is SerialBlock in the queue of cloneTo
	        OperatorFactory.newOperator(BlockEndFlag.class, new Class[] { ProgramBlockInternal.class }, targetBlock);
		} catch (CloneNotSupportedException e) {
			throw new ASMSupportException(e);
		}
    }

    /**
     * Do init here, the method is empty, specify code will be override by sub class.
     */
    protected void init() {
    	//Do nothing here, override in subclass.
    }

    /**
     * The specify the program block code you want to generate here.
     * In this method we just build a execute queue, but generate 
     * java bytecode instruction in method {@link #execute()}
     * 
     */
    public abstract void generate();

    @Override
    public void prepare() {
        init();
        scope.getStart().setName(this.getClass().toString() + " start");
        scope.getEnd().setName(this.getClass().toString() + " end");
        generate();
        // just trigger if the last is SerialBlock in the queue
        OperatorFactory.newOperator(BlockEndFlag.class, new Class[] { ProgramBlockInternal.class }, getExecutor());
    }

    @Override
    public final void execute() {
        getInsnHelper().mark(scope.getStart());
        doExecute();
        getInsnHelper().mark(scope.getEnd());
    }

    /**
     * Override this method in subclass, defined the generate instruction rule for
     * each block.
     */
    protected abstract void doExecute();

    // *******************************************************************************************//
    // Variable Operator //
    // *******************************************************************************************//
    private final LocalVariable createOnlyVariable(final AClass aClass, final String name, boolean anonymous) {
        if (!anonymous && StringUtils.isBlank(name)) {
            throw new IllegalArgumentException("variable must be non-null if 'anonymous' is false");
        }

        LocalVariableMeta lve = new LocalVariableMeta(
                anonymous || getMethod().isCreatingImplicitFinally() ? "anonymous" : name, 0, aClass);

        LocalVariableCreator lvc = OperatorFactory.newOperator(LocalVariableCreator.class, new Class<?>[] {
                ProgramBlockInternal.class, String.class, Type.class, Type.class }, getExecutor(), anonymous ? null
                : name, aClass.getType(), aClass.getType());
        ScopeLogicVariable slv = lvc.getScopeLogicVariable();
        slv.setCompileOrder(getMethod().nextInsNumber());
        LocalVariable lv = new LocalVariable(lve);
        lv.setScopeLogicVar(slv);
        return lv;
    }

    protected final LocalVariable getLocalAnonymousVariableModel(final AClass aClass) {
        return createOnlyVariable(aClass, "anonymous", true);
    }

    protected final LocalVariable getLocalVariableModel(final String name, final AClass aClass) {
        return createOnlyVariable(aClass, name, false);
    }

    /**
     * the basic create variable method.
     */
    private final LocalVariable var(String name, AClass type, boolean anonymous, KernelParameterized value) {
        LocalVariable lv = createOnlyVariable(type, name, anonymous);
        if (value == null) {
            assign(lv, Value.defaultValue(type));
        } else {
            assign(lv, value);
        }
        return lv;
    }

    @Override
    public LocalVariable var(String name, Class<?> type, KernelParameterized para) {
        return var(name, AClassFactory.getType(type), false, para);
    }

    @Override
    public LocalVariable var(Class<?> type, KernelParameterized para) {
        return var("", AClassFactory.getType(type), true, para);
    }

    @Override
    public LocalVariable var(String name, AClass type, KernelParameterized para) {
        return var(name, type, false, para);
    }

    @Override
    public LocalVariable var(AClass type, KernelParameterized para) {
        return var("", type, true, para);
    }

    @Override
	public GlobalVariable field(String name) {
		return this_().field(name);
	}

	@Override
    public final KernelAssign assign(IVariable variable, KernelParameterized val) {
        if (variable instanceof LocalVariable) {
            return OperatorFactory.newOperator(LocalVariableAssigner.class, new Class<?>[] {
                    ProgramBlockInternal.class, LocalVariable.class, KernelParameterized.class }, getExecutor(), variable,
                    val);
        } else if (variable instanceof StaticGlobalVariable) {
            return OperatorFactory.newOperator(StaticGlobalVariableAssigner.class, new Class<?>[] {
                    ProgramBlockInternal.class, StaticGlobalVariable.class, KernelParameterized.class }, getExecutor(),
                    variable, val);
        } else if (variable instanceof NonStaticGlobalVariable) {
            return OperatorFactory.newOperator(NonStaticGlobalVariableAssigner.class, new Class<?>[] {
                    ProgramBlockInternal.class, NonStaticGlobalVariable.class, KernelParameterized.class }, getExecutor(),
                    variable, val);
        } else {
            throw new IllegalArgumentException("Can't assign value to variable : " + variable.getName());
        }
    }

    // *******************************************************************************************//
    // Value Operator //
    // *******************************************************************************************//

    @Override
    public final KernelArrayValue makeArray(AClass arrayType, final KernelParameterized... allocateDims) {
    	if(!arrayType.isArray()) {
    		throw new IllegalArgumentException("Must be an array type, but actually a/an " + arrayType);
    	}
        return OperatorFactory.newOperator(KernelArrayValue.class, new Class<?>[] { ProgramBlockInternal.class,
                ArrayClass.class, KernelParameterized[].class }, getExecutor(), arrayType, allocateDims);
    }

    @Override
    public KernelArrayValue makeArray(Class<?> arraytype, KernelParameterized... dimensions) {
    	if(!arraytype.isArray()) {
    		throw new IllegalArgumentException("Must be an array type, but actually a/an " + arraytype);
    	}
        return makeArray((ArrayClass) getType(arraytype), dimensions);
    }

    @Override
    public final KernelArrayValue newarray(AClass arrayType, final Object arrayObject) {
    	if(!arrayType.isArray()) {
    		throw new IllegalArgumentException("Must be an array type, but actually a/an " + arrayType);
    	}
        return OperatorFactory.newOperator(KernelArrayValue.class, new Class<?>[] { ProgramBlockInternal.class,
                ArrayClass.class, Object.class }, getExecutor(), arrayType, arrayObject);
    }

    @Override
    public KernelArrayValue newarray(Class<?> type, Object arrayObject) {
    	if(!type.isArray()) {
    		throw new IllegalArgumentException("Must be an array type, but actually a/an " + type);
    	}
        return newarray((ArrayClass) getType(type), arrayObject);
    }

    // *******************************************************************************************//
    // Array Operator //
    // *******************************************************************************************//

    @Override
    public final KernelArrayLoad arrayLoad(KernelParameterized arrayReference, KernelParameterized pardim, KernelParameterized... parDims) {
        return OperatorFactory.newOperator(KernelArrayLoad.class, new Class<?>[] { ProgramBlockInternal.class,
                KernelParameterized.class, KernelParameterized.class, KernelParameterized[].class }, getExecutor(), arrayReference,
                pardim, parDims);
    }

    @Override
    public final KernelArrayStore arrayStore(KernelParameterized arrayReference, KernelParameterized value, KernelParameterized dim,
            KernelParameterized... dims) {
        return OperatorFactory.newOperator(KernelArrayStore.class, new Class<?>[] { ProgramBlockInternal.class,
                KernelParameterized.class, KernelParameterized.class, KernelParameterized.class, KernelParameterized[].class }, getExecutor(),
                arrayReference, value, dim, dims);
    }

    @Override
    public final KernelArrayLength arrayLength(KernelParameterized arrayReference, KernelParameterized... dims) {
        return OperatorFactory.newOperator(KernelArrayLength.class, new Class<?>[] { ProgramBlockInternal.class,
                KernelParameterized.class, KernelParameterized[].class }, getExecutor(), arrayReference, dims);
    }

    // *******************************************************************************************//
    // Check Cast //
    // *******************************************************************************************//

    @Override
    public final KernelCast checkcast(KernelParameterized cc, AClass to) {
        if (to.isPrimitive()) {
            throw new IllegalArgumentException("Cannot check cast to type " + to + " from " + cc.getResultType());
        }
        return OperatorFactory.newOperator(KernelCast.class, new Class<?>[] { ProgramBlockInternal.class,
                KernelParameterized.class, AClass.class }, getExecutor(), cc, to);
    }

    @Override
    public final KernelCast checkcast(KernelParameterized cc, Class<?> to) {
        return checkcast(cc, AClassFactory.getType(to));
    }

    // *******************************************************************************************//
    // Arithmetic Operator //
    // *******************************************************************************************//

    @Override
    public final KernelAdd add(KernelParameterized factor1, KernelParameterized factor2) {
        return OperatorFactory.newOperator(KernelAdd.class, new Class<?>[] { ProgramBlockInternal.class,
                KernelParameterized.class, KernelParameterized.class }, getExecutor(), factor1, factor2);
    }

    @Override
    public final KernelSub sub(KernelParameterized factor1, KernelParameterized factor2) {
        return OperatorFactory.newOperator(KernelSub.class, new Class<?>[] { ProgramBlockInternal.class,
                KernelParameterized.class, KernelParameterized.class }, getExecutor(), factor1, factor2);
    }

    @Override
    public final KernelMul mul(KernelParameterized factor1, KernelParameterized factor2) {
        return OperatorFactory.newOperator(KernelMul.class, new Class<?>[] { ProgramBlockInternal.class,
                KernelParameterized.class, KernelParameterized.class }, getExecutor(), factor1, factor2);
    }

    @Override
    public final KernelDiv div(KernelParameterized factor1, KernelParameterized factor2) {
        return OperatorFactory.newOperator(KernelDiv.class, new Class<?>[] { ProgramBlockInternal.class,
                KernelParameterized.class, KernelParameterized.class }, getExecutor(), factor1, factor2);
    }

    @Override
    public final KernelMod mod(KernelParameterized factor1, KernelParameterized factor2) {
        return OperatorFactory.newOperator(KernelMod.class, new Class<?>[] { ProgramBlockInternal.class,
                KernelParameterized.class, KernelParameterized.class }, getExecutor(), factor1, factor2);
    }

    @Override
    public final KernelNeg neg(KernelParameterized factor) {
        return OperatorFactory.newOperator(KernelNeg.class, new Class<?>[] { ProgramBlockInternal.class,
                KernelParameterized.class }, getExecutor(), factor);
    }

    // *******************************************************************************************//
    // Bit Operator //
    // *******************************************************************************************//

    @Override
    public final KernelReverse reverse(KernelParameterized factor) {
        return OperatorFactory.newOperator(KernelReverse.class, new Class<?>[] { ProgramBlockInternal.class,
                KernelParameterized.class }, getExecutor(), factor);
    }

    @Override
    public final KernelBitAnd band(KernelParameterized factor1, KernelParameterized factor2) {
        return OperatorFactory.newOperator(KernelBitAnd.class, new Class<?>[] { ProgramBlockInternal.class,
                KernelParameterized.class, KernelParameterized.class }, getExecutor(), factor1, factor2);
    }

    @Override
    public final KernelBitOr bor(KernelParameterized factor1, KernelParameterized factor2) {
        return OperatorFactory.newOperator(KernelBitOr.class, new Class<?>[] { ProgramBlockInternal.class,
                KernelParameterized.class, KernelParameterized.class }, getExecutor(), factor1, factor2);
    }

    @Override
    public final KernelBitXor bxor(KernelParameterized factor1, KernelParameterized factor2) {
        return OperatorFactory.newOperator(KernelBitXor.class, new Class<?>[] { ProgramBlockInternal.class,
                KernelParameterized.class, KernelParameterized.class }, getExecutor(), factor1, factor2);
    }

    @Override
    public final KernelShiftLeft shl(KernelParameterized factor1, KernelParameterized factor2) {
        return OperatorFactory.newOperator(KernelShiftLeft.class, new Class<?>[] { ProgramBlockInternal.class,
                KernelParameterized.class, KernelParameterized.class }, getExecutor(), factor1, factor2);
    }

    public final KernelShiftRight shr(KernelParameterized factor1, KernelParameterized factor2) {
        return OperatorFactory.newOperator(KernelShiftRight.class, new Class<?>[] { ProgramBlockInternal.class,
                KernelParameterized.class, KernelParameterized.class }, getExecutor(), factor1, factor2);
    }

    @Override
    public final KernelUnsignedShiftRight ushr(KernelParameterized factor1, KernelParameterized factor2) {
        return OperatorFactory.newOperator(KernelUnsignedShiftRight.class, new Class<?>[] { ProgramBlockInternal.class,
                KernelParameterized.class, KernelParameterized.class }, getExecutor(), factor1, factor2);
    }

    // *******************************************************************************************//
    // Decrement or Increment Operator //
    // *******************************************************************************************//

    @Override
    public final KernelPreDecrment predec(KernelParameterized crement) {
        return OperatorFactory.newOperator(KernelPreDecrment.class, new Class<?>[] { ProgramBlockInternal.class,
            KernelParameterized.class }, getExecutor(), crement);
    }

    @Override
    public final KernelPostDecrment postdec(KernelParameterized crement) {
        return OperatorFactory.newOperator(KernelPostDecrment.class, new Class<?>[] { ProgramBlockInternal.class,
            KernelParameterized.class }, getExecutor(), crement);
    }

    @Override
    public final KernelPreIncrment preinc(KernelParameterized crement) {
        return OperatorFactory.newOperator(KernelPreIncrment.class, new Class<?>[] { ProgramBlockInternal.class,
            KernelParameterized.class }, getExecutor(), crement);
    }

    @Override
    public final KernelPostIncrment postinc(KernelParameterized crement) {
        return OperatorFactory.newOperator(KernelPostIncrment.class, new Class<?>[] { ProgramBlockInternal.class,
            KernelParameterized.class }, getExecutor(), crement);
    }

    // *******************************************************************************************//
    // Relational Operator //
    // *******************************************************************************************//

    @Override
    public final KernelGreaterThan gt(KernelParameterized factor1, KernelParameterized factor2) {
        return OperatorFactory.newOperator(KernelGreaterThan.class, new Class<?>[] { ProgramBlockInternal.class,
                KernelParameterized.class, KernelParameterized.class }, getExecutor(), factor1, factor2);
    }

    @Override
    public final KernelGreaterEqual ge(KernelParameterized factor1, KernelParameterized factor2) {
        return OperatorFactory.newOperator(KernelGreaterEqual.class, new Class<?>[] { ProgramBlockInternal.class,
                KernelParameterized.class, KernelParameterized.class }, getExecutor(), factor1, factor2);
    }

    @Override
    public final KernelLessThan lt(KernelParameterized factor1, KernelParameterized factor2) {
        return OperatorFactory.newOperator(KernelLessThan.class, new Class<?>[] { ProgramBlockInternal.class,
                KernelParameterized.class, KernelParameterized.class }, getExecutor(), factor1, factor2);
    }

    @Override
    public final KernelLessEqual le(KernelParameterized factor1, KernelParameterized factor2) {
        return OperatorFactory.newOperator(KernelLessEqual.class, new Class<?>[] { ProgramBlockInternal.class,
                KernelParameterized.class, KernelParameterized.class }, getExecutor(), factor1, factor2);
    }

    @Override
    public final KernelEqual eq(KernelParameterized factor1, KernelParameterized factor2) {
        return OperatorFactory.newOperator(KernelEqual.class, new Class<?>[] { ProgramBlockInternal.class,
                KernelParameterized.class, KernelParameterized.class }, getExecutor(), factor1, factor2);
    }

    @Override
    public final KernelNotEqual ne(KernelParameterized factor1, KernelParameterized factor2) {
        return OperatorFactory.newOperator(KernelNotEqual.class, new Class<?>[] { ProgramBlockInternal.class,
                KernelParameterized.class, KernelParameterized.class }, getExecutor(), factor1, factor2);
    }

    // *******************************************************************************************//
    // Logic Operator //
    // *******************************************************************************************//

    @Override
    public final KernelLogicalAnd logicalAnd(KernelParameterized factor1, KernelParameterized factor2) {
        return OperatorFactory.newOperator(KernelLogicalAnd.class, new Class<?>[] { ProgramBlockInternal.class,
                KernelParameterized.class, KernelParameterized.class }, getExecutor(), factor1, factor2);
    }

    @Override
    public final KernelLogicalOr logicalOr(KernelParameterized factor1, KernelParameterized factor2) {
        return OperatorFactory.newOperator(KernelLogicalOr.class, new Class<?>[] { ProgramBlockInternal.class,
                KernelParameterized.class, KernelParameterized.class }, getExecutor(), factor1, factor2);
    }

    @Override
    public final KernelLogicalXor logicalXor(KernelParameterized factor1, KernelParameterized factor2) {
        return OperatorFactory.newOperator(KernelLogicalXor.class, new Class<?>[] { ProgramBlockInternal.class,
                KernelParameterized.class, KernelParameterized.class }, getExecutor(), factor1, factor2);
    }

    @Override
    public final KernelShortCircuitAnd and(KernelParameterized factor1, KernelParameterized factor2, KernelParameterized... otherFactors) {
        KernelShortCircuitAnd sca = OperatorFactory
                .newOperator(KernelShortCircuitAnd.class, new Class<?>[] { ProgramBlockInternal.class, KernelParameterized.class,
                        KernelParameterized.class }, getExecutor(), factor1, factor2);
        if (ArrayUtils.isNotEmpty(otherFactors)) {
            for (KernelParameterized factor : otherFactors) {
                sca = OperatorFactory.newOperator(KernelShortCircuitAnd.class, new Class<?>[] { ProgramBlockInternal.class,
                        KernelParameterized.class, KernelParameterized.class }, getExecutor(), sca, factor);
            }
        }
        return sca;
    }

    @Override
    public final KernelShortCircuitOr or(KernelParameterized factor1, KernelParameterized factor2, KernelParameterized... otherFactors) {
        KernelShortCircuitOr sco = OperatorFactory
                .newOperator(KernelShortCircuitOr.class, new Class<?>[] { ProgramBlockInternal.class, KernelParameterized.class,
                        KernelParameterized.class }, getExecutor(), factor1, factor2);
        if (ArrayUtils.isNotEmpty(otherFactors)) {
            for (KernelParameterized factor : otherFactors) {
                sco = OperatorFactory.newOperator(KernelShortCircuitOr.class, new Class<?>[] { ProgramBlockInternal.class,
                        KernelParameterized.class, KernelParameterized.class }, getExecutor(), sco, factor);
            }
        }
        return sco;
    }

    @Override
    public final KernelNot no(KernelParameterized factor) {
        return OperatorFactory.newOperator(KernelNot.class,
                new Class<?>[] { ProgramBlockInternal.class, KernelParameterized.class }, getExecutor(), factor);
    }

    @Override
    public final KernelTernary ternary(KernelParameterized exp1, KernelParameterized exp2, KernelParameterized exp3) {
        return OperatorFactory.newOperator(KernelTernary.class, new Class<?>[] { ProgramBlockInternal.class,
                KernelParameterized.class, KernelParameterized.class, KernelParameterized.class }, getExecutor(), exp1, exp2, exp3);
    }

    // *******************************************************************************************//
    // String Operator //
    // *******************************************************************************************//

    @Override
    public final KernelStrAdd stradd(KernelParameterized par1, KernelParameterized... pars) {
        return OperatorFactory.newOperator(KernelStrAdd.class, new Class<?>[] { ProgramBlockInternal.class,
                KernelParameterized.class, KernelParameterized[].class }, getExecutor(), par1, pars);
    }

    // *******************************************************************************************//
    // instanceof Operator //
    // *******************************************************************************************//

    @Override
    public final KernelInstanceof instanceof_(KernelParameterized obj, AClass type) {
        return OperatorFactory.newOperator(KernelInstanceof.class, new Class<?>[] { ProgramBlockInternal.class,
                KernelParameterized.class, AClass.class }, getExecutor(), obj, type);
    }

    @Override
    public final KernelInstanceof instanceof_(KernelParameterized obj, Class<?> type) {
        return instanceof_(obj, getType(type));
    }

    // *******************************************************************************************//
    // method invoke Operator //
    // *******************************************************************************************//

    @Override
    public final MethodInvoker call(KernelParameterized caller, String methodName, KernelParameterized... arguments) {
        return OperatorFactory.newOperator(CommonMethodInvoker.class, new Class<?>[] { ProgramBlockInternal.class,
                KernelParameterized.class, String.class, KernelParameterized[].class }, getExecutor(), caller, methodName,
                arguments);
    }

    @Override
    public MethodInvoker call(String methodName, KernelParameterized... args) {
        return call(this_(), methodName, args);
    }

    protected final void invokeVerify(AClass a) {
        if (a.isInterface()) {
            throw new MethodInvokeException("the class " + getExecutor().getMethodOwner()
                    + " is a interface and interfaces have no static methods");
        }

        if (a.isPrimitive()) {
            throw new MethodInvokeException("the class " + getExecutor().getMethodOwner()
                    + " is a primitive and primitive cannot as a method invoker owner");
        }
    }

    @Override
    public final MethodInvoker call(AClass owner, String methodName, KernelParameterized... arguments) {
        invokeVerify(owner);
        return OperatorFactory.newOperator(StaticMethodInvoker.class, new Class<?>[] { ProgramBlockInternal.class,
                AClass.class, String.class, KernelParameterized[].class }, getExecutor(), owner, methodName, arguments);
    }

    public final MethodInvoker call(Class<?> owner, String methodName, KernelParameterized... arguments) {
        return call(getType(owner), methodName, arguments);
    }

    @Override
    public final MethodInvoker new_(AClass owner, KernelParameterized... arguments) {
        invokeVerify(owner);
        return OperatorFactory.newOperator(ConstructorInvoker.class, new Class<?>[] { ProgramBlockInternal.class,
                AClass.class, KernelParameterized[].class }, getExecutor(), owner, arguments);
    }

    @Override
    public final MethodInvoker new_(Class<?> owner, KernelParameterized... arguments) {
        return this.new_(AClassFactory.getType(owner), arguments);
    }

    // *******************************************************************************************//
    // control Operator //
    // *******************************************************************************************//

    @Override
    public final IFInternal if_(IFInternal ifBlock) {
        ifBlock.setParent(getExecutor());
        getQueue().add(ifBlock);
        ifBlock.prepare();
        return ifBlock;
    }

    @Override
    public final WhileInternal while_(WhileInternal whileLoop) {
        whileLoop.setParent(getExecutor());
        getQueue().add(whileLoop);
        whileLoop.prepare();
        return whileLoop;
    }

    @Override
    public final DoWhileInternal dowhile(DoWhileInternal dowhile) {
        dowhile.setParent(getExecutor());
        getQueue().add(dowhile);
        dowhile.prepare();
        return dowhile;
    }

    @Override
    public final ForEachInternal for_(final ForEachInternal forEach) {
        forEach.setParent(getExecutor());
        getQueue().add(forEach);
        forEach.prepare();
        return forEach;
    }

    @Override
    public final void break_() {
        ProgramBlockInternal pb = getExecutor();
        while (pb != null) {
            if (pb instanceof Loop) {
                OperatorFactory.newOperator(GOTO.class, new Class<?>[] { ProgramBlockInternal.class, Label.class },
                        getExecutor(), ((Loop) pb).getBreakLabel());
                // new GOTO(getExecutor(), ((ILoop)pb).getBreakLabel());
                return;
            }
            pb = pb.getParent();
        }
        throw new InternalError("there is on loop!");
    }

    @Override
    public final void continue_() {
        ProgramBlockInternal pb = getExecutor();
        while (pb != null) {
            if (pb instanceof Loop) {
                OperatorFactory.newOperator(GOTO.class, new Class<?>[] { ProgramBlockInternal.class, Label.class },
                        getExecutor(), ((Loop) pb).getContinueLabel());
                // new GOTO(getExecutor(), ((ILoop)pb).getContinueLabel());
                return;
            }
            pb = pb.getParent();
        }
        throw new InternalError("there is on loop!");
    }

    @Override
    public final void throw_(KernelParameterized exception) {
        OperatorFactory.newOperator(KernelThrow.class, new Class<?>[] { ProgramBlockInternal.class, KernelParameterized.class },
                getExecutor(), exception);
    }

    @Override
    public final TryInternal try_(final TryInternal t) {
        new ExceptionSerialBlock(getExecutor(), t);
        return t;
    }

    @Override
    public final SynchronizedInternal sync(SynchronizedInternal s) {
        s.setParent(getExecutor());
        getQueue().add(s);
        s.prepare();
        return s;
    }

    @Override
    public final ThisVariable this_() {
        if (getMethod().isStatic()) {
            throw new ASMSupportException("cannot use \"this\" keyword in static block");
        }
        return getMethodOwner().getThisVariable();
    }

    @Override
    public final GlobalVariable this_(String name) {
        return this_().field(name);
    }

    @Override
    public final SuperVariable super_() {
        if (getMethod().isStatic()) {
            throw new ASMSupportException("cannot use \"super\" keyword in static block");
        }
        return getMethodOwner().getSuperVariable();
    }

    @Override
    public final MethodInvoker callOrig() {
        if (getMethod().getMode() == ASConstant.METHOD_CREATE_MODE_MODIFY) {
            String originalMethodName = getMethod().getMethodMeta().getName();
            if (originalMethodName.equals(ASConstant.CLINIT)) {
                originalMethodName = ASConstant.CLINIT_PROXY;
            } else if (originalMethodName.equals(ASConstant.INIT)) {
                originalMethodName = ASConstant.INIT_PROXY;
            }
            originalMethodName += ASConstant.METHOD_PROXY_SUFFIX;
            if (getMethod().isStatic()) {
                return call(getMethod().getMethodOwner(), originalMethodName, getMethodArguments());
            } else {
                return call(this_(), originalMethodName, getMethodArguments());
            }
        } else {
            throw new ASMSupportException("This method is new and not modify!");
        }
    }

    /**
     * run return statement
     * 
     * @return
     */
    @Override
    public final void return_() {
        if (!getMethod().getMethodMeta().getReturnType().equals(Type.VOID_TYPE)) {
            throw new VerifyErrorException("Do not specify a return type! ");
        }
        OperatorFactory.newOperator(KernelReturn.class, new Class<?>[] { ProgramBlockInternal.class,
                KernelParameterized.class }, getExecutor(), null);
    }

    /**
     * run return statement with return value
     * 
     * @param parame
     *            return value
     */
    @Override
    public final void return_(KernelParameterized parame) {
        OperatorFactory.newOperator(KernelReturn.class, new Class<?>[] { ProgramBlockInternal.class,
                KernelParameterized.class }, getExecutor(), parame);
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
    public Value null_(AClass type) {
        return Value.getNullValue(type);
    }

    @Override
    public Value null_(Class<?> type) {
        return Value.getNullValue(AClassFactory.getType(type));
    }

    @Override
    public AClass getType(Class<?> cls) {
        return AClassFactory.getType(cls);
    }

    @Override
    public ArrayClass getArrayType(Class<?> cls, int dim) {
        return AClassFactory.getArrayType(cls, dim);
    }

    @Override
    public ArrayClass getArrayType(AClass rootComponent, int dim) {
        return AClassFactory.getArrayType(rootComponent, dim);
    }

}
