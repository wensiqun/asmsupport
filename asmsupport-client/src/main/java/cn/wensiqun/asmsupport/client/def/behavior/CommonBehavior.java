package cn.wensiqun.asmsupport.client.def.behavior;

import cn.wensiqun.asmsupport.client.def.Param;

public interface CommonBehavior {

    BoolBehavior eq(Param para);

    BoolBehavior ne(Param para);
    
    ObjectBehavior stradd(Param param);
    
}
