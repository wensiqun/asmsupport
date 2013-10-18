package cn.wensiqun.asmsupport.operators.asmdirect;


import org.objectweb.asm.Label;

import cn.wensiqun.asmsupport.block.ProgramBlock;


/**
 * 
 * @author 温斯群(Joe Wen)
 *
 */
public class Athrow extends ASMDirect {
    
    
    public Athrow(ProgramBlock block) {
		super(block);
	}

	public Athrow(ProgramBlock block, Label lbl) {
        super(block);
    }

    @Override
    protected void executing() {
    	block.getInsnHelper().throwException();
    }
    
}