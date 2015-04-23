package cn.wensiqun.asmsupport.client.def.param;

import cn.wensiqun.asmsupport.client.def.Param;
import cn.wensiqun.asmsupport.client.def.var.FieldVar;
import cn.wensiqun.asmsupport.core.block.KernelProgramBlock;
import cn.wensiqun.asmsupport.core.definition.KernelParameterized;
import cn.wensiqun.asmsupport.standard.def.clazz.AClass;

public class DummyParam extends Param {

    protected KernelParameterized target;
    
    protected KernelProgramBlock block;
    
    public DummyParam(KernelProgramBlock block, KernelParameterized target) {
        this.block = block;
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
    public final FieldVar field(String name) {
        return new FieldVar(block, target.field(name));
    }
}
