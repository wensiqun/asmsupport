/**
 * 
 */
package cn.wensiqun.asmsupport.core.operator.numerical.relational;

import cn.wensiqun.asmsupport.core.Parameterized;
import cn.wensiqun.asmsupport.core.asm.InstructionHelper;
import cn.wensiqun.asmsupport.core.block.ProgramBlockInternal;
import cn.wensiqun.asmsupport.core.operator.Operators;
import cn.wensiqun.asmsupport.org.objectweb.asm.Label;

/**
 * @author 温斯群(Joe Wen)
 *
 */
public class GreaterEqual extends NumericalRelational {

    protected GreaterEqual(ProgramBlockInternal block, Parameterized factor1, Parameterized factor2) {
        super(block, factor1, factor2);
        this.operator = Operators.GREATER_THAN_OR_EQUAL_TO;
    }

    @Override
    protected void negativeCmp(Label lbl) {
        insnHelper.ifCmp(targetClass.getType(), InstructionHelper.LT, lbl);
    }

	@Override
	protected void positiveCmp(Label lbl) {
	    insnHelper.ifCmp(targetClass.getType(), InstructionHelper.GE, lbl);
	}

}
