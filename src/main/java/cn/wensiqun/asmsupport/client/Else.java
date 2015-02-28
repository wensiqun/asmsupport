package cn.wensiqun.asmsupport.client;

import cn.wensiqun.asmsupport.core.block.control.condition.ElseInternal;
import cn.wensiqun.asmsupport.standard.branch.IElse;

public abstract class Else extends ProgramBlock<ElseInternal> implements IElse  {

	public Else() {
		target = new ElseInternal() {
			@Override
			public void body() {
				Else.this.body();
			}
		};
	}
	
}
