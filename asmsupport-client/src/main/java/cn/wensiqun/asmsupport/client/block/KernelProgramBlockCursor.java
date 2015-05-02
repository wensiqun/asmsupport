package cn.wensiqun.asmsupport.client.block;

import cn.wensiqun.asmsupport.core.block.KernelProgramBlock;

/**
 * The {@link KernelProgramBlock} cursor, this class 
 * 
 * @author WSQ
 *
 */
public class KernelProgramBlockCursor {

    private KernelProgramBlock pointer;

    public KernelProgramBlockCursor(KernelProgramBlock pointer) {
        this.pointer = pointer;
    }

    public KernelProgramBlock getPointer() {
        return pointer;
    }

    void setPointer(KernelProgramBlock pointer) {
        this.pointer = pointer;
    }
    
}
