package cn.wensiqun.asmsupport.client.def.action;

import cn.wensiqun.asmsupport.client.def.Param;
import cn.wensiqun.asmsupport.client.def.ParamPostern;
import cn.wensiqun.asmsupport.client.def.param.DummyParam;
import cn.wensiqun.asmsupport.core.operator.Operator;
import cn.wensiqun.asmsupport.core.utils.common.BlockTracker;

public class LessEqualAction extends AbstractBinaryAction {

    public LessEqualAction(BlockTracker tracker) {
        super(tracker, Operator.LESS_THAN_OR_EQUAL_TO);
    }

    @Override
    public Param doAction(Param... operands) {
        return new DummyParam(tracker, tracker.track().le(ParamPostern.getTarget(operands[0]), ParamPostern.getTarget(operands[1])));
    }

}
