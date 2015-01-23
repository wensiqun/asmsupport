package cn.wensiqun.asmsupport.core.operator.numerical.crement;

import cn.wensiqun.asmsupport.core.Crementable;
import cn.wensiqun.asmsupport.core.block.classes.common.ProgramBlockInternal;
import cn.wensiqun.asmsupport.core.operator.Operators;

public class PostposeIncrment extends AbstractCrement {

	protected PostposeIncrment(ProgramBlockInternal block, Crementable factor) {
		super(block, factor, Operators.INCREMENT, true);
	}

}
