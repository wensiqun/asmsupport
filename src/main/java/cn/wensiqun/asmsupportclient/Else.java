package cn.wensiqun.asmsupportclient;

import cn.wensiqun.asmsupport.core.block.classes.control.condition.ElseInternal;
import cn.wensiqun.asmsupport.generic.body.CommonBody;
import cn.wensiqun.asmsupport.generic.branch.IElse;

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
