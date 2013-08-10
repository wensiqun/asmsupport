/**
 * 
 */
package jw.asmsupport.operators;

import jw.asmsupport.Parameterized;
import jw.asmsupport.block.ProgramBlock;
import jw.asmsupport.operators.numerical.crement.AbstractCrement;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.objectweb.asm.Type;

/**
 * 用于执行Return
 * @author 温斯群(Joe Wen)
 *
 */
public class Return extends BreakStack {

    private static Log log = LogFactory.getLog(Return.class);
    
    private Parameterized returner;
    private Type returnType;
    
    protected Return(ProgramBlock block, Parameterized returner) {
        super(block);
        this.returner = returner;
        if(returner != null){
            returner.asArgument();
        }
    }

    @Override
    protected void verifyArgument() {
        
    }

    @Override
    protected void checkOutCrement() {
        if(returner instanceof AbstractCrement){
            allCrement.add((AbstractCrement) returner);
        }
    }

    @Override
    protected void checkAsArgument() {
        
    }
    
    @Override
    public void breakStackExecuting() {
    	
        if(returner == null){
            log.debug("direct return from method");
            insnHelper.returnInsn();
        }else{
            returner.loadToStack(block);
            returnType = returner.getParamterizedType().getType();
            if(returnType == null){
                throw new NullPointerException("return type must be non-null!");
            }
            insnHelper.returnInsn(returnType);
        }
    }

}
