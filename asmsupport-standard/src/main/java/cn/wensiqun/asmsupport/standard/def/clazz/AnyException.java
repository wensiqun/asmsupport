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
package cn.wensiqun.asmsupport.standard.def.clazz;

import cn.wensiqun.asmsupport.org.objectweb.asm.Type;
import cn.wensiqun.asmsupport.standard.utils.AsmsupportClassLoader;


/**
 * Use internal, 
 * 
 * @author sqwen
 *
 */
public class AnyException extends BaseClass {

    public AnyException(AsmsupportClassLoader holder) {
    	super(holder);
    	type = Type.ANY_EXP_TYPE;
    }

    @Override
    public String getDescription() {
        return type.getDescriptor();
    }

    @Override
    public String getName() {
        return "ANY_EXCEPTION";
    }

    @Override
    public boolean equals(Object obj) {
    	return obj instanceof AnyException;
    }

    @Override
    public int hashCode() {
        return getName().hashCode();
    }

}
