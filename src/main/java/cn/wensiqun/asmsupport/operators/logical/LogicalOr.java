/**
 * 
 */
package cn.wensiqun.asmsupport.operators.logical;

import cn.wensiqun.asmsupport.Parameterized;
import cn.wensiqun.asmsupport.block.ProgramBlock;
import cn.wensiqun.asmsupport.clazz.AClass;
import cn.wensiqun.asmsupport.operators.Operators;

/**
 * 
 * @author 温斯群(Joe Wen)
 *
 */
public class LogicalOr extends BinaryLogical {
    
    protected LogicalOr(ProgramBlock block, Parameterized factor1, Parameterized factor2) {
        super(block, factor1, factor2);
        operator = Operators.BIT_OR;
    }

    @Override
    protected void executingProcess() {
        insnHelper.bitOr(AClass.BOOLEAN_ACLASS.getType());
    }

}
