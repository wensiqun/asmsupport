package cn.wensiqun.asmsupport.core.operator.asmdirect;

import cn.wensiqun.asmsupport.core.block.classes.common.ProgramBlockInternal;
import cn.wensiqun.asmsupport.core.operator.AbstractOperator;

public class VisitIntInsn extends AbstractOperator {

	private int opcode;
	private int operand;
	
	protected VisitIntInsn(ProgramBlockInternal block, int opcode, int operand) {
		super(block);
		this.opcode = opcode;
		this.operand = operand;
	}

	@Override
	protected void doExecute() {
        block.getInsnHelper().getMv().visitIntInsn(opcode, operand);
	}

}
