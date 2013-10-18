package cn.wensiqun.asmsupport.asm.adapter;

import cn.wensiqun.asmsupport.block.ProgramBlock;
import cn.wensiqun.asmsupport.operators.asmdirect.VisitIntInsn;

public class VisitIntInsnAdapter implements VisitXInsnAdapter {

	private int opcode;
	private int operand;
	
	public VisitIntInsnAdapter(int opcode, int operand) {
		this.opcode = opcode;
		this.operand = operand;
	}

	@Override
	public void newVisitXInsnOperator(ProgramBlock block) {
		new VisitIntInsn(block, opcode, operand);
	}

}
