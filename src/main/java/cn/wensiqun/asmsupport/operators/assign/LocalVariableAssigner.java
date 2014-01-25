package cn.wensiqun.asmsupport.operators.assign;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.wensiqun.asmsupport.Parameterized;
import cn.wensiqun.asmsupport.block.ProgramBlock;
import cn.wensiqun.asmsupport.definition.variable.LocalVariable;

/**
 * 
 * @author 温斯群(Joe Wen)
 *
 */
public class LocalVariableAssigner extends Assigner {


    private static Log log = LogFactory.getLog(LocalVariableAssigner.class);
    
    private LocalVariable var;
    
    protected LocalVariableAssigner(ProgramBlock block, final LocalVariable var, Parameterized value) {
        super(block, var, value);
        this.var = var;
    }

    @Override
    public void executing() {
        //检测是否可用
        var.availableFor(this);
        log.debug("start execute assign value to variable '" + var.getLocalVariableMeta().getName() + "' from " + value.getParamterizedType());
        /*start--执行赋值操作--start*/
        //加载值到栈
        log.debug("load value to stack");
        value.loadToStack(block);
        
        //autoBoxAndUnBox();
        autoCast();

        log.debug("store to local variable");
        //将栈内的值存储到本地变量中
        insnHelper.storeInsn(var);
                //var.getScopeLogicVar().getPosition()[0]);
        /*end--执行赋值操作--end*/
        
    }

    @Override
    public void execute() {
        var.setVariableCompileOrder(insnHelper.getMethod().nextInsNumber());
        super.execute();
    }

}
