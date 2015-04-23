package cn.wensiqun.asmsupport.client.operations.action;

import cn.wensiqun.asmsupport.client.def.Param;
import cn.wensiqun.asmsupport.client.def.ParamPostern;
import cn.wensiqun.asmsupport.client.def.param.DummyParam;
import cn.wensiqun.asmsupport.core.block.KernelProgramBlock;
import cn.wensiqun.asmsupport.core.operator.Operator;

public class ShiftLeftAction extends AbstractBinaryAction {

    public ShiftLeftAction(KernelProgramBlock block) {
        super(block, Operator.SHIFT_LEFT);
    }

    @Override
    public Param doAction(Param... operands) {
        return new DummyParam(block, block.shl(ParamPostern.getTarget(operands[0]), 
                ParamPostern.getTarget(operands[1])));
    }

}
