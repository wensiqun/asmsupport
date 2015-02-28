package cn.wensiqun.asmsupport.client;

import cn.wensiqun.asmsupport.core.block.method.common.MethodBodyInternal;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.standard.method.IMethodBody;

public abstract class MethodBody extends ProgramBlock<MethodBodyInternal> implements IMethodBody {

	public MethodBody() {
	     target = new MethodBodyInternal() {

			@Override
			public void body(LocalVariable... args) {
				MethodBody.this.body(args);
			}
	    	 
	     };
	}

}
