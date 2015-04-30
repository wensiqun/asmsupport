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
package cn.wensiqun.asmsupport.standard.def.var;

import cn.wensiqun.asmsupport.standard.def.clazz.AClass;
import cn.wensiqun.asmsupport.standard.def.var.meta.Field;

/**
 * Indicate a field of class.
 * 
 * @author sqwen
 *
 */
public interface IFieldVar extends IVar {
    
    /**
     * Returns the <code>{@link AClass}</code> object representing the class or interface
     * that declares the field represented by this <code>{@link Field}</code> object. it's
     * same to {@link Field#getDeclaringClass()}
     * 
     * @return AClass
     * @see {@link Field#getDeclaringClass()}
     */
    AClass getDeclaringClass();
    
}
