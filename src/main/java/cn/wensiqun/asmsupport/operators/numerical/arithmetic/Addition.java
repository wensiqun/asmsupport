/**
 * 
 */
package cn.wensiqun.asmsupport.operators.numerical.arithmetic;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.wensiqun.asmsupport.Parameterized;
import cn.wensiqun.asmsupport.block.ProgramBlock;
import cn.wensiqun.asmsupport.operators.Operators;

/**
 * addition operator
 * @author 温斯群(Joe Wen)
 *
 */
public class Addition extends AbstractArithmetic {

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
