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

import cn.wensiqun.asmsupport.standard.def.IParam;
import cn.wensiqun.asmsupport.standard.def.clazz.AClass;

/**
 * Indicate a variable
 * 
 * @author sqwen
 *
 */
public interface IVar extends IParam {

    /**
     * Get variable name
     * 
     * @return String
     */
    String getName();

    /**
     * <p>
     * The method is same to call {@link #getResultType()}, the
     * former type is the variable declare type. For example : 
     * </p>
     * 
     * <pre>
     * List foo = new ArrayList(); 
     * </pre>
     * 
     * <p>
     * So The former type of 'foo' variable is List instead of ArrayList
     * </p>
     * 
     * @return AClass
     */
    AClass getFormerType();
    
    /**
     * Get the variable modifier, it's an integer value which 
     * is a sum value of same field from {@link Opcodes}, the field 
     * is start with 'ACC_', for exampel : "public static" modifiers,
     * the value is  {@link Opcodes#ACC_PUBLIC} + {@link Opcodes#ACC_STATIC}
     * 
     * @return int modifiers
     */
    int getModifiers();

}
