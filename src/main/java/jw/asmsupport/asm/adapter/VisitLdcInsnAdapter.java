package jw.asmsupport.asm.adapter;

import jw.asmsupport.block.ProgramBlock;
import jw.asmsupport.operators.asmdirect.VisitLdcInsn;

public class VisitLdcInsnAdapter implements VisitXInsnAdapter {

    private Object cts;
	
	public VisitLdcInsnAdapter(Object cts) {
		this.cts = cts;
	}

	@Override
	public void newVisitXInsnOperator(ProgramBlock block) {
		new VisitLdcInsn(block, cts);
	}

}
