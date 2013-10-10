/**
 * 
 */
package jw.asmsupport.block.method.init;

import jw.asmsupport.Parameterized;
import jw.asmsupport.block.ProgramBlock;
import jw.asmsupport.block.method.SuperMethodBody;
import jw.asmsupport.clazz.AClass;
import jw.asmsupport.definition.variable.LocalVariable;
import jw.asmsupport.exception.ASMSupportException;
import jw.asmsupport.operators.method.MethodInvoker;
import jw.asmsupport.operators.method.SuperConstructorInvoker;
import jw.asmsupport.operators.util.OperatorFactory;
import jw.asmsupport.utils.ModifierUtils;

/**
 * 
 * @author 温斯群(Joe Wen)
 *
 */
public abstract class InitBody extends SuperMethodBody implements IInitBody {

	@Override
	public MethodInvoker invokeSuperConstructor(Parameterized... arguments) {
    	AClass owner = getMethodOwner();
    	if(ModifierUtils.isEnum(getMethodOwner().getModifiers())){
    		throw new ASMSupportException("Cannot invoke super constructor from enum type " + owner);
    	}
        invokeVerify(owner);

        return OperatorFactory.newOperator(SuperConstructorInvoker.class, 
        		new Class<?>[]{ProgramBlock.class, AClass.class, Parameterized[].class}, 
        		getExecuteBlock(), owner, arguments);
	}
    
    @Override
    public final void generateBody() {
    	generateBody(argments);
    }
    
    public abstract void generateBody(LocalVariable... argus);
}
