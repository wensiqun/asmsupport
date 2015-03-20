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

import cn.wensiqun.asmsupport.core.Parameterized;
import cn.wensiqun.asmsupport.core.operator.numerical.relational.Equal;
import cn.wensiqun.asmsupport.core.operator.numerical.relational.GreaterEqual;
import cn.wensiqun.asmsupport.core.operator.numerical.relational.GreaterThan;
import cn.wensiqun.asmsupport.core.operator.numerical.relational.LessEqual;
import cn.wensiqun.asmsupport.core.operator.numerical.relational.LessThan;
import cn.wensiqun.asmsupport.core.operator.numerical.relational.NotEqual;


/**
 * 生成关系运算操作
 * 
 * @author wensiqun(at)163.com
 *
 */
public interface RelationalAction {
    
    
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
    public GreaterThan gt(Parameterized factor1, Parameterized factor2);
    
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
    public GreaterEqual ge(Parameterized factor1, Parameterized factor2);
    
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
    public LessThan lt(Parameterized factor1, Parameterized factor2);

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
    public LessEqual le(Parameterized factor1, Parameterized factor2);
    
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
    public Equal eq(Parameterized factor1, Parameterized factor2);

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
    public NotEqual ne(Parameterized factor1, Parameterized factor2);
}
