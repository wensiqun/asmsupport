package cn.wensiqun.asmsupport.standard.def.clazz;

import cn.wensiqun.asmsupport.org.objectweb.asm.Opcodes;
import cn.wensiqun.asmsupport.org.objectweb.asm.Type;
import cn.wensiqun.asmsupport.standard.def.var.meta.Field;


/**
 * Indicate a java class.
 * 
 * 
 * @author 温斯群(Joe Wen)
 *
 */
public interface IClass { 
	
    /**
     * Check the class is array type.
     * 
     * @return true if current class is array.
     */
    boolean isArray();
    
    /**
     * Get the dimension number of current class. 
     * 
     * @return return dimension number if current class is array, otherwise return -1
     */
    int getDimension();

    /**
     * Get the package name.
     * 
     * @return String package name
     */
    String getPackage();

    /**
     * Get the qualified name of current class.
     * 
     * @return class qualified name
     */
    String getName();

    /**
     * Get the class modifier, it's an integer value which 
     * is a sum value of same field from {@link Opcodes}, the field 
     * is start with 'ACC_', for exampel : "public final class" modifiers,
     * the value is  {@link Opcodes#ACC_PUBLIC} + {@link Opcodes#ACC_FINAL}
     * 
     * @return int the modifiers
     */
    int getModifiers();

    /**
     * Get the class version, is should be (see {@link Opcodes#V1_1} ~ {@link Opcodes#V_MAX})
     * 
     * @return 
     */
    int getVersion();

    /**
     * Get the super class.
     * 
     * @return Class super class
     */
    Class<?> getSuperClass();

    /**
     * Get all interfaces
     * 
     * @return all interface
     */
    Class<?>[] getInterfaces();

    /**
     * Get the class description, the description is the same to {@link Type#getDescriptor()},
     * It's the indication of class in jvm.
     * 
     * @return String class description
     */
    String getDescription();
    
    /**
     * Get all field of current class, it's possible contain static and non-static field.
     * 
     * @param name
     * @return
     */
    Field getField(String name);
    
    
    /**
     * Get the asm 
     * @return
     */
    Type getType();

    /**
     * Check the class is primitive
     * 
     */
    boolean isPrimitive();

    
    /**
     * 
     * Check the class is interface
     * 
     */
    boolean isInterface();

    /**
     * Check the class is abstract class
     * 
     */
    boolean isAbstract();
    
    /**
     * When do cast will get the most specially type according the cast order.
     * 
     */
    int getCastOrder();
    
    
    /**
     * Check the class exist static init block.
     * 
     */
    boolean existStaticInitBlock();
    
    
    /**
     * if the class is array type, get the next dim type.
     * 
     */
    public IClass getNextDimType();
    
    /**
     * IF current class is array type, than get the basic type, for example : 
     * <pre>
     * String[][][] str;
     * </pre>
     * 
     * The root component class is String in preceding code.
     */
    public IClass getRootComponentClass();
    
}
