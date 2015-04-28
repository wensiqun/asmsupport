
package cn.wensiqun.asmsupport.client.def.action;

import cn.wensiqun.asmsupport.client.block.KernelProgramBlockCursor;
import cn.wensiqun.asmsupport.client.def.Param;
import cn.wensiqun.asmsupport.client.def.ParamPostern;
import cn.wensiqun.asmsupport.client.def.param.DummyParam;
import cn.wensiqun.asmsupport.core.operator.Operator;

public class ReverseAction extends AbstractUnaryAction {

    public ReverseAction(KernelProgramBlockCursor cursor) {
        super(cursor, Operator.REVERSE);
    }

    @Override
    public Param doAction(Param... operands) {
       return new DummyParam(cursor, cursor.getPointer().reverse(ParamPostern.getTarget(operands[0])));
    }

}
