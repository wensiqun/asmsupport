package cn.wensiqun.asmsupport.client.operations.action;

import cn.wensiqun.asmsupport.client.block.KernelProgramBlockCursor;
import cn.wensiqun.asmsupport.client.def.Param;
import cn.wensiqun.asmsupport.core.operator.Operator;
import cn.wensiqun.asmsupport.org.apache.commons.collections.ArrayStack;

public abstract class OperatorAction {

    protected KernelProgramBlockCursor cursor;

    private Operator operator;

    public OperatorAction(KernelProgramBlockCursor cursor, Operator operator) {
        this.cursor = cursor;
        this.operator = operator;
    }

    public Operator getOperator() {
        return operator;
    }

    public abstract Param doAction(ArrayStack<Param> operands);
    
    public abstract Param doAction(Param... operands);

}