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

import cn.wensiqun.asmsupport.core.context.MethodExecuteContext;
import cn.wensiqun.asmsupport.core.block.KernelProgramBlock;
import cn.wensiqun.asmsupport.core.definition.KernelParam;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.core.utils.log.Log;
import cn.wensiqun.asmsupport.core.utils.log.LogFactory;

/**
 * Represent a local variable assign opertion.
 * 
 * @author wensiqun at 163.com(Joe Wen)
 *
 */
public class LocalVariableAssigner extends KernelAssign {


    private static final Log LOG = LogFactory.getLog(LocalVariableAssigner.class);
    
    private LocalVariable var;
    
    protected LocalVariableAssigner(KernelProgramBlock block, final LocalVariable var, KernelParam value) {
        super(block, var, value);
        this.var = var;
    }

    @Override
    public void doExecute(MethodExecuteContext context) {
        var.availableFor(this);
        if(LOG.isPrintEnabled()) { 
            LOG.print("start execute assign value to variable '" + var.getMeta().getName() + "' from " + value.getResultType());
            LOG.print("load value to stack");
        }
        value.push(context);
        autoCast(context);
        if(LOG.isPrintEnabled()) { 
            LOG.print("store to local variable");
        }
        context.getInstructions().storeInsn(var);
    }

    @Override
    public void execute(MethodExecuteContext context) {
        var.setVariableCompileOrder(getParent().getMethod().getNextInstructionNumber());
        super.execute(context);
    }

}
