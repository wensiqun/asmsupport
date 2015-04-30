package cn.wensiqun.asmsupport.client.def.behavior;

import cn.wensiqun.asmsupport.client.def.Param;
import cn.wensiqun.asmsupport.standard.def.clazz.AClass;



public interface ObjectBehavior extends CommonBehavior {

    ObjectBehavior call(String methodName, Param... arguments);
    
    ObjectBehavior cast(Class<?> type);

    ObjectBehavior cast(AClass type);
    
    BoolBehavior instanceof_(Class<?> type);
    
    BoolBehavior instanceof_(AClass type);
    
}
