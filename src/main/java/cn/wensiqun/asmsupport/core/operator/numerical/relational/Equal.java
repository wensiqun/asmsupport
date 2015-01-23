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
public class Equal extends AbstractNullCompareRelational {

    protected Equal(ProgramBlockInternal block, Parameterized factor1, Parameterized factor2) {
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
