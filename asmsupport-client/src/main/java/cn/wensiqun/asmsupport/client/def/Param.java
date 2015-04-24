package cn.wensiqun.asmsupport.client.def;

import cn.wensiqun.asmsupport.core.definition.KernelParame;
import cn.wensiqun.asmsupport.standard.def.IParam;

public abstract class Param implements IParam {

    protected abstract KernelParame getTarget();
    
}
