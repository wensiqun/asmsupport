package cn.wensiqun.asmsupport.client.operations;

import cn.wensiqun.asmsupport.client.DummyParam;
import cn.wensiqun.asmsupport.client.Param;
import cn.wensiqun.asmsupport.client.ProgramBlock;
import cn.wensiqun.asmsupport.core.block.ProgramBlockInternal;
import cn.wensiqun.asmsupport.core.operator.Operator;
import cn.wensiqun.asmsupport.core.operator.numerical.arithmetic.KernelAdd;
import cn.wensiqun.asmsupport.org.apache.commons.collections.ArrayStack;


/**
 * The add operator
 * 
 * @author sqwen
 *
 */
public class Add extends DummyParam {

    public Add(KernelAdd target) {
        super(target);
    }

    public static class AddAction extends OperatorAction {

		public AddAction(ProgramBlock<ProgramBlockInternal> block) {
			super(block, Operator.ADD);
		}

		@Override
		public Add doAction(ArrayStack<Param> operands) {
			Param fac2 = operands.pop();
			Param fac1 = operands.pop();
			return block.add(fac1, fac2);
		}
    	
    }
}
