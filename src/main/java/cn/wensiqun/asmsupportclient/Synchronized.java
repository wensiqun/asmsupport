package cn.wensiqun.asmsupportclient;

import cn.wensiqun.asmsupport.core.Parameterized;
import cn.wensiqun.asmsupport.core.block.classes.common.SynchronizedInternal;
import cn.wensiqun.asmsupport.generic.ISynchronized;
import cn.wensiqun.asmsupport.generic.body.ParameterizedBody;

public abstract class Synchronized extends ProgramBlock<SynchronizedInternal> implements ISynchronized {

	public Synchronized(Parameterized lock) {
		target = new SynchronizedInternal(lock) {

			@Override
			public void body(Parameterized e) {
				Synchronized.this.body(e);
			}
			
		};
	}

}
