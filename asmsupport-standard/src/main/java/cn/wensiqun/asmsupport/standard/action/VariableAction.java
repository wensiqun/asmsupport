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
import cn.wensiqun.asmsupport.standard.def.var.IVar;

/**
 * 
 * All variable operations. such as assign, create new variable.
 * 
 * @author sqwen
 *
 * @param <_P> The parameterized generic type.
 * @param <_V>      The Local Variable generic type
 */
public interface VariableAction<_P extends IParam, _V extends IVar> {

    /**
     * 
     * Get {@code this} keyword as a variable, if current is a static method, will throw
     * exception while call this method.
     * 
     * @return {@link _V}
     * @see #super_()
     */
    _V this_();
    
    /**
     * Get global variable of current class according the passed name. 
     * this method is equivalence to : 
     * <pre>
     *     _this().getGlobalVariable(name);
     * <pre>
     * @param name
     * @return {@link _V}
     */
    _V this_(String name);
    
    /**
     * Get {@code super} keyword as a variable, if current is a static method, will throw
     * exception while call this method.
     * 
     * @return {@link _V}
     * @see #this_()
     */
    _V super_();
    
    /**
     * Create a local variable with anonymous
     * @param type
     * @param para
     * @return {@link _V} 
     */
    _V var(Class<?> type, _P para);

    /**
     * Create a local variable with anonymous, this method equivalent to following code :
     * <p>
     * var("", type, true, para)
     * </p>
     * @param type
     * @param para
     * @return {@link _V}
     */
    _V var(IClass type, _P para);

    /**
     * Create a local variable
     * @param name
     * @param type
     * @param para
     * @return {@link _V}
     */
    _V var(String name, Class<?> type, _P para);
    
    /**
     * Create a local variable, this method equivalent to following code :
     * <p>
     * var(name, type, false, para)
     * </p>
     * @param name
     * @param type
     * @param para
     * @return {@link _V}
     */
    _V var(String name, IClass type, _P para);
    

    /**
     * Get field from current object. the method is same to call following :
     * 
     * <pre>
     * this_().field(name);
     * </pre>
     * 
     * @param name
     * @return {@link _V}
     */
    _V field(String name);
    
	/**
	 * assign a value to a variable. for exampel:
	 * java code:<br/>
	 * <pre>
	 * i = 10;
	 * </pre>
	 * asmsupport code:<br/>
	 * <pre>
	 * assign(i, getValue(10));
	 * </pre>
	 * @param variable
	 * @param val
	 * @return {@link _P}
	 */
	_P assign(_V variable, _P val);
	
}
