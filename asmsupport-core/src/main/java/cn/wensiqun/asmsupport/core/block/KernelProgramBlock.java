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
import cn.wensiqun.asmsupport.core.block.control.condition.KernelIF;
import cn.wensiqun.asmsupport.core.block.control.exception.ExceptionSerialBlock;
import cn.wensiqun.asmsupport.core.block.control.exception.KernelTry;
import cn.wensiqun.asmsupport.core.block.control.loop.KernelDoWhile;
import cn.wensiqun.asmsupport.core.block.control.loop.KernelForEach;
import cn.wensiqun.asmsupport.core.block.control.loop.KernelWhile;
import cn.wensiqun.asmsupport.core.block.control.loop.Loop;
import cn.wensiqun.asmsupport.core.block.method.AbstractKernelMethodBody;
import cn.wensiqun.asmsupport.core.block.sync.KernelSync;
import cn.wensiqun.asmsupport.core.definition.KernelParam;
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
import cn.wensiqun.asmsupport.core.utils.common.ThrowExceptionContainer;
import cn.wensiqun.asmsupport.core.utils.memory.Scope;
import cn.wensiqun.asmsupport.core.utils.memory.ScopeLogicVariable;
import cn.wensiqun.asmsupport.core.utils.reflect.ModifierUtils;
import cn.wensiqun.asmsupport.org.objectweb.asm.Label;
import cn.wensiqun.asmsupport.org.objectweb.asm.Type;
import cn.wensiqun.asmsupport.standard.action.ActionSet;
import cn.wensiqun.asmsupport.standard.def.clazz.ArrayClass;
import cn.wensiqun.asmsupport.standard.def.clazz.ClassHolder;
import cn.wensiqun.asmsupport.standard.def.clazz.IClass;
import cn.wensiqun.asmsupport.standard.def.clazz.MutableClass;
import cn.wensiqun.asmsupport.standard.def.var.meta.VarMeta;
import cn.wensiqun.asmsupport.standard.error.ASMSupportException;
import cn.wensiqun.asmsupport.utils.AsmsupportConstant;
import cn.wensiqun.asmsupport.utils.lang.ArrayUtils;
import cn.wensiqun.asmsupport.utils.lang.StringUtils;

/**
 * A implements of ProgramBlock
 * 
 * @author wensiqun(at)163.com
 */
