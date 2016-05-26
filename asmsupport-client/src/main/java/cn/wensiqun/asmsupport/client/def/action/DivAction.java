package cn.wensiqun.asmsupport.client.def.action;

import cn.wensiqun.asmsupport.client.def.Param;
import cn.wensiqun.asmsupport.client.def.param.basic.DummyParam;
import cn.wensiqun.asmsupport.core.operator.Operator;
import cn.wensiqun.asmsupport.core.utils.common.BlockTracker;

public class DivAction extends AbstractBinaryAction {

    public DivAction(BlockTracker tracker) {
        super(tracker, Operator.MUL);
    }

    @Override
    public Param doAction(Param... operands) {
        return new DummyParam(tracker, tracker.track().div(operands[0].getTarget(), operands[1].getTarget()));
    }

}
