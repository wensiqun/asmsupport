/**
 * 
 */
package cn.wensiqun.asmsupport.core.operator.logical;

import cn.wensiqun.asmsupport.core.Parameterized;
import cn.wensiqun.asmsupport.core.block.classes.common.ProgramBlockInternal;
import cn.wensiqun.asmsupport.core.clazz.AClass;
import cn.wensiqun.asmsupport.core.operator.Operators;

/**
 * 
 * @author 温斯群(Joe Wen)
 *
 */
public class LogicalAnd extends BinaryLogical {
    
    protected LogicalAnd(ProgramBlockInternal block, Parameterized factor1, Parameterized factor2) {
        super(block, factor1, factor2);
        operator = Operators.BIT_AND;
    }

    @Override
    protected void executingProcess() {
        insnHelper.bitAnd(AClass.BOOLEAN_ACLASS.getType());    
    }

}
