package cn.wensiqun.asmsupport.core.operator.numerical.crement;

import cn.wensiqun.asmsupport.core.Crementable;
import cn.wensiqun.asmsupport.core.block.ProgramBlockInternal;
import cn.wensiqun.asmsupport.core.operator.Operators;

public class PreposeIncrment extends AbstractCrement {

	protected PreposeIncrment(ProgramBlockInternal block, Crementable factor) {
		super(block, factor, Operators.INCREMENT, false);
	}

}
