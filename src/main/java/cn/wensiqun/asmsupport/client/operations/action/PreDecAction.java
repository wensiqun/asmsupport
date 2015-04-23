package cn.wensiqun.asmsupport.client.operations.action;

import cn.wensiqun.asmsupport.client.def.Param;
import cn.wensiqun.asmsupport.client.def.ParamPostern;
import cn.wensiqun.asmsupport.client.def.param.DummyParam;
import cn.wensiqun.asmsupport.core.block.KernelProgramBlock;
import cn.wensiqun.asmsupport.core.operator.Operator;

public class PreDecAction extends AbstractUnaryAction {

    public PreDecAction(KernelProgramBlock block) {
        super(block, Operator.PRE_DEC);
    }

    @Override
    public Param doAction(Param... operands) {
        return new DummyParam(block, block.predec(ParamPostern.getTarget(operands[0])));
    }

}
