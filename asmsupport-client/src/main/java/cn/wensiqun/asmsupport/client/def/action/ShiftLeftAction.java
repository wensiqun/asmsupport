package cn.wensiqun.asmsupport.client.def.action;

import cn.wensiqun.asmsupport.client.block.KernelProgramBlockCursor;
import cn.wensiqun.asmsupport.client.def.Param;
import cn.wensiqun.asmsupport.client.def.ParamPostern;
import cn.wensiqun.asmsupport.client.def.param.DummyParam;
import cn.wensiqun.asmsupport.core.operator.Operator;

public class ShiftLeftAction extends AbstractBinaryAction {

    public ShiftLeftAction(KernelProgramBlockCursor cursor) {
        super(cursor, Operator.SHIFT_LEFT);
    }

    @Override
    public Param doAction(Param... operands) {
        return new DummyParam(cursor, cursor.peek().shl(ParamPostern.getTarget(operands[0]),
                ParamPostern.getTarget(operands[1])));
    }

}
