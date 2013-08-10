package jw.asmsupport.operators.asmdirect;

import jw.asmsupport.block.ProgramBlock;
import jw.asmsupport.operators.AbstractOperator;

public class VisitVarInsn extends AbstractOperator {

	private int opcode;
	private int var;
	
	public VisitVarInsn(ProgramBlock block, int opcode, int var) {
		super(block);
		this.opcode = opcode;
		this.var = var;
	}

	@Override
	protected void executing() {
        block.getInsnHelper().getMv().visitVarInsn(opcode, var);
	}

}
