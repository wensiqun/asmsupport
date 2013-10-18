/**
 * 
 */
package cn.wensiqun.asmsupport.definition.variable;



import org.objectweb.asm.Opcodes;

import cn.wensiqun.asmsupport.block.ProgramBlock;
import cn.wensiqun.asmsupport.clazz.AClass;
import cn.wensiqun.asmsupport.entity.GlobalVariableEntity;
import cn.wensiqun.asmsupport.entity.VariableEntity;
import cn.wensiqun.asmsupport.operators.AbstractOperator;
import cn.wensiqun.asmsupport.utils.ASConstant;

/**
 * 全局变量。这个class只用于方法体内操作变量
 * @author 温斯群(Joe Wen)
 */
public class ThisVariable extends AbstractVariable {

    private GlobalVariableEntity gve;
    
    
    public ThisVariable(AClass aclass) {
        this.gve = new GlobalVariableEntity(aclass, aclass, Opcodes.ACC_FINAL, ASConstant.THIS);
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
        return ASConstant.THIS;
    }
}
