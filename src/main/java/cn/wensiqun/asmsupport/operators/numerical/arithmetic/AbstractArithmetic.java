/**
 * 
 */
package cn.wensiqun.asmsupport.operators.numerical.arithmetic;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.wensiqun.asmsupport.Parameterized;
import cn.wensiqun.asmsupport.block.ProgramBlock;
import cn.wensiqun.asmsupport.clazz.AClass;
import cn.wensiqun.asmsupport.operators.numerical.AbstractNumerical;
import cn.wensiqun.asmsupport.operators.numerical.crement.AbstractCrement;
import cn.wensiqun.asmsupport.utils.AClassUtils;

/**
 * @author 温斯群(Joe Wen)
 *
 */
public abstract class AbstractArithmetic extends AbstractNumerical implements
        Parameterized {

    private static Log log = LogFactory.getLog(AbstractArithmetic.class);
    
    /**算数因子1 */
    protected Parameterized factor1;

    /**算数因子2 */
    protected Parameterized factor2;
    
    /**该操作是否被其他操作引用 */
    private boolean byOtherUsed;

    protected AbstractArithmetic(ProgramBlock block, Parameterized factor1, Parameterized factor2) {
        super(block);
        this.factor1 = factor1;
        this.factor2 = factor2;
    }
    
    @Override
    public void loadToStack(ProgramBlock block) {
        this.execute();
    }
    
    @Override
    protected void verifyArgument() {
        AClass f1cls = factor1.getParamterizedType();
        AClass f2cls = factor2.getParamterizedType();
        if(!AClassUtils.arithmetical(f1cls) || !AClassUtils.arithmetical(f2cls)){
            throw new ArithmeticException("cannot execute arithmetic operator whit " + f1cls + " and " + f2cls);
        }
    }

    @Override
    protected void checkOutCrement() {
        if(factor1 instanceof AbstractCrement){
            allCrement.add((AbstractCrement) factor1);
        }
        if(factor2 instanceof AbstractCrement){
            allCrement.add((AbstractCrement) factor2);
        }
    }

    @Override
    protected void checkAsArgument() {
        factor1.asArgument();
        factor2.asArgument();
    }

    @Override
    protected void afterInitProperties() {
        AClass f1cls = factor1.getParamterizedType();
        AClass f2cls = factor2.getParamterizedType();
        f1cls = AClassUtils.getPrimitiveAClass(f1cls);
        f2cls = AClassUtils.getPrimitiveAClass(f2cls);
        
        if(f1cls.getCastOrder() > f2cls.getCastOrder()){
            resultClass = f1cls;
        }else{
            resultClass = f2cls;
        }
    }
    
    @Override
    public void execute() {
        if(byOtherUsed){
            super.execute();
        }else{
            throw new ArithmeticException("the arithmetic operator " + factor1.getParamterizedType() + " " + operator + " " + 
                                          factor2.getParamterizedType() + " has not been used by other operator.");
        }
    }

    @Override
    protected void factorToStack() {
        AClass ftrCls1 = factor1.getParamterizedType();
        AClass ftrCls2 = factor2.getParamterizedType();
        
        log.debug("push the first arithmetic factor to stack");
        factor1.loadToStack(block);
        
        if(!ftrCls1.isPrimitive()){
            log.debug("unbox " + ftrCls1);
            insnHelper.unbox(ftrCls1.getType());
        }
        
        if(!ftrCls1.equals(resultClass) &&
            resultClass.getCastOrder() > AClass.INT_ACLASS.getCastOrder() ){
            log.debug("cast arithmetic factor from " + ftrCls1 + " to " + resultClass);
            insnHelper.cast(ftrCls1.getType(), resultClass.getType());    
        }

        
        log.debug("push the second arithmetic factor to stack");
        factor2.loadToStack(block);
        
        if(!ftrCls2.isPrimitive()){
            log.debug("unbox " + ftrCls2);
            insnHelper.unbox(ftrCls2.getType());
        }
        
        if(!ftrCls2.equals(resultClass) &&
            resultClass.getCastOrder() > AClass.INT_ACLASS.getCastOrder() ){
            log.debug("cast arithmetic factor from " + ftrCls2 + " to " + resultClass);
            insnHelper.cast(ftrCls2.getType(), resultClass.getType());    
        }
        
    }
    
    @Override
    public void asArgument() {
        //由参数使用者调用
        block.removeExe(this);
        //指明是被其他操作引用
        byOtherUsed = true;
    }
    
}
