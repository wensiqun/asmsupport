/**
 * 
 */
package cn.wensiqun.asmsupport.operators.asmdirect;


import org.objectweb.asm.Label;

import cn.wensiqun.asmsupport.block.ProgramBlock;


/**
 * @author 温斯群(Joe Wen)
 *
 */
public class GOTO extends ASMDirect {
    
    private Label to;

    public GOTO(ProgramBlock block, Label label) {
        super(block);
        this.to = label;
    }

    @Override
    protected void verifyArgument() {
        
    }

    @Override
    protected void checkOutCrement() {
        
    }

    @Override
    protected void checkAsArgument() {
        
    }
    
    @Override
    protected void executing() {
        block.getInsnHelper().goTo(to);
    }


}
