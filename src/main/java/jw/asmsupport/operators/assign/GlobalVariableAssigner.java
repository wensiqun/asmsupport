package jw.asmsupport.operators.assign;

import java.lang.reflect.Modifier;

import jw.asmsupport.Parameterized;
import jw.asmsupport.block.ProgramBlock;
import jw.asmsupport.definition.variable.GlobalVariable;
import jw.asmsupport.exception.ASMSupportException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @author 温斯群(Joe Wen)
 *
 */
public class GlobalVariableAssigner extends Assigner {

    private static Log log = LogFactory.getLog(GlobalVariableAssigner.class);
    
    private GlobalVariable var;
    
    protected GlobalVariableAssigner(ProgramBlock block, final GlobalVariable var, Parameterized value) {
        super(block, var, value);
        this.var = var;
    }

    @Override
    public void executing() {
    	if(log.isDebugEnabled()){
            log.debug("assign value to global variable '" + var.getVariableEntity().getName() + "' from " + value  );
        }
        /*start--执行赋值操作--start*/
        
        
        //如果不是静态类则加载当前变量的引用入栈
        if(!Modifier.isStatic(var.getVariableEntity().getModifiers())){
            //如果当前方法是静态的抛异常
            if(Modifier.isStatic(block.getMethod().getMethodEntity().getModifier())){
            	throw new ASMSupportException("current method " + block.getMethod() + " is static cannot use non-static field " + var.getVariableEntity().getName() );
            }
            var.getVariableOwner().loadToStack(block);
        }
        
        
        //加载值到栈
        value.loadToStack(block);
        
        //autoBoxAndUnBox();
        //如果是基本类型则执行类型转换
        autoCast();
        
        //将栈内的值存储到全局变量中
        //判读如果是静态变量
        if(var.getStaticOwner() != null){
            insnHelper.putStatic(var.getStaticOwner().getType(), 
                    var.getVariableEntity().getName(),
                    var.getVariableEntity().getDeclareClass().getType());
        }else if(var.getVariableOwner() != null){
            insnHelper.putField(var.getVariableOwner().getVariableEntity().getDeclareClass().getType(), 
                    var.getVariableEntity().getName(),
                    var.getVariableEntity().getDeclareClass().getType());
        }
        /*end--执行赋值操作--end*/
    }

}
