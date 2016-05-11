package cn.wensiqun.asmsupport.client.def.param;

import cn.wensiqun.asmsupport.client.block.KernelProgramBlockCursor;
import cn.wensiqun.asmsupport.client.def.Param;
import cn.wensiqun.asmsupport.client.def.ParamPostern;
import cn.wensiqun.asmsupport.client.def.action.EqualAction;
import cn.wensiqun.asmsupport.client.def.action.NotEqualAction;
import cn.wensiqun.asmsupport.client.def.behavior.CommonBehavior;
import cn.wensiqun.asmsupport.client.def.var.LocVar;
import cn.wensiqun.asmsupport.core.definition.KernelParam;
import cn.wensiqun.asmsupport.standard.def.clazz.IClass;

public abstract class CommonParam extends DummyParam implements CommonBehavior {

	public CommonParam(KernelProgramBlockCursor cursor, KernelParam target) {
		super(cursor, target);
	}
	
	@Override
    public final BoolParam eq(Param para) {
        return new BoolParam(cursor, new EqualAction(cursor), this, para);
    }

    @Override
    public final BoolParam ne(Param para) {
        return new BoolParam(cursor, new NotEqualAction(cursor), this, para);
    }

    @Override
    public final ObjectParam stradd(Param param) {
        return new ObjectParam(cursor, cursor.peek().stradd(target, ParamPostern.getTarget(param)));
    }
    
	@Override
	public LocVar asVar() {
		return new LocVar(cursor, cursor.peek().var(getResultType(), getTarget()));
	}
	
	@Override
	public LocVar asVar(IClass type) {
		return new LocVar(cursor, cursor.peek().var(type, getTarget()));
	}

	@Override
	public LocVar asVar(Class<?> type) {
		return new LocVar(cursor, cursor.peek().var(type, getTarget()));
	}

	@Override
	public LocVar asVar(String varName) {
		return asVar(varName, getResultType());
	}

	@Override
	public LocVar asVar(String varName, IClass type) {
		return new LocVar(cursor, cursor.peek().var(varName, type, getTarget()));
	}

	@Override
	public LocVar asVar(String varName, Class<?> type) {
		return new LocVar(cursor, cursor.peek().var(varName, type, getTarget()));
	}
	
}
