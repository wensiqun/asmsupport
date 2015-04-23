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

import cn.wensiqun.asmsupport.core.block.KernelProgramBlock;
import cn.wensiqun.asmsupport.core.definition.KernelParameterized;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.core.log.Log;
import cn.wensiqun.asmsupport.core.log.LogFactory;

/**
 * 
 * @author 温斯群(Joe Wen)
 *
 */
public class LocalVariableAssigner extends KernelAssign {


    private static final Log LOG = LogFactory.getLog(LocalVariableAssigner.class);
    
    private LocalVariable var;
    
    protected LocalVariableAssigner(KernelProgramBlock block, final LocalVariable var, KernelParameterized value) {
        super(block, var, value);
        this.var = var;
    }

    @Override
    public void doExecute() {
        //检测是否可用
        var.availableFor(this);

        if(LOG.isPrintEnabled()) { 
            LOG.print("start execute assign value to variable '" + var.getMeta().getName() + "' from " + value.getResultType());
            /*start--执行赋值操作--start*/
            //加载值到栈
            LOG.print("load value to stack");
        }
        value.loadToStack(block);
        
        //autoBoxAndUnBox();
        autoCast();

        if(LOG.isPrintEnabled()) { 
            LOG.print("store to local variable");
        }
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
