package cn.wensiqun.asmsupport.client.def.action;

import cn.wensiqun.asmsupport.client.def.Param;
import cn.wensiqun.asmsupport.client.def.ParamPostern;
import cn.wensiqun.asmsupport.client.def.param.DummyParam;
import cn.wensiqun.asmsupport.core.operator.Operator;
import cn.wensiqun.asmsupport.core.utils.common.BlockTracker;
import cn.wensiqun.asmsupport.standard.def.clazz.IClass;

public class InstanceofAction extends AbstractUnaryAction {

    private IClass type;
    
    public InstanceofAction(BlockTracker tracker, IClass type) {
        super(tracker, Operator.INSTANCE_OF);
        this.type = type;
    }

    @Override
    public Param doAction(Param... operands) {
        return new DummyParam(tracker, tracker.track().instanceof_(ParamPostern.getTarget(operands[0]), type));
    }

}
