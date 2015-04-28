package cn.wensiqun.asmsupport.client.def.behavior;

import cn.wensiqun.asmsupport.client.def.Param;

public interface ArrayBehavior extends ObjectBehavior {

    UncertainBehavior load(Param firstDim, Param... dims);
    
    NumBehavior length(Param... dims);
    
    UncertainBehavior store(Param value, Param firstDim, Param... dims);
}
