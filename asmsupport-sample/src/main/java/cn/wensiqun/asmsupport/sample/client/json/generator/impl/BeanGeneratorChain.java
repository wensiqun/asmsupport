package cn.wensiqun.asmsupport.sample.client.json.generator.impl;

import cn.wensiqun.asmsupport.client.block.ProgramBlock;
import cn.wensiqun.asmsupport.client.def.Param;
import cn.wensiqun.asmsupport.client.def.var.FieldVar;
import cn.wensiqun.asmsupport.client.def.var.LocVar;
import cn.wensiqun.asmsupport.core.block.KernelProgramBlock;
import cn.wensiqun.asmsupport.sample.client.json.JSONPool;
import cn.wensiqun.asmsupport.sample.client.json.generator.AbstractGeneratorChain;
import cn.wensiqun.asmsupport.standard.def.clazz.AClass;
import cn.wensiqun.asmsupport.standard.def.clazz.ProductClass;

public class BeanGeneratorChain extends AbstractGeneratorChain {

    @Override
    public boolean match(AClass type) {
        String typeStr = type.getName();
        if(typeStr.startsWith("java.") ||
                typeStr.startsWith("javax.") ) {
            return false;
        }
        return true;
    }

    @Override
    protected boolean doGenerate(GeneratorContext context, ProgramBlock<? extends KernelProgramBlock> block,
            LocVar encoder, AClass type, Param value) {
        JSONPool jsonPool = context.getJsonPool();
        if(type instanceof ProductClass) {
            if(jsonPool.getOrRegister(((ProductClass)type).getReallyClass()) != null) {
                FieldVar pool = block.this_().field("jsonPool");
                pool.call("getOrRegister", block.val(type)).call("parse", encoder, value);
                return true;
            }
        }
        return false;
    }
    
}
