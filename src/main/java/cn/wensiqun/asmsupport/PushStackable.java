/**
 * 
 */
package cn.wensiqun.asmsupport;

import cn.wensiqun.asmsupport.block.ProgramBlock;

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
    public void loadToStack(ProgramBlock block);
}
