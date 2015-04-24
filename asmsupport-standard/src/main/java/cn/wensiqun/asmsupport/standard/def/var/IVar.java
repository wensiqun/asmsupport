package cn.wensiqun.asmsupport.standard.def.var;

import cn.wensiqun.asmsupport.standard.def.IParam;
import cn.wensiqun.asmsupport.standard.def.clazz.AClass;

/**
 * Indicate a variable
 * 
 * @author sqwen
 *
 */
public interface IVar extends IParam {

    /**
     * Get variable name
     * 
     * @return String
     */
    String getName();

    /**
     * <p>
     * The method is same to call {@link #getResultType()}, the
     * former type is the variable declare type. For example : 
     * </p>
     * 
     * <pre>
     * List foo = new ArrayList(); 
     * </pre>
     * 
     * <p>
     * So The former type of 'foo' variable is List instead of ArrayList
     * </p>
     * 
     * @return AClass
     */
    AClass getFormerType();
    
    /**
     * Get the variable modifier, it's an integer value which 
     * is a sum value of same field from {@link Opcodes}, the field 
     * is start with 'ACC_', for exampel : "public static" modifiers,
     * the value is  {@link Opcodes#ACC_PUBLIC} + {@link Opcodes#ACC_STATIC}
     * 
     * @return int modifiers
     */
    int getModifiers();

}
