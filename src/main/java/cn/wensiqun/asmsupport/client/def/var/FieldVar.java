package cn.wensiqun.asmsupport.client.def.var;

import cn.wensiqun.asmsupport.core.block.KernelProgramBlock;
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

    public FieldVar(KernelProgramBlock block, GlobalVariable target) {
        super(block, target);
    }

    private GlobalVariable getPreciseTarget() {
        return (GlobalVariable) target;
    }
    
    @Override
    public AClass getDeclaringClass() {
        return getPreciseTarget().getDeclaringClass();
    }

}
