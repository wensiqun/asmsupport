package cn.wensiqun.asmsupport.core.operator.asmdirect;

import cn.wensiqun.asmsupport.core.block.classes.common.ProgramBlockInternal;
import cn.wensiqun.asmsupport.core.operator.AbstractOperator;

public class VisitVarInsn extends AbstractOperator {

	private int opcode;
	private int var;
	
	protected VisitVarInsn(ProgramBlockInternal block, int opcode, int var) {
		super(block);
		this.opcode = opcode;
		this.var = var;
	}

	@Override
	protected void doExecute() {
        block.getInsnHelper().getMv().visitVarInsn(opcode, var);
	}

}
