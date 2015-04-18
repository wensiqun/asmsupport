package json.generator;

import cn.wensiqun.asmsupport.client.ProgramBlock;
import cn.wensiqun.asmsupport.core.InternalParameterized;
import cn.wensiqun.asmsupport.core.block.ProgramBlockInternal;
import cn.wensiqun.asmsupport.core.clazz.AClass;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;

public abstract class AbstractGeneratorChain implements IValueGeneratorChain {

    protected IValueGeneratorChain next;

    @Override
    public boolean generate(GeneratorContext context, ProgramBlock<? extends ProgramBlockInternal> block,
            LocalVariable encoder, AClass type, InternalParameterized value) {
        if(match(type)) {
            return doGenerate(context, block, encoder, type, value);
        } else if(next != null) {
            return next.generate(context, block, encoder, type, value);
        }
        return false;
    }

    protected abstract boolean doGenerate(GeneratorContext context, ProgramBlock<? extends ProgramBlockInternal> block,
            LocalVariable encoder, AClass type, InternalParameterized value);
    
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
