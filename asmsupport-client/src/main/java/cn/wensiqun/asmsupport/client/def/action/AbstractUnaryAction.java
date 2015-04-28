package cn.wensiqun.asmsupport.client.def.action;

import cn.wensiqun.asmsupport.client.block.KernelProgramBlockCursor;
import cn.wensiqun.asmsupport.client.def.Param;
import cn.wensiqun.asmsupport.core.operator.Operator;
import cn.wensiqun.asmsupport.org.apache.commons.collections.ArrayStack;

public abstract class AbstractUnaryAction extends OperatorAction {

    public AbstractUnaryAction(KernelProgramBlockCursor cursor, Operator operator) {
        super(cursor, operator);
    }
    
    @Override
    public final Param doAction(ArrayStack<Param> operands) {
        return doAction(operands.pop());
    }

}
