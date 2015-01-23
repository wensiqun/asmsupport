package cn.wensiqun.asmsupport.core.operator.asmdirect;

import cn.wensiqun.asmsupport.core.block.classes.common.ProgramBlockInternal;
import cn.wensiqun.asmsupport.core.operator.AbstractOperator;

public class VisitMethodInsn extends AbstractOperator {

	private int opcode;
	private String owner;
	private String name;
	private String desc;

	protected VisitMethodInsn(ProgramBlockInternal block, int opcode, String owner, String name,
			String desc) {
		super(block);
		this.opcode = opcode;
		this.owner = owner;
		this.name = name;
		this.desc = desc;
	}

	@Override
	protected void doExecute() {
        block.getInsnHelper().getMv().visitMethodInsn(opcode, owner, name, desc);
	}

}
