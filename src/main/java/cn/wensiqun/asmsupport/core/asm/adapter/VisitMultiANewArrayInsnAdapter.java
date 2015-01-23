package cn.wensiqun.asmsupport.core.asm.adapter;

import cn.wensiqun.asmsupport.core.block.classes.common.ProgramBlockInternal;
import cn.wensiqun.asmsupport.core.operator.asmdirect.VisitMultiANewArrayInsn;
import cn.wensiqun.asmsupport.core.operator.numerical.OperatorFactory;

public class VisitMultiANewArrayInsnAdapter implements VisitXInsnAdapter {

	private int dims;
	private String desc;
	
	public VisitMultiANewArrayInsnAdapter(String desc, int dims) {
		this.dims = dims;
		this.desc = desc;
	}

	@Override
	public void newVisitXInsnOperator(ProgramBlockInternal block) {
		OperatorFactory.newOperator(VisitMultiANewArrayInsn.class, 
				new Class[]{ProgramBlockInternal.class, String.class, int.class}, 
				block, desc, dims);
		//new VisitMultiANewArrayInsn(block, desc, dims);
	}

}
