package cn.wensiqun.asmsupport.client.operations.behavior;

import cn.wensiqun.asmsupport.client.def.Param;

public interface NumBehavior extends CommonBehavior {

    NumBehavior add(Param para);
    
    NumBehavior sub(Param para);
    
    NumBehavior mul(Param para);
    
    NumBehavior div(Param para);
    
    NumBehavior mod(Param para);
    
    NumBehavior band(Param para);
    
    NumBehavior bor(Param para);
    
    NumBehavior bxor(Param para);

    NumBehavior shl(Param para);

    NumBehavior shr(Param para);

    NumBehavior ushr(Param para);
    
    BoolBehavior gt(Param para);
    
    BoolBehavior ge(Param para);
    
    BoolBehavior lt(Param para);
    
    BoolBehavior le(Param para);
}
