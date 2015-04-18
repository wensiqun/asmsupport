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

import cn.wensiqun.asmsupport.core.InternalParameterized;
import cn.wensiqun.asmsupport.core.operator.numerical.bit.BitAnd;
import cn.wensiqun.asmsupport.core.operator.numerical.bit.BitOr;
import cn.wensiqun.asmsupport.core.operator.numerical.bit.BitXor;
import cn.wensiqun.asmsupport.core.operator.numerical.bit.Reverse;
import cn.wensiqun.asmsupport.core.operator.numerical.bit.ShiftLeft;
import cn.wensiqun.asmsupport.core.operator.numerical.bit.ShiftRight;
import cn.wensiqun.asmsupport.core.operator.numerical.bit.UnsignedShiftRight;


/**
 * 
 * 位操作
 * 
 * @author wensiqun(at)163.com
 */
public interface BitwiseAction {
	
    /**
     * 
     * <p style="border:1px solid;width:300px;padding:10px;">
     * <b style="color:#FF3300">～factor1</b>
     * </p>
     * 
     * @param factor
     * @return {@link Reverse}
     */
    public Reverse reverse(InternalParameterized factor);
    
    /**
     * The bit and operator.
     * 
     * <p style="border:1px solid;width:300px;padding:10px;">
     * <b style="color:#FF3300">factor1 & factor2</b>
     * </p>
     * 
     * @param factor1
     * @param factor2
     * @return {@link BitAnd}
     */
    public BitAnd band(InternalParameterized factor1, InternalParameterized factor2);
	
    /**
     * 
     * The bit or operator, the following code is the sample :
     * 
     * <p style="border:1px solid;width:300px;padding:10px;">
     * <b style="color:#FF3300">factor1 | factor2</b>
     * </p>
     * 
     * @param factor1
     * @param factor2
     * @return {@link BitOr}
     */
    public BitOr bor(InternalParameterized factor1, InternalParameterized factor2);
    
    /**
     * 
     * The xor operator, the following code is the sample.
     * 
     * <p style="border:1px solid;width:300px;padding:10px;">
     * <b style="color:#FF3300">factor1 ^ factor2</b>
     * </p>
     * 
     * 
     * @param factor1
     * @param factor2
     * @return {@link BitXor}
     */
    public BitXor bxor(InternalParameterized factor1, InternalParameterized factor2);
    
    /**
     * 
     * The shift left operator.
     * 
     * <p style="border:1px solid;width:300px;padding:10px;">
     * <b style="color:#FF3300">factor1 << factor2</b>
     * </p>
     * 
     * @param factor1
     * @param factor2
     * @return {@link ShiftLeft}
     */
    public ShiftLeft shl(InternalParameterized factor1, InternalParameterized factor2);
    
    /**
     * The bitwise shift right operator.
     * 
     * <p style="border:1px solid;width:300px;padding:10px;">
     * <b style="color:#FF3300">factor1 >> factor2</b>
     * </p>
     * 
     * @param factor1
     * @param factor2
     * @return {@link ShiftRight}
     */
    public ShiftRight shr(InternalParameterized factor1, InternalParameterized factor2);
    
    /**
     * The unsigned shift right operator.
     * 
     * <p style="border:1px solid;width:300px;padding:10px;">
     * <b style="color:#FF3300">factor1 >>> factor2</b>
     * </p>
     * 
     * @param factor1
     * @param factor2
     * @return {@link UnsignedShiftRight}
     */
    public UnsignedShiftRight ushr(InternalParameterized factor1, InternalParameterized factor2);
    
}
