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
import cn.wensiqun.asmsupport.core.exception.ASMSupportException;
import cn.wensiqun.asmsupport.core.operator.AbstractOperator;
import cn.wensiqun.asmsupport.core.utils.memory.Scope;
import cn.wensiqun.asmsupport.core.utils.memory.ScopeLogicVariable;
import cn.wensiqun.asmsupport.standard.clazz.AClass;
import cn.wensiqun.asmsupport.standard.var.Var;

/**
 * 全局变量。这个class只用于方法体内操作变量
 * 
 * @author 温斯群(Joe Wen)
 */
public class LocalVariable extends ExplicitVariable implements Crementable, Var {

	private String name;
	
	private AClass formerType;

    protected ScopeLogicVariable scopeLogicVar;

    private boolean isFirstAssign = true;
    
    private int modifiers;
    
    public LocalVariable(AClass formerType, int modifiers, String name) {
        this.name = name;
        this.modifiers = modifiers;
        this.formerType = formerType;
    }

    public void setScopeLogicVar(ScopeLogicVariable scopeLogicVar) {
        this.scopeLogicVar = scopeLogicVar;
    }
    
    public ScopeLogicVariable getScopeLogicVar() {
        return scopeLogicVar;
    }

    @Override
    public boolean availableFor(AbstractOperator operator) {
        Scope operScope = operator.getBlock().getScope();
        // 如果此变量是operator的直系变量
        if (this.scopeLogicVar.isSubOf(operScope)) {
            if(scopeLogicVar.getCompileOrder() > operator.getCompileOrder()){
                throw new ASMSupportException("The scope cannot use the variable \"" + name + "\"");
            }
        } else {
            if (!this.scopeLogicVar.availableFor(operator.getBlock().getScope())) {
                throw new ASMSupportException("The scope cannot use the variable \"" + name + "\"");
            }
        }
        return true;
    }

    @Override
    public void loadToStack(ProgramBlockInternal block) {
        block.getMethod().getInsnHelper().loadInsn(formerType.getType(), scopeLogicVar.getInitStartPos());
    }

	@Override
	public int getModifiers() {
		return modifiers;
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
	public String getName() {
		return name;
	}

	@Override
	public AClass getFormerType() {
		return formerType;
	}

}
