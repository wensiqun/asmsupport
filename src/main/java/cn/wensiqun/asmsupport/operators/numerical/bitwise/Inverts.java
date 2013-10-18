/**
 * 
 */
package cn.wensiqun.asmsupport.operators.numerical.bitwise;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.wensiqun.asmsupport.Parameterized;
import cn.wensiqun.asmsupport.block.ProgramBlock;
import cn.wensiqun.asmsupport.operators.Operators;

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
