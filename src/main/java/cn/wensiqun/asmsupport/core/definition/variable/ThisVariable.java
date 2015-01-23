/**
 * 
 */
package cn.wensiqun.asmsupport.core.definition.variable;



import cn.wensiqun.asmsupport.core.block.classes.common.ProgramBlockInternal;
import cn.wensiqun.asmsupport.core.clazz.AClass;
import cn.wensiqun.asmsupport.core.definition.variable.meta.GlobalVariableMeta;
import cn.wensiqun.asmsupport.core.definition.variable.meta.VariableMeta;
import cn.wensiqun.asmsupport.core.operator.AbstractOperator;
import cn.wensiqun.asmsupport.core.utils.ASConstant;
import cn.wensiqun.asmsupport.org.objectweb.asm.Opcodes;

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
    public void loadToStack(ProgramBlockInternal block) {
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
