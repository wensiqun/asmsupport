package cn.wensiqun.asmsupport.core.operator.assign;

import java.lang.reflect.Modifier;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.wensiqun.asmsupport.core.Parameterized;
import cn.wensiqun.asmsupport.core.block.ProgramBlockInternal;
import cn.wensiqun.asmsupport.core.definition.variable.GlobalVariable;
import cn.wensiqun.asmsupport.core.definition.variable.NonStaticGlobalVariable;
import cn.wensiqun.asmsupport.core.definition.variable.StaticGlobalVariable;
import cn.wensiqun.asmsupport.core.exception.ASMSupportException;

/**
 * 
 * @author 温斯群(Joe Wen)
 *
 */
public class GlobalVariableAssigner_BAK extends Assigner {

    private static Log log = LogFactory.getLog(GlobalVariableAssigner_BAK.class);
    
    private GlobalVariable var;
    
    protected GlobalVariableAssigner_BAK(ProgramBlockInternal block, final GlobalVariable var, Parameterized value) {
        super(block, var, value);
        this.var = var;
    }

    @Override
    public void doExecute() {
    	if(log.isDebugEnabled()){
            log.debug("assign value to global variable '" + var.getVariableMeta().getName() + "' from " + value  );
        }
        /*start--执行赋值操作--start*/
        
        
        //如果不是静态类则加载当前变量的引用入栈
        if(!Modifier.isStatic(var.getVariableMeta().getModifiers())){
            //如果当前方法是静态的抛异常
            if(Modifier.isStatic(block.getMethod().getMethodMeta().getModifier())){
            	throw new ASMSupportException("current method " + block.getMethod() + " is static cannot use non-static field " + var.getVariableMeta().getName() );
            }
            ((NonStaticGlobalVariable)var).getOwner().loadToStack(block);
        }
        
        
        //加载值到栈
        value.loadToStack(block);
        
        //autoBoxAndUnBox();
        //如果是基本类型则执行类型转换
        autoCast();
        
        //将栈内的值存储到全局变量中
        //判读如果是静态变量
        if(((StaticGlobalVariable)var).getOwner() != null){
            insnHelper.putStatic(((StaticGlobalVariable)var).getOwner().getType(), 
                    var.getVariableMeta().getName(),
                    var.getVariableMeta().getDeclareType().getType());
        }else if(((NonStaticGlobalVariable)var).getOwner() != null){
            insnHelper.putField(((NonStaticGlobalVariable)var).getOwner().getVariableMeta().getDeclareType().getType(), 
                    var.getVariableMeta().getName(),
                    var.getVariableMeta().getDeclareType().getType());
        }
        /*end--执行赋值操作--end*/
    }

}
