package cn.wensiqun.asmsupport.client;

import cn.wensiqun.asmsupport.core.definition.KernelParameterized;
import cn.wensiqun.asmsupport.standard.def.IParameterized;

public interface Param extends IParameterized {

    KernelParameterized getTarget();
    
}
