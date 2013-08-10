package jw.asmsupport.operators.asmdirect;

import jw.asmsupport.block.ProgramBlock;
import jw.asmsupport.operators.AbstractOperator;

public class VisitMethodInsn extends AbstractOperator {

	private int opcode;
	private String owner;
	private String name;
	private String desc;

	public VisitMethodInsn(ProgramBlock block, int opcode, String owner, String name,
			String desc) {
		super(block);
		this.opcode = opcode;
		this.owner = owner;
		this.name = name;
		this.desc = desc;
	}

	@Override
	protected void executing() {
        block.getInsnHelper().getMv().visitMethodInsn(opcode, owner, name, desc);
	}

}
