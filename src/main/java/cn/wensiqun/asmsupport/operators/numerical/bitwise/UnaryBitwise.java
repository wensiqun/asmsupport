package cn.wensiqun.asmsupport.operators.numerical.bitwise;

import cn.wensiqun.asmsupport.Parameterized;
import cn.wensiqun.asmsupport.block.ProgramBlock;
import cn.wensiqun.asmsupport.clazz.AClass;
import cn.wensiqun.asmsupport.operators.numerical.crement.AbstractCrement;
import cn.wensiqun.asmsupport.utils.AClassUtils;

/**
 * 
 * @author 温斯群(Joe Wen)
 *
 */
public abstract class UnaryBitwise extends AbstractBitwise {

    protected Parameterized factor;
    
    protected UnaryBitwise(ProgramBlock block, Parameterized factor) {
        super(block);
        this.factor = factor;
    }
    
    @Override
    protected void verifyArgument() {
        AClass ftrCls = factor.getParamterizedType();
        checkFactor(ftrCls);
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
        AClass ftrCls = factor.getParamterizedType();
        resultClass = AClassUtils.getPrimitiveAClass(ftrCls);
    }
    
    @Override
    protected final void factorToStack() {
        factor.loadToStack(block);
        insnHelper.unbox(resultClass.getType());
    }
    
    
}
