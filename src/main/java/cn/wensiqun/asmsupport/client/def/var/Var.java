package cn.wensiqun.asmsupport.client.def.var;

import cn.wensiqun.asmsupport.client.DummyParam;
import cn.wensiqun.asmsupport.core.definition.variable.IVariable;
import cn.wensiqun.asmsupport.standard.def.clazz.AClass;
import cn.wensiqun.asmsupport.standard.def.var.IVar;

/**
 * The variable.
 * 
 * @author sqwen
 *
 */
public abstract class Var extends DummyParam implements IVar {

    public Var(IVariable target) {
        super(target);
    }

    private IVariable getPreciseTarget() {
        return (IVariable) target;
    }
    
    @Override
    public FieldVar field(String name) {
        return new FieldVar(target.field(name));
    }
    
    @Override
    public final String getName() {
        return getPreciseTarget().getName();
    }

    @Override
    public final AClass getFormerType() {
        return getPreciseTarget().getFormerType();
    }

    @Override
    public final int getModifiers() {
        return getPreciseTarget().getModifiers();
    }

}
