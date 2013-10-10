package jw.asmsupport.asm.adapter;

import jw.asmsupport.block.ProgramBlock;
import jw.asmsupport.operators.asmdirect.VisitInsn;

public class VisitInsnAdapter implements VisitXInsnAdapter {

	private int opcode;
	
	public VisitInsnAdapter(int opcode) {
		this.opcode = opcode;
	}

	@Override
	public void newVisitXInsnOperator(ProgramBlock block) {
		new VisitInsn(block, opcode);
	}

}
