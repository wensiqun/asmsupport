package cn.wensiqun.asmsupport.client.def.param;

import cn.wensiqun.asmsupport.client.def.Param;
import cn.wensiqun.asmsupport.client.def.ParamPostern;
import cn.wensiqun.asmsupport.client.operations.action.OperatorAction;
import cn.wensiqun.asmsupport.core.block.KernelProgramBlock;
import cn.wensiqun.asmsupport.core.definition.KernelParameterized;
import cn.wensiqun.asmsupport.org.apache.commons.collections.ArrayStack;
import cn.wensiqun.asmsupport.standard.def.clazz.AClass;
import cn.wensiqun.asmsupport.standard.def.var.IFieldVar;

public abstract class PriorityParam extends Param {

    protected KernelParameterized target; 
    protected KernelProgramBlock block;
    protected PriorityStack priorityStack;
    
    public PriorityParam(KernelProgramBlock block, OperatorAction action, Param... operands) {
    	this.block = block;
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
    public KernelParameterized getTarget() {
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
