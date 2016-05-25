package cn.wensiqun.asmsupport.client.def.action;

import cn.wensiqun.asmsupport.client.def.Param;
import cn.wensiqun.asmsupport.client.def.param.DummyParam;
import cn.wensiqun.asmsupport.core.operator.Operator;
import cn.wensiqun.asmsupport.core.utils.common.BlockTracker;

public class BandAction extends AbstractBinaryAction {

    public BandAction(BlockTracker tracker) {
        super(tracker, Operator.BIT_AND);
    }

    @Override
    public Param doAction(Param... operands) {
        return new DummyParam(tracker, tracker.track().band(operands[0].getTarget(),
                operands[1].getTarget()));
    }

}
