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
package cn.wensiqun.asmsupport.core.definition;

import cn.wensiqun.asmsupport.core.PushStackable;
import cn.wensiqun.asmsupport.core.definition.variable.GlobalVariable;
import cn.wensiqun.asmsupport.standard.def.IParam;


/**
 * This interface indicate it can be assign to a variable or method as parameter
 * 
 * @author wensiqun at 163.com(Joe Wen)
 * 
 */
public interface KernelParam extends IParam, PushStackable {
    
    /**
     * If current {@link KernelParam} has used by other, than need call this method.
     */
    void asArgument();
    
    /**
     * Override change return type.
     */
    GlobalVariable field(String name);
}
