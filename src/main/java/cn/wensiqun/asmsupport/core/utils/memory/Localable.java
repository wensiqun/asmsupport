/**
 * 
 */
package cn.wensiqun.asmsupport.core.utils.memory;

import cn.wensiqun.asmsupport.org.objectweb.asm.Type;

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
