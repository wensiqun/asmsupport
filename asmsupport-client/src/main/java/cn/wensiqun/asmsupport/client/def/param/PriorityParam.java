package cn.wensiqun.asmsupport.client.def.param;

import cn.wensiqun.asmsupport.client.block.KernelProgramBlockCursor;
import cn.wensiqun.asmsupport.client.def.Param;
import cn.wensiqun.asmsupport.client.def.ParamPostern;
import cn.wensiqun.asmsupport.client.operations.action.OperatorAction;
import cn.wensiqun.asmsupport.core.definition.KernelParame;
import cn.wensiqun.asmsupport.org.apache.commons.collections.ArrayStack;
import cn.wensiqun.asmsupport.standard.def.clazz.AClass;
import cn.wensiqun.asmsupport.standard.def.var.IFieldVar;

public abstract class PriorityParam extends Param {

    protected KernelParame target; 
    protected KernelProgramBlockCursor cursor;
    protected PriorityStack priorityStack;
    
    public PriorityParam(KernelProgramBlockCursor cursor, OperatorAction action, Param... operands) {
    	this.cursor = cursor;
    	priorityStack = new PriorityStack();
    	priorityStack.pushAction(action, operands);
    }
    
    @Override
    public AClass getResultType() {
        return getTarget().getResultType();
    }

    @Override
    public IFieldVar field(String name) {
        return getTarget().field(name);
    }

    @Override
    public KernelParame getTarget() {
    	if(target == null) {
    		priorityStack.marriageAction();
    		target = ParamPostern.getTarget(priorityStack.operandStack.pop());
    	}
        return target;
    }

    static class PriorityStack {
        
    	ArrayStack<OperatorAction> symbolStack = new ArrayStack<OperatorAction>();
        ArrayStack<Param> operandStack = new ArrayStack<Param>();
        
        void pushAction(OperatorAction action, Param... operand) {
        	if(!symbolStack.isEmpty()) {
        	    int cmpVal = action.getOperator().compare(symbolStack.peek().getOperator());
        	    if(cmpVal < 0) {
                    marriageAction();
        	    } else if (cmpVal == 0){
        	        operandStack.push(symbolStack.pop().doAction(operandStack));
        	    }
        	}
        	symbolStack.push(action);
    		for(Param oper : operand) {
    			operandStack.push(oper);
    		}
        }
        
        void marriageAction() {
        	while(!symbolStack.isEmpty()) {
        		operandStack.push(symbolStack.pop().doAction(operandStack));
        	}
        }
        
    }

}
