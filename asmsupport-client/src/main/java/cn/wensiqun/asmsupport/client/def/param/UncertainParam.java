package cn.wensiqun.asmsupport.client.def.param;

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
import cn.wensiqun.asmsupport.core.block.KernelProgramBlock;
import cn.wensiqun.asmsupport.core.definition.KernelParameterized;
import cn.wensiqun.asmsupport.standard.def.clazz.AClass;

public class UncertainParam extends DummyParam implements UncertainBehavior {

    public UncertainParam(KernelProgramBlock block, KernelParameterized target) {
        super(block, target);
    }

    @Override
    public UncertainParam call(String methodName, Param... arguments) {
        target = block.call(target, methodName, ParamPostern.getTarget(arguments));
        return this;
    }

    @Override
    public ObjectParam stradd(Param param) {
        return new ObjectParam(block, block.stradd(target, ParamPostern.getTarget(param)));
    }

    @Override
    public UncertainParam cast(Class<?> type) {
        target = block.checkcast(target, type);
        return this;
    }

    @Override
    public UncertainParam cast(AClass type) {
        target = block.checkcast(target, type);
        return this;
    }

    @Override
    public BoolParam instanceof_(Class<?> type) {
        return new BoolParam(block, new InstanceofAction(block, block.getType(type)), this);
    }

    @Override
    public BoolParam instanceof_(AClass type) {
        return new BoolParam(block, new InstanceofAction(block, type), this);
    }

    @Override
    public BoolParam eq(Param para) {
        return new BoolParam(block, new EqualAction(block), this);
    }

    @Override
    public BoolParam ne(Param para) {
        return new BoolParam(block, new NotEqualAction(block), this);
    }

    @Override
    public NumParam add(Param para) {
        return new NumParam(block, new AddAction(block), this, para);
    }

    @Override
    public NumParam sub(Param para) {
        return new NumParam(block, new SubAction(block), this, para);
    }

    @Override
    public NumParam mul(Param para) {
        return new NumParam(block, new MulAction(block), this, para);
    }

    @Override
    public NumParam div(Param para) {
        return new NumParam(block, new DivAction(block), this, para);
    }

    @Override
    public NumParam mod(Param para) {
        return new NumParam(block, new ModAction(block), this, para);
    }

    @Override
    public NumParam band(Param para) {
        return new NumParam(block, new BandAction(block), this, para);
    }

    @Override
    public NumParam bor(Param para) {
        return new NumParam(block, new BorAction(block), this, para);
    }

    @Override
    public NumParam bxor(Param para) {
        return new NumParam(block, new BxorAction(block), this, para);
    }

    @Override
    public NumParam shl(Param para) {
        return new NumParam(block, new ShiftLeftAction(block), this, para);
    }

    @Override
    public NumParam shr(Param para) {
        return new NumParam(block, new ShiftRightAction(block), this, para);
    }

    @Override
    public NumParam ushr(Param para) {
        return new NumParam(block, new ShiftRightAction(block), this, para);
    }

    @Override
    public BoolParam gt(Param para) {
        return new BoolParam(block, new GreaterThanAction(block), this);
    }

    @Override
    public BoolParam ge(Param para) {
        return new BoolParam(block, new GreaterEqualAction(block), this);
    }

    @Override
    public BoolParam lt(Param para) {
        return new BoolParam(block, new LessThanAction(block), this);
    }

    @Override
    public BoolParam le(Param para) {
        return new BoolParam(block, new LessEqualAction(block), this);
    }

    @Override
    public BoolParam and(Param param) {
        return new BoolParam(block, new AndAction(block), this);
    }

    @Override
    public BoolParam or(Param param) {
        return new BoolParam(block, new OrAction(block), this);
    }

    @Override
    public BoolParam logicAnd(Param param) {
        return new BoolParam(block, new LogicAndAction(block), this);
    }

    @Override
    public BoolParam logicOr(Param param) {
        return new BoolParam(block, new LogicOrAction(block), this);
    }

    @Override
    public BoolParam logicXor(Param param) {
        return new BoolParam(block, new LogicXorAction(block), this);
    }

    @Override
    public UncertainParam load(Param firstDim, Param... dims) {
        return new UncertainParam(block, block.arrayLoad(target, ParamPostern.getTarget(firstDim), ParamPostern.getTarget(dims)));
    }

    @Override
    public NumParam length(Param... dims) {
        Param[] operands = ParamPostern.unionParam(new DummyParam(block, target), dims);
        return new NumParam(block, new ArrayLengthAction(block, operands.length), operands);
    }

    @Override
    public UncertainParam store(Param value, Param firstDim, Param... dims) {
        return new UncertainParam(block, block.arrayStore(target, ParamPostern.getTarget(value), 
                ParamPostern.getTarget(firstDim), ParamPostern.getTarget(dims)));
    }
    
}
