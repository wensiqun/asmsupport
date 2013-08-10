/**
 * 
 */
package jw.asmsupport.definition.variable.array;

import jw.asmsupport.Parameterized;
import jw.asmsupport.entity.VariableEntity;

/**
 * 数组类型变量
 * @author 温斯群(Joe Wen)
 *
 */
@Deprecated
public interface IArrayVariable extends Parameterized{//, MethodInvokeable {
    
    /**
     * 获取当前变量的VariableEntity
     * @return
     */
    VariableEntity getVariableEntity();

}
