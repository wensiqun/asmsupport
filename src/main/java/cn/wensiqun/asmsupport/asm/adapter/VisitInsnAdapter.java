package cn.wensiqun.asmsupport.asm.adapter;

import cn.wensiqun.asmsupport.block.ProgramBlock;
import cn.wensiqun.asmsupport.operators.asmdirect.VisitInsn;

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
