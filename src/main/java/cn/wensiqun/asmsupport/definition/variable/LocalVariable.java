/**
 * 
 */
package cn.wensiqun.asmsupport.definition.variable;

import cn.wensiqun.asmsupport.Crementable;
import cn.wensiqun.asmsupport.block.ProgramBlock;
import cn.wensiqun.asmsupport.clazz.AClass;
import cn.wensiqun.asmsupport.entity.LocalVariableEntity;
import cn.wensiqun.asmsupport.entity.VariableEntity;
import cn.wensiqun.asmsupport.operators.AbstractOperator;
import cn.wensiqun.asmsupport.utils.memory.Scope;
import cn.wensiqun.asmsupport.utils.memory.ScopeLogicVariable;

/**
 * 全局变量。这个class只用于方法体内操作变量
 * 
 * @author 温斯群(Joe Wen)
 */
public class LocalVariable extends MemberVariable implements Crementable{

    private LocalVariableEntity lve;

    protected ScopeLogicVariable scopeLogicVar;

    private boolean isFirstAssign = true;
    
    public LocalVariable(LocalVariableEntity lve) {
        this.lve = lve;
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
    public void loadToStack(ProgramBlock block) {
        block.getMethod().getInsnHelper().loadInsn(lve.getDeclareClass().getType(), scopeLogicVar.getInitStartPos());
    }

    public LocalVariableEntity getLocalVariableEntity() {
        return lve;
    }

    private class VariableOperatorException extends RuntimeException {

        private static final long serialVersionUID = 1L;

        private VariableOperatorException() {
            super("the scope cannot use the variable \"" + lve.getName() + "\"");
        }
    }

    @Override
    public AClass getParamterizedType() {
        return lve.getDeclareClass();
    }

    @Override
    public VariableEntity getVariableEntity() {
        return lve;
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
    public GlobalVariable getGlobalVariable(String name) {
        return getGlobalVariable(lve.getDeclareClass(), name);
    }

    @Override
    public String toString() {
        return lve.getName();
    }

}
