package cn.wensiqun.asmsupport.client.operations;

import cn.wensiqun.asmsupport.client.DummyParam.OperatorAction;
import cn.wensiqun.asmsupport.client.Param;
import cn.wensiqun.asmsupport.client.ProgramBlock;
import cn.wensiqun.asmsupport.client.operations.Add.AddAction;
import cn.wensiqun.asmsupport.client.operations.Mul.MulAction;
import cn.wensiqun.asmsupport.core.block.ProgramBlockInternal;
import cn.wensiqun.asmsupport.core.definition.KernelParameterized;
import cn.wensiqun.asmsupport.org.apache.commons.collections.ArrayStack;
import cn.wensiqun.asmsupport.standard.def.clazz.AClass;
import cn.wensiqun.asmsupport.standard.def.var.IFieldVar;

public class NumberOperator implements Param{

    private KernelParameterized target; 
    private ProgramBlock<ProgramBlockInternal> block;
    private PriorityStack priorityStack;
    
    public NumberOperator(ProgramBlock<ProgramBlockInternal> block, OperatorAction action, Param... operands) {
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
    		target = priorityStack.operandStack.pop().getTarget();
    	}
        return target;
    }
    
    public NumberOperator add(Param para) {
    	priorityStack.pushAction(new AddAction(block), para);
    	return this;
    }
    
    public NumberOperator mul(Param para) {
    	priorityStack.pushAction(new MulAction(block), para);
    	return this;
    }
    
    private static class PriorityStack {
        
    	ArrayStack<OperatorAction> symbolStack = new ArrayStack<OperatorAction>();
        ArrayStack<Param> operandStack = new ArrayStack<Param>();
        
        void pushAction(OperatorAction action, Param... operand) {
        	OperatorAction top = symbolStack.peek();
        	if(top != null && top.getOperator().compare(action.getOperator()) >= 0) {
        		marriageAction();
        	}
        	symbolStack.push(action);
    		for(Param oper : operand) {
    			operandStack.push(oper);
    		}
        }
        
        void marriageAction() {
        	OperatorAction top;
        	while((top = symbolStack.peek()) != null) {
        		operandStack.push(top.doAction(operandStack));
        	}
        }
        
    }

}
