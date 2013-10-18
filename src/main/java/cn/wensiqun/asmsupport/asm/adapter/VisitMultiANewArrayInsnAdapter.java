package cn.wensiqun.asmsupport.asm.adapter;

import cn.wensiqun.asmsupport.block.ProgramBlock;
import cn.wensiqun.asmsupport.operators.asmdirect.VisitMultiANewArrayInsn;

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
