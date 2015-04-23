package cn.wensiqun.asmsupport.client.def.param;

import cn.wensiqun.asmsupport.client.def.Param;
import cn.wensiqun.asmsupport.client.def.ParamPostern;
import cn.wensiqun.asmsupport.client.operations.action.ArrayLengthAction;
import cn.wensiqun.asmsupport.client.operations.action.EqualAction;
import cn.wensiqun.asmsupport.client.operations.action.InstanceofAction;
import cn.wensiqun.asmsupport.client.operations.action.NotEqualAction;
import cn.wensiqun.asmsupport.client.operations.behavior.ArrayBehavior;
import cn.wensiqun.asmsupport.core.block.KernelProgramBlock;
import cn.wensiqun.asmsupport.core.definition.KernelParameterized;
import cn.wensiqun.asmsupport.standard.def.clazz.AClass;

public class ArrayParam extends DummyParam implements ArrayBehavior{

    public ArrayParam(KernelProgramBlock block, KernelParameterized arrayReference) {
        super(block, arrayReference);
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

    @Override
    public UncertainParam call(String methodName, Param... arguments) {
        return new UncertainParam(block, block.call(target, methodName, ParamPostern.getTarget(arguments)));
    }

    @Override
    public ObjectParam stradd(Param param) {
        return new ObjectParam(block, block.stradd(target, ParamPostern.getTarget(param)));
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
