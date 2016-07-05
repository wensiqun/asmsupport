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

import cn.wensiqun.asmsupport.core.context.MethodContext;
import cn.wensiqun.asmsupport.core.block.KernelProgramBlock;
import cn.wensiqun.asmsupport.core.definition.KernelParam;
import cn.wensiqun.asmsupport.core.utils.log.Log;
import cn.wensiqun.asmsupport.core.utils.log.LogFactory;
import cn.wensiqun.asmsupport.standard.def.clazz.IClass;
import cn.wensiqun.asmsupport.utils.ASConstants;

/**
 * Call constructor opertion
 * 
 * @author wensiqun at 163.com(Joe Wen)
 * 
 */
public class SuperConstructorInvoker extends MethodInvoker {

    private static final Log LOG = LogFactory.getLog(SuperConstructorInvoker.class);
    
    protected SuperConstructorInvoker(KernelProgramBlock block, IClass aclass, KernelParam[] arguments) {
        super(block, aclass.getSuperclass(), ASConstants.INIT, arguments);
    }

    @Override
    public void doExecute(MethodContext context) {
        LOG.print("call method '"+ name +"' by 'this' key word");
        LOG.print("put 'this' to stack");
        context.getInstructions().loadThis(getParent().getMethod().getMeta().getOwner().getType());
        argumentsToStack(context);
        
        IClass[] argTypes = new IClass[arguments.length];
        for(int i=0; i<argTypes.length; i++){
            argTypes[i] = arguments[i].getResultType();
        }
        context.getInstructions().invokeConstructor(getActuallyOwner().getType(), methodMeta.getParameterAsmTypes());
    }

    @Override
    public String toString() {
        return "super." + methodMeta;
    }

}
