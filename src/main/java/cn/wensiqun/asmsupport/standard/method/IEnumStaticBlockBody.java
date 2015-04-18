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
package cn.wensiqun.asmsupport.standard.method;

import cn.wensiqun.asmsupport.core.InternalParameterized;
import cn.wensiqun.asmsupport.standard.body.LocalVariablesBody;

public interface IEnumStaticBlockBody extends LocalVariablesBody {

    /**
     * 
     * @param name
     * @param argus
     */
	void constructEnumConst(String name, InternalParameterized... argus);
	
	/**
	 * <p>
	 * call {@link #constructEnumConst} method at this method.
	 * get some information about current enum type constructor.
	 * </p>
	 * <p>
	 * 在此方法中调用{@link #constructEnumConst}方法， 获取构造枚举列表中每个枚举类型需要的参数信息，枚举类型名等信息。
	 * </p>
	 */
	void constructEnumConsts();
	
}
