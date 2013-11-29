/**
 * 
 */
package cn.wensiqun.asmsupport.utils.reflet;

import java.lang.reflect.Modifier;

import org.objectweb.asm.Opcodes;





/**
 * @author 温斯群(Joe Wen)
 *
 */
public class ModifierUtils extends Modifier {
    
    public static boolean isDefault(int mod){
        return !Modifier.isPrivate(mod) && 
           !Modifier.isProtected(mod) && 
           !Modifier.isProtected(mod);
    }
    
    public static boolean isEnum(int mod){
        return (mod & Opcodes.ACC_ENUM) != 0;	
    }
    
    public static boolean isVarargs(int mod){
    	return (mod & Opcodes.ACC_VARARGS) != 0;
    }
    
    public static boolean isBridge(int mod){
    	return (mod & Opcodes.ACC_BRIDGE) != 0;
    }
}
