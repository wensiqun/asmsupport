package cn.wensiqun.asmsupport.client.def.action;

import cn.wensiqun.asmsupport.client.block.KernelProgramBlockCursor;
import cn.wensiqun.asmsupport.client.def.Param;
import cn.wensiqun.asmsupport.client.def.ParamPostern;
import cn.wensiqun.asmsupport.client.def.param.DummyParam;
import cn.wensiqun.asmsupport.core.operator.Operator;

public class PostIncAction extends AbstractUnaryAction {

    public PostIncAction(KernelProgramBlockCursor cursor) {
        super(cursor, Operator.POS_INC);
    }

    @Override
    public Param doAction(Param... operands) {
        return new DummyParam(cursor, cursor.getPointer().postinc(ParamPostern.getTarget(operands[0])));
    }

}
