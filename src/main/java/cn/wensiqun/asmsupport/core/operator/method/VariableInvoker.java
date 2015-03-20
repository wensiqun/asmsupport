/**    
 *  Asmsupport is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package cn.wensiqun.asmsupport.core.operator.method;

import java.lang.reflect.Modifier;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.wensiqun.asmsupport.core.Parameterized;
import cn.wensiqun.asmsupport.core.block.ProgramBlockInternal;
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
        super(block, var.getVariableMeta().getDeclareType(), name, arguments);
        if(var.getVariableMeta().getDeclareType().isPrimitive()){
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
            if(ve.getDeclareType().isInterface()){
                log.debug("invoke interface method : " + name);
                //如果是接口
                insnHelper.invokeInterface(ve.getDeclareType().getType(), this.name, getReturnType(), mtdEntity.getArgTypes());
            }else{
                log.debug("invoke class method : " + name);
                if(ve.getName().equals(ASConstant.SUPER)){
                    insnHelper.invokeSuperMethod(ve.getDeclareType().getType(), this.name, getReturnType(), mtdEntity.getArgTypes());
                }else {
                    insnHelper.invokeVirtual(ve.getDeclareType().getType(), this.name, getReturnType(), mtdEntity.getArgTypes());
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
