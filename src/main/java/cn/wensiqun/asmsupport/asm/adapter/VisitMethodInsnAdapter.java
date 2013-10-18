package cn.wensiqun.asmsupport.asm.adapter;

import cn.wensiqun.asmsupport.block.ProgramBlock;
import cn.wensiqun.asmsupport.operators.asmdirect.VisitMethodInsn;

public class VisitMethodInsnAdapter implements VisitXInsnAdapter {

	private int opcode;
	private String owner;
	private String name;
	private String desc;

	public VisitMethodInsnAdapter(int opcode, String owner, String name,
			String desc) {
		this.opcode = opcode;
		this.owner = owner;
		this.name = name;
		this.desc = desc;
	}

	@Override
	public void newVisitXInsnOperator(ProgramBlock block) {
		new VisitMethodInsn(block, opcode, owner, name, desc);
	}

}
