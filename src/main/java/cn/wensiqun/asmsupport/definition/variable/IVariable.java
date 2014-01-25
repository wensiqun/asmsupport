/**
 * 
 */
package cn.wensiqun.asmsupport.definition.variable;

import cn.wensiqun.asmsupport.GetGlobalVariabled;
import cn.wensiqun.asmsupport.Parameterized;
import cn.wensiqun.asmsupport.definition.variable.meta.VariableMeta;
import cn.wensiqun.asmsupport.operators.AbstractOperator;


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
