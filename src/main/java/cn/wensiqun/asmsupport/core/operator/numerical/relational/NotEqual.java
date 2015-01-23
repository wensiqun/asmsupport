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
public class NotEqual extends AbstractNullCompareRelational {

    protected NotEqual(ProgramBlockInternal block, Parameterized factor1, Parameterized factor2) {
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
