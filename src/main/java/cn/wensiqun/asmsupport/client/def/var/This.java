package cn.wensiqun.asmsupport.client.def.var;

import cn.wensiqun.asmsupport.core.definition.variable.ThisVariable;
import cn.wensiqun.asmsupport.standard.def.var.ILocVar;

public class This extends Var implements ILocVar{

    public This(ThisVariable target) {
        super(target);
    }

}
