package cn.wensiqun.asmsupport.core.operator.method;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.wensiqun.asmsupport.core.Parameterized;
import cn.wensiqun.asmsupport.core.block.classes.common.ProgramBlockInternal;
import cn.wensiqun.asmsupport.core.clazz.AClass;
import cn.wensiqun.asmsupport.core.utils.reflect.ModifierUtils;
import cn.wensiqun.asmsupport.org.objectweb.asm.Type;

/**
 * 静态方法调用
 * @author 温斯群(Joe Wen)
 *
 */
public class StaticMethodInvoker extends MethodInvoker {

    private static Log log = LogFactory.getLog(StaticMethodInvoker.class);
    
    protected StaticMethodInvoker(ProgramBlockInternal block, AClass owner, String name,
            Parameterized[] arguments) {
        super(block, owner, name, arguments);
        if (owner.isPrimitive()) {
            throw new IllegalArgumentException("Cannot call static method from primitive");
        }
        //this.methodType = MethodType.STATIC;
        //默认不保存引用
        setSaveReference(false);
    }
    
    @Override
	protected void initAdditionalProperties() {
		super.initAdditionalProperties();
		if(!ModifierUtils.isStatic(mtdEntity.getModifier())){
			throw new IllegalArgumentException("\"" + mtdEntity.toString() + "\" is not a static method ");
		}
	}

	@Override
    public void doExecute() {
        //参数入栈
        argumentsToStack();
        log.debug("invoke static method : " + name);
        insnHelper.invokeStatic(methodOwner.getType(), name, getReturnType(), mtdEntity.getArgTypes());
        if(!isSaveReference()){
            if(!getReturnType().equals(Type.VOID_TYPE)){
                insnHelper.pop();
            }
        }
    }

    @Override
    public String toString() {
        return methodOwner + "." + mtdEntity;
    }


}
