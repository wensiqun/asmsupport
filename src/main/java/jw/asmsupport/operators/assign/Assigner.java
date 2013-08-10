/**
 * 
 */
package jw.asmsupport.operators.assign;

import jw.asmsupport.Parameterized;
import jw.asmsupport.block.ProgramBlock;
import jw.asmsupport.clazz.AClass;
import jw.asmsupport.definition.variable.IVariable;
import jw.asmsupport.operators.AbstractOperator;
import jw.asmsupport.operators.numerical.crement.AbstractCrement;
import jw.asmsupport.utils.AClassUtils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author 温斯群(Joe Wen)
 */
public abstract class Assigner extends AbstractOperator implements Parameterized {

    private static Log log = LogFactory.getLog(Assigner.class);
    
    protected Parameterized value;
    
    private IVariable var;
    
    protected Assigner(ProgramBlock block, IVariable var, Parameterized value) {
        super(block);
        this.value = value;
        this.var = var;
    }

	@Override
	public String toString() {
		return var + " = " + value;
	}

	@Override
	protected void verifyArgument() {
		AClassUtils.autoCastTypeCheck(value.getParamterizedType(), var.getParamterizedType());
	}

	@Override
	protected void checkOutCrement() {
        if(value != null && value instanceof AbstractCrement){
            allCrement.add((AbstractCrement) value);
        }
	}

	@Override
	protected void checkAsArgument() {
        value.asArgument();
	}

	/**
     * auto cast
     */
    protected void autoCast(){
        autoCast(value.getParamterizedType(), var.getParamterizedType());
    }
    
    
    @Override
	public void loadToStack(ProgramBlock block) {
		var.loadToStack(block);
	}

	@Override
	public AClass getParamterizedType() {
		return var.getParamterizedType();
	}

	@Override
	public void asArgument() {
		var.asArgument();
	}

	protected static class AssignerException extends RuntimeException{

        private static final long serialVersionUID = 5667984928208743770L;
        
        protected AssignerException(String msg){
            super(msg);
        } 
    }

}
