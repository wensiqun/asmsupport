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
 * multiplication operator
 * @author 温斯群(Joe Wen)
 *
 */
public class Multiplication extends AbstractArithmetic implements Parameterized{

    private static Log log = LogFactory.getLog(Multiplication.class);
    
    protected Multiplication(ProgramBlock block, Parameterized factor1, Parameterized factor2) {
        super(block, factor1, factor2);
        operator = Operators.MUL;
    }


    @Override
    public void executing() {
        log.debug("start execute sub arithmetic operator");
        factorToStack();
        log.debug("execute the sub instruction");
        insnHelper.mul(resultClass.getType());
    }


}