package cn.wensiqun.asmsupport.client.def.action;

import cn.wensiqun.asmsupport.client.def.Param;
import cn.wensiqun.asmsupport.core.operator.Operator;
import cn.wensiqun.asmsupport.core.utils.common.BlockTracker;
import cn.wensiqun.asmsupport.org.apache.commons.collections.ArrayStack;

/**
 * 
 * This action indicate the operator have one operand.
 * 
 * @author WSQ
 *
 */
public abstract class AbstractUnaryAction extends OperatorAction {

    public AbstractUnaryAction(BlockTracker tracker, Operator operator) {
        super(tracker, operator);
    }
    
    @Override
    public final Param doAction(ArrayStack<Param> operands) {
        return doAction(operands.pop());
    }

}
