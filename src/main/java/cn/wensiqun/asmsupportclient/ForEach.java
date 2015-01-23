package cn.wensiqun.asmsupportclient;

import cn.wensiqun.asmsupport.core.block.classes.control.loop.ForEachInternal;
import cn.wensiqun.asmsupport.core.definition.variable.ExplicitVariable;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.generic.loop.IForEach;

public abstract class ForEach extends ProgramBlock<ForEachInternal> implements IForEach {

	public ForEach(ExplicitVariable iteratorVar) {
		target = new ForEachInternal(iteratorVar) {

			@Override
			public void body(LocalVariable e) {
				ForEach.this.body(e);
			}
			
		};
	}
	
}
