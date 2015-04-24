package cn.wensiqun.asmsupport.client.def.param;

import cn.wensiqun.asmsupport.client.block.KernelProgramBlockCursor;
import cn.wensiqun.asmsupport.client.def.Param;
import cn.wensiqun.asmsupport.client.def.ParamPostern;
import cn.wensiqun.asmsupport.client.operations.action.EqualAction;
import cn.wensiqun.asmsupport.client.operations.action.InstanceofAction;
import cn.wensiqun.asmsupport.client.operations.action.NotEqualAction;
import cn.wensiqun.asmsupport.client.operations.behavior.ObjectBehavior;
import cn.wensiqun.asmsupport.core.definition.KernelParame;
import cn.wensiqun.asmsupport.standard.def.clazz.AClass;

public class ObjectParam extends DummyParam implements ObjectBehavior {

    public ObjectParam(KernelProgramBlockCursor cursor, KernelParame target) {
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
        return new BoolParam(cursor, new EqualAction(cursor), this);
    }

    @Override
    public BoolParam ne(Param para) {
        return new BoolParam(cursor, new NotEqualAction(cursor), this);
    }

}
