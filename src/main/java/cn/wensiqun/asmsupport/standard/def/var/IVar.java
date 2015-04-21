package cn.wensiqun.asmsupport.standard.def.var;

import cn.wensiqun.asmsupport.standard.def.IParameterized;
import cn.wensiqun.asmsupport.standard.def.clazz.AClass;

public interface IVar extends IParameterized {

    /**
     * Get variable name
     * 
     * @return
     */
    String getName();

    /**
     * The method is same to call {@link #getResultType()}
     * 
     * @return AClass
     */
    AClass getFormerType();
    
    /**
     * Get variable modifier
     * 
     * @return
     */
    int getModifiers();

}
