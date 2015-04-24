package cn.wensiqun.asmsupport.sample.client.json.generator.impl;

import cn.wensiqun.asmsupport.client.block.ProgramBlock;
import cn.wensiqun.asmsupport.client.def.Param;
import cn.wensiqun.asmsupport.client.def.param.UncertainParam;
import cn.wensiqun.asmsupport.client.def.var.LocVar;
import cn.wensiqun.asmsupport.client.def.var.Var;
import cn.wensiqun.asmsupport.core.block.KernelProgramBlock;
import cn.wensiqun.asmsupport.core.clazz.AClassFactory;
import cn.wensiqun.asmsupport.sample.client.json.generator.AbstractGeneratorChain;
import cn.wensiqun.asmsupport.standard.def.clazz.AClass;

public class IterableGeneratorChain extends AbstractGeneratorChain {

    @Override
    public boolean match(AClass type) {
        return type.isChildOrEqual(AClassFactory.getType(Iterable.class));
    }

    @Override
    protected boolean doGenerate(GeneratorContext context, ProgramBlock<? extends KernelProgramBlock> block,
            LocVar encoder, AClass type, Param value) {
        Var jsonPool = block.this_().field("jsonPool");
        UncertainParam getOrRegisterCall = jsonPool.call("getOrRegister", block.val(Iterable.class));
        getOrRegisterCall.call("parse", encoder, value);
        return true;
    }


}
