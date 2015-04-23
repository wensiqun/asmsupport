package cn.wensiqun.asmsupport.client.def.param;

import cn.wensiqun.asmsupport.client.def.Param;
import cn.wensiqun.asmsupport.client.def.ParamPostern;
import cn.wensiqun.asmsupport.client.operations.action.EqualAction;
import cn.wensiqun.asmsupport.client.operations.action.InstanceofAction;
import cn.wensiqun.asmsupport.client.operations.action.NotEqualAction;
import cn.wensiqun.asmsupport.client.operations.behavior.ObjectBehavior;
import cn.wensiqun.asmsupport.core.block.KernelProgramBlock;
import cn.wensiqun.asmsupport.core.definition.KernelParameterized;
import cn.wensiqun.asmsupport.standard.def.clazz.AClass;

public class ObjectParam extends DummyParam implements ObjectBehavior {

    public ObjectParam(KernelProgramBlock block, KernelParameterized target) {
        super(block, target);
    }

    @Override
    public UncertainParam call(String methodName, Param... arguments) {
        return new UncertainParam(block, block.call(target, methodName, ParamPostern.getTarget(arguments)));
    }

    @Override
    public ObjectParam stradd(Param param) {
        target = block.stradd(target, ParamPostern.getTarget(param));
        return this;
    }

    @Override
    public UncertainParam cast(Class<?> type) {
        return new UncertainParam(block, block.checkcast(target, type));
    }

    @Override
    public UncertainParam cast(AClass type) {
        return new UncertainParam(block, block.checkcast(target, type));
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

}
