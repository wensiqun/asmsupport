/**
 * 
 */
package cn.wensiqun.asmsupport.operators.numerical;

import cn.wensiqun.asmsupport.Parameterized;
import cn.wensiqun.asmsupport.block.ProgramBlock;
import cn.wensiqun.asmsupport.clazz.AClass;
import cn.wensiqun.asmsupport.operators.AbstractOperator;

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
