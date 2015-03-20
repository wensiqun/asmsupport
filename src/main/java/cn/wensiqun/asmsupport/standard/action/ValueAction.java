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

import cn.wensiqun.asmsupport.core.clazz.AClass;
import cn.wensiqun.asmsupport.core.definition.value.Value;


/**
 * Get basic val.
 *
 * @author wensiqun(at)163.com
 */
public interface ValueAction {
    

    /**
     * Get {@code int} val
     * 
     * @param val
     * @return
     */
    Value val(Integer val);

    /**
     * Get {@code short} val
     * 
     * @param val
     * @return
     */
    Value val(Short val);

    /**
     * Get {@code byte} val
     * 
     * @param val
     * @return
     */
    Value val(Byte val);

    /**
     * Get {@code boolean} val
     * 
     * @param val
     * @return
     */
    Value val(Boolean val);

    /**
     * Get {@code long} val
     * 
     * @param val
     * @return
     */
    Value val(Long val);

    /**
     * Get {@code double} val
     * 
     * @param val
     * @return
     */
    Value val(Double val);

    /**
     * Get {@code char} val
     * 
     * @param val
     * @return
     */
    Value val(Character val);

    /**
     * Get {@code float} val
     * 
     * @param val
     * @return
     */
    Value val(Float val);

    /**
     * Get {@code class} val
     * 
     * @param val
     * @return
     */
    Value val(AClass val);

    /**
     * Get {@code class} val
     * 
     * @param val
     * @return
     */
    Value val(Class<?> val);

    /**
     * Get {@code String} val
     * 
     * @param val
     * @return
     */
    Value val(String val);

    /**
     * Get {@code null} val
     * 
     * @param val
     * @return
     */
    Value _null(AClass type);

    /**
     * Get {@code null} val
     * 
     * @param val
     * @return
     */
    Value _null(Class<?> type);
}
