/**
 * 
 */
package jw.asmsupport.operators.numerical;

import jw.asmsupport.Parameterized;
import jw.asmsupport.block.ProgramBlock;
import jw.asmsupport.clazz.AClass;
import jw.asmsupport.operators.AbstractOperator;

/**
 * @author 温斯群(Joe Wen)
 *
 */
public abstract class AbstractNumerical extends AbstractOperator implements
        Parameterized {

    /**执行的结果类型 */
    protected AClass resultClass;
    
    protected String operator;
    
    protected AbstractNumerical(ProgramBlock block) {
        super(block);
    }

    
    /**
     * 运算因子入栈
     */
    protected abstract void factorToStack();
    
    @Override
    public final AClass getParamterizedType() {
        return resultClass;
    }
    
}
