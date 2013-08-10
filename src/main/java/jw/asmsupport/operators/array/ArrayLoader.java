/**
 * 
 */
package jw.asmsupport.operators.array;

import jw.asmsupport.Parameterized;
import jw.asmsupport.block.ProgramBlock;
import jw.asmsupport.clazz.AClass;
import jw.asmsupport.clazz.ArrayClass;
import jw.asmsupport.exception.ArrayOperatorException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author 温斯群(Joe Wen)
 *
 */
public class ArrayLoader extends AbstractArrayOperator implements Parameterized {

    private static Log log = LogFactory.getLog(ArrayLoader.class);
    
    private AClass valueClass;
    
    private boolean useByOther;

    private void init(Parameterized pardim, Parameterized... parDims){
    	this.parDims = new Parameterized[1 + parDims.length];
        this.parDims[0] = pardim;
        System.arraycopy(parDims, 0, this.parDims, 1, parDims.length);
        
        valueClass = arrayReference.getParamterizedType();
        for(int i=0; i<this.parDims.length; i++){
            valueClass = ((ArrayClass) valueClass).getNextDimType();
        }
    }
    
    protected ArrayLoader(ProgramBlock block, Parameterized arrayReference, Parameterized pardim, Parameterized... parDims) {
        super(block, arrayReference);
        init(pardim, parDims);
    }
    
    
	@Override
    public void executing() {
		if(!useByOther){
            throw new RuntimeException(this.toString() + " not use by other operator");
        }
        ArrayClass cls = (ArrayClass) arrayReference.getParamterizedType();
        if(parDims != null && parDims.length > cls.getDimension()){
            throw new ArrayOperatorException(toString() + " dimension not enough!");
        }
        log.debug("start load array value");
        getValue();
    }

    @Override
    public void loadToStack(ProgramBlock block) {
        this.execute();
    }

    @Override
    public AClass getParamterizedType() {
        return valueClass;
    }

    @Override
    public void asArgument() {
        useByOther = true;
        block.removeExe(this);
    }

	@Override
	public String toString() {
		StringBuilder toString = new StringBuilder(arrayReference.toString());
		for(Parameterized p : parDims){
			toString.append("[").append(p).append("]");
		}
		return toString.toString();
	}

}
