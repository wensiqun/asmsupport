package cn.wensiqun.asmsupport.client.def.var;

import cn.wensiqun.asmsupport.client.block.KernelProgramBlockCursor;
import cn.wensiqun.asmsupport.client.def.param.UncertainParam;
import cn.wensiqun.asmsupport.core.definition.variable.IVariable;
import cn.wensiqun.asmsupport.standard.def.clazz.IClass;
import cn.wensiqun.asmsupport.standard.def.var.IVar;

/**
 * The variable.
 * 
 * @author sqwen
 *
 */
public abstract class Var extends UncertainParam implements IVar {

    public Var(KernelProgramBlockCursor cursor, IVariable target) {
        super(cursor, target);
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

}
