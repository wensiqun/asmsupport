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

import cn.wensiqun.asmsupport.standard.def.IParameterized;


/**
 * 生成关系运算操作
 * 
 * @author wensiqun(at)163.com
 *
 */
public interface RelationalAction<_P extends IParameterized> {
    
    
    /**
     * 
     * The greater than operator
     * 
     * <p style="border:1px solid;width:300px;padding:10px;">
     * <b style="color:#FF3300">factor1 > factor2;</b>
     * </p>
     * 
     * 
     * @param factor1
     * @param factor2
     * @return
     */
    _P gt(_P factor1, _P factor2);
    
    /**
     * 
     * The greater equals operator.
     * 
     * <p style="border:1px solid;width:300px;padding:10px;">
     * <b style="color:#FF3300">factor1 >= factor2;</b>
     * </p>
     * 
     * @param factor1
     * @param factor2
     * @return
     */
    _P ge(_P factor1, _P factor2);
    
    /**
     * The less than equals.
     * 
     * <p style="border:1px solid;width:300px;padding:10px;">
     * <b style="color:#FF3300">factor1 < factor2;</b>
     * </p>
     * 
     * @param factor1
     * @param factor2
     * @return
     */
    _P lt(_P factor1, _P factor2);

    /**
     * 
     * The less equals operator.
     * 
     * <p style="border:1px solid;width:300px;padding:10px;">
     * <b style="color:#FF3300">factor1 <= factor2;</b>
     * </p>
     * 
     * @param factor1
     * @param factor2
     * @return
     */
    _P le(_P factor1, _P factor2);
    
    /**
     * 
     * The equal operator.
     * 
     * <p style="border:1px solid;width:300px;padding:10px;">
     * <b style="color:#FF3300">factor1 == factor2;</b>
     * </p>
     * 
     * @param factor1
     * @param factor2
     * @return
     */
    _P eq(_P factor1, _P factor2);

    /**
     * 
     * The not equal operator.
     * 
     * <p style="border:1px solid;width:300px;padding:10px;">
     * <b style="color:#FF3300">factor1 != factor2;</b>
     * </p>
     * 
     * @param factor1
     * @param factor2
     * @return
     */
    _P ne(_P factor1, _P factor2);
}
