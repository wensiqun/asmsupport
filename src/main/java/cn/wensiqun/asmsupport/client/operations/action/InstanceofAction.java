package cn.wensiqun.asmsupport.client.operations.action;

import cn.wensiqun.asmsupport.client.def.Param;
import cn.wensiqun.asmsupport.client.def.ParamPostern;
import cn.wensiqun.asmsupport.client.def.param.DummyParam;
import cn.wensiqun.asmsupport.core.block.KernelProgramBlock;
import cn.wensiqun.asmsupport.core.operator.Operator;
import cn.wensiqun.asmsupport.standard.def.clazz.AClass;

public class InstanceofAction extends AbstractBinaryAction {

    private AClass type;
    
    public InstanceofAction(KernelProgramBlock block, AClass type) {
        super(block, Operator.INSTANCE_OF);
        this.type = type;
    }

    @Override
    public Param doAction(Param... operands) {
        return new DummyParam(block, block.instanceof_(ParamPostern.getTarget(operands[0]), type));
    }

}
