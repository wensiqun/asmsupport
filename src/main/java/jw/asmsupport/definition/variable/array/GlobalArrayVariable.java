/**
 * 
 */
package jw.asmsupport.definition.variable.array;

import jw.asmsupport.definition.variable.GlobalVariable;

/**
 * @author 温斯群(Joe Wen)
 *
 */
@Deprecated
public class GlobalArrayVariable extends AbstractArrayVariable {
	
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
    public GlobalArrayVariable(GlobalVariable ve) {
        variable = ve;
    }
}
