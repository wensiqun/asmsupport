/**    
 *  Asmsupport is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
/**
 * 
 */
package cn.wensiqun.asmsupport.core.definition.variable;

import cn.wensiqun.asmsupport.core.context.MethodContext;
import cn.wensiqun.asmsupport.core.operator.AbstractOperator;
import cn.wensiqun.asmsupport.core.utils.memory.Scope;
import cn.wensiqun.asmsupport.core.utils.memory.ScopeLogicVariable;
import cn.wensiqun.asmsupport.standard.def.clazz.IClass;
import cn.wensiqun.asmsupport.standard.def.var.ILocVar;
import cn.wensiqun.asmsupport.standard.def.var.meta.VarMeta;

/**
 * Represent a local variable
 * 
 * @author wensiqun at 163.com(Joe Wen)
 */
public class LocalVariable extends ExplicitVariable implements ILocVar{

    private VarMeta meta;

    protected ScopeLogicVariable scopeLogicVar;

    private boolean isFirstAssign = true;
    
    public LocalVariable(VarMeta lve) {
        this.meta = lve;
    }
    
    @Override
    public boolean availableFor(AbstractOperator operator) {
        Scope operScope = operator.getParent().getScope();
        if (this.scopeLogicVar.isSubOf(operScope)) {
            if(scopeLogicVar.getCompileOrder() > operator.getCompileOrder()){
                throw new VariableOperatorException();
            }
        } else {
            if (!this.scopeLogicVar
                    .availableFor(operator.getParent().getScope())) {
                throw new VariableOperatorException();
            }
        }
        return true;
    }

    @Override
    public void loadToStack(MethodContext context) {
        context.getMethod().getInstructions().loadInsn(meta.getType().getType(), scopeLogicVar.getInitStartPos());
    }

    private class VariableOperatorException extends RuntimeException {

        private static final long serialVersionUID = 1L;

        private VariableOperatorException() {
            super("the scope cannot use the variable \"" + meta.getName() + "\"");
        }
    }

    @Override
    public IClass getResultType() {
        return meta.getType();
    }

    @Override
    public VarMeta getMeta() {
        return meta;
    }

    /**
     * Set the {@link ScopeLogicVariable}
     * @param scopeLogicVar
     */
    public void setScopeLogicVar(ScopeLogicVariable scopeLogicVar) {
        this.scopeLogicVar = scopeLogicVar;
    }
    
    /**
     * Get the {@link ScopeLogicVariable}
     */
    public ScopeLogicVariable getScopeLogicVar() {
        return scopeLogicVar;
    }
    
    /**
     * Set the compile order
     * @param complieOrder compile order
     */
    public void setVariableCompileOrder(int complieOrder){
        if(isFirstAssign){
            scopeLogicVar.setCompileOrder(complieOrder);
            isFirstAssign = false;
        }
    }

}
