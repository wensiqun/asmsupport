package json.generator.impl;

import json.generator.AbstractGeneratorChain;
import cn.wensiqun.asmsupport.client.Param;
import cn.wensiqun.asmsupport.client.ForEach;
import cn.wensiqun.asmsupport.client.ProgramBlock;
import cn.wensiqun.asmsupport.client.def.var.LocVar;
import cn.wensiqun.asmsupport.core.block.ProgramBlockInternal;
import cn.wensiqun.asmsupport.standard.def.clazz.AClass;

public class ArrayGeneratorChain extends AbstractGeneratorChain {

    @Override
    public boolean match(AClass type) {
        return type.isArray();
    }

    @Override
    protected boolean doGenerate(final GeneratorContext context, ProgramBlock<? extends ProgramBlockInternal> block,
            final LocVar encoder, final AClass type, Param value) {
        block.call(encoder, "appendDirect", block.val('['));
        block.for_(new ForEach(value) {
            @Override
            public void body(LocVar e) {
                context.getHeader().generate(context, this, encoder, type.getNextDimType(), e);
                call(encoder, "append", val(','));
            }
        });
        block.call(encoder, "trimLastComma");
        block.call(encoder, "appendDirect", block.val(']'));
        return true;
    }


}
