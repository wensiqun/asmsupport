/**    
 *  Asmsupport is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package cn.wensiqun.asmsupport.client.def.param;

import cn.wensiqun.asmsupport.client.def.Param;
import cn.wensiqun.asmsupport.client.def.ParamPostern;
import cn.wensiqun.asmsupport.client.def.action.OperatorAction;
import cn.wensiqun.asmsupport.client.def.behavior.CommonBehavior;
import cn.wensiqun.asmsupport.client.def.var.FieldVar;
import cn.wensiqun.asmsupport.client.def.var.LocVar;
import cn.wensiqun.asmsupport.core.definition.KernelParam;
import cn.wensiqun.asmsupport.core.operator.Operator;
import cn.wensiqun.asmsupport.core.utils.common.BlockTracker;
import cn.wensiqun.asmsupport.org.apache.commons.collections.ArrayStack;
import cn.wensiqun.asmsupport.standard.def.clazz.IClass;


/**
 * Indicate a parameter that's support priority for each operator
 * 
 * @author WSQ
 *
 */
public abstract class PriorityParam extends Param implements CommonBehavior {

    protected KernelParam target; 
    protected BlockTracker tracker;
    protected PriorityStack priorityStack;
    
    protected PriorityParam(BlockTracker tracker, Param result) {
    	this.tracker = tracker;
    	priorityStack = new PriorityStack();
    	priorityStack.operandStack.push(result);
    }
    
    public PriorityParam(BlockTracker tracker, OperatorAction action, Param... operands) {
    	this.tracker = tracker;
    	priorityStack = new PriorityStack();
    	priorityStack.pushAction(action, operands);
    }
    
    @Override
    public IClass getResultType() {
        return getTarget().getResultType();
    }

    @Override
    public KernelParam getTarget() {
    	if(target == null) {
    		priorityStack.marriageAction(Operator.MIN_PRIORITY);
    		target = ParamPostern.getTarget(priorityStack.operandStack.pop());
    	}
        return target;
    }

    @Override
    public final FieldVar field(String name) {
        return new FieldVar(tracker, getTarget().field(name));
    }
    
    @Override
    public final ObjectParam stradd(Param param) {
        return new ObjectParam(tracker, tracker.track().stradd(target, ParamPostern.getTarget(param)));
    }

	@Override
	public LocVar asVar() {
		return asVar(getResultType());
	}
	
	@Override
	public LocVar asVar(IClass type) {
		return new LocVar(tracker, tracker.track().var(type, getTarget()));
	}

	@Override
	public LocVar asVar(Class<?> type) {
		return new LocVar(tracker, tracker.track().var(type, getTarget()));
	}

	@Override
	public LocVar asVar(String varName) {
		return asVar(varName, getResultType());
	}

	@Override
	public LocVar asVar(String varName, IClass type) {
		return new LocVar(tracker, tracker.track().var(varName, type, getTarget()));
	}

	@Override
	public LocVar asVar(String varName, Class<?> type) {
		return new LocVar(tracker, tracker.track().var(varName, type, getTarget()));
	}

	static class PriorityStack {
        
    	ArrayStack<OperatorAction> symbolStack = new ArrayStack<OperatorAction>();
        ArrayStack<Param> operandStack = new ArrayStack<Param>();
        
        void pushAction(OperatorAction action, Param... operand) {
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
        
        void marriageAction(Operator operator) {
        	while(!symbolStack.isEmpty() && 
        		   symbolStack.peek().getOperator().compare(operator) >= 0) {
        		operandStack.push(symbolStack.pop().doAction(operandStack));
        	}
        }
        
    }
}
