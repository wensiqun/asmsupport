package cn.wensiqun.asmsupport.client.operations;

import cn.wensiqun.asmsupport.client.DummyParam;
import cn.wensiqun.asmsupport.client.Param;
import cn.wensiqun.asmsupport.client.ProgramBlock;
import cn.wensiqun.asmsupport.core.block.ProgramBlockInternal;
import cn.wensiqun.asmsupport.core.operator.Operator;
import cn.wensiqun.asmsupport.core.operator.numerical.arithmetic.KernelMul;
import cn.wensiqun.asmsupport.org.apache.commons.collections.ArrayStack;

public class Mul extends DummyParam {

    public Mul(KernelMul target) {
        super(target);
    }

    public static class MulAction extends OperatorAction {

		public MulAction(ProgramBlock<ProgramBlockInternal> block) {
			super(block, Operator.MUL);
		}

		@Override
		public Param doAction(ArrayStack<Param> operands) {
			Param fac2 = operands.pop();
			Param fac1 = operands.pop();
			return block.mul(fac1, fac2);
		}
    	
    }
}
