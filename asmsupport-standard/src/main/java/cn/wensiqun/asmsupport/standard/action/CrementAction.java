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
 * 创建自增或自减操作
 * 
 * @author wensiqun(at)163.com
 *
 */
public interface CrementAction<_P extends IParam> {
    
    /**
     * 生成类似--i操作指令
     * 
     * @param crement
     * @return {@link _P}
     */
    _P predec(_P crement);
    
    /**
     * 生成类似i--操作指令
     * 
     * @param crement
     * @return {@link PreposeIncrement}
     */
    _P postdec(_P crement);
    
    /**
     * 生成类似++i操作指令
     * 
     * @param crement
     * @return {@link PreposeIncrement}
     */
    _P preinc(_P crement);
    
    /**
     * 生成类似i++操作指令
     * 
     * @param crement
     * @return {@link PostposeIncrement}
     */
    _P postinc(_P crement);
    
}

