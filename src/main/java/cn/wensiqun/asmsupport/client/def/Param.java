package cn.wensiqun.asmsupport.client.def;

import cn.wensiqun.asmsupport.core.definition.KernelParameterized;
import cn.wensiqun.asmsupport.standard.def.IParameterized;

public abstract class Param implements IParameterized {

    protected abstract KernelParameterized getTarget();
    
}
