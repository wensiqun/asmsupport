package cn.wensiqun.asmsupport.standard.def;

import cn.wensiqun.asmsupport.standard.def.clazz.AClass;
import cn.wensiqun.asmsupport.standard.def.var.IFieldVar;

public interface IParameterized {
    
    /**
     * 获取当前参数化类型的返回参数
     * @return
     */
    AClass getResultType();

    /**
     * Get field
     * @param name
     * @return T the sub type of {@link IFieldVar}
     */
    IFieldVar field(String name);
}
