package cn.wensiqun.asmsupport.core.asm.adapter;

import cn.wensiqun.asmsupport.core.block.classes.common.ProgramBlockInternal;
import cn.wensiqun.asmsupport.core.operator.asmdirect.VisitIntInsn;
import cn.wensiqun.asmsupport.core.operator.numerical.OperatorFactory;

public class VisitIntInsnAdapter implements VisitXInsnAdapter {

	private int opcode;
	private int operand;
	
	public VisitIntInsnAdapter(int opcode, int operand) {
		this.opcode = opcode;
		this.operand = operand;
	}

	@Override
	public void newVisitXInsnOperator(ProgramBlockInternal block) {
		OperatorFactory.newOperator(VisitIntInsn.class, 
				new Class[]{ProgramBlockInternal.class, int.class, int.class}, 
				block, opcode, operand);
		//new VisitIntInsn(block, opcode, operand);
	}

}
