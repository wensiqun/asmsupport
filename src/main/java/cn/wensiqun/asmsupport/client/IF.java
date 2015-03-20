package cn.wensiqun.asmsupport.client;

import cn.wensiqun.asmsupport.core.Parameterized;
import cn.wensiqun.asmsupport.core.block.control.condition.IFInternal;
import cn.wensiqun.asmsupport.standard.branch.IIF;

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
	public ElseIF elseif(ElseIF elseif) {
		target.elseif(elseif.target);
		return elseif;
	}
	
	@Override
	public Else else_(Else els) {
		target.else_(els.target);
		return els;
	}
}
