package cn.wensiqun.asmsupport.core.operator.assign;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.wensiqun.asmsupport.core.Parameterized;
import cn.wensiqun.asmsupport.core.block.ProgramBlockInternal;
import cn.wensiqun.asmsupport.core.definition.variable.StaticGlobalVariable;

/**
 * 
 * @author 温斯群(Joe Wen)
 *
 */
public class StaticGlobalVariableAssigner extends Assigner {

    private static Log log = LogFactory.getLog(StaticGlobalVariableAssigner.class);
    
    private StaticGlobalVariable var;
    
    protected StaticGlobalVariableAssigner(ProgramBlockInternal block, final StaticGlobalVariable var, Parameterized value) {
        super(block, var, value);
        this.var = var;
    }

    @Override
    public void doExecute() {
    	if(log.isDebugEnabled()){
            log.debug("assign value to global variable '" + var.getVariableMeta().getName() + "' from " + value  );
        }
        /*start--执行赋值操作--start*/
        
        //加载值到栈
        value.loadToStack(block);
        
        //autoBoxAndUnBox();
        //如果是基本类型则执行类型转换
        autoCast();
        
        //将栈内的值存储到全局变量中
        //判读如果是静态变量
        insnHelper.putStatic(var.getOwner().getType(), 
                var.getVariableMeta().getName(),
                var.getVariableMeta().getDeclareType().getType());
        /*end--执行赋值操作--end*/
    }

}
