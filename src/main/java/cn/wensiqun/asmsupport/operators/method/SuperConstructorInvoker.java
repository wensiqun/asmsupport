package cn.wensiqun.asmsupport.operators.method;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.wensiqun.asmsupport.Parameterized;
import cn.wensiqun.asmsupport.block.ProgramBlock;
import cn.wensiqun.asmsupport.clazz.AClass;
import cn.wensiqun.asmsupport.clazz.AClassFactory;

/**
 * 构造方法调用者。
 * 
 * @author 温斯群(Joe Wen)
 * 
 */
public class SuperConstructorInvoker extends MethodInvoker {

    private static Log log = LogFactory.getLog(SuperConstructorInvoker.class);
    
    protected SuperConstructorInvoker(ProgramBlock block, AClass aclass, Parameterized[] arguments) {
        super(block, AClassFactory.getProductClass(aclass.getSuperClass()), METHOD_NAME_INIT, arguments);
        //this.methodType = MethodType.THIS;
        //默认不保存引用
        //setSaveReference(false);
    }

    @Override
    public void executing() {
        log.debug("call method '"+ name +"' by 'this' key word");
        log.debug("put 'this' to stack");
        insnHelper.loadThis();
        argumentsToStack();
        
        AClass[] argTypes = new AClass[arguments.length];
        for(int i=0; i<argTypes.length; i++){
            argTypes[i] = arguments[i].getParamterizedType();
        }
        insnHelper.invokeConstructor(getActuallyOwner().getType(), mtdEntity.getArgTypes());
    }

/*    @Override
    public void preExecuteProcess() {
    }*/

    @Override
    public String toString() {
        return "super." + mtdEntity;
    }

}
