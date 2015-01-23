package cn.wensiqun.asmsupportclient;

import cn.wensiqun.asmsupport.core.Parameterized;
import cn.wensiqun.asmsupport.core.block.classes.control.loop.DoWhileInternal;
import cn.wensiqun.asmsupport.generic.loop.IDoWhile;

public abstract class DoWhile extends ProgramBlock<DoWhileInternal> implements IDoWhile {
    
	public DoWhile(Parameterized condition) {
		target = new DoWhileInternal(condition) {
			@Override
			public void body() {
				DoWhile.this.body();
			}
		};
	}
}
