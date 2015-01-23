package cn.wensiqun.asmsupportclient;

import cn.wensiqun.asmsupport.core.block.classes.control.exception.FinallyInternal;
import cn.wensiqun.asmsupport.core.clazz.AClass;
import cn.wensiqun.asmsupport.generic.excep.IFinally;

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
