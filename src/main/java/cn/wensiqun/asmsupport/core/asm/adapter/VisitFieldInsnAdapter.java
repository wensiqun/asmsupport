package cn.wensiqun.asmsupport.core.asm.adapter;

import cn.wensiqun.asmsupport.core.block.classes.common.ProgramBlockInternal;
import cn.wensiqun.asmsupport.core.operator.asmdirect.VisitFieldInsn;
import cn.wensiqun.asmsupport.core.operator.numerical.OperatorFactory;

public class VisitFieldInsnAdapter implements VisitXInsnAdapter {

	private int opcode;
	private String owner;
	private String name;
	private String desc;
	
	public VisitFieldInsnAdapter(int opcode, String owner, String name,
			String desc) {
		super();
		this.opcode = opcode;
		this.owner = owner;
		this.name = name;
		this.desc = desc;
	}



	@Override
	public void newVisitXInsnOperator(ProgramBlockInternal block) {
		OperatorFactory.newOperator(VisitFieldInsn.class, 
				new Class[]{ProgramBlockInternal.class, int.class, String.class,
							String.class, String.class}, 
				block, opcode, owner, name, desc);
	}

}
