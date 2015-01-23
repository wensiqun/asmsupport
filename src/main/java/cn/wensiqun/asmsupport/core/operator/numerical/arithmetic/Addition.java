/**
 * 
 */
package cn.wensiqun.asmsupport.core.operator.numerical.arithmetic;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.wensiqun.asmsupport.core.Parameterized;
import cn.wensiqun.asmsupport.core.block.classes.common.ProgramBlockInternal;
import cn.wensiqun.asmsupport.core.operator.Operators;

/**
 * addition operator
 * @author 温斯群(Joe Wen)
 *
 */
public class Addition extends AbstractArithmetic {

    private static Log log = LogFactory.getLog(Addition.class);
    
    protected Addition(ProgramBlockInternal block, Parameterized factor1, Parameterized factor2) {
        super(block, factor1, factor2);
        operator = Operators.ADD;
    }

    @Override
    public void doExecute() {
        log.debug("start execute add arithmetic operator");
        factorToStack();
        log.debug("execute the add instruction");
        insnHelper.add(targetClass.getType());
    }


}
