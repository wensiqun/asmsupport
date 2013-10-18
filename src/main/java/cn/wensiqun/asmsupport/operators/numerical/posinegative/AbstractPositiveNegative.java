package cn.wensiqun.asmsupport.operators.numerical.posinegative;

import cn.wensiqun.asmsupport.Parameterized;
import cn.wensiqun.asmsupport.block.ProgramBlock;
import cn.wensiqun.asmsupport.clazz.AClass;
import cn.wensiqun.asmsupport.operators.numerical.AbstractNumerical;
import cn.wensiqun.asmsupport.operators.numerical.crement.AbstractCrement;
import cn.wensiqun.asmsupport.utils.AClassUtils;

/**
 * 
 * @author 温斯群(Joe Wen)
 *
 */
public abstract class AbstractPositiveNegative extends AbstractNumerical {
    
    protected Parameterized factor;
    
    /**该操作是否被其他操作引用 */
    private boolean byOtherUsed;
    

    protected AbstractPositiveNegative(ProgramBlock block, Parameterized factor) {
        super(block);
        this.factor = factor;
    }
    

    @Override
    public void loadToStack(ProgramBlock block) {
        this.execute();
    }
    
    @Override
    public void execute() {
        if(byOtherUsed){
            super.execute();
        }else{
            throw new ArithmeticException("the operator has not been used by other operator.");
        }
        
    }
    
    @Override
    protected void verifyArgument() {
        AClass fatCls = factor.getParamterizedType();
        if(!AClassUtils.arithmetical(fatCls)){
            throw new ArithmeticException("cannot execute arithmetic operator whit " + fatCls);
        }
    }


    @Override
    protected void checkOutCrement() {
        if(factor instanceof AbstractCrement){
            allCrement.add((AbstractCrement) factor);
        }
    }


    @Override
    protected void checkAsArgument() {
        factor.asArgument();
    }


    @Override
    protected void afterInitProperties() {
        AClass fatCls = factor.getParamterizedType();
        resultClass = AClassUtils.getPrimitiveAClass(fatCls);
    }

    @Override
    public void asArgument() {
        //由参数使用者调用
        block.removeExe(this);
        //指明是被其他操作引用
        byOtherUsed = true;
    }

    @Override
    protected void factorToStack() {
        factor.loadToStack(block);
        insnHelper.unbox(factor.getParamterizedType().getType());
    }


}
