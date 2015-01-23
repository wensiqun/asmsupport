package cn.wensiqun.asmsupport.core.operator.numerical.posinegative;

import cn.wensiqun.asmsupport.core.Parameterized;
import cn.wensiqun.asmsupport.core.block.classes.common.ProgramBlockInternal;
import cn.wensiqun.asmsupport.core.clazz.AClass;
import cn.wensiqun.asmsupport.core.operator.numerical.AbstractNumerical;
import cn.wensiqun.asmsupport.core.utils.AClassUtils;

/**
 * 
 * @author 温斯群(Joe Wen)
 *
 */
public abstract class AbstractPositiveNegative extends AbstractNumerical {
    
    protected Parameterized factor;
    
    /**该操作是否被其他操作引用 */
    private boolean byOtherUsed;
    

    protected AbstractPositiveNegative(ProgramBlockInternal block, Parameterized factor) {
        super(block);
        this.factor = factor;
    }
    

    @Override
    public void loadToStack(ProgramBlockInternal block) {
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
    protected void initAdditionalProperties() {
        AClass fatCls = factor.getParamterizedType();
        targetClass = AClassUtils.getPrimitiveAClass(fatCls);
    }
    
    @Override
    protected void verifyArgument() {
        AClass fatCls = factor.getParamterizedType();
        if(!AClassUtils.isArithmetical(fatCls)){
            throw new ArithmeticException("cannot execute arithmetic operator whit " + fatCls);
        }
    }

    @Override
    protected void checkAsArgument() {
        factor.asArgument();
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
