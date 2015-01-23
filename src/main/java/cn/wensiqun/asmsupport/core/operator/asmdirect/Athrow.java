package cn.wensiqun.asmsupport.core.operator.asmdirect;


import cn.wensiqun.asmsupport.core.block.classes.common.ProgramBlockInternal;
import cn.wensiqun.asmsupport.org.objectweb.asm.Label;


/**
 * 
 * @author 温斯群(Joe Wen)
 *
 */
public class Athrow extends ASMDirect {
    
    
	protected Athrow(ProgramBlockInternal block) {
		super(block);
	}

	protected Athrow(ProgramBlockInternal block, Label lbl) {
        super(block);
    }

    @Override
    protected void doExecute() {
    	block.getInsnHelper().throwException();
    }
    
}