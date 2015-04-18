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
import cn.wensiqun.asmsupport.core.log.Log;
import cn.wensiqun.asmsupport.core.log.LogFactory;

/**
 * 构造方法调用者。
 * 
 * @author 温斯群(Joe Wen)
 * 
 */
public class ConstructorInvoker extends MethodInvoker {

    private static final Log LOG = LogFactory.getLog(ConstructorInvoker.class);

    /**
     * 
     * @param clazz
     * @param argumentClasses
     * @param arguments
     * @param mv
     * @param dup
     */
    protected ConstructorInvoker(ProgramBlockInternal block, AClass aclass, InternalParameterized[] arguments) {
        super(block, aclass, METHOD_NAME_INIT, arguments);
        if (aclass.isPrimitive()) {
            throw new IllegalArgumentException("Cannot new a primitive class");
        }else if(aclass.isAbstract()){
            throw new IllegalArgumentException(aclass.getName() + "is an abstract class cannot new an abstract class");
        }
        //默认不保存引用
        setSaveReference(false);
    }

    @Override
    public void doExecute() {
        LOG.print("new a instance of class :" + this.methodOwner.getName());
        LOG.print("put class reference to stack");
        insnHelper.newInstance(methodOwner.getType());
        if (isSaveReference()) {
            insnHelper.dup();
        }
        //将参数入栈
        argumentsToStack();
        LOG.print("call the constrcutor");
        insnHelper.invokeConstructor(methodOwner.getType(), mtdEntity.getArgTypes());
    }

    @Override
    public String toString() {
        return  "new " + methodOwner.getName() + "." + mtdEntity;
    }

}
