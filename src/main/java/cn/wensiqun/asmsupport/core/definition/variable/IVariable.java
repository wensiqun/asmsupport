/**
 * 
 */
package cn.wensiqun.asmsupport.core.definition.variable;

import cn.wensiqun.asmsupport.core.GetGlobalVariabled;
import cn.wensiqun.asmsupport.core.Parameterized;
import cn.wensiqun.asmsupport.core.definition.variable.meta.VariableMeta;
import cn.wensiqun.asmsupport.core.operator.AbstractOperator;


/**
 * 
 * 变量的接口
 * @author 温斯群(Joe Wen)
 *
 */
public interface IVariable extends Parameterized, GetGlobalVariabled{
    
    /**
     * 当前变量对于传入的操作是否可用
     * @param operator
     */
    boolean availableFor(AbstractOperator operator);
    
    /**
     * 获取当前变量的VariableEntity
     * @return
     */
    VariableMeta getVariableMeta();

}
