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
 * Represent a call constructor operation.
 * 
 * @author wensiqun at 163.com(Joe Wen)
 * 
 */
public class ConstructorInvoker extends MethodInvoker {

    private static final Log LOG = LogFactory.getLog(ConstructorInvoker.class);

    /**
     * 
     * @param block
     * @param argumentClasses
     * @param arguments
     */
    protected ConstructorInvoker(KernelProgramBlock block, IClass argumentClasses, KernelParam[] arguments) {
        super(block, argumentClasses, ASConstants.INIT, arguments);
        if (argumentClasses.isPrimitive()) {
            throw new IllegalArgumentException("Cannot new a primitive class");
        }else if(argumentClasses.isAbstract()){
            throw new IllegalArgumentException(argumentClasses.getName() + "is an abstract class cannot new an abstract class");
        }
        //default to don't save return result reference of this method.
        setSaveReference(false);
    }

    @Override
    public void doExecute(MethodContext context) {
        LOG.print("new a instance of class :" + this.methodOwner.getName());
        LOG.print("put class reference to stack");
        getInstructions().newInstance(methodOwner.getType());
        if (isSaveReference()) {
            getInstructions().dup();
        }
        argumentsToStack(context);
        LOG.print("call the constrcutor");
        getInstructions().invokeConstructor(methodOwner.getType(), methodMeta.getParameterAsmTypes());
    }

    @Override
    public String toString() {
        return  "new " + methodOwner.getName() + "." + methodMeta;
    }

}
