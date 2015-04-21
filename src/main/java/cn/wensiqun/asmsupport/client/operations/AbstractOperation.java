package cn.wensiqun.asmsupport.client.operations;

import cn.wensiqun.asmsupport.client.Param;
import cn.wensiqun.asmsupport.core.definition.KernelParameterized;

public abstract class AbstractOperation<T extends KernelParameterized> extends Param {

    protected AbstractOperation(T target) {
        super(target);
    }

}
