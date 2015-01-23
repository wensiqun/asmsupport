package cn.wensiqun.asmsupport.core.asm.adapter;

import cn.wensiqun.asmsupport.core.block.classes.common.ProgramBlockInternal;
import cn.wensiqun.asmsupport.core.operator.asmdirect.VisitTypeInsn;
import cn.wensiqun.asmsupport.core.operator.numerical.OperatorFactory;

public class VisitTypeInsnAdapter implements VisitXInsnAdapter {

	private int opcode;
	private String type;
	
	public VisitTypeInsnAdapter(int opcode, String type) {
		this.opcode = opcode;
		this.type = type;
	}

	@Override
	public void newVisitXInsnOperator(ProgramBlockInternal block) {
		OperatorFactory.newOperator(VisitTypeInsn.class, 
				new Class[]{ProgramBlockInternal.class, int.class, String.class}, 
				block, opcode, type);
		//new VisitTypeInsn(block, opcode, type);
	}

}
