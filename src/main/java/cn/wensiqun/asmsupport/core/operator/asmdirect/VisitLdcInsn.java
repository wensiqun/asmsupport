package cn.wensiqun.asmsupport.core.operator.asmdirect;

import cn.wensiqun.asmsupport.core.block.classes.common.ProgramBlockInternal;
import cn.wensiqun.asmsupport.core.operator.AbstractOperator;

public class VisitLdcInsn extends AbstractOperator {

	private Object cts;
	
	protected VisitLdcInsn(ProgramBlockInternal block, Object cts) {
		super(block);
		this.cts = cts;
	}

	@Override
	protected void doExecute() {
        block.getInsnHelper().getMv().visitLdcInsn(cts);
	}

}
