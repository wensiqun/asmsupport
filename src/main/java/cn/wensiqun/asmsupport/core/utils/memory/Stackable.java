package cn.wensiqun.asmsupport.core.utils.memory;

import cn.wensiqun.asmsupport.org.objectweb.asm.Type;

/**
 * 
 * @author 温斯群(Joe Wen)
 *
 */
public interface Stackable {
    
    public int getSize();
    
    public Type getType();
}
