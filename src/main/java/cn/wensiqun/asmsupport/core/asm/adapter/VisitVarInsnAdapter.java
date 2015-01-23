package cn.wensiqun.asmsupport.core.asm.adapter;

import cn.wensiqun.asmsupport.core.block.classes.common.ProgramBlockInternal;
import cn.wensiqun.asmsupport.core.operator.asmdirect.VisitVarInsn;
import cn.wensiqun.asmsupport.core.operator.numerical.OperatorFactory;

public class VisitVarInsnAdapter implements VisitXInsnAdapter {

	private int opcode;
	private int var;
	
	public VisitVarInsnAdapter(int opcode, int var) {
		this.opcode = opcode;
		this.var = var;
	}

	@Override
	public void newVisitXInsnOperator(ProgramBlockInternal block) {
		OperatorFactory.newOperator(VisitVarInsn.class, 
				new Class[]{ProgramBlockInternal.class, int.class, int.class}, 
				block, opcode, var);
		//new VisitVarInsn(block, opcode, var);
	}

}
