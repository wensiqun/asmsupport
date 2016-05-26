package cn.wensiqun.asmsupport.client.def.var;

import cn.wensiqun.asmsupport.client.def.param.CommonParam;
import cn.wensiqun.asmsupport.core.definition.variable.SuperVariable;
import cn.wensiqun.asmsupport.core.utils.common.BlockTracker;
import cn.wensiqun.asmsupport.standard.def.var.ILocVar;

/**
 * Indicate a {@code super} keyword of variable
 * 
 * @author WSQ
 *
 */
public class Super extends Var implements ILocVar{

    public Super(BlockTracker tracker, SuperVariable target) {
        super(tracker, target);
    }

    @Override
    public CommonParam assign(CommonParam param) {
        throw new UnsupportedOperationException("Unsupported assign operation to 'super'");
    }
}
