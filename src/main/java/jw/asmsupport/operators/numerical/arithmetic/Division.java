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
 * division operator
 * @author 温斯群(Joe Wen)
 *
 */
public class Division extends AbstractArithmetic implements Parameterized{

    private static Log log = LogFactory.getLog(Division.class);
    
    protected Division(ProgramBlock block, Parameterized factor1, Parameterized factor2) {
        super(block, factor1, factor2);
        operator = Operators.DIV;
    }


    @Override
    public void executing() {
        log.debug("start execute sub arithmetic operator");
        factorToStack();
        log.debug("execute the sub instruction");
        insnHelper.div(resultClass.getType());
    }


}
