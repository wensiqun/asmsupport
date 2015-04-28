package cn.wensiqun.asmsupport.client.def.action;

import cn.wensiqun.asmsupport.client.block.KernelProgramBlockCursor;
import cn.wensiqun.asmsupport.client.def.Param;
import cn.wensiqun.asmsupport.client.def.ParamPostern;
import cn.wensiqun.asmsupport.client.def.param.DummyParam;
import cn.wensiqun.asmsupport.core.operator.Operator;

public class LogicOrAction extends AbstractBinaryAction {

    public LogicOrAction(KernelProgramBlockCursor cursor) {
        super(cursor, Operator.BIT_OR);
    }

    @Override
    public Param doAction(Param... operands) {
        return new DummyParam(cursor, cursor.getPointer().logicalOr(ParamPostern.getTarget(operands[0]), 
                ParamPostern.getTarget(operands[1])));
    }

}
