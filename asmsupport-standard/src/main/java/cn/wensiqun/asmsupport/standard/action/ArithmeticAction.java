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
 * Indicate arithmetic action here
 *
 * @author wensiqun(at)163.com
 */
public interface ArithmeticAction<_P extends IParam> {
    
    /**
     * Generate addition instructions such as : leftFactor + rightFactor, seem like following
     * 
     * <p style="border:1px solid;width:300px;padding:10px;">
     * int count = <b style="color:#FF3300">leftFactor + rightFactor;</b>
     * </p>
     * 
     * @param leftFactor
     * @param rightFactor
     * @return {@link _P}
     */
    _P add(_P leftFactor, _P rightFactor);

    /**
     * Generate substruction instructions such as : leftFactor - rightFactor, seem like following
     * 
     * <p style="border:1px solid;width:300px;padding:10px;">
     * int count = <b style="color:#FF3300">leftFactor - leftrightFactor;</b>
     * </p>
     * 
     * @param leftFactor
     * @param rightFactor
     * @return {@link _P}
     */
    _P sub(_P leftFactor, _P rightFactor);
    
    /**
     * Generate multiplication instructions such as : leftFactor * rightFactor, seem like following
     * 
     * <p style="border:1px solid;width:300px;padding:10px;">
     * int count = <b style="color:#FF3300">leftFactor * leftrightFactor;</b>
     * </p>
     * 
     * @param leftFactor
     * @param rightFactor
     * @return {@link _P}
     */
    _P mul(_P leftFactor, _P rightFactor);
    
    /**
     * generate division instruction : leftFactor / rightFactor
     * 
     * <p style="border:1px solid;width:300px;padding:10px;">
     * int count = <b style="color:#FF3300">leftFactor / leftrightFactor;</b>
     * </p>
     * 
     * @param leftFactor
     * @param rightFactor
     * @return {@link _P}
     */
    _P div(_P leftFactor, _P rightFactor);
    
    /**
     * 
     * generate mod instruction : leftFactor % rightFactor, seem like following
     * 
     * <p style="border:1px solid;width:300px;padding:10px;">
     * int count = <b style="color:#FF3300">leftFactor % leftrightFactor;</b>
     * </p>
     * 
     * 
     * @param leftFactor
     * @param rightFactor
     * @return {@link _P}
     */
    _P mod(_P leftFactor, _P rightFactor);
    
}
