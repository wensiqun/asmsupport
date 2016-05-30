package cn.wensiqun.asmsupport.client.block;

import cn.wensiqun.asmsupport.client.ClientBridge;
import cn.wensiqun.asmsupport.client.def.Param;
import cn.wensiqun.asmsupport.client.def.ParamPostern;
import cn.wensiqun.asmsupport.client.def.action.*;
import cn.wensiqun.asmsupport.client.def.param.*;
import cn.wensiqun.asmsupport.client.def.param.basic.DummyParam;
import cn.wensiqun.asmsupport.client.def.param.basic.NullParam;
import cn.wensiqun.asmsupport.client.def.param.impl.*;
import cn.wensiqun.asmsupport.client.def.var.FieldVar;
import cn.wensiqun.asmsupport.client.def.var.Super;
import cn.wensiqun.asmsupport.client.def.var.This;
import cn.wensiqun.asmsupport.client.def.var.Var;
import cn.wensiqun.asmsupport.core.block.KernelProgramBlock;
import cn.wensiqun.asmsupport.standard.action.OperationSet;
import cn.wensiqun.asmsupport.standard.def.clazz.IClass;

/**
 * Created by sqwen on 2016/5/26.
 */
public class OperationBlock<B extends KernelProgramBlock> implements OperationSet<Param, Var> {

    private ClientBridge<B> clientBridge;

    @Override
    public final UncertainParam call(Param objRef, String methodName, Param... arguments) {
        return new UncertainParamImpl(getClientBridge(), getGenerateTimeBlock().getBlockTracker().track().call(objRef.getTarget(), methodName, ParamPostern.getTarget(arguments)));
    }

    @Override
    public final UncertainParam call(String methodName, Param... args) {
        return new UncertainParamImpl(getClientBridge(), getGenerateTimeBlock().call(methodName, ParamPostern.getTarget(args)));
    }

    @Override
    public final UncertainParam call(IClass owner, String methodName, Param... arguments) {
        return new UncertainParamImpl(getClientBridge(), getGenerateTimeBlock().call(owner, methodName, ParamPostern.getTarget(arguments)));
    }

    @Override
    public final UncertainParam call(Class<?> owner, String methodName, Param... arguments) {
        return new UncertainParamImpl(getClientBridge(), getGenerateTimeBlock().call(owner, methodName, ParamPostern.getTarget(arguments)));
    }

    @Override
    public final UncertainParam new_(Class<?> owner, Param... arguments) {
        return new UncertainParamImpl(getClientBridge(), getGenerateTimeBlock().new_(owner, ParamPostern.getTarget(arguments)));
    }

    @Override
    public final UncertainParam new_(IClass owner, Param... arguments) {
        return new UncertainParamImpl(getClientBridge(), getGenerateTimeBlock().new_(owner, ParamPostern.getTarget(arguments)));
    }

    @Override
    public final UncertainParam callOrig() {
        return new UncertainParamImpl(getClientBridge(), getGenerateTimeBlock().callOrig());
    }

    @Override
    public final ArrayParam makeArray(IClass aClass, Param... dimensions) {
        return new ArrayParamImpl(getClientBridge(), getGenerateTimeBlock().makeArray(aClass, ParamPostern.getTarget(dimensions)));
    }

    @Override
    public final ArrayParam makeArray(Class<?> arraytype, Param... dimensions) {
        return new ArrayParamImpl(getClientBridge(), getGenerateTimeBlock().makeArray(arraytype, ParamPostern.getTarget(dimensions)));
    }

    @Override
    public final ArrayParam newarray(IClass aClass, Object arrayObject) {
        return new ArrayParamImpl(getClientBridge(), getGenerateTimeBlock().newarray(aClass, ParamPostern.getTarget(arrayObject)));
    }

    /**
     * The second parameter must be a array and element type of array is {@link Param} type
     */
    @Override
    public final ArrayParam newarray(Class<?> type, Object arrayObject) {
        return new ArrayParamImpl(getClientBridge(), getGenerateTimeBlock().newarray(type, ParamPostern.getTarget(arrayObject)));
    }

