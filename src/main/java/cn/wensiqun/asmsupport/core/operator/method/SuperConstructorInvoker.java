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

import cn.wensiqun.asmsupport.core.InternalParameterized;
import cn.wensiqun.asmsupport.core.block.ProgramBlockInternal;
import cn.wensiqun.asmsupport.core.clazz.AClass;
import cn.wensiqun.asmsupport.core.clazz.AClassFactory;
import cn.wensiqun.asmsupport.core.log.Log;
import cn.wensiqun.asmsupport.core.log.LogFactory;
import cn.wensiqun.asmsupport.core.utils.ASConstant;

/**
 * 构造方法调用者。
 * 
 * @author 温斯群(Joe Wen)
 * 
 */
public class SuperConstructorInvoker extends MethodInvoker {

    private static final Log LOG = LogFactory.getLog(SuperConstructorInvoker.class);
    
    protected SuperConstructorInvoker(ProgramBlockInternal block, AClass aclass, InternalParameterized[] arguments) {
        super(block, AClassFactory.getType(aclass.getSuperClass()), ASConstant.INIT, arguments);
    }

    @Override
    public void doExecute() {
        LOG.print("call method '"+ name +"' by 'this' key word");
        LOG.print("put 'this' to stack");
        insnHelper.loadThis();
        argumentsToStack();
        
        AClass[] argTypes = new AClass[arguments.length];
        for(int i=0; i<argTypes.length; i++){
            argTypes[i] = arguments[i].getResultType();
        }
        insnHelper.invokeConstructor(getActuallyOwner().getType(), mtdEntity.getArgTypes());
    }

    @Override
    public String toString() {
        return "super." + mtdEntity;
    }

}
