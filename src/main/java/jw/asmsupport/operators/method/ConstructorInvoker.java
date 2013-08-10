package jw.asmsupport.operators.method;

import jw.asmsupport.Parameterized;
import jw.asmsupport.block.ProgramBlock;
import jw.asmsupport.clazz.AClass;

import org.apache.commons.lang.IllegalClassException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 构造方法调用者。
 * 
 * @author 温斯群(Joe Wen)
 * 
 */
public class ConstructorInvoker extends MethodInvoker {

    private static Log log = LogFactory.getLog(ConstructorInvoker.class);

    /**
     * 
     * @param clazz
     * @param argumentClasses
     * @param arguments
     * @param mv
     * @param dup
     */
    protected ConstructorInvoker(ProgramBlock block, AClass aclass, Parameterized[] arguments) {
        super(block, aclass, METHOD_NAME_INIT, arguments);
        //this.methodType = MethodType.CONSTRUCTOR;
        if (aclass.isPrimitive()) {
            throw new IllegalClassException("Cannot new a primitive class");
        }else if(aclass.isAbstract()){
            throw new IllegalClassException(aclass.getName() + "is an abstract class cannot new an abstract class");
        }
        //默认不保存引用
        setSaveReference(false);
    }

    @Override
    public void executing() {
        log.debug("new a instance of class :" + this.methodOwner.getName());
        log.debug("put class reference to stack");
        insnHelper.newInstance(methodOwner.getType());
        if (isSaveReference()) {
            insnHelper.dup();
        }
        //将参数入栈
        argumentsToStack();
        log.debug("call the constrcutor");
        insnHelper.invokeConstructor(methodOwner.getType(), mtdEntity.getArgTypes());
    }

    @Override
    public String toString() {
        return  "new " + methodOwner.getName() + "." + mtdEntity;
    }

}
