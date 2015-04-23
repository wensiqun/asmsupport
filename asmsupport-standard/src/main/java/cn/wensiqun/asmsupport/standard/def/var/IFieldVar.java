package cn.wensiqun.asmsupport.standard.def.var;

import cn.wensiqun.asmsupport.standard.def.clazz.AClass;


public interface IFieldVar extends IVar {
    
    /**
     * Get the field declaring class.
     * 
     * @return
     */
    AClass getDeclaringClass();
    
}
