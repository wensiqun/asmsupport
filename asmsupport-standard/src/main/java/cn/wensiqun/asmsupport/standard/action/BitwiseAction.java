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

import cn.wensiqun.asmsupport.standard.def.IParam;


/**
 * 
 * Bit operations
 * 
 * @author wensiqun(at)163.com
 */
public interface BitwiseAction<_P extends IParam> {
	
    /**
     * 
     * <p style="border:1px solid;width:300px;padding:10px;">
     * <b style="color:#FF3300">～leftFactor</b>
     * </p>
     * 
     * @param factor
     * @return {@link _P}
     */
    _P reverse(_P factor);
    
    /**
     * The bit and operator.
     * 
     * <p style="border:1px solid;width:300px;padding:10px;">
     * <b style="color:#FF3300">leftFactor & rightFactor</b>
     * </p>
     * 
     * @param leftFactor
     * @param rightFactor
     * @return {@link _P}
     */
    _P band(_P leftFactor, _P rightFactor);
	
    /**
     * 
     * The bit or operator, the following code is the sample :
     * 
     * <p style="border:1px solid;width:300px;padding:10px;">
     * <b style="color:#FF3300">leftFactor | rightFactor</b>
     * </p>
     * 
     * @param leftFactor
     * @param rightFactor
     * @return {@link _P}
     */
    _P bor(_P leftFactor, _P rightFactor);
    
    /**
     * 
     * The xor operator, the following code is the sample.
     * 
     * <p style="border:1px solid;width:300px;padding:10px;">
     * <b style="color:#FF3300">leftFactor ^ rightFactor</b>
     * </p>
     * 
     * 
     * @param leftFactor
     * @param rightFactor
     * @return {@link _P}
     */
    _P bxor(_P leftFactor, _P rightFactor);
    
    /**
     * 
     * The shift left operator.
     * 
     * <p style="border:1px solid;width:300px;padding:10px;">
     * <b style="color:#FF3300">leftFactor << rightFactor</b>
     * </p>
     * 
     * @param leftFactor
     * @param rightFactor
     * @return {@link _P}
     */
    _P shl(_P leftFactor, _P rightFactor);
    
    /**
     * The bitwise shift right operator.
     * 
     * <p style="border:1px solid;width:300px;padding:10px;">
     * <b style="color:#FF3300">leftFactor >> rightFactor</b>
     * </p>
     * 
     * @param leftFactor
     * @param rightFactor
     * @return {@link _P}
     */
    _P shr(_P leftFactor, _P rightFactor);
    
    /**
     * The unsigned shift right operator.
     * 
     * <p style="border:1px solid;width:300px;padding:10px;">
     * <b style="color:#FF3300">leftFactor >>> rightFactor</b>
     * </p>
     * 
     * @param leftFactor
     * @param rightFactor
     * @return {@link _P}
     */
    _P ushr(_P leftFactor, _P rightFactor);
    
}
