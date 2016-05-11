package cn.wensiqun.asmsupport.client.block;

import cn.wensiqun.asmsupport.core.block.KernelProgramBlock;
import cn.wensiqun.asmsupport.org.apache.commons.collections.ArrayStack;

/**
 * The {@link KernelProgramBlock} cursor, this class 
 * 
 * @author WSQ
 *
 */
public class KernelProgramBlockCursor {

    private ArrayStack<KernelProgramBlock> stack = new ArrayStack<>();

    public KernelProgramBlockCursor(KernelProgramBlock pointer) {
        stack.push(pointer);
    }

    public void push(KernelProgramBlock pointer) {
        stack.push(pointer);
    }

    public KernelProgramBlock peek() {
        return stack.peek();
    }

    public KernelProgramBlock pop() {
        return stack.pop();
    }
    
}
