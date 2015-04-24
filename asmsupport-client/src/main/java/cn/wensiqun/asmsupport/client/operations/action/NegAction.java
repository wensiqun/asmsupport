
package cn.wensiqun.asmsupport.client.operations.action;

import cn.wensiqun.asmsupport.client.block.KernelProgramBlockCursor;
import cn.wensiqun.asmsupport.client.def.Param;
import cn.wensiqun.asmsupport.client.def.ParamPostern;
import cn.wensiqun.asmsupport.client.def.param.DummyParam;
import cn.wensiqun.asmsupport.core.operator.Operator;

public class NegAction extends AbstractUnaryAction {

    public NegAction(KernelProgramBlockCursor cursor) {
        super(cursor, Operator.NEG);
    }

    @Override
    public Param doAction(Param... operands) {
       return new DummyParam(cursor, cursor.getPointer().neg(ParamPostern.getTarget(operands[0])));
    }

}