    @Override
    public final UncertainParam arrayLoad(Param arrayReference, Param pardim, Param... parDims) {
        return new UncertainParamImpl(getClientBridge(), getGenerateTimeBlock().arrayLoad(arrayReference.getTarget(), pardim.getTarget(), ParamPostern.getTarget(parDims)));
    }

    @Override
    public final UncertainParam arrayStore(Param arrayReference, Param value, Param dim, Param... dims) {
        return new UncertainParamImpl(getClientBridge(), getGenerateTimeBlock().arrayStore(arrayReference.getTarget(),
                value.getTarget(), dim.getTarget(), ParamPostern.getTarget(dims)));
    }

    @Override
    public final NumParam arrayLength(Param arrayReference, Param... dims) {
        Param[] operands = ParamPostern.unionParam(arrayReference, dims);
        return new NumParamImpl(getClientBridge(), new ArrayLengthAction(getClientBridge(), operands.length - 1), operands);
    }

    @Override
    public final NumParam add(Param leftFactor, Param rightFactor) {
        return new NumParamImpl(getClientBridge(), new AddAction(getClientBridge()), leftFactor, rightFactor);
    }

    @Override
    public final NumParam sub(Param leftFactor, Param rightFactor) {
        return new NumParamImpl(getClientBridge(), new SubAction(getClientBridge()), leftFactor, rightFactor);
    }

    @Override
    public final NumParam mul(Param leftFactor, Param rightFactor) {
        return new NumParamImpl(getClientBridge(), new MulAction(getClientBridge()), leftFactor, rightFactor);
    }

    @Override
    public final NumParam div(Param leftFactor, Param rightFactor) {
        return new NumParamImpl(getClientBridge(), new DivAction(getClientBridge()), leftFactor, rightFactor);
    }

    @Override
    public final NumParam mod(Param leftFactor, Param rightFactor) {
        return new NumParamImpl(getClientBridge(), new ModAction(getClientBridge()), leftFactor, rightFactor);
    }

    @Override
    public final NumParam reverse(Param factor) {
        return new NumParamImpl(getClientBridge(), new ReverseAction(getClientBridge()), factor);
    }

    @Override
    public final NumParam band(Param leftFactor, Param rightFactor) {
        return new NumParamImpl(getClientBridge(), new BandAction(getClientBridge()), leftFactor, rightFactor);
    }

    @Override
    public final NumParam bor(Param leftFactor, Param rightFactor) {
        return new NumParamImpl(getClientBridge(), new BorAction(getClientBridge()), leftFactor, rightFactor);
    }

    @Override
    public final NumParam bxor(Param leftFactor, Param rightFactor) {
        return new NumParamImpl(getClientBridge(), new BxorAction(getClientBridge()), leftFactor, rightFactor);
    }

    @Override
    public final NumParam shl(Param leftFactor, Param rightFactor) {
        return new NumParamImpl(getClientBridge(), new ShiftLeftAction(getClientBridge()), leftFactor, rightFactor);
    }

    @Override
    public final NumParam shr(Param leftFactor, Param rightFactor) {
        return new NumParamImpl(getClientBridge(), new ShiftRightAction(getClientBridge()), leftFactor, rightFactor);
    }

    @Override
    public final NumParam ushr(Param leftFactor, Param rightFactor) {
        return new NumParamImpl(getClientBridge(), new UnsignedShiftRightAction(getClientBridge()), leftFactor, rightFactor);
    }

    @Override
    public final NumParam predec(Param crement) {
        return new NumParamImpl(getClientBridge(),
                new DummyParam(getClientBridge(), getGenerateTimeBlock().predec(crement.getTarget())));
    }

    @Override
    public final NumParam postdec(Param crement) {
        return new NumParamImpl(getClientBridge(),
                new DummyParam(getClientBridge(), getGenerateTimeBlock().postdec(crement.getTarget())));
    }

    @Override
    public final NumParam preinc(Param crement) {
        return new NumParamImpl(getClientBridge(),
                new DummyParam(getClientBridge(), getGenerateTimeBlock().preinc(crement.getTarget())));
    }

