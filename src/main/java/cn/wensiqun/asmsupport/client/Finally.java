package cn.wensiqun.asmsupport.client;

import cn.wensiqun.asmsupport.core.block.control.exception.FinallyInternal;
import cn.wensiqun.asmsupport.core.clazz.AClass;
import cn.wensiqun.asmsupport.standard.excep.IFinally;

public abstract class Finally extends ProgramBlock<FinallyInternal> implements IFinally {

	public Finally(AClass aclass) {
		target = new FinallyInternal() {

			@Override
			public void body() {
				Finally.this.body();
			}
		};
	}
	
}
