package cn.wensiqun.asmsupport.client.param;

import cn.wensiqun.asmsupport.client.Param;

public interface ObjectBehavior {

    ObjectBehavior call(String methodName, Param... arguments);
    
    ObjectBehavior stradd(Param param);
    
}
