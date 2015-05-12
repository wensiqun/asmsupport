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
 * Generate relational operator.
 * 
 * @author wensiqun(at)163.com
 *
 */
public interface RelationalAction<_P extends IParam> {
    
    
    /**
     * 
     * The greater than operator
     * 
     * <p style="border:1px solid;width:300px;padding:10px;">
     * <b style="color:#FF3300">leftFactor > rightFactor;</b>
     * </p>
     * 
     * 
     * @param leftFactor
     * @param rightFactor
     * @return {@link _P}
     */
    _P gt(_P leftFactor, _P rightFactor);
    
    /**
     * 
     * The greater equals operator.
     * 
     * <p style="border:1px solid;width:300px;padding:10px;">
     * <b style="color:#FF3300">leftFactor >= rightFactor;</b>
     * </p>
     * 
     * @param leftFactor
     * @param rightFactor
     * @return {@link _P} 
     */
    _P ge(_P leftFactor, _P rightFactor);
    
    /**
     * The less than equals.
     * 
     * <p style="border:1px solid;width:300px;padding:10px;">
     * <b style="color:#FF3300">leftFactor < rightFactor;</b>
     * </p>
     * 
     * @param leftFactor
     * @param rightFactor
     * @return {@link _P}
     */
    _P lt(_P leftFactor, _P rightFactor);

    /**
     * 
     * The less equals operator.
     * 
     * <p style="border:1px solid;width:300px;padding:10px;">
     * <b style="color:#FF3300">leftFactor <= rightFactor;</b>
     * </p>
     * 
     * @param leftFactor
     * @param rightFactor
     * @return {@link _P}
     */
    _P le(_P leftFactor, _P rightFactor);
    
    /**
     * 
     * The equal operator.
     * 
     * <p style="border:1px solid;width:300px;padding:10px;">
     * <b style="color:#FF3300">leftFactor == rightFactor;</b>
     * </p>
     * 
     * @param leftFactor
     * @param rightFactor
     * @return {@link _P}
     */
    _P eq(_P leftFactor, _P rightFactor);

    /**
     * 
     * The not equal operator.
     * 
     * <p style="border:1px solid;width:300px;padding:10px;">
     * <b style="color:#FF3300">leftFactor != rightFactor;</b>
     * </p>
     * 
     * @param leftFactor
     * @param rightFactor
     * @return {@link _P}
     */
    _P ne(_P leftFactor, _P rightFactor);
}
