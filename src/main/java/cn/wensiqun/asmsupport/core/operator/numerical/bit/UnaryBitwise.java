package cn.wensiqun.asmsupport.core.operator.numerical.bit;

import cn.wensiqun.asmsupport.core.Parameterized;
import cn.wensiqun.asmsupport.core.block.classes.common.ProgramBlockInternal;
import cn.wensiqun.asmsupport.core.clazz.AClass;
import cn.wensiqun.asmsupport.core.utils.AClassUtils;

/**
 * 
 * @author 温斯群(Joe Wen)
 *
 */
public abstract class UnaryBitwise extends AbstractBitwise {

    protected Parameterized factor;
    
    protected UnaryBitwise(ProgramBlockInternal block, Parameterized factor) {
        super(block);
        this.factor = factor;
    }
    
    @Override
    protected void verifyArgument() {
        AClass ftrCls = factor.getParamterizedType();
        checkFactor(ftrCls);
    }

    @Override
    protected void checkAsArgument() {
        factor.asArgument();
    }

    @Override
    protected void initAdditionalProperties() {
        AClass ftrCls = factor.getParamterizedType();
        targetClass = AClassUtils.getPrimitiveAClass(ftrCls);
    }
    
    @Override
    protected final void factorToStack() {
        factor.loadToStack(block);
        insnHelper.unbox(targetClass.getType());
    }
    
    
}
