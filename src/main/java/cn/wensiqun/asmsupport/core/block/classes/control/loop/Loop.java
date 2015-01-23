/**
 * 
 */
package cn.wensiqun.asmsupport.core.block.classes.control.loop;

import cn.wensiqun.asmsupport.org.objectweb.asm.Label;

/**
 * 
 * @author 温斯群(Joe Wen)
 *
 */
public interface Loop {
    
    public Label getBreakLabel();
    
    public Label getContinueLabel();
}
