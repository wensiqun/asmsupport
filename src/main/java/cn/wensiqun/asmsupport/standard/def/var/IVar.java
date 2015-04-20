package cn.wensiqun.asmsupport.standard.def.var;

import cn.wensiqun.asmsupport.core.clazz.AClass;
import cn.wensiqun.asmsupport.standard.Parameterized;

public interface IVar extends Parameterized {

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
