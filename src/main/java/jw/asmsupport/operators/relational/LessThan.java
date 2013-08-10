/**
 * 
 */
package jw.asmsupport.operators.relational;

import jw.asmsupport.Parameterized;
import jw.asmsupport.asm.InstructionHelper;
import jw.asmsupport.block.ProgramBlock;
import jw.asmsupport.operators.Operators;

/**
 * @author 温斯群(Joe Wen)
 *
 */
public class LessThan extends NumericalRelational {

    protected LessThan(ProgramBlock block, Parameterized factor1, Parameterized factor2) {
        super(block, factor1, factor2);
        this.operator = Operators.LESS_THAN;
    }

    @Override
    protected void relationalOperator() {
        ifCmp(targetClass.getType(), InstructionHelper.GE, falseLbl);
    }

	@Override
	protected void relationalOperatorWithInLoopCondition() {
        ifCmp(targetClass.getType(), InstructionHelper.LT, falseLbl);
	}

    
}
