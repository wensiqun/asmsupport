package cn.wensiqun.asmsupport.client.def.var;

import cn.wensiqun.asmsupport.client.def.behavior.CommonBehavior;
import cn.wensiqun.asmsupport.client.def.param.UncertainParam;
import cn.wensiqun.asmsupport.core.definition.variable.IVariable;
import cn.wensiqun.asmsupport.core.utils.common.BlockTracker;
import cn.wensiqun.asmsupport.standard.def.clazz.IClass;
import cn.wensiqun.asmsupport.standard.def.var.IVar;

/**
 * The variable.
 * 
 * @author sqwen
 *
 */
public abstract class Var extends UncertainParam implements IVar {

    public Var(BlockTracker tracker, IVariable target) {
        super(tracker, target);
    }

    private IVariable getPreciseTarget() {
        return (IVariable) target;
    }
    
    @Override
    public final String getName() {
        return getPreciseTarget().getName();
    }

    @Override
    public final IClass getFormerType() {
        return getPreciseTarget().getFormerType();
    }

    @Override
    public final int getModifiers() {
        return getPreciseTarget().getModifiers();
    }

    public CommonBehavior assign(CommonBehavior param) {
        return param.assignTo(this);
    }
}
