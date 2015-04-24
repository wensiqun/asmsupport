package cn.wensiqun.asmsupport.client.def.param;

import cn.wensiqun.asmsupport.client.block.KernelProgramBlockCursor;
import cn.wensiqun.asmsupport.client.def.Param;
import cn.wensiqun.asmsupport.client.def.var.FieldVar;
import cn.wensiqun.asmsupport.core.definition.KernelParame;
import cn.wensiqun.asmsupport.standard.def.clazz.AClass;

public class DummyParam extends Param {

    protected KernelParame target;
    
    protected KernelProgramBlockCursor cursor;
    
    public DummyParam(KernelProgramBlockCursor cursor, KernelParame target) {
        this.cursor = cursor;
        this.target = target;
    }

    @Override
    public final AClass getResultType() {
        return target.getResultType();
    }

    @Override
    protected KernelParame getTarget() {
        return target;
    }

    @Override
    public final FieldVar field(String name) {
        return new FieldVar(cursor, target.field(name));
    }

    @Override
    public String toString() {
        return getTarget().toString();
    }
    
    
}
