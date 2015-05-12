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
 * Logical operator
 * 
 * @author wensiqun(at)163.com
 *
 */
public interface LogicalAction<_P extends IParam> {
    
    /**
     * Generate seem like following code :
     * 
     * <p style="border:1px solid;width:300px;padding:10px;">
     * <b style="color:#FF3300">leftFactor & rightFactor;</b>
     * </p>
     * 
     * @param leftFactor
     * @param rightFactor
     * @return {@link _P}
     */
    _P logicalAnd(_P leftFactor, _P rightFactor);
    
    /**
     * Generate seem like following code :
     * 
     * <p style="border:1px solid;width:300px;padding:10px;">
     * <b style="color:#FF3300">leftFactor | rightFactor;</b>
     * </p>
     * 
     *  
     * @param leftFactor
     * @param rightFactor
     * @return {@link _P}
     */
    _P logicalOr(_P leftFactor, _P rightFactor);

    /**
     * 
     * Generate seem like following code :
     * 
     * <p style="border:1px solid;width:300px;padding:10px;">
     * <b style="color:#FF3300">leftFactor ^ rightFactor;</b>
     * </p>
     *  
     * @param leftFactor
     * @param rightFactor
     * @return {@link _P}
     */
    _P logicalXor(_P leftFactor, _P rightFactor);
    
    /**
     * 
     * Generate seem like following code :
     * 
     * <p style="border:1px solid;width:300px;padding:10px;">
     * <b style="color:#FF3300">leftFactor && rightFactor;</b>
     * </p>
     * 
     * 
     * @param leftFactor
     * @param rightFactor
     * @return {@link _P}
     */
    _P and(_P leftFactor, _P rightFactor, _P... otherFactors);
    
    /**
     * 
     * Generate seem like following code :
     * 
     * <p style="border:1px solid;width:300px;padding:10px;">
     * <b style="color:#FF3300">leftFactor || rightFactor;</b>
     * </p>
     * 
     * @param leftFactor
     * @param rightFactor
     * @return {@link }
     */
    _P or(_P leftFactor, _P rightFactor, _P... otherFactors);
    
    /**
     * Generate seem like following code :
     * 
     * <p style="border:1px solid;width:300px;padding:10px;">
     * <b style="color:#FF3300">!factor;</b>
     * </p>
     * 
     * 
     * @param factor
     * @return {@link _P}
     */
    _P no(_P factor);
}
