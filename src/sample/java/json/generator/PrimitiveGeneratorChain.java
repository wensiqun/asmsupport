package json.generator;

import cn.wensiqun.asmsupport.client.ProgramBlock;
import cn.wensiqun.asmsupport.core.Parameterized;
import cn.wensiqun.asmsupport.core.block.ProgramBlockInternal;
import cn.wensiqun.asmsupport.core.clazz.AClass;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;

public class PrimitiveGeneratorChain extends AbstractGeneratorChain {

    @Override
    public boolean match(AClass type) {
        return type.isPrimitive();
    }

    @Override
    protected boolean doGenerate(GeneratorContext context, ProgramBlock<? extends ProgramBlockInternal> block,
            LocalVariable encoder, AClass type, Parameterized value) {
        block.call(encoder, "append", value); 
        return true;
    }


}
