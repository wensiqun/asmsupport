package cn.wensiqun.asmsupport.client.action;

import cn.wensiqun.asmsupport.client.Param;
import cn.wensiqun.asmsupport.core.block.ProgramBlockInternal;
import cn.wensiqun.asmsupport.core.operator.Operator;
import cn.wensiqun.asmsupport.org.apache.commons.collections.ArrayStack;

public abstract class OperatorAction {

    protected ProgramBlockInternal block;

    private Operator operator;

    public OperatorAction(ProgramBlockInternal block, Operator operator) {
        this.block = block;
        this.operator = operator;
    }

    public Operator getOperator() {
        return operator;
    }

    public abstract Param doAction(ArrayStack<Param> operands);

}