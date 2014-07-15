package cn.wensiqun.asmsupport.operators.method;

import java.lang.reflect.Modifier;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.objectweb.asm.Type;

import cn.wensiqun.asmsupport.Parameterized;
import cn.wensiqun.asmsupport.block.ProgramBlock;

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
    protected MethodInvokeInvoker(ProgramBlock block, MethodInvoker caller, String name, Parameterized[] arguments) {
        super(block, caller.getReturnClass(), name, arguments);
        this.caller = caller;
        //this.methodType = MethodType.METHOD;
        //默认不保存引用
        setSaveReference(false);
        //set the method caller to save reference;
        caller.setSaveReference(true);    
    }
    
    
    
    @Override
    protected void beforeInitProperties() {
        methodOwner = caller.getReturnClass();
        super.beforeInitProperties();
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
    public void onEndPrepareProcess() {
        if(Modifier.isStatic(getModifiers())){
            block.removeExe(caller);
            MethodInvoker mi = new StaticMethodInvoker(block, getActuallyOwner(), name, arguments);
            block.removeExe(mi);
            block.replaceExe(this, mi);
        }
        super.onEndPrepareProcess();
    }

    @Override
    public void executing() {
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
