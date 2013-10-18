package cn.wensiqun.asmsupport.asm.adapter;

import cn.wensiqun.asmsupport.block.ProgramBlock;
import cn.wensiqun.asmsupport.operators.asmdirect.VisitLdcInsn;

public class VisitLdcInsnAdapter implements VisitXInsnAdapter {

    private Object cts;
	
	public VisitLdcInsnAdapter(Object cts) {
		this.cts = cts;
	}

	@Override
	public void newVisitXInsnOperator(ProgramBlock block) {
		new VisitLdcInsn(block, cts);
	}

}
