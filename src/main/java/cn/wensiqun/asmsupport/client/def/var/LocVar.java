package cn.wensiqun.asmsupport.client.def.var;

import cn.wensiqun.asmsupport.core.block.KernelProgramBlock;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.standard.def.var.ILocVar;

/**
 * The local variable.
 * 
 * @author sqwen
 *
 */
public class LocVar extends Var implements ILocVar {

    public LocVar(KernelProgramBlock block, LocalVariable target) {
        super(block, target);
    }
    
}
