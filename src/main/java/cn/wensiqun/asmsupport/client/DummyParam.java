package cn.wensiqun.asmsupport.client;

import cn.wensiqun.asmsupport.client.def.var.FieldVar;
import cn.wensiqun.asmsupport.core.block.ProgramBlockInternal;
import cn.wensiqun.asmsupport.core.definition.KernelParameterized;
import cn.wensiqun.asmsupport.core.operator.Operator;
import cn.wensiqun.asmsupport.org.apache.commons.collections.ArrayStack;
import cn.wensiqun.asmsupport.standard.def.clazz.AClass;

public class DummyParam implements Param {

    protected KernelParameterized target;
    
    public DummyParam(KernelParameterized target) {
        this.target = target;
    }

    @Override
    public final AClass getResultType() {
        return target.getResultType();
    }

    @Override
    public KernelParameterized getTarget() {
        return target;
    }

    @Override
    public FieldVar field(String name) {
        return new FieldVar(target.field(name));
    }
    
    public static abstract class OperatorAction {
    	
    	protected ProgramBlock<ProgramBlockInternal> block;
    	
    	private Operator operator;
    	
    	public OperatorAction(ProgramBlock<ProgramBlockInternal> block, Operator operator) {
			this.block = block;
			this.operator = operator;
		}
    	
		public Operator getOperator() {
			return operator;
		}

		public abstract Param doAction(ArrayStack<Param> operands);
    	
    }
}
