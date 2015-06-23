package cn.wensiqun.asmsupport.sample.client.json.generator.impl;

import java.util.Map;

import cn.wensiqun.asmsupport.client.block.ProgramBlock;
import cn.wensiqun.asmsupport.client.def.Param;
import cn.wensiqun.asmsupport.client.def.param.UncertainParam;
import cn.wensiqun.asmsupport.client.def.var.LocVar;
import cn.wensiqun.asmsupport.client.def.var.Var;
import cn.wensiqun.asmsupport.core.block.KernelProgramBlock;
import cn.wensiqun.asmsupport.sample.client.json.generator.AbstractGeneratorChain;
import cn.wensiqun.asmsupport.standard.def.clazz.ClassHolder;
import cn.wensiqun.asmsupport.standard.def.clazz.IClass;

public class MapGeneratorChain extends AbstractGeneratorChain {

    @Override
    public boolean match(IClass type, ClassHolder holder) {
        return type.isChildOrEqual(holder.getType(Map.class));
    }

    @Override
    protected boolean doGenerate(GeneratorContext context, ProgramBlock<? extends KernelProgramBlock> block,
            LocVar encoder, IClass type, Param value) {
        Var jsonPool = block.this_().field("jsonPool");
        UncertainParam getOrRegisterCall = jsonPool.call("getOrRegister", block.val(Map.class));
        getOrRegisterCall.call("parse", encoder, value);
        return true;
    }


}
