package cn.wensiqun.asmsupport.standard.body;

import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;

public interface LocalVariablesBody extends IBody
{
    void body(LocalVariable... args);
}