    @Override
    public final NumParam postinc(Param crement) {
        return new NumParamImpl(getClientBridge(),
                new DummyParam(getClientBridge(), getGenerateTimeBlock().postinc(crement.getTarget())));
    }

    @Override
    public final BoolParam gt(Param leftFactor, Param rightFactor) {
        return new BoolParamImpl(getClientBridge(), new GreaterThanAction(getClientBridge()), leftFactor, rightFactor);
    }

    @Override
    public final BoolParam ge(Param leftFactor, Param rightFactor) {
        return new BoolParamImpl(getClientBridge(), new GreaterEqualAction(getClientBridge()), leftFactor, rightFactor);
    }

    @Override
    public final BoolParam lt(Param leftFactor, Param rightFactor) {
        return new BoolParamImpl(getClientBridge(), new LessThanAction(getClientBridge()), leftFactor, rightFactor);
    }

    @Override
    public final BoolParam le(Param leftFactor, Param rightFactor) {
        return new BoolParamImpl(getClientBridge(), new LessEqualAction(getClientBridge()), leftFactor, rightFactor);
    }

    @Override
    public final BoolParam eq(Param leftFactor, Param rightFactor) {
        return new BoolParamImpl(getClientBridge(), new EqualAction(getClientBridge()), leftFactor, rightFactor);
    }

    @Override
    public final BoolParam ne(Param leftFactor, Param rightFactor) {
        return new BoolParamImpl(getClientBridge(), new NotEqualAction(getClientBridge()), leftFactor, rightFactor);
    }

    @Override
    public final BoolParam logicalAnd(Param leftFactor, Param rightFactor) {
        return new BoolParamImpl(getClientBridge(), new LogicAndAction(getClientBridge()), leftFactor, rightFactor);
    }

    @Override
    public final BoolParam logicalOr(Param leftFactor, Param rightFactor) {
        return new BoolParamImpl(getClientBridge(), new LogicOrAction(getClientBridge()), leftFactor, rightFactor);
    }

    @Override
    public final BoolParam logicalXor(Param leftFactor, Param rightFactor) {
        return new BoolParamImpl(getClientBridge(), new LogicXorAction(getClientBridge()), leftFactor, rightFactor);
    }

    @Override
    public final BoolParam and(Param leftFactor, Param rightFactor, Param... otherFactor) {
        return new BoolParamImpl(getClientBridge(), new AndAction(getClientBridge()), leftFactor, rightFactor);
    }

    @Override
    public final BoolParam or(Param leftFactor, Param rightFactor, Param... otherFactor) {
        return new BoolParamImpl(getClientBridge(), new OrAction(getClientBridge()), leftFactor, rightFactor);
    }

    @Override
    public final BoolParam no(Param factor) {
        return new BoolParamImpl(getClientBridge(), new NotAction(getClientBridge()), factor);
    }

    @Override
    public This this_() {
        return new This(getClientBridge(), getGenerateTimeBlock().this_());
    }

    @Override
    public FieldVar this_(String name) {
        return new FieldVar(getClientBridge(), getGenerateTimeBlock().this_(name));
    }

    @Override
    public Super super_() {
        return new Super(getClientBridge(), getGenerateTimeBlock().super_());
    }

    @Override
    public FieldVar field(String name) {
        return new FieldVar(getClientBridge(), getGenerateTimeBlock().field(name));
    }

    @Override
    public final UncertainParam checkcast(Param cc, IClass to) {
        return new UncertainParamImpl(getClientBridge(), getGenerateTimeBlock().checkcast(cc.getTarget(), to));
    }

    @Override
    public final UncertainParam checkcast(Param cc, Class<?> to) {
        return new UncertainParamImpl(getClientBridge(), getGenerateTimeBlock().checkcast(cc.getTarget(), to));
    }

    @Override
    public final NumParam neg(Param factor) {
        return new NumParamImpl(getClientBridge(), new NegAction(getClientBridge()), factor);
    }

