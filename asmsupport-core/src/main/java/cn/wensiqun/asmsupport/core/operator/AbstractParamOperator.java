package cn.wensiqun.asmsupport.core.operator;

import cn.wensiqun.asmsupport.core.block.KernelProgramBlock;
import cn.wensiqun.asmsupport.core.definition.KernelParam;
import cn.wensiqun.asmsupport.core.definition.variable.GlobalVariable;
import cn.wensiqun.asmsupport.core.definition.variable.NonStaticGlobalVariable;
import cn.wensiqun.asmsupport.core.definition.variable.StaticGlobalVariable;
import cn.wensiqun.asmsupport.core.utils.reflect.ModifierUtils;
import cn.wensiqun.asmsupport.standard.def.clazz.ArrayClass;
import cn.wensiqun.asmsupport.standard.def.var.meta.Field;
import cn.wensiqun.asmsupport.standard.error.ASMSupportException;

public abstract class AbstractParamOperator extends AbstractOperator implements KernelParam{

    protected AbstractParamOperator(KernelProgramBlock block, Operator operatorSymbol) {
        super(block, operatorSymbol);
    }

    @Override
    public GlobalVariable field(String name) {
        if(this.getResultType() instanceof ArrayClass){
            throw new ASMSupportException("Cannot get global variable from array type variable : " + this);
        }
        Field field = getResultType().getField(name);
        if(ModifierUtils.isStatic(field.getModifiers())){
            return new StaticGlobalVariable(field.getDeclaringClass(), field);
        } else {
            return new NonStaticGlobalVariable(this, field);
        }
    }


}
