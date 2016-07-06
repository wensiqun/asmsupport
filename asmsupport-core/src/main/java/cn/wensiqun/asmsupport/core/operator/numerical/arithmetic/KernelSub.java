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
/**
 * 
 */
package cn.wensiqun.asmsupport.core.operator.numerical.arithmetic;

import cn.wensiqun.asmsupport.core.context.MethodExecuteContext;
import cn.wensiqun.asmsupport.core.block.KernelProgramBlock;
import cn.wensiqun.asmsupport.core.definition.KernelParam;
import cn.wensiqun.asmsupport.core.operator.Operator;
import cn.wensiqun.asmsupport.core.utils.log.Log;
import cn.wensiqun.asmsupport.core.utils.log.LogFactory;

/**
 * subtraction operator
 * @author wensiqun at 163.com(Joe Wen)
 *
 */
public class KernelSub extends AbstractArithmetic {

    private static final Log LOG = LogFactory.getLog(KernelSub.class);
    
    protected KernelSub(KernelProgramBlock block, KernelParam leftFactor, KernelParam rightFactor) {
        super(block, leftFactor, rightFactor, Operator.SUB);
    }

    @Override
    public void doExecute(MethodExecuteContext context) {
        if(LOG.isPrintEnabled()) {
            LOG.print("Start execute sub arithmetic operator");
        }
        factorToStack(context);
        if(LOG.isPrintEnabled()) {
            LOG.print("Execute the sub instruction");
        }
        context.getInstructions().sub(targetClass.getType());
    }


}
