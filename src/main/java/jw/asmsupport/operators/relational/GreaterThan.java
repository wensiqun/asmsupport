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
public class GreaterThan extends NumericalRelational {

    protected GreaterThan(ProgramBlock block, Parameterized factor1, Parameterized factor2) {
        super(block, factor1, factor2);
        this.operator = Operators.GREATER_THAN;
    }

    @Override
    protected void relationalOperator() {
        ifCmp(targetClass.getType(), InstructionHelper.LE, falseLbl);
        
    }

	@Override
	protected void relationalOperatorWithInLoopCondition() {
        ifCmp(targetClass.getType(), InstructionHelper.GT, falseLbl);
	}

}
