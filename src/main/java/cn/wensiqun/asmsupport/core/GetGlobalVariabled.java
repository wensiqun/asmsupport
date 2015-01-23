/**
 * 
 */
package cn.wensiqun.asmsupport.core;

import cn.wensiqun.asmsupport.core.definition.variable.GlobalVariable;

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
