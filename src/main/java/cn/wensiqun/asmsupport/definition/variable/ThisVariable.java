/**
 * 
 */
package cn.wensiqun.asmsupport.definition.variable;



import org.objectweb.asm.Opcodes;

import cn.wensiqun.asmsupport.block.ProgramBlock;
import cn.wensiqun.asmsupport.clazz.AClass;
import cn.wensiqun.asmsupport.definition.variable.meta.GlobalVariableMeta;
import cn.wensiqun.asmsupport.definition.variable.meta.VariableMeta;
import cn.wensiqun.asmsupport.operators.AbstractOperator;
import cn.wensiqun.asmsupport.utils.ASConstant;

/**
 * 全局变量。这个class只用于方法体内操作变量
 * @author 温斯群(Joe Wen)
 */
public class ThisVariable extends ImplicitVariable {

    private GlobalVariableMeta globalVariableMeta;
    
    
    public ThisVariable(AClass aclass) {
        this.globalVariableMeta = new GlobalVariableMeta(aclass, aclass, aclass, Opcodes.ACC_FINAL, ASConstant.THIS);
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
        return globalVariableMeta.getDeclareClass();
    }

    @Override
    public VariableMeta getVariableMeta() {
        return globalVariableMeta;
    }

    @Override
    public GlobalVariable getGlobalVariable(String name) {
        return getGlobalVariable(globalVariableMeta.getDeclareClass(), name);
    }

    @Override
    public String toString() {
        return ASConstant.THIS;
    }
}
