/**
 * 
 */
package cn.wensiqun.asmsupport.core.operator.asmdirect;


import cn.wensiqun.asmsupport.core.block.classes.common.ProgramBlockInternal;
import cn.wensiqun.asmsupport.org.objectweb.asm.Label;


/**
 * @author 温斯群(Joe Wen)
 *
 */
public class GOTO extends ASMDirect {
    
    private Label to;

    protected GOTO(ProgramBlockInternal block, Label label) {
        super(block);
        this.to = label;
    }

    @Override
    protected void verifyArgument() {
        
    }

    @Override
    protected void checkCrement() {
        
    }

    @Override
    protected void checkAsArgument() {
        
    }
    
    @Override
    protected void doExecute() {
        block.getInsnHelper().goTo(to);
    }


}
