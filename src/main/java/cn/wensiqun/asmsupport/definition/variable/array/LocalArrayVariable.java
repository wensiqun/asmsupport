/**
 * 
 */
package cn.wensiqun.asmsupport.definition.variable.array;

import cn.wensiqun.asmsupport.definition.variable.LocalVariable;

/**
 * @author 温斯群(Joe Wen)
 *
 */
@Deprecated
public class LocalArrayVariable extends AbstractArrayVariable {
    
    /**
     * 
     * 
     * 通过Class获取的全局变量
     * @param owner 变量拥有者
     * @param declareClass 变量声明类型
     * @param actuallyClass 变量真实类型
     * @param modifiers 变量的修饰符
     * @param name 变量名
     * 
     */
    public LocalArrayVariable(LocalVariable ve) {
        variable = ve;
    }

}
