package cn.wensiqun.asmsupport.core.utils.common;

import cn.wensiqun.asmsupport.core.block.KernelProgramBlock;

import java.util.Stack;

/**
 * Created by sqwen on 2016/5/11.
 */
public class BlockStack implements BlockTracker {

    private Stack<KernelProgramBlock> stack = new Stack<>();

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
