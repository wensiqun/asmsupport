package cn.wensiqun.asmsupport.client.operations.action;

import cn.wensiqun.asmsupport.client.def.Param;
import cn.wensiqun.asmsupport.core.block.KernelProgramBlock;
import cn.wensiqun.asmsupport.core.operator.Operator;
import cn.wensiqun.asmsupport.org.apache.commons.collections.ArrayStack;

public abstract class OperatorAction {

    protected KernelProgramBlock block;

    private Operator operator;

    public OperatorAction(KernelProgramBlock block, Operator operator) {
        this.block = block;
        this.operator = operator;
    }

    public Operator getOperator() {
        return operator;
    }

    public abstract Param doAction(ArrayStack<Param> operands);
    
    public abstract Param doAction(Param... operands);

}