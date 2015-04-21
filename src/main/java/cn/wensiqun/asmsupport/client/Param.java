package cn.wensiqun.asmsupport.client;

import cn.wensiqun.asmsupport.client.def.var.FieldVar;
import cn.wensiqun.asmsupport.core.definition.KernelParameterized;
import cn.wensiqun.asmsupport.standard.def.IParameterized;
import cn.wensiqun.asmsupport.standard.def.clazz.AClass;

public class Param implements IParameterized {

    protected KernelParameterized target;
    
    public Param(KernelParameterized target) {
        this.target = target;
    }

    @Override
    public final AClass getResultType() {
        return target.getResultType();
    }

    protected KernelParameterized getTarget() {
        return target;
    }

    @Override
    public FieldVar field(String name) {
        return new FieldVar(target.field(name));
    }
    
}
