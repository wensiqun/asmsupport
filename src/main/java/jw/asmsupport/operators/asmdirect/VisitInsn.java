package jw.asmsupport.operators.asmdirect;

import jw.asmsupport.block.ProgramBlock;
import jw.asmsupport.operators.AbstractOperator;

public class VisitInsn extends AbstractOperator {

	private int opcode;
	
	public VisitInsn(ProgramBlock block, int opcode) {
		super(block);
		this.opcode = opcode;
	}

	@Override
	protected void executing() {
        block.getInsnHelper().getMv().visitInsn(opcode);
	}

}
