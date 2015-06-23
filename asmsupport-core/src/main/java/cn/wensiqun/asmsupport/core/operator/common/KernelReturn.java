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
package cn.wensiqun.asmsupport.core.operator.common;

import cn.wensiqun.asmsupport.core.block.KernelProgramBlock;
import cn.wensiqun.asmsupport.core.definition.KernelParam;
import cn.wensiqun.asmsupport.core.operator.BreakStack;
import cn.wensiqun.asmsupport.core.utils.log.Log;
import cn.wensiqun.asmsupport.core.utils.log.LogFactory;
import cn.wensiqun.asmsupport.standard.def.clazz.IClass;

/**
 * Represent a return statement in java code.
 * 
 * @author wensiqun at 163.com(Joe Wen)
 *
 */
public class KernelReturn extends BreakStack {

    private static final Log LOG = LogFactory.getLog(KernelReturn.class);
    
    private KernelParam returner;
    
    protected KernelReturn(KernelProgramBlock block, KernelParam returner) {
        super(block, false);
        this.returner = returner;
        if(returner != null){
            returner.asArgument();
        }
    }
    
    protected KernelReturn(KernelProgramBlock block, KernelParam returner, boolean autoCreate) {
        super(block, autoCreate);
        this.returner = returner;
        if(returner != null){
            returner.asArgument();
        }
    }
    
    

    @Override
    protected void verifyArgument() {
        // Do nothing
    }

    @Override
    protected void checkAsArgument() {
        // Do nothing
    }
    
    @Override
    public void breakStackExecuting() {
        if(returner == null){
            if(LOG.isPrintEnabled()) {
                LOG.print("direct return from method");
            }
            insnHelper.returnInsn();
        }else{
            returner.loadToStack(block);
            IClass actullyReturnType = returner.getResultType();
            if(actullyReturnType == null){
                throw new NullPointerException("Return type must be non-null!");
            }
            IClass methodReturnType = block.getMethod().getMeta().getReturnClass();
            autoCast(actullyReturnType, methodReturnType, false);
            insnHelper.returnInsn(methodReturnType.getType());
        }
    }

}
