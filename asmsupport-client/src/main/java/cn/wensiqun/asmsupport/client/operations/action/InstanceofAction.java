package cn.wensiqun.asmsupport.client.operations.action;

import cn.wensiqun.asmsupport.client.block.KernelProgramBlockCursor;
import cn.wensiqun.asmsupport.client.def.Param;
import cn.wensiqun.asmsupport.client.def.ParamPostern;
import cn.wensiqun.asmsupport.client.def.param.DummyParam;
import cn.wensiqun.asmsupport.core.operator.Operator;
import cn.wensiqun.asmsupport.standard.def.clazz.AClass;

public class InstanceofAction extends AbstractUnaryAction {

    private AClass type;
    
    public InstanceofAction(KernelProgramBlockCursor cursor, AClass type) {
        super(cursor, Operator.INSTANCE_OF);
        this.type = type;
    }

    @Override
    public Param doAction(Param... operands) {
        return new DummyParam(cursor, cursor.getPointer().instanceof_(ParamPostern.getTarget(operands[0]), type));
    }

}
