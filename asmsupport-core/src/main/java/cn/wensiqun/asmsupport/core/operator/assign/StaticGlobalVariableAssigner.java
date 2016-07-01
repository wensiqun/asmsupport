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
import cn.wensiqun.asmsupport.core.definition.KernelParam;
import cn.wensiqun.asmsupport.core.definition.variable.StaticGlobalVariable;
import cn.wensiqun.asmsupport.core.utils.log.Log;
import cn.wensiqun.asmsupport.core.utils.log.LogFactory;

/**
 * Represent a static field assign opertion.
 * 
 * @author wensiqun at 163.com(Joe Wen)
 *
 */
public class StaticGlobalVariableAssigner extends KernelAssign {

    private static final Log LOG = LogFactory.getLog(StaticGlobalVariableAssigner.class);
    
    private StaticGlobalVariable var;
    
    protected StaticGlobalVariableAssigner(KernelProgramBlock block, final StaticGlobalVariable var, KernelParam value) {
        super(block, var, value);
        this.var = var;
    }

    @Override
    public void doExecute() {
    	if(LOG.isPrintEnabled()){
            LOG.print("assign value to global variable '" + var.getMeta().getName() + "' from " + value  );
        }
        value.loadToStack(block);
        autoCast();
        getInstructions().putStatic(var.getOwner().getType(),
                var.getMeta().getName(),
                var.getMeta().getType().getType());
    }

}
