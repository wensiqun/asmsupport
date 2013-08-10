/**
 * 
 */
package jw.asmsupport.operators.numerical.bitwise;

import jw.asmsupport.Parameterized;
import jw.asmsupport.block.ProgramBlock;
import jw.asmsupport.operators.Operators;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @author 温斯群(Joe Wen)
 *
 */
public class Inverts extends UnaryBitwise {

    private static Log log = LogFactory.getLog(Inverts.class);
    
    protected Inverts(ProgramBlock block, Parameterized factor) {
        super(block, factor);
        this.operator = Operators.INVERTS;
    }

    @Override
    public void executing() {
        log.debug("start inverts operaotr : " + this.operator);
        log.debug("factor to stack");
        factorToStack();
        log.debug("start invert");
        insnHelper.inverts(resultClass.getType());
    }

}
