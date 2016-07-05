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
package cn.wensiqun.asmsupport.core.operator.numerical.bit;

import cn.wensiqun.asmsupport.core.context.MethodContext;
import cn.wensiqun.asmsupport.core.block.KernelProgramBlock;
import cn.wensiqun.asmsupport.core.definition.KernelParam;
import cn.wensiqun.asmsupport.core.operator.Operator;
import cn.wensiqun.asmsupport.core.utils.log.Log;
import cn.wensiqun.asmsupport.core.utils.log.LogFactory;

/**
 * 
 * @author wensiqun at 163.com(Joe Wen)
 *
 */
public class KernelReverse extends KernelUnaryBitwise {

    private static final Log LOG = LogFactory.getLog(KernelReverse.class);
    
    protected KernelReverse(KernelProgramBlock block, KernelParam factor) {
        super(block, factor, Operator.REVERSE);
    }

    @Override
    public void doExecute(MethodContext context) {
        if(LOG.isPrintEnabled()) {
            LOG.print("Start inverts operator : " + getOperatorSymbol());
            LOG.print("Factor to stack"); 
        }
        factorToStack(context);
        if(LOG.isPrintEnabled()) {
            LOG.print("Start invert");
        }
        context.getInstructions().inverts(targetClass.getType());
    }

}
