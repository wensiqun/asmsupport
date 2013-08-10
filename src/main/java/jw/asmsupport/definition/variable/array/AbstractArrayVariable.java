/**
 * 
 */
package jw.asmsupport.definition.variable.array;

import jw.asmsupport.block.ProgramBlock;
import jw.asmsupport.clazz.AClass;
import jw.asmsupport.definition.variable.GlobalVariable;
import jw.asmsupport.definition.variable.IVariable;
import jw.asmsupport.definition.variable.MemberVariable;
import jw.asmsupport.entity.VariableEntity;
import jw.asmsupport.exception.ASMSupportException;
import jw.asmsupport.operators.AbstractOperator;

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
