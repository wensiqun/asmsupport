
package cn.wensiqun.asmsupport.client.operations.action;

import cn.wensiqun.asmsupport.client.def.Param;
import cn.wensiqun.asmsupport.client.def.ParamPostern;
import cn.wensiqun.asmsupport.client.def.param.DummyParam;
import cn.wensiqun.asmsupport.core.block.KernelProgramBlock;
import cn.wensiqun.asmsupport.core.operator.Operator;

public class NegAction extends AbstractUnaryAction {

    public NegAction(KernelProgramBlock block) {
        super(block, Operator.NEG);
    }

    @Override
    public Param doAction(Param... operands) {
       return new DummyParam(block, block.neg(ParamPostern.getTarget(operands[0])));
    }

}
