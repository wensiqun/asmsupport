package cn.wensiqun.asmsupport.client.def;

import cn.wensiqun.asmsupport.core.definition.KernelParam;
import cn.wensiqun.asmsupport.standard.def.IParam;

public interface Param extends IParam {

    KernelParam getTarget();
    
}
