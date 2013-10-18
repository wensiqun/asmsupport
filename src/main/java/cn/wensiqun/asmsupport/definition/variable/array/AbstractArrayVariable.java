/**
 * 
 */
package cn.wensiqun.asmsupport.definition.variable.array;

import cn.wensiqun.asmsupport.block.ProgramBlock;
import cn.wensiqun.asmsupport.clazz.AClass;
import cn.wensiqun.asmsupport.definition.variable.GlobalVariable;
import cn.wensiqun.asmsupport.definition.variable.IVariable;
import cn.wensiqun.asmsupport.definition.variable.MemberVariable;
import cn.wensiqun.asmsupport.entity.VariableEntity;
import cn.wensiqun.asmsupport.exception.ASMSupportException;
import cn.wensiqun.asmsupport.operators.AbstractOperator;

/**
 * @author 温斯群(Joe Wen)
 *
 */
@Deprecated
public abstract class AbstractArrayVariable extends MemberVariable implements IArrayVariable {

    protected IVariable variable;
    
    @Override
    public AClass getParamterizedType() {
        return this.getVariableEntity().getDeclareClass();
    }

    @Override
    public GlobalVariable getGlobalVariable(String name) {
        throw new ASMSupportException("cannot get global variable from array type");
    }

    @Override
    public VariableEntity getVariableEntity() {
        return variable.getVariableEntity();
    }

    @Override
    public void loadToStack(ProgramBlock block) {
        variable.loadToStack(block);
    }

	@Override
    public final boolean availableFor(AbstractOperator operator) {
        return variable.availableFor(operator);
    }
    
    public IVariable getVariable() {
        return variable;
    }
}
