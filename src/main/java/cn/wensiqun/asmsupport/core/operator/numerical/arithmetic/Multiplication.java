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
 * multiplication operator
 * @author 温斯群(Joe Wen)
 *
 */
public class Multiplication extends AbstractArithmetic {

    private static Log log = LogFactory.getLog(Multiplication.class);
    
    protected Multiplication(ProgramBlockInternal block, Parameterized factor1, Parameterized factor2) {
        super(block, factor1, factor2);
        operator = Operators.MUL;
    }


    @Override
    public void doExecute() {
        log.debug("start execute sub arithmetic operator");
        factorToStack();
        log.debug("execute the sub instruction");
        insnHelper.mul(targetClass.getType());
    }


}
