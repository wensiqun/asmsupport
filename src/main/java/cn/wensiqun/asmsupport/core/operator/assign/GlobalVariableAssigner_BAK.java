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
package cn.wensiqun.asmsupport.core.operator.assign;

import java.lang.reflect.Modifier;

import cn.wensiqun.asmsupport.core.InternalParameterized;
import cn.wensiqun.asmsupport.core.block.ProgramBlockInternal;
import cn.wensiqun.asmsupport.core.definition.variable.GlobalVariable;
import cn.wensiqun.asmsupport.core.definition.variable.NonStaticGlobalVariable;
import cn.wensiqun.asmsupport.core.definition.variable.StaticGlobalVariable;
import cn.wensiqun.asmsupport.core.exception.ASMSupportException;
import cn.wensiqun.asmsupport.core.log.Log;
import cn.wensiqun.asmsupport.core.log.LogFactory;

/**
 * 
 * @author 温斯群(Joe Wen)
 *
 */
public class GlobalVariableAssigner_BAK extends Assigner {

    private static final Log LOG = LogFactory.getLog(GlobalVariableAssigner_BAK.class);
    
    private GlobalVariable var;
    
    protected GlobalVariableAssigner_BAK(ProgramBlockInternal block, final GlobalVariable var, InternalParameterized value) {
        super(block, var, value);
        this.var = var;
    }

    @Override
    public void doExecute() {
    	if(LOG.isPrintEnabled()){
            LOG.print("assign value to global variable '" + var.getVariableMeta().getName() + "' from " + value  );
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
