package cn.wensiqun.asmsupportclient;

import cn.wensiqun.asmsupport.core.block.classes.method.common.StaticMethodBodyInternal;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.generic.body.LocalVariablesBody;
import cn.wensiqun.asmsupport.generic.method.IStaticMethodBody;

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
