package cn.wensiqun.asmsupport.operators.asmdirect;

import cn.wensiqun.asmsupport.block.ProgramBlock;
import cn.wensiqun.asmsupport.definition.variable.LocalVariable;

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
