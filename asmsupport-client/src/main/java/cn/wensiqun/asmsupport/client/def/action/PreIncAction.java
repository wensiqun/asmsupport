package cn.wensiqun.asmsupport.client.def.action;

import cn.wensiqun.asmsupport.client.def.Param;
import cn.wensiqun.asmsupport.client.def.param.DummyParam;
import cn.wensiqun.asmsupport.core.operator.Operator;
import cn.wensiqun.asmsupport.core.utils.common.BlockTracker;

public class PreIncAction extends AbstractUnaryAction {

    public PreIncAction(BlockTracker tracker) {
        super(tracker, Operator.PRE_INC);
    }

    @Override
    public Param doAction(Param... operands) {
        return new DummyParam(tracker, tracker.track().preinc(operands[0].getTarget()));
    }
}
