package cn.wensiqun.asmsupport.client.def.action;

import cn.wensiqun.asmsupport.client.def.Param;
import cn.wensiqun.asmsupport.client.def.param.basic.DummyParam;
import cn.wensiqun.asmsupport.core.operator.Operator;
import cn.wensiqun.asmsupport.core.utils.common.BlockTracker;

public class UnsignedShiftRightAction extends AbstractBinaryAction {

    public UnsignedShiftRightAction(BlockTracker tracker) {
        super(tracker, Operator.UNSIGNED_SHIFT_RIGHT);
    }

    @Override
    public Param doAction(Param... operands) {
        return new DummyParam(tracker, tracker.track().ushr(operands[0].getTarget(),
                operands[1].getTarget()));
    }

}