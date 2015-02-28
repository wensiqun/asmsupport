package cn.wensiqun.asmsupport.core.operator;

import cn.wensiqun.asmsupport.core.block.ProgramBlockInternal;

public class BlockEndFlag extends AbstractOperator implements UnreachableCodeCheckSkipable {

	protected BlockEndFlag(ProgramBlockInternal block) {
		super(block);
	}

	@Override
	protected void doExecute() {

	}

}
