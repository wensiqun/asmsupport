package cn.wensiqun.asmsupport.client.def.action;

import cn.wensiqun.asmsupport.client.def.Param;
import cn.wensiqun.asmsupport.core.operator.Operator;
import cn.wensiqun.asmsupport.core.utils.common.BlockTracker;

import java.util.Stack;

/**
 * A wrapper of an operator 
 * 
 * @author WSQ
 *
 */
public abstract class OperatorAction {

    protected BlockTracker tracker;

    private Operator operator;

    public OperatorAction(BlockTracker tracker, Operator operator) {
        this.tracker = tracker;
        this.operator = operator;
    }

    /**
     * Get the {@link Operator} corresponding to current action
     * 
     * @return
     */
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
    public abstract Param doAction(Stack<Param> operands);
    
    /**
     * Execute a operator, get the related operands from arguments.
     * 
     * @param operands
     * @return {@link Param} the result
     */
    public abstract Param doAction(Param... operands);

}