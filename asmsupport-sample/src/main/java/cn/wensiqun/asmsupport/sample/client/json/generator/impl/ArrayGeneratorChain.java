package cn.wensiqun.asmsupport.sample.client.json.generator.impl;

import cn.wensiqun.asmsupport.client.block.ForEach;
import cn.wensiqun.asmsupport.client.block.ProgramBlock;
import cn.wensiqun.asmsupport.client.def.Param;
import cn.wensiqun.asmsupport.client.def.var.LocVar;
import cn.wensiqun.asmsupport.core.block.KernelProgramBlock;
import cn.wensiqun.asmsupport.sample.client.json.generator.AbstractGeneratorChain;
import cn.wensiqun.asmsupport.standard.def.clazz.AClass;

public class ArrayGeneratorChain extends AbstractGeneratorChain {

    @Override
    public boolean match(AClass type) {
        return type.isArray();
    }

    @Override
    protected boolean doGenerate(final GeneratorContext context, ProgramBlock<? extends KernelProgramBlock> block,
            final LocVar encoder, final AClass type, Param value) {
        encoder.call("appendDirect", block.val('['));
        block.for_(new ForEach(value) {
            @Override
            public void body(LocVar e) {
                context.getHeader().generate(context, this, encoder, type.getNextDimType(), e);
                encoder.call("append", val(','));
            }
        });
        encoder.call("trimLastComma");
        encoder.call("appendDirect", block.val(']'));
        return true;
    }


}
