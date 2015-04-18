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

import cn.wensiqun.asmsupport.core.GetGlobalVariabled;
import cn.wensiqun.asmsupport.core.InternalParameterized;
import cn.wensiqun.asmsupport.core.definition.variable.meta.VariableMeta;
import cn.wensiqun.asmsupport.core.operator.AbstractOperator;


/**
 * 
 * 变量的接口
 * @author 温斯群(Joe Wen)
 *
 */
public interface IVariable extends InternalParameterized, GetGlobalVariabled{
    
    /**
     * 当前变量对于传入的操作是否可用
     * @param operator
     */
    boolean availableFor(AbstractOperator operator);
    
    /**
     * 获取当前变量的VariableEntity
     * @return
     */
    VariableMeta getVariableMeta();

}
