package cn.wensiqun.asmsupport.core.operator.logical;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.wensiqun.asmsupport.core.Parameterized;
import cn.wensiqun.asmsupport.core.block.classes.common.ProgramBlockInternal;
import cn.wensiqun.asmsupport.core.clazz.AClass;
import cn.wensiqun.asmsupport.core.exception.ASMSupportException;
import cn.wensiqun.asmsupport.core.operator.numerical.bit.BinaryBitwise;

/**
 * 
 * @author 温斯群(Joe Wen)
 *
 */
public abstract class BinaryLogical extends AbstractLogical {
    
    private static Log log = LogFactory.getLog(BinaryBitwise.class);
    
    protected Parameterized factor1;
    protected Parameterized factor2;
    
    protected BinaryLogical(ProgramBlockInternal block, Parameterized factor1, Parameterized factor2) {
        super(block);
        this.factor1 = factor1;
        this.factor2 = factor2;
    }

	@Override
	protected void verifyArgument() {
		AClass ftrCls1 = factor1.getParamterizedType();
        AClass ftrCls2 = factor2.getParamterizedType();
        
        if(!((ftrCls1.equals(AClass.BOOLEAN_ACLASS) || ftrCls1.equals(AClass.BOOLEAN_WRAP_ACLASS)) &&
           (ftrCls2.equals(AClass.BOOLEAN_ACLASS) || ftrCls2.equals(AClass.BOOLEAN_WRAP_ACLASS)))){
            throw new ASMSupportException("the factor type must be boolean or Boolean for logical operator!");
        }
	}

	@Override
	protected void checkAsArgument() {
        factor1.asArgument();
        factor2.asArgument();
	}

	@Override
    public void execute() {
        if(byOtherUsed){
            super.execute();
        }else{
            throw new ArithmeticException("the logical operator " + factor1.getParamterizedType() + " " + operator + " " + 
                                          factor2.getParamterizedType() + " has not been used by other operator.");
        }
    }

    @Override
    protected void factorToStack() {
        log.debug("factors to stack");
        factor1.loadToStack(block);
        insnHelper.unbox(factor1.getParamterizedType().getType());
        
        factor2.loadToStack(block);
        insnHelper.unbox(factor2.getParamterizedType().getType());
    }

}
