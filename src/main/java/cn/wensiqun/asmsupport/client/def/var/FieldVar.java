package cn.wensiqun.asmsupport.client.def.var;

import cn.wensiqun.asmsupport.client.ClientParameterized;
import cn.wensiqun.asmsupport.core.clazz.AClass;
import cn.wensiqun.asmsupport.core.definition.variable.GlobalVariable;
import cn.wensiqun.asmsupport.standard.def.var.IFieldVar;

public class FieldVar extends ClientParameterized<GlobalVariable> implements IFieldVar {

    public FieldVar(GlobalVariable target) {
        super(target);
    }

    @Override
    public String getName() {
        return target.getName();
    }

    @Override
    public AClass getFormerType() {
        return target.getFormerType();
    }

    @Override
    public int getModifiers() {
        return target.getModifiers();
    }

    @Override
    public AClass getDeclaringClass() {
        return target.getDeclaringClass();
    }

}
