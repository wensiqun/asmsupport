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
package cn.wensiqun.asmsupport.core.definition.variable;

import cn.wensiqun.asmsupport.core.definition.KernelParameterized;
import cn.wensiqun.asmsupport.core.operator.AbstractOperator;
import cn.wensiqun.asmsupport.standard.def.var.IVar;
import cn.wensiqun.asmsupport.standard.def.var.meta.VariableMeta;


/**
 * 
 * 变量的接口
 * @author 温斯群(Joe Wen)
 *
 */
public interface IVariable extends KernelParameterized, IVar {
    
    /**
     * 当前变量对于传入的操作是否可用
     * @param operator
     */
    boolean availableFor(AbstractOperator operator);
    
    /**
     * 获取当前变量的VariableEntity
     * @return
     */
    VariableMeta getMeta();
    
    /**
     * Get field value from current variable.
     * 
     * @param name
     * @return
     */
    GlobalVariable field(String name);

}
