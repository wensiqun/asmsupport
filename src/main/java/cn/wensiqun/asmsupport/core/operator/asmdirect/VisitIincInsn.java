package cn.wensiqun.asmsupport.core.operator.asmdirect;

import cn.wensiqun.asmsupport.core.block.classes.common.ProgramBlockInternal;
import cn.wensiqun.asmsupport.core.operator.AbstractOperator;

public class VisitIincInsn extends AbstractOperator {

	private int var;
	private int increment;
	
	protected VisitIincInsn(ProgramBlockInternal block, int var, int increment) {
		super(block);
		this.var = var;
		this.increment = increment;
	}

	@Override
	protected void doExecute() {
        block.getInsnHelper().getMv().visitIincInsn(var, increment);
	}

}
