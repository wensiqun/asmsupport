package cn.wensiqun.asmsupport.client;

import cn.wensiqun.asmsupport.core.Parameterized;
import cn.wensiqun.asmsupport.core.block.SynchronizedInternal;
import cn.wensiqun.asmsupport.standard.sync.ISynchronized;

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
