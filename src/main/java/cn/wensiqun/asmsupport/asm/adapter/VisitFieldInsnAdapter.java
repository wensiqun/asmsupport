package cn.wensiqun.asmsupport.asm.adapter;

import cn.wensiqun.asmsupport.block.ProgramBlock;
import cn.wensiqun.asmsupport.operators.asmdirect.VisitFieldInsn;

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