    @Override
    public final DummyParam ternary(Param exp1, Param exp2, Param exp3) {
        return new DummyParam(getClientBridge(), getGenerateTimeBlock().ternary(exp1.getTarget(), exp2.getTarget(), exp3.getTarget()));
    }

    @Override
    public final UncertainParam stradd(Param par1, Param... pars) {
        return new UncertainParamImpl(getClientBridge(), getGenerateTimeBlock().stradd(par1.getTarget(), ParamPostern.getTarget(pars)));
    }

    @Override
    public final BoolParam instanceof_(Param obj, IClass type) {
        return new BoolParamImpl(getClientBridge(), new InstanceofAction(getClientBridge(), type), obj);
    }

    @Override
    public final BoolParam instanceof_(Param obj, Class<?> type) {
        return new BoolParamImpl(getClientBridge(), new InstanceofAction(getClientBridge(), getType(type)), obj);
    }

    @Override
    public final NumParam val(Integer val) {
        return new NumParamImpl(getClientBridge(), new DummyParam(getClientBridge(), getGenerateTimeBlock().val(val)));
    }

    @Override
    public final NumParam val(Short val) {
        return new NumParamImpl(getClientBridge(), new DummyParam(getClientBridge(), getGenerateTimeBlock().val(val)));
    }

    @Override
    public final NumParam val(Byte val) {
        return new NumParamImpl(getClientBridge(), new DummyParam(getClientBridge(), getGenerateTimeBlock().val(val)));
    }

    @Override
    public final DummyParam val(Boolean val) {
        return new DummyParam(getClientBridge(), getGenerateTimeBlock().val(val));
    }

    @Override
    public final NumParam val(Long val) {
        return new NumParamImpl(getClientBridge(), new DummyParam(getClientBridge(), getGenerateTimeBlock().val(val)));
    }

    @Override
    public final NumParam val(Double val) {
        return new NumParamImpl(getClientBridge(), new DummyParam(getClientBridge(), getGenerateTimeBlock().val(val)));
    }

    @Override
    public final NumParam val(Character val) {
        return new NumParamImpl(getClientBridge(), new DummyParam(getClientBridge(), getGenerateTimeBlock().val(val)));
    }

    @Override
    public final NumParam val(Float val) {
        return new NumParamImpl(getClientBridge(), new DummyParam(getClientBridge(), getGenerateTimeBlock().val(val)));
    }

    @Override
    public final ObjectParam val(IClass val) {
        return new ObjectParamImpl(getClientBridge(), getGenerateTimeBlock().val(val));
    }

    @Override
    public final ObjectParam val(Class<?> val) {
        return new ObjectParamImpl(getClientBridge(), getGenerateTimeBlock().val(val));
    }

    @Override
    public final ObjectParam val(String val) {
        return new ObjectParamImpl(getClientBridge(), getGenerateTimeBlock().val(val));
    }

    @Override
    public final NullParam null_(IClass type) {
        return new NullParam(getClientBridge(), getGenerateTimeBlock().null_(type));
    }

    @Override
    public final NullParam null_(Class<?> type) {
        return new NullParam(getClientBridge(), getGenerateTimeBlock().null_(type));
    }

    @Override
    public final IClass getType(Class<?> cls) {
        return getGenerateTimeBlock().getType(cls);
    }

    @Override
    public final IClass getType(String cls) {
        return getGenerateTimeBlock().getType(cls);
    }

    @Override
    public final IClass getArrayType(Class<?> cls, int dim) {
        return getGenerateTimeBlock().getArrayType(cls, dim);
    }

    @Override
    public final IClass getArrayType(IClass rootComponent, int dim) {
        return getGenerateTimeBlock().getArrayType(rootComponent, dim);
    }

    protected final KernelProgramBlock getGenerateTimeBlock() {
        return getClientBridge().track();
    }


    public final ClientBridge<B> getClientBridge() {
        return clientBridge;
    }

    /**
     * Get the delegate
     * @return
     */
    public final B getDelegate() {
        return clientBridge.getDelegate();
    }

    final void setKernelBlock(B kernelBlock) {
        this.clientBridge = new ClientBridge(kernelBlock);
    }

}
