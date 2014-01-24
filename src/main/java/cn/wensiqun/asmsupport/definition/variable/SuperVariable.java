/**
 * 
 */
package cn.wensiqun.asmsupport.definition.variable;


import org.objectweb.asm.Opcodes;

import cn.wensiqun.asmsupport.block.ProgramBlock;
import cn.wensiqun.asmsupport.clazz.AClass;
import cn.wensiqun.asmsupport.clazz.AClassFactory;
import cn.wensiqun.asmsupport.definition.variable.meta.GlobalVariableMeta;
import cn.wensiqun.asmsupport.definition.variable.meta.VariableMeta;
import cn.wensiqun.asmsupport.operators.AbstractOperator;
import cn.wensiqun.asmsupport.utils.ASConstant;


/**
 * 全局变量。这个class只用于方法体内操作变量
 * @author 温斯群(Joe Wen)
 */
public class SuperVariable extends ImplicitVariable{

    private GlobalVariableMeta gve;
    
    /**
     * 通过Class获取的全局变量
     * @param insnHelper
     */
    public SuperVariable(AClass aclass) {
        this.gve = new GlobalVariableMeta(
                AClassFactory.getProductClass(aclass.getSuperClass()), 
                AClassFactory.getProductClass(aclass.getSuperClass()), 
                Opcodes.ACC_FINAL, ASConstant.SUPER);
    }

    @Override
    public void loadToStack(ProgramBlock block) {
        block.getInsnHelper().loadThis();
    }

    @Override
    public boolean availableFor(AbstractOperator operator) {
        return true;
    }


    @Override
    public AClass getParamterizedType() {
        return gve.getDeclareClass();
    }

    @Override
    public VariableMeta getVariableEntity() {
        return gve;
    }

    @Override
    public GlobalVariable getGlobalVariable(String name) {
        return getGlobalVariable(gve.getDeclareClass(), name);
    }

    @Override
    public String toString() {
        return ASConstant.SUPER;
    }
   
}
