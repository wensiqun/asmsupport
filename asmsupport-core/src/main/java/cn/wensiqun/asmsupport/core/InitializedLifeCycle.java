package cn.wensiqun.asmsupport.core;

import cn.wensiqun.asmsupport.core.context.ClassExecuteContext;

/**
 * Created by sqwen on 2016/7/6.
 */
public interface InitializedLifeCycle extends LifeCycle<ClassExecuteContext>{

    /**
     * First phase, asmsupport will create all of meta information such
     * as "Field information", "Method information" etc.
     */
    void initialized(ClassExecuteContext context);

    /**
     * Second phase, asmsupport will verify the code you want's to generated.
     * such as : check the method call is legally.  must call {@link #initialized(ClassExecuteContext)}
     * before call this method
     */
    @Override
    void prepare();


    /**
     * Third phase, asmsupport will generate byte code. and
     * will return the byte array of class. must call {@link #prepare()}
     * before call this method
     */
    @Override
    void execute(ClassExecuteContext context);

}
