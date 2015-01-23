/**
 * 
 */
package cn.wensiqun.asmsupport.core.operator.array;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.wensiqun.asmsupport.core.Parameterized;
import cn.wensiqun.asmsupport.core.asm.InstructionHelper;
import cn.wensiqun.asmsupport.core.block.classes.common.ProgramBlockInternal;
import cn.wensiqun.asmsupport.core.clazz.AClass;
import cn.wensiqun.asmsupport.core.clazz.ArrayClass;
import cn.wensiqun.asmsupport.core.exception.ClassException;
import cn.wensiqun.asmsupport.core.operator.AbstractOperator;
import cn.wensiqun.asmsupport.core.utils.AClassUtils;
import cn.wensiqun.asmsupport.org.apache.commons.lang3.ArrayUtils;

/**
 * @author 温斯群(Joe Wen)
 *
 */
public abstract class AbstractArrayOperator extends AbstractOperator {

    private static Log log = LogFactory.getLog(AbstractArrayOperator.class);
    
    protected Parameterized arrayReference;
    
    protected Parameterized[] parDims;
    
    protected AbstractArrayOperator(ProgramBlockInternal block, Parameterized arrayVar) {
        super(block);
        this.arrayReference = arrayVar;
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
				if(!AClassUtils.checkAssignable(par.getParamterizedType(), AClass.INT_ACLASS)) {
					throw new IllegalArgumentException("Type mismatch: cannot convert from " + par.getParamterizedType() + " to " + AClass.INT_ACLASS + "");
				}
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
            autoCast(parDims[i].getParamterizedType(), AClass.INT_ACLASS, false);
            ih.arrayLoad(cls.getType());
        }
        
    }
}
