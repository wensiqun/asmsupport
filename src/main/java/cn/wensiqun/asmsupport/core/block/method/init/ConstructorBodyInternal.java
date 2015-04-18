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
package cn.wensiqun.asmsupport.core.block.method.init;

import cn.wensiqun.asmsupport.core.InternalParameterized;
import cn.wensiqun.asmsupport.core.block.ProgramBlockInternal;
import cn.wensiqun.asmsupport.core.block.method.AbstractMethodBody;
import cn.wensiqun.asmsupport.core.clazz.AClass;
import cn.wensiqun.asmsupport.core.exception.ASMSupportException;
import cn.wensiqun.asmsupport.core.operator.method.MethodInvoker;
import cn.wensiqun.asmsupport.core.operator.method.SuperConstructorInvoker;
import cn.wensiqun.asmsupport.core.operator.numerical.OperatorFactory;
import cn.wensiqun.asmsupport.core.utils.reflect.ModifierUtils;
import cn.wensiqun.asmsupport.standard.method.IContructorBody;

/**
 * 
 * @author 温斯群(Joe Wen)
 *
 */
public abstract class ConstructorBodyInternal extends AbstractMethodBody implements IContructorBody {

	public MethodInvoker supercall(InternalParameterized... arguments) {
    	AClass owner = getMethodOwner();
    	if(ModifierUtils.isEnum(getMethodOwner().getModifiers())){
    		throw new ASMSupportException("Cannot invoke super constructor from enum type " + owner);
    	}
        invokeVerify(owner);

        return OperatorFactory.newOperator(SuperConstructorInvoker.class, 
        		new Class<?>[]{ProgramBlockInternal.class, AClass.class, InternalParameterized[].class}, 
        		getExecutor(), owner, arguments);
	}
    
    @Override
    public final void generateBody() {
        body(argments);
    }
    
}
