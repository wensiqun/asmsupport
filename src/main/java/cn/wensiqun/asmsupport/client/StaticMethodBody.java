package cn.wensiqun.asmsupport.client;

import cn.wensiqun.asmsupport.core.block.method.common.StaticMethodBodyInternal;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.standard.method.IStaticMethodBody;

public abstract class StaticMethodBody extends ProgramBlock<StaticMethodBodyInternal> implements IStaticMethodBody {

	public StaticMethodBody() {
	     target = new StaticMethodBodyInternal() {

			@Override
			public void body(LocalVariable... args) {
				StaticMethodBody.this.body(args);
			}
	    	 
	     };
	}

}
