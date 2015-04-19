package cn.wensiqun.asmsupport.client;

import cn.wensiqun.asmsupport.core.InternalParameterized;
import cn.wensiqun.asmsupport.standard.Parameterized;
import cn.wensiqun.asmsupport.standard.clazz.AClass;

public class ClientParameterized<P extends InternalParameterized> implements Parameterized {

	protected P target;
	
	public ClientParameterized(P target) {
		this.target = target;
	}

	@Override
	public AClass getResultType() {
		return target.getResultType();
	}

	P getTarget() {
		return target;
	}
}
