/**
 * 
 */
package jw.asmsupport.operators.numerical.posinegative;

import jw.asmsupport.Parameterized;
import jw.asmsupport.block.ProgramBlock;
import jw.asmsupport.operators.Operators;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author 温斯群(Joe Wen)
 *
 */
public class Negative extends AbstractPositiveNegative {

    private static Log log = LogFactory.getLog(Negative.class);
    
    protected Negative(ProgramBlock block, Parameterized factor) {
        super(block, factor);
        operator = Operators.NEG;
    }
    
    @Override
    public void executing() {
        log.debug("run the negative operator");
        factorToStack();
        insnHelper.neg(factor.getParamterizedType().getType());
    }

}
