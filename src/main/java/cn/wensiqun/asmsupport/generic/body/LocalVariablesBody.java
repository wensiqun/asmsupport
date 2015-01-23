package cn.wensiqun.asmsupport.generic.body;

import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;

public interface LocalVariablesBody extends IBody
{
    void body(LocalVariable... args);
}
