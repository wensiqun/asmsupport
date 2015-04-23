package cn.wensiqun.asmsupport.client.operations.action;

import cn.wensiqun.asmsupport.client.def.Param;
import cn.wensiqun.asmsupport.core.block.KernelProgramBlock;
import cn.wensiqun.asmsupport.core.operator.Operator;
import cn.wensiqun.asmsupport.org.apache.commons.collections.ArrayStack;

public abstract class AbstractUnaryAction extends OperatorAction {

    public AbstractUnaryAction(KernelProgramBlock block, Operator operator) {
        super(block, operator);
    }
    
    @Override
    public final Param doAction(ArrayStack<Param> operands) {
        return doAction(operands.pop());
    }

}
