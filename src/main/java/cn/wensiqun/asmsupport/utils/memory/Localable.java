/**
 * 
 */
package cn.wensiqun.asmsupport.utils.memory;

import org.objectweb.asm.Type;

/**
 * 
 * @author 温斯群(Joe Wen)
 *
 */
public interface Localable {

    public Type getDeclareType();
    
    public Type getActuallyType();
    
    public String getName();
    
    public int[] getPositions();
}
