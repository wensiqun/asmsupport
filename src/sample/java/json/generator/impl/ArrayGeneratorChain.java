package json.generator.impl;

import json.generator.AbstractGeneratorChain;
import cn.wensiqun.asmsupport.client.ForEach;
import cn.wensiqun.asmsupport.client.ProgramBlock;
import cn.wensiqun.asmsupport.core.InternalParameterized;
import cn.wensiqun.asmsupport.core.block.ProgramBlockInternal;
import cn.wensiqun.asmsupport.core.clazz.AClass;
import cn.wensiqun.asmsupport.core.clazz.ArrayClass;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;

public class ArrayGeneratorChain extends AbstractGeneratorChain {

    @Override
    public boolean match(AClass type) {
        return type.isArray();
    }

    @Override
    protected boolean doGenerate(final GeneratorContext context, ProgramBlock<? extends ProgramBlockInternal> block,
            final LocalVariable encoder, final AClass type, InternalParameterized value) {
        block.call(encoder, "appendDirect", block.val('['));
        block.for_(new ForEach(value) {
            @Override
            public void body(LocalVariable e) {
                ArrayClass arrayType = (ArrayClass)type;
                context.getHeader().generate(context, this, encoder, arrayType.getNextDimType(), e);
                call(encoder, "append", val(','));
            }
        });
        block.call(encoder, "trimLastComma");
        block.call(encoder, "appendDirect", block.val(']'));
        return true;
    }


}
