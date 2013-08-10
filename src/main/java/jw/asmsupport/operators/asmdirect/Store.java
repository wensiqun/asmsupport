package jw.asmsupport.operators.asmdirect;

import jw.asmsupport.block.ProgramBlock;
import jw.asmsupport.definition.variable.LocalVariable;

/**
 * 
 * @author 温斯群(Joe Wen)
 *
 */
public class Store extends ASMDirect {

    private LocalVariable var;
    
    public Store(ProgramBlock block, LocalVariable var) {
        super(block);
        this.var = var;
    }

    @Override
    protected void executing() {
        insnHelper.storeInsn(var);
    }

}
