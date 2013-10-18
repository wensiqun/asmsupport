package cn.wensiqun.asmsupport.operators.asmdirect;

import cn.wensiqun.asmsupport.block.ProgramBlock;
import cn.wensiqun.asmsupport.operators.AbstractOperator;

public class VisitMultiANewArrayInsn extends AbstractOperator {

	private int dims;
	private String desc;

	public VisitMultiANewArrayInsn(ProgramBlock block, String desc, int dims) {
		super(block);
		this.dims = dims;
		this.desc = desc;
	}

	@Override
	protected void executing() {
        block.getInsnHelper().getMv().visitMultiANewArrayInsn(desc, dims);
	}

}
