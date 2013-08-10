/**
 * 
 */
package jw.asmsupport.operators.array;

import jw.asmsupport.Parameterized;
import jw.asmsupport.asm.InstructionHelper;
import jw.asmsupport.block.ProgramBlock;
import jw.asmsupport.clazz.AClass;
import jw.asmsupport.clazz.ArrayClass;
import jw.asmsupport.exception.ClassException;
import jw.asmsupport.operators.AbstractOperator;
import jw.asmsupport.operators.numerical.crement.AbstractCrement;
import jw.asmsupport.utils.AClassUtils;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author 温斯群(Joe Wen)
 *
 */
public abstract class AbstractArrayOperator extends AbstractOperator {

    private static Log log = LogFactory.getLog(AbstractArrayOperator.class);
    
    protected Parameterized arrayReference;
    
    protected Parameterized[] parDims;
    
    protected AbstractArrayOperator(ProgramBlock block, Parameterized arrayVar) {
        super(block);
        this.arrayReference = arrayVar;
    }
    
	@Override
	protected final void checkOutCrement() {
        if(parDims != null){
            for(Parameterized par : parDims){
                if(par instanceof AbstractCrement){
                    allCrement.add((AbstractCrement) par);
                }
            }
        }
	}
	
    @Override
	protected void checkAsArgument() {
        arrayReference.asArgument();
        if(parDims != null){
            for(Parameterized par : parDims){
                par.asArgument();
            }
        }
	}
    
	@Override
	protected void verifyArgument() {
		if(!(arrayReference.getParamterizedType() instanceof ArrayClass)){
        	throw new ClassException(toString() + " : the declare class of " + arrayReference.toString() + " is not a array type");
        }
		
		if(ArrayUtils.isNotEmpty(parDims)){
			for(Parameterized par : parDims){
				AClassUtils.autoCastTypeCheck(par.getParamterizedType(), AClass.INT_ACLASS);
			}
		}
		
	}

	protected void getValue(){
        InstructionHelper ih = block.getInsnHelper();
        AClass cls = arrayReference.getParamterizedType();
        if(log.isDebugEnabled()){
            log.debug("load the array reference to stack");
        }
        arrayReference.loadToStack(block);
        
        for(int i=0; i<parDims.length; i++){
            cls = ((ArrayClass) cls).getNextDimType();
            parDims[i].loadToStack(block);
            autoCast(parDims[i].getParamterizedType(), AClass.INT_ACLASS);
            ih.arrayLoad(cls.getType());
        }
        
    }
}
