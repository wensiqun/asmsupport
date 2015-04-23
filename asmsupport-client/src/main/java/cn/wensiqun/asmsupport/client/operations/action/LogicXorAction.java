package cn.wensiqun.asmsupport.client.operations.action;

import cn.wensiqun.asmsupport.client.def.Param;
import cn.wensiqun.asmsupport.client.def.ParamPostern;
import cn.wensiqun.asmsupport.client.def.param.DummyParam;
import cn.wensiqun.asmsupport.core.block.KernelProgramBlock;
import cn.wensiqun.asmsupport.core.operator.Operator;

public class LogicXorAction extends AbstractBinaryAction {

    public LogicXorAction(KernelProgramBlock block) {
        super(block, Operator.XOR);
    }

    @Override
    public Param doAction(Param... operands) {
        return new DummyParam(block, block.logicalXor(ParamPostern.getTarget(operands[0]), 
                ParamPostern.getTarget(operands[1])));
    }

}
