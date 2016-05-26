package cn.wensiqun.asmsupport.client.def.action;

import cn.wensiqun.asmsupport.client.def.Param;
import cn.wensiqun.asmsupport.client.def.param.basic.DummyParam;
import cn.wensiqun.asmsupport.core.operator.Operator;
import cn.wensiqun.asmsupport.core.utils.common.BlockTracker;

public class AddAction extends AbstractBinaryAction {

    public AddAction(BlockTracker tracker) {
        super(tracker, Operator.ADD);
    }

    @Override
    public Param doAction(Param... operands) {
        return new DummyParam(tracker, tracker.track().add(operands[0].getTarget(), operands[1].getTarget()));
    }

}
