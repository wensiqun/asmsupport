package jw.asmsupport.operators.asmdirect;

import jw.asmsupport.block.ProgramBlock;
import jw.asmsupport.operators.AbstractOperator;

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
