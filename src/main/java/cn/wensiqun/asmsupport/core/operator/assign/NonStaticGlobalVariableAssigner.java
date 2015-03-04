package cn.wensiqun.asmsupport.core.operator.assign;

import java.lang.reflect.Modifier;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.wensiqun.asmsupport.core.Parameterized;
import cn.wensiqun.asmsupport.core.block.ProgramBlockInternal;
import cn.wensiqun.asmsupport.core.definition.variable.NonStaticGlobalVariable;
import cn.wensiqun.asmsupport.core.exception.ASMSupportException;

/**
 * 
 * @author 温斯群(Joe Wen)
 *
 */
public class NonStaticGlobalVariableAssigner extends Assigner {

    private static Log log = LogFactory.getLog(NonStaticGlobalVariableAssigner.class);
    
    private NonStaticGlobalVariable var;
    
    protected NonStaticGlobalVariableAssigner(ProgramBlockInternal block, final NonStaticGlobalVariable var, Parameterized value) {
        super(block, var, value);
        this.var = var;
    }

    @Override
    public void doExecute() {
    	if(log.isDebugEnabled()){
            log.debug("assign value to global variable '" + var.getVariableMeta().getName() + "' from " + value  );
        }
        /*start--执行赋值操作--start*/
    	//如果当前方法是静态的抛异常
        if(Modifier.isStatic(block.getMethod().getMethodMeta().getModifier())){
            throw new ASMSupportException("current method " + block.getMethod() + " is static cannot use non-static field " + var.getVariableMeta().getName() );
        }
        var.getOwner().loadToStack(block);
        
        
        //加载值到栈
        value.loadToStack(block);
        
        //autoBoxAndUnBox();
        //如果是基本类型则执行类型转换
        autoCast();
        
        //将栈内的值存储到全局变量中
        insnHelper.putField(var.getOwner().getVariableMeta().getDeclareType().getType(), 
                var.getVariableMeta().getName(),
                var.getVariableMeta().getDeclareType().getType());
        /*end--执行赋值操作--end*/
    }

}
