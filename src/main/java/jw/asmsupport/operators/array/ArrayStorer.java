/**
 * 
 */
package jw.asmsupport.operators.array;

import jw.asmsupport.Parameterized;
import jw.asmsupport.asm.InstructionHelper;
import jw.asmsupport.block.ProgramBlock;
import jw.asmsupport.clazz.AClass;
import jw.asmsupport.clazz.ArrayClass;
import jw.asmsupport.utils.AClassUtils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author 温斯群(Joe Wen)
 *
 */
public class ArrayStorer extends AbstractArrayOperator {


    private static Log log = LogFactory.getLog(ArrayStorer.class);
    
    private Parameterized value;
    
    private AClass storeClass;
    
    private Parameterized lastDim;

    private void init(Parameterized value, Parameterized pardim, Parameterized... parDims){
    	this.value = value;
        this.parDims = new Parameterized[parDims.length];
        
        if(parDims.length != 0){
            this.parDims[0] = pardim;
            System.arraycopy(parDims, 0, this.parDims, 1, parDims.length - 1);
            lastDim = parDims[parDims.length - 1];
        }else{
            lastDim = pardim;
        }
        
        storeClass = arrayReference.getParamterizedType();
        for(int i=0, length = this.parDims.length + 1; i<length; i++){
            storeClass = ((ArrayClass) storeClass).getNextDimType();
        }
    }

    protected ArrayStorer(ProgramBlock block, Parameterized arrayReference, Parameterized value, Parameterized pardim, Parameterized... parDims) {
        super(block, arrayReference);
        init(value, pardim, parDims);
    }

    
	@Override
	protected void checkAsArgument() {
		super.checkAsArgument();
        value.asArgument();
	}
	
	
    
    @Override
	protected void verifyArgument() {
		super.verifyArgument();
	    AClassUtils.autoCastTypeCheck(value.getParamterizedType(), storeClass);
	    AClassUtils.autoCastTypeCheck(lastDim.getParamterizedType(), AClass.INT_ACLASS);
	}

	@Override
    public void executing() {
        log.debug("start get value for store array");
        getValue();
        InstructionHelper ih = block.getInsnHelper();
        log.debug("push the last dim index to stack");
        lastDim.loadToStack(block);
        autoCast(lastDim.getParamterizedType(), AClass.INT_ACLASS);
        
        value.loadToStack(block);
        autoCast(value.getParamterizedType(), storeClass);
        log.debug("store value to corresponse to index of the array");
        ih.arrayStore(storeClass.getType());
    }

}
