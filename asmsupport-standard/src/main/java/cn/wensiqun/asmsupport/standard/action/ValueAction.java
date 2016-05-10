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
import cn.wensiqun.asmsupport.standard.def.clazz.IClass;



/**
 * Get basic val.
 *
 * @author wensiqun(at)163.com
 */
public interface ValueAction<_P extends IParam> {
    

    /**
     * Get {@code int} val
     * 
     * @param val
     * @return {@link _P}
     */
    _P val(Integer val);

    /**
     * Get {@code short} val
     * 
     * @param val
     * @return {@link _P}
     */
    _P val(Short val);

    /**
     * Get {@code byte} val
     * 
     * @param val
     * @return {@link _P}
     */
    _P val(Byte val);

    /**
     * Get {@code boolean} val
     * 
     * @param val
     * @return {@link _P}
     */
    _P val(Boolean val);

    /**
     * Get {@code long} val
     * 
     * @param val
     * @return {@link _P}
     */
    _P val(Long val);

    /**
     * Get {@code double} val
     * 
     * @param val
     * @return {@link _P}
     */
    _P val(Double val);

    /**
     * Get {@code char} val
     * 
     * @param val
     * @return {@link _P}
     */
    _P val(Character val);

    /**
     * Get {@code float} val
     * 
     * @param val
     * @return {@link _P}
     */
    _P val(Float val);

    /**
     * Get {@code class} val
     * 
     * @param val
     * @return {@link _P}
     */
    _P val(IClass val);

    /**
     * Get {@code class} val
     * 
     * @param val
     * @return {@link _P}
     */
    _P val(Class<?> val);

    /**
     * Get {@code String} val
     * 
     * @param val
     * @return {@link _P}
     */
    _P val(String val);

    /**
     * Get {@code null} val
     * 
     * @param type
     * @return {@link _P}
     */
    _P null_(IClass type);

    /**
     * Get {@code null} val
     * 
     * @param type
     * @return {@link _P}
     */
    _P null_(Class<?> type);
}
