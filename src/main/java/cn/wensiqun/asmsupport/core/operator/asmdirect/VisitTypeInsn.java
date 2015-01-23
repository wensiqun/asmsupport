package cn.wensiqun.asmsupport.core.operator.asmdirect;

import cn.wensiqun.asmsupport.core.block.classes.common.ProgramBlockInternal;
import cn.wensiqun.asmsupport.core.operator.AbstractOperator;

public class VisitTypeInsn extends AbstractOperator {

	private int opcode;
	private String type;
	
	protected VisitTypeInsn(ProgramBlockInternal block, int opcode, String type) {
		super(block);
		this.opcode = opcode;
		this.type = type;
	}

	@Override
	protected void doExecute() {
        block.getInsnHelper().getMv().visitTypeInsn(opcode, type);
	}

}
