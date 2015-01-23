package cn.wensiqun.asmsupport.core.operator.asmdirect;

import cn.wensiqun.asmsupport.core.block.classes.common.ProgramBlockInternal;
import cn.wensiqun.asmsupport.core.operator.UnreachableCodeCheckSkipable;

/**
 * 
 * @author 温斯群(Joe Wen)
 *
 */
public class NOP extends ASMDirect implements UnreachableCodeCheckSkipable {
    
	protected NOP(ProgramBlockInternal block) {
        super(block);
    }

    @Override
    protected void doExecute() {
        block.getInsnHelper().nop();
    }
    
}