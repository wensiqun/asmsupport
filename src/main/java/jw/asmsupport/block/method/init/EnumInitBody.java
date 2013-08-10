/**
 * 
 */
package jw.asmsupport.block.method.init;

import org.apache.commons.lang.ArrayUtils;

import jw.asmsupport.Parameterized;
import jw.asmsupport.block.ProgramBlock;
import jw.asmsupport.block.method.SuperMethodBody;
import jw.asmsupport.clazz.AClass;
import jw.asmsupport.definition.variable.LocalVariable;
import jw.asmsupport.operators.method.SuperConstructorInvoker;
import jw.asmsupport.operators.util.OperatorFactory;
import jw.asmsupport.utils.ASConstant;


/**
 * 
 * @author 温斯群(Joe Wen)
 *
 */
public abstract class EnumInitBody extends SuperMethodBody implements IEnumInitBody {
    
	
	
    @Override
    public final void generateBody() {
    	if(getMethod().getMethodEntity().getName().equals(ASConstant.INIT)){
            OperatorFactory.newOperator(SuperConstructorInvoker.class, 
            		new Class<?>[]{ProgramBlock.class, AClass.class, Parameterized[].class}, 
            		getExecuteBlock(), getMethodOwner(), new Parameterized[]{argments[0], argments[1]});
    		/*new SuperConstructorInvoker(getExecuteBlock(), getMethodOwner(),  
    				new Parameterized[]{argments[0], argments[1]});*/
            generateBody((LocalVariable[]) ArrayUtils.subarray(argments, 2, argments.length));
    	}else{
    		generateBody(argments);
    	}
    }
    
    public abstract void generateBody(LocalVariable... argus);

}
