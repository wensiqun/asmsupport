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
public class GreaterThan extends NumericalRelational {

    protected GreaterThan(ProgramBlockInternal block, Parameterized factor1, Parameterized factor2) {
        super(block, factor1, factor2);
        this.operator = Operators.GREATER_THAN;
    }

    @Override
    protected void relationalOperator() {
        insnHelper.ifCmp(targetClass.getType(), InstructionHelper.LE, falseLbl);
        
    }

	@Override
	protected void relationalOperatorWithInLoopCondition() {
	    insnHelper.ifCmp(targetClass.getType(), InstructionHelper.GT, falseLbl);
	}

}
