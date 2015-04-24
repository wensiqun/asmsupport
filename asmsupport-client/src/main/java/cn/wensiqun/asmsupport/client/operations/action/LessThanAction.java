package cn.wensiqun.asmsupport.client.operations.action;

import cn.wensiqun.asmsupport.client.block.KernelProgramBlockCursor;
import cn.wensiqun.asmsupport.client.def.Param;
import cn.wensiqun.asmsupport.client.def.ParamPostern;
import cn.wensiqun.asmsupport.client.def.param.DummyParam;
import cn.wensiqun.asmsupport.core.operator.Operator;

public class LessThanAction extends AbstractBinaryAction {

    public LessThanAction(KernelProgramBlockCursor cursor) {
        super(cursor, Operator.LESS_THAN);
    }

    @Override
    public Param doAction(Param... operands) {
        return new DummyParam(cursor, cursor.getPointer().lt(ParamPostern.getTarget(operands[0]), ParamPostern.getTarget(operands[1])));
    }

}
