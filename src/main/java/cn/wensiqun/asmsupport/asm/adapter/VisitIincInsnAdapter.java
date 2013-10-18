package cn.wensiqun.asmsupport.asm.adapter;

import cn.wensiqun.asmsupport.block.ProgramBlock;
import cn.wensiqun.asmsupport.operators.asmdirect.VisitIincInsn;

public class VisitIincInsnAdapter implements VisitXInsnAdapter {

	private int var;
	private int increment;
	
	public VisitIincInsnAdapter(int var, int increment) {
		this.var = var;
		this.increment = increment;
	}

	@Override
	public void newVisitXInsnOperator(ProgramBlock block) {
		new VisitIincInsn(block, var, increment);
	}

}