public abstract class KernelProgramBlock extends AbstractKernelBlock implements 
ActionSet<KernelParam, IVariable, GlobalVariable,
KernelIF, KernelWhile, KernelDoWhile, KernelForEach, KernelTry, KernelSync> {

	/** the actually executor.*/
    private KernelProgramBlock executor = this;

    private KernelProgramBlock parent;

    private Scope scope;

    protected InstructionHelper instructionHelper;

    private ThrowExceptionContainer throwExceptions;
    
    private boolean finish;

    /**
     * Generate current body instruction to other program block.
     * 
     * @param targetBlock the target block it you want clone to.
     */
    public void generateTo(KernelProgramBlock targetBlock) {
		try {
			KernelProgramBlock clone = (KernelProgramBlock) clone();
	        clone.setExecutor(targetBlock);
	        clone.generate();
	        // just trigger if the last is SerialBlock in the queue of cloneTo
	        OperatorFactory.newOperator(BlockEndFlag.class, new Class[] { KernelProgramBlock.class }, targetBlock);
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
        OperatorFactory.newOperator(BlockEndFlag.class, new Class[] { KernelProgramBlock.class }, getExecutor());
    }

    @Override
    public final void execute() {
        getInstructionHelper().mark(scope.getStart());
        doExecute();
        getInstructionHelper().mark(scope.getEnd());
    }

    /**
     * Override this method in subclass, defined the generate instruction rule for
     * each block.
     */
    protected abstract void doExecute();

    // *******************************************************************************************//
    // Variable Operator //
    // *******************************************************************************************//
    private final LocalVariable createOnlyVariable(final IClass aClass, final String name, boolean anonymous) {
        if (!anonymous && StringUtils.isBlank(name)) {
            throw new IllegalArgumentException("variable must be non-null if 'anonymous' is false");
        }

        VarMeta lve = new VarMeta(anonymous ? "anonymous" : name, 0, aClass);

        LocalVariableCreator lvc = OperatorFactory.newOperator(LocalVariableCreator.class, new Class<?>[] {
                KernelProgramBlock.class, String.class, Type.class, Type.class }, getExecutor(), anonymous ? null
                : name, aClass.getType(), aClass.getType());
        ScopeLogicVariable slv = lvc.getScopeLogicVariable();
        slv.setCompileOrder(getMethod().getNextInstructionNumber());
        LocalVariable lv = new LocalVariable(lve);
        lv.setScopeLogicVar(slv);
        return lv;
    }

    protected final LocalVariable getLocalAnonymousVariableModel(final IClass aClass) {
        return createOnlyVariable(aClass, "anonymous", true);
    }

    protected final LocalVariable getLocalVariableModel(final String name, final IClass aClass) {
        return createOnlyVariable(aClass, name, false);
    }

    /**
     * the basic create variable method.
     */
    private final LocalVariable var(String name, IClass type, boolean anonymous, KernelParam value) {
        LocalVariable lv = createOnlyVariable(type, name, anonymous);
        if (value == null) {
            assign(lv, Value.defaultValue(type));
        } else {
            assign(lv, value);
        }
        return lv;
    }

    @Override
    public LocalVariable var(String name, Class<?> type, KernelParam para) {
        return var(name, getClassHolder().getType(type), false, para);
    }

    @Override
    public LocalVariable var(Class<?> type, KernelParam para) {
        return var("", getClassHolder().getType(type), true, para);
    }

    @Override
    public LocalVariable var(String name, IClass type, KernelParam para) {
        return var(name, type, false, para);
    }

    @Override
    public LocalVariable var(IClass type, KernelParam para) {
        return var("", type, true, para);
    }

    @Override
	public GlobalVariable field(String name) {
		return this_().field(name);
	}

	@Override
    public final KernelAssign assign(IVariable variable, KernelParam val) {
        if (variable instanceof LocalVariable) {
            return OperatorFactory.newOperator(LocalVariableAssigner.class, new Class<?>[] {
                    KernelProgramBlock.class, LocalVariable.class, KernelParam.class }, getExecutor(), variable,
                    val);
        } else if (variable instanceof StaticGlobalVariable) {
            return OperatorFactory.newOperator(StaticGlobalVariableAssigner.class, new Class<?>[] {
                    KernelProgramBlock.class, StaticGlobalVariable.class, KernelParam.class }, getExecutor(),
                    variable, val);
        } else if (variable instanceof NonStaticGlobalVariable) {
            return OperatorFactory.newOperator(NonStaticGlobalVariableAssigner.class, new Class<?>[] {
                    KernelProgramBlock.class, NonStaticGlobalVariable.class, KernelParam.class }, getExecutor(),
                    variable, val);
        } else {
            throw new IllegalArgumentException("Can't assign value to variable : " + variable.getName());
        }
    }

    // *******************************************************************************************//
    // Value Operator //
    // *******************************************************************************************//

    @Override
    public final KernelArrayValue makeArray(IClass arrayType, final KernelParam... allocateDims) {
    	if(!arrayType.isArray()) {
    		throw new IllegalArgumentException("Must be an array type, but actually a/an " + arrayType);
    	}
        return OperatorFactory.newOperator(KernelArrayValue.class, new Class<?>[] { KernelProgramBlock.class,
                ArrayClass.class, KernelParam[].class }, getExecutor(), arrayType, allocateDims);
    }

    @Override
    public KernelArrayValue makeArray(Class<?> arraytype, KernelParam... dimensions) {
    	if(!arraytype.isArray()) {
    		throw new IllegalArgumentException("Must be an array type, but actually a/an " + arraytype);
    	}
        return makeArray((ArrayClass) getType(arraytype), dimensions);
    }

    @Override
    public final KernelArrayValue newarray(IClass arrayType, final Object arrayObject) {
    	if(!arrayType.isArray()) {
    		throw new IllegalArgumentException("Must be an array type, but actually a/an " + arrayType);
    	}
        return OperatorFactory.newOperator(KernelArrayValue.class, new Class<?>[] { KernelProgramBlock.class,
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
    public final KernelArrayLoad arrayLoad(KernelParam arrayReference, KernelParam pardim, KernelParam... parDims) {
        return OperatorFactory.newOperator(KernelArrayLoad.class, new Class<?>[] { KernelProgramBlock.class,
                KernelParam.class, KernelParam.class, KernelParam[].class }, getExecutor(), arrayReference,
                pardim, parDims);
    }

    @Override
    public final KernelArrayStore arrayStore(KernelParam arrayReference, KernelParam value, KernelParam dim,
            KernelParam... dims) {
        return OperatorFactory.newOperator(KernelArrayStore.class, new Class<?>[] { KernelProgramBlock.class,
                KernelParam.class, KernelParam.class, KernelParam.class, KernelParam[].class }, getExecutor(),
                arrayReference, value, dim, dims);
    }

    @Override
    public final KernelArrayLength arrayLength(KernelParam arrayReference, KernelParam... dims) {
        return OperatorFactory.newOperator(KernelArrayLength.class, new Class<?>[] { KernelProgramBlock.class,
                KernelParam.class, KernelParam[].class }, getExecutor(), arrayReference, dims);
    }

    // *******************************************************************************************//
    // Check Cast //
    // *******************************************************************************************//

    @Override
    public final KernelCast checkcast(KernelParam cc, IClass to) {
        if (to.isPrimitive()) {
            throw new IllegalArgumentException("Cannot check cast to type " + to + " from " + cc.getResultType());
        }
        return OperatorFactory.newOperator(KernelCast.class, new Class<?>[] { KernelProgramBlock.class,
                KernelParam.class, IClass.class }, getExecutor(), cc, to);
    }

    @Override
    public final KernelCast checkcast(KernelParam cc, Class<?> to) {
        return checkcast(cc, getClassHolder().getType(to));
    }

    // *******************************************************************************************//
    // Arithmetic Operator //
    // *******************************************************************************************//

    @Override
    public final KernelAdd add(KernelParam leftFactor, KernelParam rightFactor) {
        return OperatorFactory.newOperator(KernelAdd.class, new Class<?>[] { KernelProgramBlock.class,
                KernelParam.class, KernelParam.class }, getExecutor(), leftFactor, rightFactor);
    }

    @Override
    public final KernelSub sub(KernelParam leftFactor, KernelParam rightFactor) {
        return OperatorFactory.newOperator(KernelSub.class, new Class<?>[] { KernelProgramBlock.class,
                KernelParam.class, KernelParam.class }, getExecutor(), leftFactor, rightFactor);
    }

    @Override
    public final KernelMul mul(KernelParam leftFactor, KernelParam rightFactor) {
        return OperatorFactory.newOperator(KernelMul.class, new Class<?>[] { KernelProgramBlock.class,
                KernelParam.class, KernelParam.class }, getExecutor(), leftFactor, rightFactor);
    }

    @Override
    public final KernelDiv div(KernelParam leftFactor, KernelParam rightFactor) {
        return OperatorFactory.newOperator(KernelDiv.class, new Class<?>[] { KernelProgramBlock.class,
                KernelParam.class, KernelParam.class }, getExecutor(), leftFactor, rightFactor);
    }

    @Override
    public final KernelMod mod(KernelParam leftFactor, KernelParam rightFactor) {
        return OperatorFactory.newOperator(KernelMod.class, new Class<?>[] { KernelProgramBlock.class,
                KernelParam.class, KernelParam.class }, getExecutor(), leftFactor, rightFactor);
    }

    @Override
    public final KernelNeg neg(KernelParam factor) {
        return OperatorFactory.newOperator(KernelNeg.class, new Class<?>[] { KernelProgramBlock.class,
                KernelParam.class }, getExecutor(), factor);
    }

    // *******************************************************************************************//
    // Bit Operator //
    // *******************************************************************************************//

    @Override
    public final KernelReverse reverse(KernelParam factor) {
        return OperatorFactory.newOperator(KernelReverse.class, new Class<?>[] { KernelProgramBlock.class,
                KernelParam.class }, getExecutor(), factor);
    }

    @Override
    public final KernelBitAnd band(KernelParam leftFactor, KernelParam rightFactor) {
        return OperatorFactory.newOperator(KernelBitAnd.class, new Class<?>[] { KernelProgramBlock.class,
                KernelParam.class, KernelParam.class }, getExecutor(), leftFactor, rightFactor);
    }

    @Override
    public final KernelBitOr bor(KernelParam leftFactor, KernelParam rightFactor) {
        return OperatorFactory.newOperator(KernelBitOr.class, new Class<?>[] { KernelProgramBlock.class,
                KernelParam.class, KernelParam.class }, getExecutor(), leftFactor, rightFactor);
    }

    @Override
    public final KernelBitXor bxor(KernelParam leftFactor, KernelParam rightFactor) {
        return OperatorFactory.newOperator(KernelBitXor.class, new Class<?>[] { KernelProgramBlock.class,
                KernelParam.class, KernelParam.class }, getExecutor(), leftFactor, rightFactor);
    }

    @Override
    public final KernelShiftLeft shl(KernelParam leftFactor, KernelParam rightFactor) {
        return OperatorFactory.newOperator(KernelShiftLeft.class, new Class<?>[] { KernelProgramBlock.class,
                KernelParam.class, KernelParam.class }, getExecutor(), leftFactor, rightFactor);
    }

    public final KernelShiftRight shr(KernelParam leftFactor, KernelParam rightFactor) {
        return OperatorFactory.newOperator(KernelShiftRight.class, new Class<?>[] { KernelProgramBlock.class,
                KernelParam.class, KernelParam.class }, getExecutor(), leftFactor, rightFactor);
    }

    @Override
    public final KernelUnsignedShiftRight ushr(KernelParam leftFactor, KernelParam rightFactor) {
        return OperatorFactory.newOperator(KernelUnsignedShiftRight.class, new Class<?>[] { KernelProgramBlock.class,
                KernelParam.class, KernelParam.class }, getExecutor(), leftFactor, rightFactor);
    }

    // *******************************************************************************************//
    // Decrement or Increment Operator //
    // *******************************************************************************************//

    @Override
    public final KernelPreDecrment predec(KernelParam crement) {
        return OperatorFactory.newOperator(KernelPreDecrment.class, new Class<?>[] { KernelProgramBlock.class,
            KernelParam.class }, getExecutor(), crement);
    }

    @Override
    public final KernelPostDecrment postdec(KernelParam crement) {
        return OperatorFactory.newOperator(KernelPostDecrment.class, new Class<?>[] { KernelProgramBlock.class,
            KernelParam.class }, getExecutor(), crement);
    }

    @Override
    public final KernelPreIncrment preinc(KernelParam crement) {
        return OperatorFactory.newOperator(KernelPreIncrment.class, new Class<?>[] { KernelProgramBlock.class,
            KernelParam.class }, getExecutor(), crement);
    }

    @Override
    public final KernelPostIncrment postinc(KernelParam crement) {
        return OperatorFactory.newOperator(KernelPostIncrment.class, new Class<?>[] { KernelProgramBlock.class,
            KernelParam.class }, getExecutor(), crement);
    }

    // *******************************************************************************************//
    // Relational Operator //
    // *******************************************************************************************//

    @Override
    public final KernelGreaterThan gt(KernelParam leftFactor, KernelParam rightFactor) {
        return OperatorFactory.newOperator(KernelGreaterThan.class, new Class<?>[] { KernelProgramBlock.class,
                KernelParam.class, KernelParam.class }, getExecutor(), leftFactor, rightFactor);
    }

    @Override
    public final KernelGreaterEqual ge(KernelParam leftFactor, KernelParam rightFactor) {
        return OperatorFactory.newOperator(KernelGreaterEqual.class, new Class<?>[] { KernelProgramBlock.class,
                KernelParam.class, KernelParam.class }, getExecutor(), leftFactor, rightFactor);
    }

    @Override
    public final KernelLessThan lt(KernelParam leftFactor, KernelParam rightFactor) {
        return OperatorFactory.newOperator(KernelLessThan.class, new Class<?>[] { KernelProgramBlock.class,
                KernelParam.class, KernelParam.class }, getExecutor(), leftFactor, rightFactor);
    }

    @Override
    public final KernelLessEqual le(KernelParam leftFactor, KernelParam rightFactor) {
        return OperatorFactory.newOperator(KernelLessEqual.class, new Class<?>[] { KernelProgramBlock.class,
                KernelParam.class, KernelParam.class }, getExecutor(), leftFactor, rightFactor);
    }

    @Override
    public final KernelEqual eq(KernelParam leftFactor, KernelParam rightFactor) {
        return OperatorFactory.newOperator(KernelEqual.class, new Class<?>[] { KernelProgramBlock.class,
                KernelParam.class, KernelParam.class }, getExecutor(), leftFactor, rightFactor);
    }

    @Override
    public final KernelNotEqual ne(KernelParam leftFactor, KernelParam rightFactor) {
        return OperatorFactory.newOperator(KernelNotEqual.class, new Class<?>[] { KernelProgramBlock.class,
                KernelParam.class, KernelParam.class }, getExecutor(), leftFactor, rightFactor);
    }

    // *******************************************************************************************//
    // Logic Operator //
    // *******************************************************************************************//

    @Override
    public final KernelLogicalAnd logicalAnd(KernelParam leftFactor, KernelParam rightFactor) {
        return OperatorFactory.newOperator(KernelLogicalAnd.class, new Class<?>[] { KernelProgramBlock.class,
                KernelParam.class, KernelParam.class }, getExecutor(), leftFactor, rightFactor);
    }

    @Override
    public final KernelLogicalOr logicalOr(KernelParam leftFactor, KernelParam rightFactor) {
        return OperatorFactory.newOperator(KernelLogicalOr.class, new Class<?>[] { KernelProgramBlock.class,
                KernelParam.class, KernelParam.class }, getExecutor(), leftFactor, rightFactor);
    }

    @Override
    public final KernelLogicalXor logicalXor(KernelParam leftFactor, KernelParam rightFactor) {
        return OperatorFactory.newOperator(KernelLogicalXor.class, new Class<?>[] { KernelProgramBlock.class,
                KernelParam.class, KernelParam.class }, getExecutor(), leftFactor, rightFactor);
    }

    @Override
    public final KernelShortCircuitAnd and(KernelParam leftFactor, KernelParam rightFactor, KernelParam... otherFactors) {
        KernelShortCircuitAnd sca = OperatorFactory
                .newOperator(KernelShortCircuitAnd.class, new Class<?>[] { KernelProgramBlock.class, KernelParam.class,
                        KernelParam.class }, getExecutor(), leftFactor, rightFactor);
        if (ArrayUtils.isNotEmpty(otherFactors)) {
            for (KernelParam factor : otherFactors) {
                sca = OperatorFactory.newOperator(KernelShortCircuitAnd.class, new Class<?>[] { KernelProgramBlock.class,
                        KernelParam.class, KernelParam.class }, getExecutor(), sca, factor);
            }
        }
        return sca;
    }

    @Override
    public final KernelShortCircuitOr or(KernelParam leftFactor, KernelParam rightFactor, KernelParam... otherFactors) {
        KernelShortCircuitOr sco = OperatorFactory
                .newOperator(KernelShortCircuitOr.class, new Class<?>[] { KernelProgramBlock.class, KernelParam.class,
                        KernelParam.class }, getExecutor(), leftFactor, rightFactor);
        if (ArrayUtils.isNotEmpty(otherFactors)) {
            for (KernelParam factor : otherFactors) {
                sco = OperatorFactory.newOperator(KernelShortCircuitOr.class, new Class<?>[] { KernelProgramBlock.class,
                        KernelParam.class, KernelParam.class }, getExecutor(), sco, factor);
            }
        }
        return sco;
    }

    @Override
    public final KernelNot no(KernelParam factor) {
        return OperatorFactory.newOperator(KernelNot.class,
                new Class<?>[] { KernelProgramBlock.class, KernelParam.class }, getExecutor(), factor);
    }

    @Override
    public final KernelTernary ternary(KernelParam exp1, KernelParam exp2, KernelParam exp3) {
        return OperatorFactory.newOperator(KernelTernary.class, new Class<?>[] { KernelProgramBlock.class,
                KernelParam.class, KernelParam.class, KernelParam.class }, getExecutor(), exp1, exp2, exp3);
    }

    // *******************************************************************************************//
    // String Operator //
    // *******************************************************************************************//

    @Override
    public final KernelStrAdd stradd(KernelParam par1, KernelParam... pars) {
        return OperatorFactory.newOperator(KernelStrAdd.class, new Class<?>[] { KernelProgramBlock.class,
                KernelParam.class, KernelParam[].class }, getExecutor(), par1, pars);
    }

    // *******************************************************************************************//
    // instanceof Operator //
    // *******************************************************************************************//

    @Override
    public final KernelInstanceof instanceof_(KernelParam obj, IClass type) {
        return OperatorFactory.newOperator(KernelInstanceof.class, new Class<?>[] { KernelProgramBlock.class,
                KernelParam.class, IClass.class }, getExecutor(), obj, type);
    }

    @Override
    public final KernelInstanceof instanceof_(KernelParam obj, Class<?> type) {
        return instanceof_(obj, getType(type));
    }

    // *******************************************************************************************//
    // method invoke Operator //
    // *******************************************************************************************//

    @Override
    public final MethodInvoker call(KernelParam caller, String methodName, KernelParam... arguments) {
        return OperatorFactory.newOperator(CommonMethodInvoker.class, new Class<?>[] { KernelProgramBlock.class,
                KernelParam.class, String.class, KernelParam[].class }, getExecutor(), caller, methodName,
                arguments);
    }

    @Override
    public MethodInvoker call(String methodName, KernelParam... args) {
    	if(ModifierUtils.isStatic(getMethod().getMeta().getModifiers())) {
            return call(getMethodDeclaringClass(), methodName, args);
    	} else {
            return call(this_(), methodName, args);
    	}
    }

    protected final void invokeVerify(IClass a) {
        if (a.isInterface()) {
            throw new MethodInvokeException("the class " + getExecutor().getMethodDeclaringClass()
                    + " is a interface and interfaces have no static methods");
        }

        if (a.isPrimitive()) {
            throw new MethodInvokeException("the class " + getExecutor().getMethodDeclaringClass()
                    + " is a primitive and primitive cannot as a method invoker owner");
        }
    }

    @Override
    public final MethodInvoker call(IClass owner, String methodName, KernelParam... arguments) {
        invokeVerify(owner);
        return OperatorFactory.newOperator(StaticMethodInvoker.class, new Class<?>[] { KernelProgramBlock.class,
                IClass.class, String.class, KernelParam[].class }, getExecutor(), owner, methodName, arguments);
    }

    public final MethodInvoker call(Class<?> owner, String methodName, KernelParam... arguments) {
        return call(getType(owner), methodName, arguments);
    }

    @Override
    public final MethodInvoker new_(IClass owner, KernelParam... arguments) {
        invokeVerify(owner);
        return OperatorFactory.newOperator(ConstructorInvoker.class, new Class<?>[] { KernelProgramBlock.class,
                IClass.class, KernelParam[].class }, getExecutor(), owner, arguments);
    }

    @Override
    public final MethodInvoker new_(Class<?> owner, KernelParam... arguments) {
        return this.new_(getClassHolder().getType(owner), arguments);
    }

    // *******************************************************************************************//
    // control Operator //
    // *******************************************************************************************//

    @Override
    public final KernelIF if_(KernelIF ifBlock) {
        ifBlock.setParent(getExecutor());
        getQueue().add(ifBlock);
        ifBlock.prepare();
        return ifBlock;
    }

    @Override
    public final KernelWhile while_(KernelWhile whileLoop) {
        whileLoop.setParent(getExecutor());
        getQueue().add(whileLoop);
        whileLoop.prepare();
        return whileLoop;
    }

    @Override
    public final KernelDoWhile dowhile(KernelDoWhile dowhile) {
        dowhile.setParent(getExecutor());
        getQueue().add(dowhile);
        dowhile.prepare();
        return dowhile;
    }

    @Override
    public final KernelForEach for_(final KernelForEach forEach) {
        forEach.setParent(getExecutor());
        getQueue().add(forEach);
        forEach.prepare();
        return forEach;
    }

    @Override
    public final void break_() {
        KernelProgramBlock pb = getExecutor();
        while (pb != null) {
            if (pb instanceof Loop) {
                OperatorFactory.newOperator(GOTO.class, new Class<?>[] { KernelProgramBlock.class, Label.class },
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
        KernelProgramBlock pb = getExecutor();
        while (pb != null) {
            if (pb instanceof Loop) {
                OperatorFactory.newOperator(GOTO.class, new Class<?>[] { KernelProgramBlock.class, Label.class },
                        getExecutor(), ((Loop) pb).getContinueLabel());
                return;
            }
            pb = pb.getParent();
        }
        throw new InternalError("there is on loop!");
    }

    @Override
    public final void throw_(KernelParam exception) {
        OperatorFactory.newOperator(KernelThrow.class, new Class<?>[] { KernelProgramBlock.class, KernelParam.class },
                getExecutor(), exception);
    }

    @Override
    public final KernelTry try_(final KernelTry t) {
        new ExceptionSerialBlock(getExecutor(), t);
        return t;
    }

    @Override
    public final KernelSync sync(KernelSync s) {
        s.setParent(getExecutor());
        getQueue().add(s);
        s.prepare();
        return s;
    }

    @Override
    public final ThisVariable this_() {
        if (ModifierUtils.isStatic(getMethod().getMeta().getModifiers())) {
            throw new ASMSupportException("cannot use \"this\" keyword in static block");
        }
        return getMethod().getThisVariable();
    }

    @Override
    public final GlobalVariable this_(String name) {
        return this_().field(name);
    }

    @Override
    public final SuperVariable super_() {
        if (ModifierUtils.isStatic(getMethod().getMeta().getModifiers())) {
            throw new ASMSupportException("cannot use \"super\" keyword in static block");
        }
        return getMethod().getSuperVariable();
    }

    @Override
    public final MethodInvoker callOrig() {
        if (getMethod().getMode() == AsmsupportConstant.METHOD_CREATE_MODE_MODIFY) {
            String originalMethodName = getMethod().getMeta().getName();
            if (originalMethodName.equals(AsmsupportConstant.CLINIT)) {
                originalMethodName = AsmsupportConstant.CLINIT_PROXY;
            } else if (originalMethodName.equals(AsmsupportConstant.INIT)) {
                originalMethodName = AsmsupportConstant.INIT_PROXY;
            }
            originalMethodName += AsmsupportConstant.METHOD_PROXY_SUFFIX;
            if (ModifierUtils.isStatic(getMethod().getMeta().getModifiers())) {
                return call(getMethodDeclaringClass(), originalMethodName, getMethodArguments());
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
        if (!getMethod().getMeta().getReturnType().equals(Type.VOID_TYPE)) {
            throw new VerifyErrorException("Do not specify a return type! ");
        }
        OperatorFactory.newOperator(KernelReturn.class, new Class<?>[] { KernelProgramBlock.class,
                KernelParam.class }, getExecutor(), null);
    }

    /**
     * run return statement with return value
     * 
     * @param param
     *            return value
     */
    @Override
    public final void return_(KernelParam param) {
        OperatorFactory.newOperator(KernelReturn.class, new Class<?>[] { KernelProgramBlock.class,
                KernelParam.class }, getExecutor(), param);
    }

    @Override
    public Value val(Integer val) {
        return Value.value(getClassHolder(), val);
    }

    @Override
    public Value val(Short val) {
        return Value.value(getClassHolder(), val);
    }

    @Override
    public Value val(Byte val) {
        return Value.value(getClassHolder(), val);
    }

    @Override
    public Value val(Boolean val) {
        return Value.value(getClassHolder(), val);
    }

    @Override
    public Value val(Long val) {
        return Value.value(getClassHolder(), val);
    }

    @Override
    public Value val(Double val) {
        return Value.value(getClassHolder(), val);
    }

    @Override
    public Value val(Character val) {
        return Value.value(getClassHolder(), val);
    }

    @Override
    public Value val(Float val) {
        return Value.value(getClassHolder(), val);
    }

    @Override
    public Value val(IClass val) {
        return Value.value(getClassHolder(), val);
    }

    @Override
    public Value val(Class<?> val) {
        return Value.value(getClassHolder(), val);
    }

    @Override
    public Value val(String val) {
        return Value.value(getClassHolder(), val);
    }

    @Override
    public Value null_(IClass type) {
        return Value.getNullValue(type);
    }

    @Override
    public Value null_(Class<?> type) {
        return Value.getNullValue(getClassHolder().getType(type));
    }

	@Override
    public IClass getType(Class<?> cls) {
        return getClassHolder().getType(cls);
    }
    
    @Override
	public IClass getType(String className) {
        return getClassHolder().getType(className);
	}

	@Override
    public IClass getArrayType(Class<?> cls, int dim) {
        return getClassHolder().getArrayType(cls, dim);
    }

    @Override
    public IClass getArrayType(IClass rootComponent, int dim) {
        return getClassHolder().getArrayType(rootComponent, dim);
    }
    
    /**
     * Set executor
     * 
     * @param exeBlock
     */
    public void setExecutor(KernelProgramBlock exeBlock) {
        executor = exeBlock;
    }

    /**
     * Get the actually executor.
     * 
     * @return
     */
    protected KernelProgramBlock getExecutor() {
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
	}/**
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
     * @param instructionHelper
     */
    public void setInstructionHelper(InstructionHelper instructionHelper) {
        this.instructionHelper = instructionHelper;
    }

    public ClassHolder getClassHolder() {
    	return instructionHelper.getMethod().getClassLoader();
    }
    
    /**
     * Get the {@link InstructionHelper}
     * 
     * @return
     */
    public InstructionHelper getInstructionHelper() {
        return instructionHelper;
    }

    /**
     * Get the parent of current block
     * 
     * @return
     */
    public KernelProgramBlock getParent() {
        return parent;
    }

    public void setParent(KernelProgramBlock block) {
        parent = block;
        setInstructionHelper(block.instructionHelper);
        setScope(new Scope(getMethod().getLocals(), block.getScope()));
    }

    /**
     * Geth the method of current block bellow.
     * 
     * @return
     */
    public AMethod getMethod() {
        return instructionHelper.getMethod();
    }

    /**
     * Get the method argument that's corresponding to current block.
     * 
     * @return
     */
    public LocalVariable[] getMethodArguments() {
        return getMethod().getParameters();
    }

    /**
     * Get the MethodBody that's corresponding to current block.
     * 
     * @return
     */
    protected AbstractKernelMethodBody getMethodBody() {
        return getMethod().getMethodBody();
    }

    /**
     * Get current method owner, generally is the SemiClass it's
     * a class which you want geneate.
     * 
     * @return
     */
    public MutableClass getMethodDeclaringClass() {
        return getMethod().getDeclaringClass();
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
    public void addException(IClass exceptionType) {
        if (throwExceptions == null) {
            throwExceptions = new ThrowExceptionContainer();
        }
        throwExceptions.add(exceptionType);
    }
    
    /**
     * Remove exception type from current exception container.
     * 
     * @param exceptionType
     */
    public void removeException(IClass exceptionType) {
        if (throwExceptions != null) {
            throwExceptions.remove(exceptionType);
        }
    }
    

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj instanceof KernelProgramBlock) {
            return this.scope == ((KernelProgramBlock) obj).scope;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return scope.hashCode();
    }
    
}
