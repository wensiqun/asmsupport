package cn.wensiqun.asmsupport.client.def.param;

import cn.wensiqun.asmsupport.client.def.Param;
import cn.wensiqun.asmsupport.client.def.action.EqualAction;
import cn.wensiqun.asmsupport.client.def.action.NotEqualAction;
import cn.wensiqun.asmsupport.client.def.behavior.CommonBehavior;
import cn.wensiqun.asmsupport.client.def.var.LocVar;
import cn.wensiqun.asmsupport.core.definition.KernelParam;
import cn.wensiqun.asmsupport.core.utils.common.BlockTracker;
import cn.wensiqun.asmsupport.standard.def.clazz.IClass;

public abstract class CommonParam extends DummyParam implements CommonBehavior {

	public CommonParam(BlockTracker tracker, KernelParam target) {
		super(tracker, target);
	}
	
	@Override
    public final BoolParam eq(Param para) {
        return new BoolParam(tracker, new EqualAction(tracker), this, para);
    }

    @Override
    public final BoolParam ne(Param para) {
        return new BoolParam(tracker, new NotEqualAction(tracker), this, para);
    }

    @Override
    public final ObjectParam stradd(Param param) {
        return new ObjectParam(tracker, tracker.track().stradd(target, param.getTarget()));
    }
    
	@Override
	public LocVar asVar() {
		return new LocVar(tracker, tracker.track().var(getResultType(), getTarget()));
	}
	
	@Override
	public LocVar asVar(IClass type) {
		return new LocVar(tracker, tracker.track().var(type, getTarget()));
	}

	@Override
	public LocVar asVar(Class<?> type) {
		return new LocVar(tracker, tracker.track().var(type, getTarget()));
	}

	@Override
	public LocVar asVar(String varName) {
		return asVar(varName, getResultType());
	}

	@Override
	public LocVar asVar(String varName, IClass type) {
		return new LocVar(tracker, tracker.track().var(varName, type, getTarget()));
	}

	@Override
	public LocVar asVar(String varName, Class<?> type) {
		return new LocVar(tracker, tracker.track().var(varName, type, getTarget()));
	}
	
}
