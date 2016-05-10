package cn.wensiqun.asmsupport.grammar.def;

import cn.wensiqun.asmsupport.client.def.Param;
import cn.wensiqun.asmsupport.core.definition.KernelParam;
import cn.wensiqun.asmsupport.standard.def.clazz.IClass;
import cn.wensiqun.asmsupport.standard.def.var.IFieldVar;

public class NullFlag extends Param {

	@Override
	public IClass getResultType() {
		throw new UnsupportedOperationException();
	}

	@Override
	public IFieldVar field(String name) {
		throw new UnsupportedOperationException();
	}

	@Override
	protected KernelParam getTarget() {
		throw new UnsupportedOperationException();
	}
	
}
