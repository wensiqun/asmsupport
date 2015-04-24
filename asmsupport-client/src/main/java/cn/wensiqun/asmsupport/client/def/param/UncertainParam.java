package cn.wensiqun.asmsupport.client.def.param;

import cn.wensiqun.asmsupport.client.block.KernelProgramBlockCursor;
import cn.wensiqun.asmsupport.client.def.Param;
import cn.wensiqun.asmsupport.client.def.ParamPostern;
import cn.wensiqun.asmsupport.client.operations.action.AddAction;
import cn.wensiqun.asmsupport.client.operations.action.AndAction;
import cn.wensiqun.asmsupport.client.operations.action.ArrayLengthAction;
import cn.wensiqun.asmsupport.client.operations.action.BandAction;
import cn.wensiqun.asmsupport.client.operations.action.BorAction;
import cn.wensiqun.asmsupport.client.operations.action.BxorAction;
import cn.wensiqun.asmsupport.client.operations.action.DivAction;
import cn.wensiqun.asmsupport.client.operations.action.EqualAction;
import cn.wensiqun.asmsupport.client.operations.action.GreaterEqualAction;
import cn.wensiqun.asmsupport.client.operations.action.GreaterThanAction;
import cn.wensiqun.asmsupport.client.operations.action.InstanceofAction;
import cn.wensiqun.asmsupport.client.operations.action.LessEqualAction;
import cn.wensiqun.asmsupport.client.operations.action.LessThanAction;
import cn.wensiqun.asmsupport.client.operations.action.LogicAndAction;
import cn.wensiqun.asmsupport.client.operations.action.LogicOrAction;
import cn.wensiqun.asmsupport.client.operations.action.LogicXorAction;
import cn.wensiqun.asmsupport.client.operations.action.ModAction;
import cn.wensiqun.asmsupport.client.operations.action.MulAction;
import cn.wensiqun.asmsupport.client.operations.action.NotEqualAction;
import cn.wensiqun.asmsupport.client.operations.action.OrAction;
import cn.wensiqun.asmsupport.client.operations.action.ShiftLeftAction;
import cn.wensiqun.asmsupport.client.operations.action.ShiftRightAction;
import cn.wensiqun.asmsupport.client.operations.action.SubAction;
import cn.wensiqun.asmsupport.client.operations.behavior.UncertainBehavior;
import cn.wensiqun.asmsupport.core.definition.KernelParame;
import cn.wensiqun.asmsupport.core.definition.value.Value;
import cn.wensiqun.asmsupport.standard.def.clazz.AClass;

public class UncertainParam extends DummyParam implements UncertainBehavior {

    public UncertainParam(KernelProgramBlockCursor cursor, KernelParame target) {
        super(cursor, target);
    }

    @Override
    public UncertainParam call(String methodName, Param... arguments) {
        return new UncertainParam(cursor, cursor.getPointer().call(target, methodName, ParamPostern.getTarget(arguments)));
    }

    @Override
    public ObjectParam stradd(Param param) {
        return new ObjectParam(cursor, cursor.getPointer().stradd(target, ParamPostern.getTarget(param)));
    }

    @Override
    public UncertainParam cast(Class<?> type) {
        return new UncertainParam(cursor, cursor.getPointer().checkcast(target, type));
    }

    @Override
    public UncertainParam cast(AClass type) {
        return new UncertainParam(cursor, cursor.getPointer().checkcast(target, type));
    }

    @Override
    public BoolParam instanceof_(Class<?> type) {
        return new BoolParam(cursor, new InstanceofAction(cursor, cursor.getPointer().getType(type)), this);
    }

    @Override
    public BoolParam instanceof_(AClass type) {
        return new BoolParam(cursor, new InstanceofAction(cursor, type), this);
    }

    @Override
    public BoolParam eq(Param para) {
        return new BoolParam(cursor, new EqualAction(cursor), this, para);
    }

    @Override
    public BoolParam ne(Param para) {
        return new BoolParam(cursor, new NotEqualAction(cursor), this, para);
    }

