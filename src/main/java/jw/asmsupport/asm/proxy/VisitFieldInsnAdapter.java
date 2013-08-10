package jw.asmsupport.asm.proxy;

import jw.asmsupport.block.ProgramBlock;
import jw.asmsupport.operators.asmdirect.VisitFieldInsn;

public class VisitFieldInsnAdapter implements VisitXInsnAdapter {

	private int opcode;
	private String owner;
	private String name;
	private String desc;
	
	public VisitFieldInsnAdapter(int opcode, String owner, String name,
			String desc) {
		super();
		this.opcode = opcode;
		this.owner = owner;
		this.name = name;
		this.desc = desc;
	}



	@Override
	public void newVisitXInsnOperator(ProgramBlock block) {
		new VisitFieldInsn(block, opcode, owner, name, desc);
	}

}
