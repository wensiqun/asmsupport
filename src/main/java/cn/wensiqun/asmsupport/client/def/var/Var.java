package cn.wensiqun.asmsupport.client.def.var;

import cn.wensiqun.asmsupport.client.ClientParameterized;
import cn.wensiqun.asmsupport.core.clazz.AClass;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.standard.def.var.IVar;

public class Var extends ClientParameterized<LocalVariable> implements IVar {

    public Var(LocalVariable target) {
        super(target);
    }

    @Override
    public String getName() {
        return getTarget().getName();
    }

    @Override
    public AClass getFormerType() {
        return getTarget().getFormerType();
    }

    @Override
    public int getModifiers() {
        return getTarget().getModifiers();
    }
    
}
