package cn.wensiqun.asmsupport.utils;

import org.objectweb.asm.Type;

/**
 * 
 * @author 温斯群(Joe Wen)
 *
 */
public interface Stackable {
    
    public int getSize();
    
    public Type getType();
}
