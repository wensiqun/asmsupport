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

import cn.wensiqun.asmsupport.core.Crementable;
import cn.wensiqun.asmsupport.core.block.ProgramBlockInternal;
import cn.wensiqun.asmsupport.core.clazz.AClass;
import cn.wensiqun.asmsupport.core.definition.variable.meta.LocalVariableMeta;
import cn.wensiqun.asmsupport.core.definition.variable.meta.VariableMeta;
import cn.wensiqun.asmsupport.core.operator.AbstractOperator;
import cn.wensiqun.asmsupport.core.utils.memory.Scope;
import cn.wensiqun.asmsupport.core.utils.memory.ScopeLogicVariable;

/**
 * 全局变量。这个class只用于方法体内操作变量
 * 
 * @author 温斯群(Joe Wen)
 */
public class LocalVariable extends ExplicitVariable implements Crementable{

    private LocalVariableMeta localVariableMeta;

    protected ScopeLogicVariable scopeLogicVar;

    private boolean isFirstAssign = true;
    
    public LocalVariable(LocalVariableMeta lve) {
        this.localVariableMeta = lve;
    }

    public boolean availableFor(AbstractOperator operator) {
        Scope operScope = operator.getBlock().getScope();
        // 如果此变量是operator的直系变量
        if (this.scopeLogicVar.isSubOf(operScope)) {
            if(scopeLogicVar.getCompileOrder() > operator.getCompileOrder()){
                throw new VariableOperatorException();
            }
        } else {
            if (!this.scopeLogicVar
                    .availableFor(operator.getBlock().getScope())) {
                throw new VariableOperatorException();
            }
        }
        return true;
    }

    @Override
    public void loadToStack(ProgramBlockInternal block) {
        block.getMethod().getInsnHelper().loadInsn(localVariableMeta.getDeclareType().getType(), scopeLogicVar.getInitStartPos());
    }

    public LocalVariableMeta getLocalVariableMeta() {
        return localVariableMeta;
    }

    private class VariableOperatorException extends RuntimeException {

        private static final long serialVersionUID = 1L;

        private VariableOperatorException() {
            super("the scope cannot use the variable \"" + localVariableMeta.getName() + "\"");
        }
    }

    @Override
    public AClass getResultType() {
        return localVariableMeta.getDeclareType();
    }

    @Override
    public VariableMeta getVariableMeta() {
        return localVariableMeta;
    }

    public void setScopeLogicVar(ScopeLogicVariable scopeLogicVar) {
        this.scopeLogicVar = scopeLogicVar;
    }
    
    public ScopeLogicVariable getScopeLogicVar() {
        return scopeLogicVar;
    }
    
    /**
     * 设置逻辑变量编译顺序
     * @param complieOrder
     */
    public void setVariableCompileOrder(int complieOrder){
        if(isFirstAssign){
            scopeLogicVar.setCompileOrder(complieOrder);
            isFirstAssign = false;
        }
    }

    @Override
    public String toString() {
        return localVariableMeta.getName();
    }

}
