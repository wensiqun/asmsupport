package cn.wensiqun.asmsupport.core.block;

import cn.wensiqun.asmsupport.core.ByteCodeExecutor;
import cn.wensiqun.asmsupport.core.utils.collections.CommonLinkedList;

public abstract class AbstractBlockInternal extends ByteCodeExecutor implements Cloneable {

    private CommonLinkedList<ByteCodeExecutor> queue;

	public AbstractBlockInternal() {
		this.queue = new CommonLinkedList<ByteCodeExecutor>();
	}

	public CommonLinkedList<ByteCodeExecutor> getQueue() {
		return queue;
	}

    /**
     * 
     * @param exe
     */
    public void removeExe(ByteCodeExecutor exe) {
        getQueue().remove(exe);
    }
}
