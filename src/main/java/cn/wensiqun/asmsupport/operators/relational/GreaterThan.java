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
