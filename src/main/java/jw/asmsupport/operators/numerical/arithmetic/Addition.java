/**
 * 
 */
package jw.asmsupport.operators.numerical.arithmetic;

import jw.asmsupport.Parameterized;
import jw.asmsupport.block.ProgramBlock;
import jw.asmsupport.operators.Operators;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * addition operator
 * @author 温斯群(Joe Wen)
 *
 */
public class Addition extends AbstractArithmetic implements Parameterized{

    private static Log log = LogFactory.getLog(Addition.class);
    
    protected Addition(ProgramBlock block, Parameterized factor1, Parameterized factor2) {
        super(block, factor1, factor2);
        operator = Operators.ADD;
    }

    @Override
    public void executing() {
        log.debug("start execute add arithmetic operator");
        factorToStack();
        log.debug("execute the add instruction");
        insnHelper.add(resultClass.getType());
    }


}
