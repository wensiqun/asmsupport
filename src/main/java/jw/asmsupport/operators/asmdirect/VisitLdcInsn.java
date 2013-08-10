package jw.asmsupport.operators.asmdirect;

import jw.asmsupport.block.ProgramBlock;
import jw.asmsupport.operators.AbstractOperator;

public class VisitLdcInsn extends AbstractOperator {

	private Object cts;
	
	public VisitLdcInsn(ProgramBlock block, Object cts) {
		super(block);
		this.cts = cts;
	}

	@Override
	protected void executing() {
        block.getInsnHelper().getMv().visitLdcInsn(cts);
	}

}
