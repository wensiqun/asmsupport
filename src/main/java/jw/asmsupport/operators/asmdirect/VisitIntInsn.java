package jw.asmsupport.operators.asmdirect;

import jw.asmsupport.block.ProgramBlock;
import jw.asmsupport.operators.AbstractOperator;

public class VisitIntInsn extends AbstractOperator {

	private int opcode;
	private int operand;
	
	public VisitIntInsn(ProgramBlock block, int opcode, int operand) {
		super(block);
		this.opcode = opcode;
		this.operand = operand;
	}

	@Override
	protected void executing() {
        block.getInsnHelper().getMv().visitIntInsn(opcode, operand);
	}

}
