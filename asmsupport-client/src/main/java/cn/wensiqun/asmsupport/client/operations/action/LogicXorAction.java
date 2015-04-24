package cn.wensiqun.asmsupport.client.operations.action;

import cn.wensiqun.asmsupport.client.block.KernelProgramBlockCursor;
import cn.wensiqun.asmsupport.client.def.Param;
import cn.wensiqun.asmsupport.client.def.ParamPostern;
import cn.wensiqun.asmsupport.client.def.param.DummyParam;
import cn.wensiqun.asmsupport.core.operator.Operator;

public class LogicXorAction extends AbstractBinaryAction {

    public LogicXorAction(KernelProgramBlockCursor cursor) {
        super(cursor, Operator.XOR);
    }

    @Override
    public Param doAction(Param... operands) {
        return new DummyParam(cursor, cursor.getPointer().logicalXor(ParamPostern.getTarget(operands[0]), 
                ParamPostern.getTarget(operands[1])));
    }

}
