package cn.wensiqun.asmsupport.client.def.behavior;

import cn.wensiqun.asmsupport.client.def.Param;


public interface BoolBehavior extends CommonBehavior {

    BoolBehavior and(Param param);
    
    BoolBehavior and(boolean param);
    
    BoolBehavior or(Param param);
    
    BoolBehavior or(boolean param);

    BoolBehavior logicAnd(Param param);
    
    BoolBehavior logicAnd(boolean param);

    BoolBehavior logicOr(Param param);
    
    BoolBehavior logicOr(boolean param);

    BoolBehavior logicXor(Param param);
    
    BoolBehavior logicXor(boolean param);
    
}
