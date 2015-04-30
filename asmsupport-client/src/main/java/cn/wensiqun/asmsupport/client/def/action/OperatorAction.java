package cn.wensiqun.asmsupport.client.def.action;

import cn.wensiqun.asmsupport.client.block.KernelProgramBlockCursor;
import cn.wensiqun.asmsupport.client.def.Param;
import cn.wensiqun.asmsupport.core.operator.Operator;
import cn.wensiqun.asmsupport.org.apache.commons.collections.ArrayStack;

/**
 * A wrapper of an operator 
 * 
 * @author WSQ
 *
 */
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

    /**
     * Execute a operator, and get the related operands from 
     * a stack
     * 
     * @param operands the operands stack.
     * @return {@link Param} the result
     */
    public abstract Param doAction(ArrayStack<Param> operands);
    
    /**
     * Execute a operator, get the related operands from arguments.
     * 
     * @param operands
     * @return {@link Param} the result
     */
    public abstract Param doAction(Param... operands);

}