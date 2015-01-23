package cn.wensiqun.asmsupport.core.operator.asmdirect;

import cn.wensiqun.asmsupport.core.block.classes.common.ProgramBlockInternal;
import cn.wensiqun.asmsupport.core.operator.AbstractOperator;

public class VisitInsn extends AbstractOperator {

	private int opcode;
	
	protected VisitInsn(ProgramBlockInternal block, int opcode) {
		super(block);
		this.opcode = opcode;
	}

	@Override
	protected void doExecute() {
        block.getInsnHelper().getMv().visitInsn(opcode);
	}

}
