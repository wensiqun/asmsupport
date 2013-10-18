/**
 * 
 */
package cn.wensiqun.asmsupport.block.control;

import org.objectweb.asm.Label;

/**
 * 
 * @author 温斯群(Joe Wen)
 *
 */
public interface ILoop {
    
    public Label getBreakLabel();
    
    public Label getContinueLabel();
}
