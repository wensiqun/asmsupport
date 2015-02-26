package cn.wensiqun.asmsupport.core.block.classes.control.exception;

import cn.wensiqun.asmsupport.core.ByteCodeExecutor;
import cn.wensiqun.asmsupport.core.block.classes.control.EpisodeBlock;
import cn.wensiqun.asmsupport.standard.excep.IFinally;

public abstract class FinallyInternal extends EpisodeBlock<ExceptionSerialBlock> implements IFinally
{

    @Override
    public void generate()
    {
        body();
    }

    @Override
    protected void doExecute()
    {
        for(ByteCodeExecutor exe : getQueue()){
            exe.execute();
        }
    }

}
