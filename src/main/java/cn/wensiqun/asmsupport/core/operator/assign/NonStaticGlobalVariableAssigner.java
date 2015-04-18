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
import cn.wensiqun.asmsupport.core.definition.variable.NonStaticGlobalVariable;
import cn.wensiqun.asmsupport.core.exception.ASMSupportException;
import cn.wensiqun.asmsupport.core.log.Log;
import cn.wensiqun.asmsupport.core.log.LogFactory;

/**
 * 
 * @author 温斯群(Joe Wen)
 *
 */
public class NonStaticGlobalVariableAssigner extends Assigner {

    private static final Log LOG = LogFactory.getLog(NonStaticGlobalVariableAssigner.class);
    
    private NonStaticGlobalVariable var;
    
    protected NonStaticGlobalVariableAssigner(ProgramBlockInternal block, final NonStaticGlobalVariable var, InternalParameterized value) {
        super(block, var, value);
        this.var = var;
    }

    @Override
    public void doExecute() {
    	if(LOG.isPrintEnabled()){
            LOG.print("assign value to global variable '" + var.getVariableMeta().getName() + "' from " + value  );
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
