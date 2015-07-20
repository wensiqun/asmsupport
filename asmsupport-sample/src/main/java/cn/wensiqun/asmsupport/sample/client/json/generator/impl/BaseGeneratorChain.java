package cn.wensiqun.asmsupport.sample.client.json.generator.impl;

import cn.wensiqun.asmsupport.client.block.ProgramBlock;
import cn.wensiqun.asmsupport.client.def.Param;
import cn.wensiqun.asmsupport.client.def.var.LocVar;
import cn.wensiqun.asmsupport.core.block.KernelProgramBlock;
import cn.wensiqun.asmsupport.sample.client.json.generator.AbstractGeneratorChain;
import cn.wensiqun.asmsupport.standard.def.clazz.ClassHolder;
import cn.wensiqun.asmsupport.standard.def.clazz.IClass;
import cn.wensiqun.asmsupport.standard.utils.IClassUtils;

public class BaseGeneratorChain extends AbstractGeneratorChain {

    @Override
    public boolean match(IClass type, ClassHolder holder) {
        return type.isPrimitive() ||
               type.isChildOrEqual(holder.getType(CharSequence.class)) || 
               IClassUtils.isPrimitiveWrapAClass(type);
    }

    @Override
    protected boolean doGenerate(GeneratorContext context, ProgramBlock<? extends KernelProgramBlock> block,
            LocVar encoder, IClass type, Param value) {
        if(type.isChildOrEqual(block.getType(CharSequence.class)) ||
           type.equals(block.getType(char.class)) ||
           type.equals(block.getType(Character.class))) {
            encoder.call("appendDirect", block.val('"')).call("append", value).call("appendDirect", block.val('"'));
        } else {
            encoder.call("append", value); 
        }
        return true;
    }


}
