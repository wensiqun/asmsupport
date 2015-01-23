/**
 * 
 */
package cn.wensiqun.asmsupport.core.operator;

import cn.wensiqun.asmsupport.core.Parameterized;
import cn.wensiqun.asmsupport.core.block.classes.common.ProgramBlockInternal;
import cn.wensiqun.asmsupport.core.clazz.AClass;
import cn.wensiqun.asmsupport.core.clazz.AnyException;
import cn.wensiqun.asmsupport.core.exception.ASMSupportException;

/**
 * @author 温斯群(Joe Wen)
 *
 */
public class Throw extends BreakStack {
    
    private Parameterized exception;
    
    protected Throw(ProgramBlockInternal block, Parameterized exception) {
        super(block, false);
        this.exception = exception;
    }

    protected Throw(ProgramBlockInternal block, Parameterized exception, boolean autoCreate) {
        super(block, true);
        this.exception = exception;
    }
    
    @Override
	protected void startingPrepare() {
    	if(!AnyException.ANY.equals(exception.getParamterizedType()))
    	{
    		block.addException(exception.getParamterizedType());
    	}
		super.startingPrepare();
	}

	@Override
    protected void verifyArgument() {
        AClass type = exception.getParamterizedType();
        if(AnyException.ANY != type &&
           !type.isChildOrEqual(AClass.THROWABLE_ACLASS)){
            throw new ASMSupportException("No exception of type " + type + " can be thrown; an exception type must be a subclass of Throwable");
        }
    }

    @Override
    protected void checkCrement() {
        
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

	public AClass getThrowExceptionType()
	{
		return exception.getParamterizedType();
	}
}
