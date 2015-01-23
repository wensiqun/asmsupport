package cn.wensiqun.asmsupport.core.operator.asmdirect;

import cn.wensiqun.asmsupport.core.block.classes.common.ProgramBlockInternal;
import cn.wensiqun.asmsupport.core.operator.AbstractOperator;

public class VisitMultiANewArrayInsn extends AbstractOperator {

	private int dims;
	private String desc;

	protected VisitMultiANewArrayInsn(ProgramBlockInternal block, String desc, int dims) {
		super(block);
		this.dims = dims;
		this.desc = desc;
	}

	@Override
	protected void doExecute() {
        block.getInsnHelper().getMv().visitMultiANewArrayInsn(desc, dims);
	}

}
