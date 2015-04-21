package json.generator;

import cn.wensiqun.asmsupport.client.Param;
import cn.wensiqun.asmsupport.client.ProgramBlock;
import cn.wensiqun.asmsupport.client.def.var.LocVar;
import cn.wensiqun.asmsupport.core.block.ProgramBlockInternal;
import cn.wensiqun.asmsupport.standard.def.clazz.AClass;

public abstract class AbstractGeneratorChain implements IValueGeneratorChain {

    protected IValueGeneratorChain next;

    @Override
    public boolean generate(GeneratorContext context, ProgramBlock<? extends ProgramBlockInternal> block,
            LocVar encoder, AClass type, Param value) {
        if(match(type)) {
            return doGenerate(context, block, encoder, type, value);
        } else if(next != null) {
            return next.generate(context, block, encoder, type, value);
        }
        return false;
    }

    protected abstract boolean doGenerate(GeneratorContext context, ProgramBlock<? extends ProgramBlockInternal> block,
            LocVar encoder, AClass type, Param value);
    
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
