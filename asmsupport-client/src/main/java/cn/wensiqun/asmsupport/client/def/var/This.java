package cn.wensiqun.asmsupport.client.def.var;

import cn.wensiqun.asmsupport.client.block.KernelProgramBlockCursor;
import cn.wensiqun.asmsupport.core.definition.variable.ThisVariable;
import cn.wensiqun.asmsupport.standard.def.var.ILocVar;

/**
 * 
 * Indicate a {@code this} keyword of variable
 * 
 * @author WSQ
 *
 */
public class This extends Var implements ILocVar{

    public This(KernelProgramBlockCursor cursor, ThisVariable target) {
        super(cursor, target);
    }

}
