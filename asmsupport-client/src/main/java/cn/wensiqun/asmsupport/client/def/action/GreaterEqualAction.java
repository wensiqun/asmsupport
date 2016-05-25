package cn.wensiqun.asmsupport.client.def.action;

import cn.wensiqun.asmsupport.client.def.Param;
import cn.wensiqun.asmsupport.client.def.param.DummyParam;
import cn.wensiqun.asmsupport.core.operator.Operator;
import cn.wensiqun.asmsupport.core.utils.common.BlockTracker;

public class GreaterEqualAction extends AbstractBinaryAction {

    public GreaterEqualAction(BlockTracker tracker) {
        super(tracker, Operator.GREATER_THAN_OR_EQUAL_TO);
    }

    @Override
    public Param doAction(Param... operands) {
        return new DummyParam(tracker, tracker.track().ge(operands[0].getTarget(), operands[1].getTarget()));
    }

}
