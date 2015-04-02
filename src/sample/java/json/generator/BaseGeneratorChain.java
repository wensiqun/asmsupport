package json.generator;

import cn.wensiqun.asmsupport.client.IF;
import cn.wensiqun.asmsupport.client.ProgramBlock;
import cn.wensiqun.asmsupport.core.Parameterized;
import cn.wensiqun.asmsupport.core.block.ProgramBlockInternal;
import cn.wensiqun.asmsupport.core.clazz.AClass;
import cn.wensiqun.asmsupport.core.clazz.AClassFactory;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.core.utils.AClassUtils;

public class BaseGeneratorChain extends AbstractGeneratorChain {

    @Override
    public boolean match(AClass type) {
        return type.isChildOrEqual(AClassFactory.defType(CharSequence.class)) || 
               AClassUtils.isPrimitiveWrapAClass(type);
    }

    @Override
    protected boolean doGenerate(GeneratorContext context, ProgramBlock<? extends ProgramBlockInternal> block,
            LocalVariable encoder, AClass type, Parameterized value) {
        block.call(encoder, "append", value); 
        return true;
    }


}
