package json.generator;

import cn.wensiqun.asmsupport.client.ForEach;
import cn.wensiqun.asmsupport.client.ProgramBlock;
import cn.wensiqun.asmsupport.core.Parameterized;
import cn.wensiqun.asmsupport.core.block.ProgramBlockInternal;
import cn.wensiqun.asmsupport.core.clazz.AClass;
import cn.wensiqun.asmsupport.core.clazz.AClassFactory;
import cn.wensiqun.asmsupport.core.definition.variable.GlobalVariable;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;

public class MapGeneratorChain extends AbstractGeneratorChain {

    @Override
    public boolean match(AClass type) {
        return type.isChildOrEqual(AClassFactory.defType(Iterable.class));
    }

    @Override
    protected boolean doGenerate(final GeneratorContext context, ProgramBlock<? extends ProgramBlockInternal> block,
            final LocalVariable encoder, AClass type, Parameterized value) {
        block.call(encoder, "appendDirect", block.val('{'));
        
        block.for_(new ForEach(block.call(value, "entrySet")) {
            @Override
            public void body(LocalVariable entry) {
                //encoder.appendDirect('"')
                call(encoder, "appendDirect", val('"'));
                //encoder.append(entry.getKey())
                call(encoder, "append", call(entry, "getKey"));
                //encoder.append('"')
                call(encoder, "appendDirect", val('"'));
                //encoder.append(':')
                call(encoder, "appendDirect", val(':'));
                
                //Object entryValue = entry.getValue(); 
                LocalVariable entryValue = var("entryValue", Object.class, call(entry, "getValue"));
                
                //Class elementType = entryValue.getClass();
                LocalVariable elementType = var("elementType", Class.class, call(entryValue, "getClass"));
                
                //JSONPool jsonPool = this.jsonPool;
                GlobalVariable jsonPool = this_().field("jsonPool");
                
                //jsonPool.getOrRegister(elementType).parse(encoder, entryValue);
                call(call(jsonPool, "getOrRegister", elementType), "parse", encoder, entryValue);
                
                //encoder.appendDirect(',');
                call(encoder, "appendDirect", val(','));
            }
        });
        block.call(encoder, "trimLastComma");
        block.call(encoder, "appendDirect", block.val('}'));
        return true;
    }


}
