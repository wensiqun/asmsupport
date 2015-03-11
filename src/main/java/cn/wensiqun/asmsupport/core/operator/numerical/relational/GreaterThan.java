/**
 * 
 */
package cn.wensiqun.asmsupport.core.operator.numerical.relational;

import cn.wensiqun.asmsupport.core.Parameterized;
import cn.wensiqun.asmsupport.core.asm.InstructionHelper;
import cn.wensiqun.asmsupport.core.block.ProgramBlockInternal;
import cn.wensiqun.asmsupport.core.operator.Operators;
import cn.wensiqun.asmsupport.org.objectweb.asm.Label;

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
    protected void negativeCmp(Label lbl) {
        insnHelper.ifCmp(targetClass.getType(), InstructionHelper.LE, lbl);
        
    }

	@Override
	protected void positiveCmp(Label lbl) {
	    insnHelper.ifCmp(targetClass.getType(), InstructionHelper.GT, lbl);
	}

}
