
package cn.wensiqun.asmsupport.client.def.action;

import cn.wensiqun.asmsupport.client.def.Param;
import cn.wensiqun.asmsupport.client.def.param.basic.DummyParam;
import cn.wensiqun.asmsupport.core.operator.Operator;
import cn.wensiqun.asmsupport.core.utils.common.BlockTracker;

public class NotAction extends AbstractUnaryAction {

    public NotAction(BlockTracker tracker) {
        super(tracker, Operator.NOT);
    }

    @Override
    public Param doAction(Param... operands) {
       return new DummyParam(tracker, tracker.track().no(operands[0].getTarget()));
    }

}
