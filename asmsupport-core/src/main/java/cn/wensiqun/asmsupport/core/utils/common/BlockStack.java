package cn.wensiqun.asmsupport.core.utils.common;

import cn.wensiqun.asmsupport.core.block.KernelProgramBlock;
import cn.wensiqun.asmsupport.org.apache.commons.collections.ArrayStack;

/**
 * Created by sqwen on 2016/5/11.
 */
public class BlockStack implements BlockTracker {

    private ArrayStack<KernelProgramBlock> stack = new ArrayStack<>();

    public BlockStack() {
    }

    public void push(KernelProgramBlock pointer) {
        stack.push(pointer);
    }

    @Override
    public KernelProgramBlock track() {
        return stack.peek();
    }

    public KernelProgramBlock pop() {
        return stack.pop();
    }

}
