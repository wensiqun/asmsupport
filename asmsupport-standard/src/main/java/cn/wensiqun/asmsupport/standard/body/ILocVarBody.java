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
package cn.wensiqun.asmsupport.standard.body;

import cn.wensiqun.asmsupport.standard.def.var.ILocVar;

/**
 * Indicate a program block, it's will be passes an local variable (is the sub type of
 * {@link ILocVar}) when enter the block.
 * 
 * @author sqwen
 *
 * @param <_Var>
 */
public interface ILocVarBody<_Var extends ILocVar> extends IBody {
    
    /**
     * enter a program block with a local variable.
     * 
     * @param e
     */
    void body(_Var e);
}
