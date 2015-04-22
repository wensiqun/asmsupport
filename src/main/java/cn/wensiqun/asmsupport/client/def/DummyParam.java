package cn.wensiqun.asmsupport.client.def;

import cn.wensiqun.asmsupport.client.Param;
import cn.wensiqun.asmsupport.client.def.var.FieldVar;
import cn.wensiqun.asmsupport.core.definition.KernelParameterized;
import cn.wensiqun.asmsupport.standard.def.clazz.AClass;

public class DummyParam extends Param {

    protected KernelParameterized target;
    
    public DummyParam(KernelParameterized target) {
        this.target = target;
    }

    @Override
    public final AClass getResultType() {
        return target.getResultType();
    }

    @Override
    protected KernelParameterized getTarget() {
        return target;
    }

    @Override
    public FieldVar field(String name) {
        return new FieldVar(target.field(name));
    }
}
