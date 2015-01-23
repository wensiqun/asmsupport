package cn.wensiqun.asmsupportclient;

import cn.wensiqun.asmsupport.core.Parameterized;
import cn.wensiqun.asmsupport.core.block.classes.control.condition.IFInternal;
import cn.wensiqun.asmsupport.generic.body.CommonBody;
import cn.wensiqun.asmsupport.generic.branch.IIF;

public abstract class IF extends ProgramBlock<IFInternal> implements IIF<ElseIF, Else> {

	public IF(Parameterized condition) {
		target = new IFInternal(condition) {
			@Override
			public void body() {
				IF.this.body();
			}
		};
	}
	
	@Override
	public ElseIF _elseif(ElseIF elseif) {
		target._elseif(elseif.target);
		return elseif;
	}
	
	@Override
	public Else _else(Else els) {
		target._else(els.target);
		return els;
	}
}
