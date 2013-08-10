/**
 * 
 */
package jw.asmsupport.definition.variable;


import org.objectweb.asm.Opcodes;

import jw.asmsupport.block.ProgramBlock;
import jw.asmsupport.clazz.AClass;
import jw.asmsupport.clazz.AClassFactory;
import jw.asmsupport.entity.GlobalVariableEntity;
import jw.asmsupport.entity.VariableEntity;
import jw.asmsupport.operators.AbstractOperator;
import jw.asmsupport.utils.ASConstant;


/**
 * 全局变量。这个class只用于方法体内操作变量
 * @author 温斯群(Joe Wen)
 */
public class SuperVariable extends AbstractVariable{

    private GlobalVariableEntity gve;
    
    /**
     * 通过Class获取的全局变量
     * @param insnHelper
     */
    public SuperVariable(AClass aclass) {
        this.gve = new GlobalVariableEntity(
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
    public VariableEntity getVariableEntity() {
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
