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
package cn.wensiqun.asmsupport.standard.action;

import cn.wensiqun.asmsupport.core.clazz.AClass;
import cn.wensiqun.asmsupport.standard.def.clazz.IClass;
import cn.wensiqun.asmsupport.standard.def.var.IFieldVar;

public interface AClassDefAction<_Field extends IFieldVar, _Class extends IClass<_Field>> {
    
    /**
     * Defined a class object to AClass
     * 
     * @param cls
     * @return
     */
    _Class getType(Class<?> cls);
    
    /**
     * Defined an array type according component and dimension
     * 
     * <pre>
     *     defArrayType(int.class, 3) is equals to defArrayType(int[][][].class)
     * </pre>
     * 
     * @param cls
     * @param dim
     * @return
     */
    _Class getArrayType(Class<?> cls, int dim);
    
    /**
     * Similar to {@link #getArrayType(Class, int)}
     * 
     * @param rootComponent
     * @param dim
     * @return
     */
    _Class getArrayType(AClass rootComponent, int dim);
}
