package jw.asmsupport.operators.logical;

import jw.asmsupport.Parameterized;
import jw.asmsupport.block.ProgramBlock;
import jw.asmsupport.clazz.AClass;
import jw.asmsupport.exception.ASMSupportException;
import jw.asmsupport.operators.numerical.bitwise.BinaryBitwise;
import jw.asmsupport.operators.numerical.crement.AbstractCrement;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.objectweb.asm.Label;

/**
 * 
 * @author 温斯群(Joe Wen)
 *
 */
public abstract class UnaryLogical extends AbstractLogical {
    
    private static Log log = LogFactory.getLog(BinaryBitwise.class);
    
    protected Parameterized factor;
    
    protected Label trueLbl;
    protected Label falseLbl;
    
    protected UnaryLogical(ProgramBlock block, Parameterized factor) {
        super(block);
        this.factor = factor;
        falseLbl = new Label();
        trueLbl = new Label();
    }

    @Override
	protected void verifyArgument() {
    	AClass ftrCls = factor.getParamterizedType();
        if(!(ftrCls.equals(AClass.BOOLEAN_ACLASS) && !ftrCls.equals(AClass.BOOLEAN_WRAP_ACLASS))){
            throw new ASMSupportException("the factor type must be boolean or Boolean for logical operator!");
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
    public void execute() {
        if(byOtherUsed){
            super.execute();
        }else{
            throw new ASMSupportException("the logical operator " + operator + " " + 
                    factor.getParamterizedType() + " has not been used by other operator.");
        }
    }

	@Override
    protected void factorToStack() {
        log.debug("factors to stack");
        factor.loadToStack(block);
        insnHelper.unbox(factor.getParamterizedType().getType());
    }

}
