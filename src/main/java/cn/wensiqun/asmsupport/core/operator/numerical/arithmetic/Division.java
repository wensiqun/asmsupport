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
 * division operator
 * @author 温斯群(Joe Wen)
 *
 */
public class Division extends AbstractArithmetic {

    private static Log log = LogFactory.getLog(Division.class);
    
    protected Division(ProgramBlockInternal block, Parameterized factor1, Parameterized factor2) {
        super(block, factor1, factor2);
        operator = Operators.DIV;
    }


    @Override
    public void doExecute() {
        log.debug("start execute sub arithmetic operator");
        factorToStack();
        log.debug("execute the sub instruction");
        insnHelper.div(targetClass.getType());
    }


}
