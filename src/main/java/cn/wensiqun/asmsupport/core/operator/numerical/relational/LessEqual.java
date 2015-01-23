/**
 * 
 */
package cn.wensiqun.asmsupport.core.operator.numerical.relational;

import cn.wensiqun.asmsupport.core.Parameterized;
import cn.wensiqun.asmsupport.core.asm.InstructionHelper;
import cn.wensiqun.asmsupport.core.block.classes.common.ProgramBlockInternal;
import cn.wensiqun.asmsupport.core.operator.Operators;

/**
 * @author 温斯群(Joe Wen)
 *
 */
public class LessEqual extends NumericalRelational {

    protected LessEqual(ProgramBlockInternal block, Parameterized factor1, Parameterized factor2) {
        super(block, factor1, factor2);
        this.operator = Operators.LESS_THAN_OR_EQUAL_TO;
    }

    @Override
    protected void relationalOperator() {
        insnHelper.ifCmp(targetClass.getType(), InstructionHelper.GT, falseLbl);
    }

	@Override
	protected void relationalOperatorWithInLoopCondition() {
	    insnHelper.ifCmp(targetClass.getType(), InstructionHelper.LE, falseLbl);
	}

}
