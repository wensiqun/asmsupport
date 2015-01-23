/**
 * 
 */
package cn.wensiqun.asmsupport.core.block.classes.method.init;

import cn.wensiqun.asmsupport.core.Parameterized;
import cn.wensiqun.asmsupport.core.block.classes.common.ProgramBlockInternal;
import cn.wensiqun.asmsupport.core.block.classes.method.AbstractMethodBody;
import cn.wensiqun.asmsupport.core.clazz.AClass;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.core.operator.method.SuperConstructorInvoker;
import cn.wensiqun.asmsupport.core.operator.numerical.OperatorFactory;
import cn.wensiqun.asmsupport.generic.method.IEnumContructorBody;
import cn.wensiqun.asmsupport.org.apache.commons.lang3.ArrayUtils;


/**
 * 
 * @author 温斯群(Joe Wen)
 *
 */
public abstract class EnumInitBodyInternal extends AbstractMethodBody implements IEnumContructorBody {
    
    @Override
    public final void generateBody() {
        OperatorFactory.newOperator(SuperConstructorInvoker.class, 
        		new Class<?>[]{ProgramBlockInternal.class, AClass.class, Parameterized[].class}, 
        		getExecutor(), getMethodOwner(), new Parameterized[]{argments[0], argments[1]});
        body((LocalVariable[]) ArrayUtils.subarray(argments, 2, argments.length));
    }
    

}
