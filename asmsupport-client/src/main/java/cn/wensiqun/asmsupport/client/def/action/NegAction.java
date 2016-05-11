
package cn.wensiqun.asmsupport.client.def.action;

import cn.wensiqun.asmsupport.client.def.Param;
import cn.wensiqun.asmsupport.client.def.ParamPostern;
import cn.wensiqun.asmsupport.client.def.param.DummyParam;
import cn.wensiqun.asmsupport.core.operator.Operator;
import cn.wensiqun.asmsupport.core.utils.common.BlockTracker;

public class NegAction extends AbstractUnaryAction {

    public NegAction(BlockTracker tracker) {
        super(tracker, Operator.NEG);
    }

    @Override
    public Param doAction(Param... operands) {
       return new DummyParam(tracker, tracker.track().neg(ParamPostern.getTarget(operands[0])));
    }

}
