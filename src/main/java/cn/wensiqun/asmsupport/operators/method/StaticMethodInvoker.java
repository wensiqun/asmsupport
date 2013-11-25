package cn.wensiqun.asmsupport.operators.method;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.objectweb.asm.Type;

import cn.wensiqun.asmsupport.Parameterized;
import cn.wensiqun.asmsupport.block.ProgramBlock;
import cn.wensiqun.asmsupport.clazz.AClass;
import cn.wensiqun.asmsupport.utils.reflet.ModifierUtils;

/**
 * 静态方法调用
 * @author 温斯群(Joe Wen)
 *
 */
public class StaticMethodInvoker extends MethodInvoker {

    private static Log log = LogFactory.getLog(StaticMethodInvoker.class);
    
    protected StaticMethodInvoker(ProgramBlock block, AClass owner, String name,
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
	protected void beforeInitProperties() {
		super.beforeInitProperties();
		if(!ModifierUtils.isStatic(mtdEntity.getModifier())){
			throw new IllegalArgumentException("\"" + mtdEntity.toString() + "\" is not a static method ");
		}
	}

	@Override
    public void executing() {
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
