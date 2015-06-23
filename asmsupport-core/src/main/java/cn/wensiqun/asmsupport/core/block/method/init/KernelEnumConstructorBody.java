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

import cn.wensiqun.asmsupport.core.block.KernelProgramBlock;
import cn.wensiqun.asmsupport.core.block.method.AbstractKernelMethodBody;
import cn.wensiqun.asmsupport.core.definition.KernelParam;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.core.operator.method.SuperConstructorInvoker;
import cn.wensiqun.asmsupport.core.operator.numerical.OperatorFactory;
import cn.wensiqun.asmsupport.standard.block.method.IEnumConstructorBody;
import cn.wensiqun.asmsupport.standard.def.clazz.IClass;
import cn.wensiqun.asmsupport.utils.lang.ArrayUtils;


/**
 * 
 * @author wensiqun at 163.com(Joe Wen)
 *
 */
public abstract class KernelEnumConstructorBody extends AbstractKernelMethodBody implements IEnumConstructorBody<LocalVariable> {
    
    @Override
    public final void generateBody() {
        OperatorFactory.newOperator(SuperConstructorInvoker.class, 
        		new Class<?>[]{KernelProgramBlock.class, IClass.class, KernelParam[].class}, 
        		getExecutor(), getMethodDeclaringClass(), new KernelParam[]{argments[0], argments[1]});
        body((LocalVariable[]) ArrayUtils.subarray(argments, 2, argments.length));
    }
    

}
