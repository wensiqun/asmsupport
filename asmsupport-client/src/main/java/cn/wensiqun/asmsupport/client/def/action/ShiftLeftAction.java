package cn.wensiqun.asmsupport.client.def.action;

import cn.wensiqun.asmsupport.client.def.Param;
import cn.wensiqun.asmsupport.client.def.param.DummyParam;
import cn.wensiqun.asmsupport.core.operator.Operator;
import cn.wensiqun.asmsupport.core.utils.common.BlockTracker;

public class ShiftLeftAction extends AbstractBinaryAction {

    public ShiftLeftAction(BlockTracker tracker) {
        super(tracker, Operator.SHIFT_LEFT);
    }

    @Override
    public Param doAction(Param... operands) {
        return new DummyParam(tracker, tracker.track().shl(operands[0].getTarget(),
                operands[1].getTarget()));
    }

}
