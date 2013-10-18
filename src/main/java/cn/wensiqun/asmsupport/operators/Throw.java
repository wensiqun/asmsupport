/**
 * 
 */
package cn.wensiqun.asmsupport.operators;

import cn.wensiqun.asmsupport.Parameterized;
import cn.wensiqun.asmsupport.block.ProgramBlock;
import cn.wensiqun.asmsupport.clazz.AClass;
import cn.wensiqun.asmsupport.exception.ASMSupportException;

/**
 * @author 温斯群(Joe Wen)
 *
 */
public class Throw extends BreakStack {
    
    private Parameterized exception;
    
    protected Throw(ProgramBlock block, Parameterized exception) {
        super(block);
        this.exception = exception;
        this.setAutoCreate(false);
    }

    protected Throw(ProgramBlock block, Parameterized exception, boolean autoCreate) {
        super(block);
        this.exception = exception;
        this.setAutoCreate(autoCreate);
    }
    
    @Override
	protected void beforeInitProperties() {
		block.addException(exception.getParamterizedType());
	}

	@Override
    protected void verifyArgument() {
        AClass excAcls = exception.getParamterizedType();
        if(!excAcls.isChildOrEqual(AClass.THROWABLE_ACLASS)){
            throw new ASMSupportException("the throw type " + excAcls + " is not assign from java.lang.Throwable.class");
        }
    }

    @Override
    protected void checkOutCrement() {
        
    }

    @Override
    protected void checkAsArgument() {
        exception.asArgument();
    }
    
    @Override
    protected void breakStackExecuting() {
        exception.loadToStack(block);
        insnHelper.throwException();
    }

	@Override
	public String toString() {
		return " throw " + exception;
	}

}
