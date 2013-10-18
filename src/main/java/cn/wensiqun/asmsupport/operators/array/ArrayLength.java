package cn.wensiqun.asmsupport.operators.array;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.wensiqun.asmsupport.Parameterized;
import cn.wensiqun.asmsupport.asm.InstructionHelper;
import cn.wensiqun.asmsupport.block.ProgramBlock;
import cn.wensiqun.asmsupport.clazz.AClass;

/**
 * 
 * @author 温斯群(Joe Wen)
 *
 */
public class ArrayLength extends AbstractArrayOperator implements Parameterized {


    private static Log log = LogFactory.getLog(ArrayLength.class);
    
    private boolean useByOther;
    
    /**
     * 
     * @param block
     * @param arrayReference
     * @param parDims
     */
    protected ArrayLength(ProgramBlock block, Parameterized arrayReference, Parameterized... parDims) {
        super(block, arrayReference);
        this.parDims = parDims;
    }
    

    @Override
    public void executing() {
		if(!useByOther){
            throw new RuntimeException(this.toString() + " not use by other operator");
        }
        InstructionHelper ih = block.getInsnHelper();
        if(log.isDebugEnabled()) log.debug("start get length of array");
        getValue();
        if(log.isDebugEnabled()) log.debug("got length and push to stack");
        ih.arrayLength();
    }

    @Override
    public void loadToStack(ProgramBlock block) {
        this.execute();
    }

    @Override
    public AClass getParamterizedType() {
        return AClass.INT_ACLASS;
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
		toString.append(".length");
		return toString.toString();
	}
}
