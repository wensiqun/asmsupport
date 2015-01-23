/**
 * 
 */
package cn.wensiqun.asmsupport.core;

import cn.wensiqun.asmsupport.core.block.classes.common.ProgramBlockInternal;

/**
 * 
 * 如果某类继承此接口那么该类则能够将值做入栈操作
 * @author 温斯群(Joe Wen)
 * 
 */
public interface PushStackable {
    
    /**
     * 在指定程序块内将值压入栈
     * @param block
     */
    public void loadToStack(ProgramBlockInternal block);
}
