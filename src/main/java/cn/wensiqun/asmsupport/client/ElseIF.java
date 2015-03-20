package cn.wensiqun.asmsupport.client;

import cn.wensiqun.asmsupport.core.Parameterized;
import cn.wensiqun.asmsupport.core.block.control.condition.ElseIFInternal;
import cn.wensiqun.asmsupport.standard.branch.IElseIF;

public abstract class ElseIF extends ProgramBlock<ElseIFInternal> implements IElseIF<ElseIF, Else> {
	
	public ElseIF(Parameterized condition) {
		target = new ElseIFInternal(condition) {
			@Override
			public void body() {
				ElseIF.this.body();
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
