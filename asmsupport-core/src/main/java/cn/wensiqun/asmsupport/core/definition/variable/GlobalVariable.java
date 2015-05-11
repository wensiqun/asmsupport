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

import cn.wensiqun.asmsupport.standard.def.clazz.AClass;
import cn.wensiqun.asmsupport.standard.def.var.IFieldVar;
import cn.wensiqun.asmsupport.standard.def.var.meta.Field;

/**
 * Represent a field
 * 
 * @author wensiqun at 163.com(Joe Wen)
 */
public abstract class GlobalVariable extends ExplicitVariable implements IFieldVar {

    protected Field meta;

    public GlobalVariable(Field meta) {
        this.meta = meta;
    }
    
    @Override
    public Field getMeta() {
        return meta;
    }

    @Override
    public final AClass getDeclaringClass() {
        return getMeta().getDeclaringClass();
    }

}