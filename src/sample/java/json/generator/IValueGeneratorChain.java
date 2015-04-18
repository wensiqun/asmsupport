package json.generator;

import json.JSONPool;
import cn.wensiqun.asmsupport.client.ProgramBlock;
import cn.wensiqun.asmsupport.core.InternalParameterized;
import cn.wensiqun.asmsupport.core.block.ProgramBlockInternal;
import cn.wensiqun.asmsupport.core.clazz.AClass;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;

public interface IValueGeneratorChain {

    boolean generate(GeneratorContext context, ProgramBlock<? extends ProgramBlockInternal> block, 
            LocalVariable encoder, AClass type, InternalParameterized value);
    
    boolean match(AClass type);
    
    IValueGeneratorChain getNext();
    
    IValueGeneratorChain setNext(IValueGeneratorChain next);
    
    public static class GeneratorContext {
        
        private JSONPool jsonPool;
        
        private IValueGeneratorChain header;
        
        public GeneratorContext(JSONPool jsonPool, IValueGeneratorChain header) {
            this.jsonPool = jsonPool;
            this.header = header;
        }

        public JSONPool getJsonPool() {
            return jsonPool;
        }

        public IValueGeneratorChain getHeader() {
            return header;
        }
        
    }
}
