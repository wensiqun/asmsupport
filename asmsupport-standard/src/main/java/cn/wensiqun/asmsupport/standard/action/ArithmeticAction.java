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
     * Generate addition instructions such as : factor1 + factor2, seem like following
     * 
     * <p style="border:1px solid;width:300px;padding:10px;">
     * int count = <b style="color:#FF3300">factor1 + factor2;</b>
     * </p>
     * 
     * @param factor1
     * @param factor2
     * @return {@link _P}
     */
    _P add(_P factor1, _P factor2);

    /**
     * Generate substruction instructions such as : factor1 - factor2, seem like following
     * 
     * <p style="border:1px solid;width:300px;padding:10px;">
     * int count = <b style="color:#FF3300">factor1 - factor12;</b>
     * </p>
     * 
     * @param factor1
     * @param factor2
     * @return {@link _P}
     */
    _P sub(_P factor1, _P factor2);
    
    /**
     * Generate multiplication instructions such as : factor1 * factor2, seem like following
     * 
     * <p style="border:1px solid;width:300px;padding:10px;">
     * int count = <b style="color:#FF3300">factor1 * factor12;</b>
     * </p>
     * 
     * @param factor1
     * @param factor2
     * @return {@link _P}
     */
    _P mul(_P factor1, _P factor2);
    
    /**
     * generate division instruction : factor1 / factor2
     * 
     * <p style="border:1px solid;width:300px;padding:10px;">
     * int count = <b style="color:#FF3300">factor1 / factor12;</b>
     * </p>
     * 
     * @param factor1
     * @param factor2
     * @return {@link _P}
     */
    _P div(_P factor1, _P factor2);
    
    /**
     * 
     * generate mod instruction : factor1 % factor2, seem like following
     * 
     * <p style="border:1px solid;width:300px;padding:10px;">
     * int count = <b style="color:#FF3300">factor1 % factor12;</b>
     * </p>
     * 
     * 
     * @param factor1
     * @param factor2
     * @return {@link _P}
     */
    _P mod(_P factor1, _P factor2);
    
}
