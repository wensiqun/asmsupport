package cn.wensiqun.asmsupport.client.def.var;

import cn.wensiqun.asmsupport.core.block.KernelProgramBlock;
import cn.wensiqun.asmsupport.core.definition.variable.SuperVariable;
import cn.wensiqun.asmsupport.standard.def.var.ILocVar;

public class Super extends Var implements ILocVar{

    public Super(KernelProgramBlock block, SuperVariable target) {
        super(block, target);
    }

}
