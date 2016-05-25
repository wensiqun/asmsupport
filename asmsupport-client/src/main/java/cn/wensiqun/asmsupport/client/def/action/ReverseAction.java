
package cn.wensiqun.asmsupport.client.def.action;

import cn.wensiqun.asmsupport.client.def.Param;
import cn.wensiqun.asmsupport.client.def.param.DummyParam;
import cn.wensiqun.asmsupport.core.operator.Operator;
import cn.wensiqun.asmsupport.core.utils.common.BlockTracker;

public class ReverseAction extends AbstractUnaryAction {

    public ReverseAction(BlockTracker tracker) {
        super(tracker, Operator.REVERSE);
    }

    @Override
    public Param doAction(Param... operands) {
       return new DummyParam(tracker, tracker.track().reverse(operands[0].getTarget()));
    }

}
