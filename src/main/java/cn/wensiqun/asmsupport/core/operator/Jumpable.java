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
package cn.wensiqun.asmsupport.core.operator;


import cn.wensiqun.asmsupport.core.InternalParameterized;
import cn.wensiqun.asmsupport.org.objectweb.asm.Label;

/**
 * Indicate the operator need support jump instructions.
 * <p>
 * What's the jump in jvm instruction. such as : {@code  if_xxx, ifxx } instruction.
 * </p>
 */
public interface Jumpable extends InternalParameterized {

    /**
     * <p>
     * To execute positive jump and generate relevant byte code.
     * </p>
     * <p>
     * For example : the operator {@code '1 == 1'} then the positive jump is {@code if_icmpeq}.
     * the operator {@code 'if(isTrue()){ ... }'}(here we suppose the method {@code isTrue})
     * return boolean value, then the positive jump is {@code ifne}
     * </p>
     * @param from
     * @param posLbl
     * @param negLbl
     */
    void jumpPositive(InternalParameterized from, Label posLbl, Label negLbl);
    
    
    /**
     * <p>
     * To execute negative jump and generate relevant byte code.
     * </p>
     * <p>
     * For example : the operator {@code '1 == 1'} then the negative jump is {@code if_icmpne}.
     * the operator {@code 'if(isTrue()){ ... }'}(here we suppose the method {@code isTrue})
     * return boolean value, then the positive jump is {@code ifeq}
     * </p>
     * @param from
     * @param posLbl
     * @param negLbl
     */
    void jumpNegative(InternalParameterized from, Label posLbl, Label negLbl);
    
}
