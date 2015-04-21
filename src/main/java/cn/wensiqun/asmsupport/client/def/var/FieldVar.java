package cn.wensiqun.asmsupport.client.def.var;

import cn.wensiqun.asmsupport.core.definition.variable.GlobalVariable;
import cn.wensiqun.asmsupport.standard.def.clazz.AClass;
import cn.wensiqun.asmsupport.standard.def.var.IFieldVar;


/**
 * The field variable
 * 
 * @author sqwen
 *
 */
public class FieldVar extends Var implements IFieldVar {

    public FieldVar(GlobalVariable target) {
        super(target);
    }

    private GlobalVariable getPreciseTarget() {
        return (GlobalVariable) target;
    }
    
    @Override
    public AClass getDeclaringClass() {
        return getPreciseTarget().getDeclaringClass();
    }

}
