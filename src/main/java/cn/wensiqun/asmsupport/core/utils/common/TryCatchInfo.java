package cn.wensiqun.asmsupport.core.utils.common;

import cn.wensiqun.asmsupport.org.objectweb.asm.Label;
import cn.wensiqun.asmsupport.org.objectweb.asm.Type;

public class TryCatchInfo
{
    private Label start;
    private Label end;
    private Label hander;
    private Type  exception;

    public TryCatchInfo(Label start, Label end, Label hander, Type exception)
    {
        this.start = start;
        this.end = end;
        this.hander = hander;
        this.exception = exception;
    }

    public Label getStart()
    {
        return start;
    }

    public Label getEnd()
    {
        return end;
    }

    public Label getHander()
    {
        return hander;
    }

    public Type getException()
    {
        return exception;
    }

}