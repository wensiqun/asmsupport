package cn.wensiqun.asmsupport.client.def.param;

import cn.wensiqun.asmsupport.client.block.KernelProgramBlockCursor;
import cn.wensiqun.asmsupport.client.def.Param;
import cn.wensiqun.asmsupport.client.def.ParamPostern;
import cn.wensiqun.asmsupport.client.def.action.ArrayLengthAction;
import cn.wensiqun.asmsupport.client.def.action.EqualAction;
import cn.wensiqun.asmsupport.client.def.action.InstanceofAction;
import cn.wensiqun.asmsupport.client.def.action.NotEqualAction;
import cn.wensiqun.asmsupport.client.def.behavior.ArrayBehavior;
import cn.wensiqun.asmsupport.core.definition.KernelParame;
import cn.wensiqun.asmsupport.standard.def.clazz.AClass;

public class ArrayParam extends DummyParam implements ArrayBehavior{

    public ArrayParam(KernelProgramBlockCursor cursor, KernelParame arrayReference) {
        super(cursor, arrayReference);
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
        return new BoolParam(cursor, new EqualAction(cursor), this);
    }

    @Override
    public BoolParam ne(Param para) {
        return new BoolParam(cursor, new NotEqualAction(cursor), this);
    }

}
