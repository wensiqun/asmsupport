package cn.wensiqun.asmsupport.operators.asmdirect;

import cn.wensiqun.asmsupport.block.ProgramBlock;

/**
 * 
 * @author 温斯群(Joe Wen)
 *
 */
public class NOP extends ASMDirect {
    
    public NOP(ProgramBlock block) {
        super(block);
    }

    @Override
    protected void executing() {
        block.getInsnHelper().nop();
    }
    
}