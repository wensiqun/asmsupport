package cn.wensiqun.asmsupport.core.operator.method;

import java.lang.reflect.Modifier;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.wensiqun.asmsupport.core.Parameterized;
import cn.wensiqun.asmsupport.core.block.classes.common.ProgramBlockInternal;
import cn.wensiqun.asmsupport.org.objectweb.asm.Type;

/**
 * 
 * @author 温斯群(Joe Wen)
 * @see CommonMethodInvoker
 */
@Deprecated
public class MethodInvokeInvoker extends MethodInvoker {

    private static Log log = LogFactory.getLog(MethodInvokeInvoker.class);
    
    private MethodInvoker caller;
    
    @Deprecated
    protected MethodInvokeInvoker(ProgramBlockInternal block, MethodInvoker caller, String name, Parameterized[] arguments) {
        super(block, caller.getReturnClass(), name, arguments);
        this.caller = caller;
        //this.methodType = MethodType.METHOD;
        //默认不保存引用
        setSaveReference(false);
        //set the method caller to save reference;
        caller.setSaveReference(true);    
    }
    
    
    
    @Override
    protected void initAdditionalProperties() {
        methodOwner = caller.getReturnClass();
        super.initAdditionalProperties();
    }

    @Override
    protected void verifyArgument() {
        if(caller.getReturnClass().isPrimitive()){
            throw new IllegalArgumentException("Variable must be a non-primitive variable");
        }
        super.verifyArgument();
    }

    @Override
    protected void checkAsArgument() {
        caller.asArgument();
        super.checkAsArgument();
    }

    @Override
    public void endingPrepare() {
        if(Modifier.isStatic(getModifiers())){
            block.removeExe(caller);
            MethodInvoker mi = new StaticMethodInvoker(block, getActuallyOwner(), name, arguments);
            block.removeExe(mi);
            block.getQueue().replace(this, mi);
        }
    }

    @Override
    public void doExecute() {
        caller.loadToStack(block);
        if(!Modifier.isStatic(getModifiers())){
            log.debug("call method by method return value");
            argumentsToStack();
            if(caller.getReturnClass().isInterface()){
                log.debug("invoke interface method : " + name);
                //如果是接口
                insnHelper.invokeInterface(this.caller.getReturnType(), this.name, getReturnType(), mtdEntity.getArgTypes());
            }else{
                log.debug("invoke class method : " + name);
                insnHelper.invokeVirtual(this.caller.getReturnType(), this.name, getReturnType(), mtdEntity.getArgTypes());
            }
        }
        
        if(!isSaveReference()){
            if(!getReturnType().equals(Type.VOID_TYPE)){
                insnHelper.pop();
            }
        }
    }

    MethodInvoker getCaller() {
        return caller;
    }

    @Override
    public String toString() {
        return caller + "." + mtdEntity;
    }

}
