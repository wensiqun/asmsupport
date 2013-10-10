package jw.asmsupport.asm.adapter;

import jw.asmsupport.block.ProgramBlock;
import jw.asmsupport.operators.asmdirect.VisitMultiANewArrayInsn;

public class VisitMultiANewArrayInsnAdapter implements VisitXInsnAdapter {

	private int dims;
	private String desc;
	
	public VisitMultiANewArrayInsnAdapter(String desc, int dims) {
		this.dims = dims;
		this.desc = desc;
	}

	@Override
	public void newVisitXInsnOperator(ProgramBlock block) {
		new VisitMultiANewArrayInsn(block, desc, dims);
	}

}
