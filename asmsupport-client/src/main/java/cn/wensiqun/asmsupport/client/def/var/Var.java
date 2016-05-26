package cn.wensiqun.asmsupport.client.def.var;

import cn.wensiqun.asmsupport.client.def.param.CommonParam;
import cn.wensiqun.asmsupport.client.def.param.impl.UncertainParamImpl;
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
public abstract class Var extends UncertainParamImpl implements IVar {

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

    public CommonParam assign(CommonParam param) {
        return param.assignTo(this);
    }
}
