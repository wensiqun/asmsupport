package cn.wensiqun.asmsupport.core.utils.common;

import cn.wensiqun.asmsupport.core.block.KernelProgramBlock;

/**
 * Created by sqwen on 2016/5/11.
 */
public interface BlockTracker {

    /**
     * Get the current {@link KernelProgramBlock} at generate time.
     * @return
     */
    KernelProgramBlock track();

}
