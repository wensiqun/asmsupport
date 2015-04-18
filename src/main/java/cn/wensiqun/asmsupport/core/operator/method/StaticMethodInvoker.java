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
import cn.wensiqun.asmsupport.core.utils.reflect.ModifierUtils;
import cn.wensiqun.asmsupport.org.objectweb.asm.Type;

/**
 * 静态方法调用
 * @author 温斯群(Joe Wen)
 *
 */
public class StaticMethodInvoker extends MethodInvoker {

    private static final Log LOG = LogFactory.getLog(StaticMethodInvoker.class);
    
    protected StaticMethodInvoker(ProgramBlockInternal block, AClass owner, String name,
            InternalParameterized[] arguments) {
        super(block, owner, name, arguments);
        if (owner.isPrimitive()) {
            throw new IllegalArgumentException("Cannot call static method from primitive");
        }
        //this.methodType = MethodType.STATIC;
        //默认不保存引用
        setSaveReference(false);
    }
    
    @Override
	protected void initAdditionalProperties() {
		super.initAdditionalProperties();
		if(!ModifierUtils.isStatic(mtdEntity.getModifier())){
			throw new IllegalArgumentException("\"" + mtdEntity.toString() + "\" is not a static method ");
		}
	}

	@Override
    public void doExecute() {
        //参数入栈
        argumentsToStack();
        LOG.print("invoke static method : " + name);
        insnHelper.invokeStatic(methodOwner.getType(), name, getReturnType(), mtdEntity.getArgTypes());
        if(!isSaveReference()){
            if(!getReturnType().equals(Type.VOID_TYPE)){
                insnHelper.pop();
            }
        }
    }

    @Override
    public String toString() {
        return methodOwner + "." + mtdEntity;
    }


}
