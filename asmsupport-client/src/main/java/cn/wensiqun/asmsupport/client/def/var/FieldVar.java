package cn.wensiqun.asmsupport.client.def.var;

import cn.wensiqun.asmsupport.client.block.KernelProgramBlockCursor;
import cn.wensiqun.asmsupport.core.definition.variable.GlobalVariable;
import cn.wensiqun.asmsupport.standard.def.clazz.IClass;
import cn.wensiqun.asmsupport.standard.def.var.IFieldVar;


/**
 * The field variable
 * 
 * @author sqwen
 *
 */
public class FieldVar extends Var implements IFieldVar {

    public FieldVar(KernelProgramBlockCursor cursor, GlobalVariable target) {
        super(cursor, target);
    }

    private GlobalVariable getPreciseTarget() {
        return (GlobalVariable) target;
    }
    
    @Override
    public IClass getDeclaringClass() {
        return getPreciseTarget().getDeclaringClass();
    }

}
