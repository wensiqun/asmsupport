package cn.wensiqun.asmsupport.client.operations.behavior;

import cn.wensiqun.asmsupport.client.def.Param;


public interface BoolBehavior extends CommonBehavior {

    BoolBehavior and(Param param);
    
    BoolBehavior or(Param param);

    BoolBehavior logicAnd(Param param);

    BoolBehavior logicOr(Param param);

    BoolBehavior logicXor(Param param);
    
}
