package json.generator;

import cn.wensiqun.asmsupport.client.ForEach;
import cn.wensiqun.asmsupport.client.ProgramBlock;
import cn.wensiqun.asmsupport.core.Parameterized;
import cn.wensiqun.asmsupport.core.block.ProgramBlockInternal;
import cn.wensiqun.asmsupport.core.clazz.AClass;
import cn.wensiqun.asmsupport.core.clazz.AClassFactory;
import cn.wensiqun.asmsupport.core.definition.variable.GlobalVariable;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;

public class IterableGeneratorChain extends AbstractGeneratorChain {

    @Override
    public boolean match(AClass type) {
        return type.isChildOrEqual(AClassFactory.defType(Iterable.class));
    }

    @Override
    protected boolean doGenerate(final GeneratorContext context, ProgramBlock<? extends ProgramBlockInternal> block,
            final LocalVariable encoder, AClass type, Parameterized value) {
        block.call(encoder, "appendDirect", block.val('['));
        block.for_(new ForEach(value) {
            @Override
            public void body(LocalVariable e) {
                LocalVariable elementType = var("elementType", Class.class, call(e, "getClass"));
                GlobalVariable jsonPool = this_().field("jsonPool");
                call(call(jsonPool, "getOrRegister", elementType), "parse", encoder, e);
                call(encoder, "append", val(','));
            }
        });
        block.call(encoder, "trimLastComma");
        block.call(encoder, "appendDirect", block.val(']'));
        return true;
    }


}
