package cn.wensiqun.asmsupport.sample.client.json.generator;

import cn.wensiqun.asmsupport.client.block.ProgramBlock;
import cn.wensiqun.asmsupport.client.def.Param;
import cn.wensiqun.asmsupport.client.def.var.LocVar;
import cn.wensiqun.asmsupport.core.block.KernelProgramBlock;
import cn.wensiqun.asmsupport.standard.def.clazz.IClass;

public abstract class AbstractGeneratorChain implements IValueGeneratorChain {

    protected IValueGeneratorChain next;

    @Override
    public boolean generate(GeneratorContext context, ProgramBlock<? extends KernelProgramBlock> block,
            LocVar encoder, IClass type, Param value) {
        if(match(type, block)) {
            return doGenerate(context, block, encoder, type, value);
        } else if(next != null) {
            return next.generate(context, block, encoder, type, value);
        }
        return false;
    }

    protected abstract boolean doGenerate(GeneratorContext context, ProgramBlock<? extends KernelProgramBlock> block,
            LocVar encoder, IClass type, Param value);
    
    @Override
    public final IValueGeneratorChain getNext() {
        return next;
    }

    @Override
    public final IValueGeneratorChain setNext(IValueGeneratorChain next) {
        this.next = next;
        return next;
    }

}
