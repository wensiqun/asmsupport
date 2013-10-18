package cn.wensiqun.asmsupport.block.operator;

import cn.wensiqun.asmsupport.asm.InstructionHelper;
import cn.wensiqun.asmsupport.block.IBlockOperators;

public class AbstractBlockOperator {
	
	protected IBlockOperators block;

    protected InstructionHelper insnHelper;
    
	public AbstractBlockOperator(IBlockOperators block, InstructionHelper insnHelper) {
		this.block = block;
		this.insnHelper = insnHelper;
	}
	
	
}
