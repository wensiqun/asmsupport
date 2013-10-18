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
