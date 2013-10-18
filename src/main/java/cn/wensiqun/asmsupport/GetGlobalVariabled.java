/**
 * 
 */
package cn.wensiqun.asmsupport;

import cn.wensiqun.asmsupport.definition.variable.GlobalVariable;

/**
 * 
 * @author 温斯群(Joe Wen)
 *
 */
public interface GetGlobalVariabled {
    
    /**
     * get global variable name
     * @param name
     * @return
     */
    GlobalVariable getGlobalVariable(String name);
}
