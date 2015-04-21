package json.generator.impl;

import java.util.Map;

import json.generator.AbstractGeneratorChain;
import cn.wensiqun.asmsupport.client.Param;
import cn.wensiqun.asmsupport.client.ProgramBlock;
import cn.wensiqun.asmsupport.client.def.var.LocVar;
import cn.wensiqun.asmsupport.client.def.var.Var;
import cn.wensiqun.asmsupport.client.operations.Call;
import cn.wensiqun.asmsupport.core.block.ProgramBlockInternal;
import cn.wensiqun.asmsupport.core.clazz.AClassFactory;
import cn.wensiqun.asmsupport.standard.def.clazz.AClass;

public class MapGeneratorChain extends AbstractGeneratorChain {

    @Override
    public boolean match(AClass type) {
        return type.isChildOrEqual(AClassFactory.getType(Map.class));
    }

    @Override
    protected boolean doGenerate(GeneratorContext context, ProgramBlock<? extends ProgramBlockInternal> block,
            LocVar encoder, AClass type, Param value) {
        Var jsonPool = block.this_().field("jsonPool");
        Call getOrRegisterCall = block.call(jsonPool, "getOrRegister", block.val(block.getType(Map.class)));
        block.call(getOrRegisterCall, "parse", encoder, value);
        return true;
    }


}
