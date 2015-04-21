package cn.wensiqun.asmsupport.client;

import cn.wensiqun.asmsupport.client.def.var.FieldVar;
import cn.wensiqun.asmsupport.core.definition.KernelParameterized;
import cn.wensiqun.asmsupport.standard.def.clazz.AClass;

public class DummyParam implements Param {

    protected KernelParameterized target;
    
    public DummyParam(KernelParameterized target) {
        this.target = target;
    }

    @Override
    public final AClass getResultType() {
        return target.getResultType();
    }

    @Override
    public KernelParameterized getTarget() {
        return target;
    }

    @Override
    public FieldVar field(String name) {
        return new FieldVar(target.field(name));
    }
    
}
