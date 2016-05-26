package cn.wensiqun.asmsupport.client.def.action;

import cn.wensiqun.asmsupport.client.def.Param;
import cn.wensiqun.asmsupport.client.def.param.basic.DummyParam;
import cn.wensiqun.asmsupport.core.operator.Operator;
import cn.wensiqun.asmsupport.core.utils.common.BlockTracker;

public class EqualAction extends AbstractBinaryAction {

    public EqualAction(BlockTracker tracker) {
        super(tracker, Operator.EQUAL_TO);
    }

    @Override
    public Param doAction(Param... operands) {
        return new DummyParam(tracker, tracker.track().eq(operands[0].getTarget(), operands[1].getTarget()));
    }

}
