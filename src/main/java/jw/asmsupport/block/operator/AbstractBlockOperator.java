package jw.asmsupport.block.operator;

import jw.asmsupport.asm.InstructionHelper;
import jw.asmsupport.block.IBlockOperators;

public class AbstractBlockOperator {
	
	protected IBlockOperators block;

    protected InstructionHelper insnHelper;
    
	public AbstractBlockOperator(IBlockOperators block, InstructionHelper insnHelper) {
		this.block = block;
		this.insnHelper = insnHelper;
	}
	
	
}
