/**
 * 
 */
package cn.wensiqun.asmsupport.core.block.method.init;

import cn.wensiqun.asmsupport.core.Parameterized;
import cn.wensiqun.asmsupport.core.block.ProgramBlockInternal;
import cn.wensiqun.asmsupport.core.block.method.AbstractMethodBody;
import cn.wensiqun.asmsupport.core.clazz.AClass;
import cn.wensiqun.asmsupport.core.exception.ASMSupportException;
import cn.wensiqun.asmsupport.core.operator.method.MethodInvoker;
import cn.wensiqun.asmsupport.core.operator.method.SuperConstructorInvoker;
import cn.wensiqun.asmsupport.core.operator.numerical.OperatorFactory;
import cn.wensiqun.asmsupport.core.utils.reflect.ModifierUtils;
import cn.wensiqun.asmsupport.standard.method.IContructorBody;

/**
 * 
 * @author 温斯群(Joe Wen)
 *
 */
public abstract class ConstructorBodyInternal extends AbstractMethodBody implements IContructorBody {

	public MethodInvoker _supercall(Parameterized... arguments) {
    	AClass owner = getMethodOwner();
    	if(ModifierUtils.isEnum(getMethodOwner().getModifiers())){
    		throw new ASMSupportException("Cannot invoke super constructor from enum type " + owner);
    	}
        invokeVerify(owner);

        return OperatorFactory.newOperator(SuperConstructorInvoker.class, 
        		new Class<?>[]{ProgramBlockInternal.class, AClass.class, Parameterized[].class}, 
        		getExecutor(), owner, arguments);
	}
    
    @Override
    public final void generateBody() {
        body(argments);
    }
    
}
