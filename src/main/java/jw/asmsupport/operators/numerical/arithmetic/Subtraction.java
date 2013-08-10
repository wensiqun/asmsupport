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
 * subtraction operator
 * @author 温斯群(Joe Wen)
 *
 */
public class Subtraction extends AbstractArithmetic implements Parameterized{

    private static Log log = LogFactory.getLog(Subtraction.class);
    
    protected Subtraction(ProgramBlock block, Parameterized factor1, Parameterized factor2) {
        super(block, factor1, factor2);
        operator = Operators.SUB;
    }

    @Override
    public void executing() {
        log.debug("start execute sub arithmetic operator");
        factorToStack();
        log.debug("execute the sub instruction");
        insnHelper.sub(resultClass.getType());
    }


}
