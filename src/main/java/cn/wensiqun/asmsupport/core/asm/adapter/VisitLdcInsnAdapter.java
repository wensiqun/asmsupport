package cn.wensiqun.asmsupport.core.asm.adapter;

import cn.wensiqun.asmsupport.core.block.classes.common.ProgramBlockInternal;
import cn.wensiqun.asmsupport.core.operator.asmdirect.VisitLdcInsn;
import cn.wensiqun.asmsupport.core.operator.numerical.OperatorFactory;

public class VisitLdcInsnAdapter implements VisitXInsnAdapter {

    private Object cts;
	
	public VisitLdcInsnAdapter(Object cts) {
		this.cts = cts;
	}

	@Override
	public void newVisitXInsnOperator(ProgramBlockInternal block) {
		OperatorFactory.newOperator(VisitLdcInsn.class, 
				new Class[]{ProgramBlockInternal.class, Object.class}, 
				block, cts);
		//new VisitLdcInsn(block, cts);
	}

}
