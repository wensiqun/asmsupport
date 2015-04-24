package cn.wensiqun.asmsupport.standard.def.var;

import cn.wensiqun.asmsupport.standard.def.clazz.AClass;
import cn.wensiqun.asmsupport.standard.def.var.meta.Field;

/**
 * Indicate a field of class.
 * 
 * @author sqwen
 *
 */
public interface IFieldVar extends IVar {
    
    /**
     * Returns the <code>{@link AClass}</code> object representing the class or interface
     * that declares the field represented by this <code>{@link Field}</code> object. it's
     * same to {@link Field#getDeclaringClass()}
     * 
     * @return AClass
     * @see {@link Field#getDeclaringClass()}
     */
    AClass getDeclaringClass();
    
}
