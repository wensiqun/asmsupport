/**
 * 
 */
package cn.wensiqun.asmsupport.operators.numerical.posinegative;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.wensiqun.asmsupport.Parameterized;
import cn.wensiqun.asmsupport.block.ProgramBlock;
import cn.wensiqun.asmsupport.operators.Operators;

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
