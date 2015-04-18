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
/**
 * 
 */
package cn.wensiqun.asmsupport.core;

import cn.wensiqun.asmsupport.standard.Parameterized;


/**
 * This interface indicate it can be assign to a variable or method as parameter
 * 
 * @author 温斯群(Joe Wen)
 * 
 */
public interface InternalParameterized extends Parameterized, PushStackable {
    
    /**
     * 
     * <p>如果当前操作或者变量被其他操作所引用，那么就需要调用当前的这个操作对象或者变量的asArgument方法.</p>
     * <p>大多数情况下，重写这个方法是为了将当前操作从栈中移除。</p>
     */
    public void asArgument();
}
