package cn.wensiqun.asmsupport.client.def.var;

import cn.wensiqun.asmsupport.client.block.KernelProgramBlockCursor;
import cn.wensiqun.asmsupport.core.definition.variable.SuperVariable;
import cn.wensiqun.asmsupport.standard.def.var.ILocVar;

public class Super extends Var implements ILocVar{

    public Super(KernelProgramBlockCursor cursor, SuperVariable target) {
        super(cursor, target);
    }

}
