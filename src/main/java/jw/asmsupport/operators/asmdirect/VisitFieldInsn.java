package jw.asmsupport.operators.asmdirect;

import jw.asmsupport.block.ProgramBlock;
import jw.asmsupport.operators.AbstractOperator;

public class VisitFieldInsn extends AbstractOperator {

	private int opcode;
	private String owner;
	private String name;
	private String desc;

	public VisitFieldInsn(ProgramBlock block, int opcode, String owner,
			String name, String desc) {
		super(block);
		this.opcode = opcode;
		this.owner = owner;
		this.name = name;
		this.desc = desc;
	}

	@Override
	protected void executing() {
        block.getInsnHelper().getMv().visitFieldInsn(opcode, owner, name, desc);
	}

}
