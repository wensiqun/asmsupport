package cn.wensiqun.asmsupport.client.operations.action;

import cn.wensiqun.asmsupport.client.def.Param;
import cn.wensiqun.asmsupport.client.def.ParamPostern;
import cn.wensiqun.asmsupport.client.def.param.DummyParam;
import cn.wensiqun.asmsupport.core.block.KernelProgramBlock;
import cn.wensiqun.asmsupport.core.operator.Operator;

public class EqualAction extends AbstractBinaryAction {

    public EqualAction(KernelProgramBlock block) {
        super(block, Operator.EQUAL_TO);
    }

    @Override
    public Param doAction(Param... operands) {
        return new DummyParam(block, block.eq(ParamPostern.getTarget(operands[0]), ParamPostern.getTarget(operands[1])));
    }

}
