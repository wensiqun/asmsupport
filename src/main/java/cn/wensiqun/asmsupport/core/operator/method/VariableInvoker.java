package cn.wensiqun.asmsupport.core.operator.method;

import java.lang.reflect.Modifier;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.wensiqun.asmsupport.core.Parameterized;
import cn.wensiqun.asmsupport.core.block.classes.common.ProgramBlockInternal;
import cn.wensiqun.asmsupport.core.definition.variable.IVariable;
import cn.wensiqun.asmsupport.core.definition.variable.meta.VariableMeta;
import cn.wensiqun.asmsupport.core.utils.ASConstant;
import cn.wensiqun.asmsupport.org.objectweb.asm.Type;

/**
 * 构造方法调用者。
 * 
 * @author 温斯群(Joe Wen)
 * @see CommonMethodInvoker
 */
@Deprecated
public class VariableInvoker extends MethodInvoker {

	@Deprecated
    private static Log log = LogFactory.getLog(VariableInvoker.class);
    
    private IVariable var;
     
    @Deprecated
    protected VariableInvoker(ProgramBlockInternal block, IVariable var, String name, Parameterized[] arguments) {
        super(block, var.getVariableMeta().getDeclareClass(), name, arguments);
        if(var.getVariableMeta().getDeclareClass().isPrimitive()){
            throw new IllegalArgumentException("primitive variable \"" + var.getVariableMeta().getName() +  "\"  cannot invoke method : variable must be a non-primitive variable");
        }
        this.var = var;
        //默认不保存引用
        setSaveReference(false);
    }

    @Override
    public void endingPrepare() {
        //如果是静态方法那么则创建一个静态方法调用者到执行队列
        if(Modifier.isStatic(getModifiers())){
            //移除当前的方法调用
            MethodInvoker mi = new StaticMethodInvoker(block, getActuallyOwner(), name, arguments);
            block.removeExe(mi);
            block.getQueue().replace(this, mi);
        }
    }

    @Override
    public void doExecute() {
        VariableMeta ve = var.getVariableMeta();
        
        /* if method is non satic*/
        if(!Modifier.isStatic(getModifiers())){
            log.debug("call method by variable :" + var.getVariableMeta().getName());
            log.debug("put variable reference to stack");
            //变量入栈
            var.loadToStack(block);
            argumentsToStack();
            if(ve.getDeclareClass().isInterface()){
                log.debug("invoke interface method : " + name);
                //如果是接口
                insnHelper.invokeInterface(ve.getDeclareClass().getType(), this.name, getReturnType(), mtdEntity.getArgTypes());
            }else{
                log.debug("invoke class method : " + name);
                if(ve.getName().equals(ASConstant.SUPER)){
                    insnHelper.invokeSuperMethod(ve.getDeclareClass().getType(), this.name, getReturnType(), mtdEntity.getArgTypes());
                }else {
                    insnHelper.invokeVirtual(ve.getDeclareClass().getType(), this.name, getReturnType(), mtdEntity.getArgTypes());
                }
            }
            if(!isSaveReference()){
                if(!getReturnType().equals(Type.VOID_TYPE)){
                    insnHelper.pop();
                }
            }
        }
    }
    

    @Override
    public String toString() {
        return var + "." + mtdEntity;
    }
}
