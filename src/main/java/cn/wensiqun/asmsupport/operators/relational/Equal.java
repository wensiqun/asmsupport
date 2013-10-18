/**
 * 
 */
package cn.wensiqun.asmsupport.operators.relational;

import cn.wensiqun.asmsupport.Parameterized;
import cn.wensiqun.asmsupport.asm.InstructionHelper;
import cn.wensiqun.asmsupport.block.ProgramBlock;
import cn.wensiqun.asmsupport.operators.Operators;

/**
 * @author 温斯群(Joe Wen)
 *
 */
public class Equal extends AbstractNullCompareRelational {

    protected Equal(ProgramBlock block, Parameterized factor1, Parameterized factor2) {
        super(block, factor1, factor2);
        this.operator = Operators.EQUAL_TO;
    }

    
    
    @Override
    protected void relationalOperator() {
        ifCmp(targetClass.getType(), InstructionHelper.NE, falseLbl);
    }

	@Override
	protected void relationalOperatorWithInLoopCondition() {
        ifCmp(targetClass.getType(), InstructionHelper.EQ, falseLbl);
	}

	
}
