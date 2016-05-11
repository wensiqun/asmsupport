package cn.wensiqun.asmsupport.client.def.var;

import cn.wensiqun.asmsupport.client.def.behavior.CommonBehavior;
import cn.wensiqun.asmsupport.core.definition.variable.ThisVariable;
import cn.wensiqun.asmsupport.core.utils.common.BlockTracker;
import cn.wensiqun.asmsupport.standard.def.var.ILocVar;

/**
 * 
 * Indicate a {@code this} keyword of variable
 * 
 * @author WSQ
 *
 */
public class This extends Var implements ILocVar{

    public This(BlockTracker tracker, ThisVariable target) {
        super(tracker, target);
    }

    @Override
    public CommonBehavior assign(CommonBehavior param) {
        throw new UnsupportedOperationException("Unsupported assign operation to 'this'");
    }
}
