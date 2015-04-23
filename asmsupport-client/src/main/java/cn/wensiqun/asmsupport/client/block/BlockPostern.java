package cn.wensiqun.asmsupport.client.block;

import cn.wensiqun.asmsupport.core.block.KernelProgramBlock;

public class BlockPostern {

    public static <B extends KernelProgramBlock, D extends ProgramBlock<B>> B getTarget(D block) {
        return block.targetBlock;
    }
    
}
