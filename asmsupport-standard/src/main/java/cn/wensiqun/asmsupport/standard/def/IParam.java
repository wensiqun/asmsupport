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
package cn.wensiqun.asmsupport.standard.def;

import cn.wensiqun.asmsupport.standard.def.clazz.AClass;
import cn.wensiqun.asmsupport.standard.def.var.IFieldVar;


/**
 * The interface indicate a operand among operator, such as a variable, a constant, 
 * a method invoke or arithmetic operator and so on.
 * 
 * @author sqwen
 */
public interface IParam {
    
    /**
     * Get the result type of current operand. 
     * 
     * @return
     */
    AClass getResultType();

    /**
     * Get a field from a {@link IParam}
     * 
     * @param name The field name
     * @return T The sub type of {@link IFieldVar}
     */
    IFieldVar field(String name);
}
