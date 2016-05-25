package cn.wensiqun.asmsupport.client;

import cn.wensiqun.asmsupport.client.def.Param;
import cn.wensiqun.asmsupport.client.def.action.OperatorAction;
import cn.wensiqun.asmsupport.core.block.KernelProgramBlock;
import cn.wensiqun.asmsupport.core.definition.KernelParam;
import cn.wensiqun.asmsupport.core.operator.Operator;
import cn.wensiqun.asmsupport.core.utils.common.BlockTracker;
import cn.wensiqun.asmsupport.org.apache.commons.collections.ArrayStack;

/**
 * Created by sqwen on 2016/5/23.
 */
public class ClientBridge<B extends KernelProgramBlock> implements BlockTracker {

    private B delegate;

    public ClientBridge(B delegate) {
        this.delegate = delegate;
    }

    @Override
    public KernelProgramBlock track() {
        return delegate.getBlockTracker().track();
    }

    public B getDelegate() {
        return delegate;
    }

    /**
     * Build a {@PriorityStack} with a special {@OperatorAction} and related operands.
     * @param action
     * @param operands
     * @return
     */
    public PriorityStack build(OperatorAction action, Param... operands) {
        PriorityStack priorityStack = new PriorityStack();
        priorityStack.pushAction(action, operands);
        return priorityStack;
    }

    /**
     *
     * @param operand
     * @return
     */
    public PriorityStack build(Param operand) {
        PriorityStack priorityStack = new PriorityStack();
        priorityStack.operandStack.push(operand);
        return priorityStack;
    }

    /**
     * Support priority for multiple operators in expression.
     */
    public static class PriorityStack {

        private ArrayStack<OperatorAction> symbolStack = new ArrayStack<>();

        private ArrayStack<Param> operandStack = new ArrayStack<>();

        private PriorityStack(){}

        public void pushAction(OperatorAction action, Param... operand) {
            if(!symbolStack.isEmpty()) {
                int cmpVal = action.getOperator().compare(symbolStack.peek().getOperator());
                if(cmpVal < 0) {
                    marriageAction(action.getOperator());
                } else if (cmpVal == 0){
                    operandStack.push(symbolStack.pop().doAction(operandStack));
                }
            }
            symbolStack.push(action);
            for(Param param : operand) {
                operandStack.push(param);
            }
        }

        public KernelParam execute() {
            marriageAction(Operator.MIN_PRIORITY);
            return operandStack.peek().getTarget();
        }

        private void marriageAction(Operator operator) {
            while(!symbolStack.isEmpty() &&
                    symbolStack.peek().getOperator().compare(operator) >= 0) {
                operandStack.push(symbolStack.pop().doAction(operandStack));
            }
        }

    }

}
