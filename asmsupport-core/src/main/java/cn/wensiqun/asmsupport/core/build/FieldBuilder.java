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
package cn.wensiqun.asmsupport.core.build;

import cn.wensiqun.asmsupport.core.Executable;
import cn.wensiqun.asmsupport.core.context.FieldContext;

/**
 * 
 * @author wensiqun at 163.com(Joe Wen)
 *
 */
public interface FieldBuilder extends Executable<FieldContext> {
    
    /**
     * Create a field.
     * 
     * @param cv
     */
    void create(BytecodeResolver cv);

    /**
     * Get method name.
     */
    String getName();

    /**
     * Get the modifiers of the field you want to build.
     * @return
     */
    int getModifiers();
}
