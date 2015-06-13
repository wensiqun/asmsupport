package cn.wensiqun.asmsupport.sample.client.json.generator.impl;

import cn.wensiqun.asmsupport.client.block.ProgramBlock;
import cn.wensiqun.asmsupport.client.def.Param;
import cn.wensiqun.asmsupport.client.def.var.LocVar;
import cn.wensiqun.asmsupport.core.block.KernelProgramBlock;
import cn.wensiqun.asmsupport.sample.client.json.generator.AbstractGeneratorChain;
import cn.wensiqun.asmsupport.standard.def.clazz.AClass;
import cn.wensiqun.asmsupport.standard.def.clazz.AClassFactory;
import cn.wensiqun.asmsupport.standard.utils.AClassUtils;

public class BaseGeneratorChain extends AbstractGeneratorChain {

    @Override
    public boolean match(AClass type) {
        return type.isPrimitive() ||
               type.isChildOrEqual(AClassFactory.getType(CharSequence.class)) || 
               AClassUtils.isPrimitiveWrapAClass(type);
    }

    @Override
    protected boolean doGenerate(GeneratorContext context, ProgramBlock<? extends KernelProgramBlock> block,
            LocVar encoder, AClass type, Param value) {
        if(type.isChildOrEqual(AClassFactory.getType(CharSequence.class)) ||
           type.equals(AClassFactory.getType(char.class)) ||
           type.equals(AClassFactory.getType(Character.class))) {
            encoder.call("appendDirect", block.val('"')).call("append", value).call("appendDirect", block.val('"'));
        } else {
            encoder.call("append", value); 
        }
        return true;
    }


}
