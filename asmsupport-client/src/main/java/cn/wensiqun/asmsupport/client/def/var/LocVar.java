package cn.wensiqun.asmsupport.client.def.var;

import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.core.utils.common.BlockTracker;
import cn.wensiqun.asmsupport.standard.def.var.ILocVar;

/**
 * The local variable.
 * 
 * @author sqwen
 *
 */
public class LocVar extends Var implements ILocVar {

    public LocVar(BlockTracker tracker, LocalVariable target) {
        super(tracker, target);
    }
    
}
