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
public class NotEqual extends AbstractNullCompareRelational {

    protected NotEqual(ProgramBlock block, Parameterized factor1, Parameterized factor2) {
        super(block, factor1, factor2);
        this.operator = Operators.NOT_EQUAL_TO;
    }

    @Override
    protected void relationalOperator() {
        this.ifCmp(targetClass.getType(), InstructionHelper.EQ, falseLbl);
    }

	@Override
	protected void relationalOperatorWithInLoopCondition() {
        this.ifCmp(targetClass.getType(), InstructionHelper.NE, falseLbl);
	}

}
