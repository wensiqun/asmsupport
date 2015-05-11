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

import cn.wensiqun.asmsupport.core.definition.KernelParam;
import cn.wensiqun.asmsupport.core.operator.AbstractOperator;
import cn.wensiqun.asmsupport.standard.def.var.IVar;
import cn.wensiqun.asmsupport.standard.def.var.meta.VarMeta;


/**
 * Represent a variable
 * 
 * @author wensiqun at 163.com(Joe Wen)
 *
 */
public interface IVariable extends KernelParam, IVar {
    
    /**
     * check the variable is available for current operator. for example 
     * <pre>
     * int i = 10;
     * if(i % 2 == 0) {
     *     int j = 100;
     *     System.out.println(i);
     * } else {
     *     System.out.println(j);
     * }
     * </pre>
     * The preceding code will be get an error cause by variable j is not 
     * available for the second method call operator.
     * 
     */
    boolean availableFor(AbstractOperator operator);
    
    /**
     * Get meta
     */
    VarMeta getMeta();
    
    /**
     * Get field value from current variable.
     * 
     * @param name
     */
    GlobalVariable field(String name);

}
