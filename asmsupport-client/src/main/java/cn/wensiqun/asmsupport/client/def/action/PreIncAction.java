package cn.wensiqun.asmsupport.client.def.action;

import cn.wensiqun.asmsupport.client.block.KernelProgramBlockCursor;
import cn.wensiqun.asmsupport.client.def.Param;
import cn.wensiqun.asmsupport.client.def.ParamPostern;
import cn.wensiqun.asmsupport.client.def.param.DummyParam;
import cn.wensiqun.asmsupport.core.operator.Operator;

public class PreIncAction extends AbstractUnaryAction {

    public PreIncAction(KernelProgramBlockCursor cursor) {
        super(cursor, Operator.PRE_INC);
    }

    @Override
    public Param doAction(Param... operands) {
        return new DummyParam(cursor, cursor.peek().preinc(ParamPostern.getTarget(operands[0])));
    }
}
