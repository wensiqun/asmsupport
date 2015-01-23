package cn.wensiqun.asmsupportclient;

import cn.wensiqun.asmsupport.core.block.classes.method.common.CommonMethodBodyInternal;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.generic.method.IMethodBody;

public abstract class MethodBody extends ProgramBlock<CommonMethodBodyInternal> implements IMethodBody {

	public MethodBody() {
	     target = new CommonMethodBodyInternal() {

			@Override
			public void body(LocalVariable... args) {
				MethodBody.this.body(args);
			}
	    	 
	     };
	}

}