    @Override
    public NumParam add(Param para) {
        return new NumParam(cursor, new AddAction(cursor), this, para);
    }

    @Override
    public NumParam sub(Param para) {
        return new NumParam(cursor, new SubAction(cursor), this, para);
    }

    @Override
    public NumParam mul(Param para) {
        return new NumParam(cursor, new MulAction(cursor), this, para);
    }

    @Override
    public NumParam div(Param para) {
        return new NumParam(cursor, new DivAction(cursor), this, para);
    }

    @Override
    public NumParam mod(Param para) {
        return new NumParam(cursor, new ModAction(cursor), this, para);
    }

    @Override
    public NumParam band(Param para) {
        return new NumParam(cursor, new BandAction(cursor), this, para);
    }

    @Override
    public NumParam bor(Param para) {
        return new NumParam(cursor, new BorAction(cursor), this, para);
    }

    @Override
    public NumParam bxor(Param para) {
        return new NumParam(cursor, new BxorAction(cursor), this, para);
    }

    @Override
    public NumParam shl(Param para) {
        return new NumParam(cursor, new ShiftLeftAction(cursor), this, para);
    }

    @Override
    public NumParam shr(Param para) {
        return new NumParam(cursor, new ShiftRightAction(cursor), this, para);
    }

    @Override
    public NumParam ushr(Param para) {
        return new NumParam(cursor, new ShiftRightAction(cursor), this, para);
    }

    @Override
    public BoolParam gt(Param para) {
        return new BoolParam(cursor, new GreaterThanAction(cursor), this, para);
    }

    @Override
    public BoolParam ge(Param para) {
        return new BoolParam(cursor, new GreaterEqualAction(cursor), this, para);
    }

    @Override
    public BoolParam lt(Param para) {
        return new BoolParam(cursor, new LessThanAction(cursor), this, para);
    }

    @Override
    public BoolParam le(Param para) {
        return new BoolParam(cursor, new LessEqualAction(cursor), this, para);
    }

    @Override
    public BoolParam and(Param param) {
        return new BoolParam(cursor, new AndAction(cursor), this, param);
    }

    @Override
    public BoolParam or(Param param) {
        return new BoolParam(cursor, new OrAction(cursor), this, param);
    }

    @Override
    public BoolParam logicAnd(Param param) {
        return new BoolParam(cursor, new LogicAndAction(cursor), this, param);
    }

    @Override
    public BoolParam logicOr(Param param) {
        return new BoolParam(cursor, new LogicOrAction(cursor), this, param);
    }

    @Override
    public BoolParam logicXor(Param param) {
        return new BoolParam(cursor, new LogicXorAction(cursor), this, param);
    }

    @Override
    public BoolParam and(boolean param) {
        return and(new DummyParam(cursor, Value.value(param)));
    }

    @Override
    public BoolParam or(boolean param) {
        return or(new DummyParam(cursor, Value.value(param)));
    }

    @Override
    public BoolParam logicAnd(boolean param) {
        return logicAnd(new DummyParam(cursor, Value.value(param)));
    }

    @Override
    public BoolParam logicOr(boolean param) {
        return logicOr(new DummyParam(cursor, Value.value(param)));
    }

    @Override
    public BoolParam logicXor(boolean param) {
        return logicXor(new DummyParam(cursor, Value.value(param)));
    }

    @Override
    public UncertainParam load(Param firstDim, Param... dims) {
        return new UncertainParam(cursor, cursor.getPointer().arrayLoad(target, ParamPostern.getTarget(firstDim), ParamPostern.getTarget(dims)));
    }

    @Override
    public NumParam length(Param... dims) {
        Param[] operands = ParamPostern.unionParam(new DummyParam(cursor, target), dims);
        return new NumParam(cursor, new ArrayLengthAction(cursor, operands.length), operands);
    }

    @Override
    public UncertainParam store(Param value, Param firstDim, Param... dims) {
        return new UncertainParam(cursor, cursor.getPointer().arrayStore(target, ParamPostern.getTarget(value), 
                ParamPostern.getTarget(firstDim), ParamPostern.getTarget(dims)));
    }
    
}
