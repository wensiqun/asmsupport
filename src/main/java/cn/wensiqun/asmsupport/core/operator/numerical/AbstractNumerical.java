/**
 * 
 */
package cn.wensiqun.asmsupport.core.operator.numerical;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.wensiqun.asmsupport.core.Parameterized;
import cn.wensiqun.asmsupport.core.block.classes.common.ProgramBlockInternal;
import cn.wensiqun.asmsupport.core.clazz.AClass;
import cn.wensiqun.asmsupport.core.operator.AbstractOperator;
import cn.wensiqun.asmsupport.core.operator.numerical.arithmetic.AbstractArithmetic;
import cn.wensiqun.asmsupport.core.utils.AClassUtils;

/**
 * @author 温斯群(Joe Wen)
 *
 */
public abstract class AbstractNumerical extends AbstractOperator implements
        Parameterized {
    
    private static Log log = LogFactory.getLog(AbstractArithmetic.class);

    /**执行的结果类型 */
    protected AClass targetClass;
    
    protected String operator;
    
    protected AbstractNumerical(ProgramBlockInternal block) {
        super(block);
    }

    
    /**
     * 运算因子入栈
     */
    protected abstract void factorToStack();

    /**
     * 
     * @param factor
     */
    protected void pushFactorToStack(Parameterized factor){
        
        AClass factorCls = factor.getParamterizedType();
        
        //factor to stack
        log.debug("push the first arithmetic factor to stack");
        factor.loadToStack(block);
        
        AClass factorPrimitiveAClass = factorCls;
        //unbox if needs
        if(!factorCls.isPrimitive()){
            log.debug("unbox " + factorCls);
            insnHelper.unbox(factorCls.getType());
            factorPrimitiveAClass = AClassUtils.getPrimitiveAClass(factorCls);
        }
        
        //cast if needs
        if(factorPrimitiveAClass.getCastOrder() < targetClass.getCastOrder() &&
            targetClass.getCastOrder() > AClass.INT_ACLASS.getCastOrder())
         {
             log.debug("cast factor from " + factorCls + " to " + targetClass);
             insnHelper.cast(factorPrimitiveAClass.getType(), targetClass.getType());    
         }
    }
    
    @Override
    public final AClass getParamterizedType() {
        return targetClass;
    }
    
    
}
