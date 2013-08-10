/**
 * 
 */
package jw.asmsupport.operators.logical;

import jw.asmsupport.Parameterized;
import jw.asmsupport.block.ProgramBlock;
import jw.asmsupport.clazz.AClass;
import jw.asmsupport.operators.Operators;

/**
 * 
 * @author 温斯群(Joe Wen)
 *
 */
public class LogicalXor extends BinaryLogical {
    
    protected LogicalXor(ProgramBlock block, Parameterized factor1, Parameterized factor2) {
        super(block, factor1, factor2);
        operator = Operators.XOR;
    }

    @Override
    protected void executingProcess() {
        insnHelper.bitXor(AClass.BOOLEAN_ACLASS.getType());
    }

}
