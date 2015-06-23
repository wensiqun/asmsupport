package cn.wensiqun.asmsupport.sample.client.json.generator;

import cn.wensiqun.asmsupport.client.block.ProgramBlock;
import cn.wensiqun.asmsupport.client.def.Param;
import cn.wensiqun.asmsupport.client.def.var.LocVar;
import cn.wensiqun.asmsupport.core.block.KernelProgramBlock;
import cn.wensiqun.asmsupport.sample.client.json.JSONPool;
import cn.wensiqun.asmsupport.standard.def.clazz.ClassHolder;
import cn.wensiqun.asmsupport.standard.def.clazz.IClass;

public interface IValueGeneratorChain {

    boolean generate(GeneratorContext context, ProgramBlock<? extends KernelProgramBlock> block,
            LocVar encoder, IClass type, Param value);
    
    boolean match(IClass type, ClassHolder loader);
    
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
