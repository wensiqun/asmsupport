package cn.wensiqun.asmsupport.core.block.control.exception;

import cn.wensiqun.asmsupport.core.ByteCodeExecutor;
import cn.wensiqun.asmsupport.core.block.control.EpisodeBlock;
import cn.wensiqun.asmsupport.core.exception.ASMSupportException;
import cn.wensiqun.asmsupport.standard.excep.ITry;

public abstract class TryInternal extends EpisodeBlock<ExceptionSerialBlock> implements ITry<CatchInternal, FinallyInternal>
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

    @Override
    public CatchInternal catch_(CatchInternal catchBlock)
    {
        ExceptionSerialBlock serial = getSerial();
        
        if(serial.getFinally() != null)
        {
            throw new ASMSupportException("Exists finally block. please create catch before finally block");
        }
        getSerial().appendEpisode(catchBlock);
        return catchBlock;
    }
    
    @Override
    public FinallyInternal finally_(FinallyInternal block)
    {
        ExceptionSerialBlock serial = getSerial();
        if(serial.getFinally() != null)
        {
            throw new ASMSupportException("Already exists finally block.");
        }
        getSerial().appendEpisode(block);
        
        return block;
    }
    
}
