package cn.wensiqun.asmsupport.asm.adapter;

import cn.wensiqun.asmsupport.block.ProgramBlock;
import cn.wensiqun.asmsupport.operators.asmdirect.VisitTypeInsn;

public class VisitTypeInsnAdapter implements VisitXInsnAdapter {

	private int opcode;
	private String type;
	
	public VisitTypeInsnAdapter(int opcode, String type) {
		this.opcode = opcode;
		this.type = type;
	}

	@Override
	public void newVisitXInsnOperator(ProgramBlock block) {
		new VisitTypeInsn(block, opcode, type);
	}

}
