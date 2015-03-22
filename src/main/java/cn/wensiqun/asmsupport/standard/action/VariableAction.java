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
import cn.wensiqun.asmsupport.core.clazz.AClass;
import cn.wensiqun.asmsupport.core.clazz.ArrayClass;
import cn.wensiqun.asmsupport.core.clazz.AClassFactory;
import cn.wensiqun.asmsupport.core.definition.variable.ExplicitVariable;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.core.operator.assign.Assigner;

/**
 * 变量操作
 *
 */
public interface VariableAction {

    /**
     * create a local variable, this method equivalent to following code :
     * <p>
     * _var(name, {@link AClassFactory#defType(type)}, false, para)
     * </p>
     * @param name
     * @param type
     * @param para
     * @return
     */
    public LocalVariable var(final String name, final Class<?> type, final Parameterized para);

    /**
     * create a local variable, this method equivalent to following code :
     * <p>
     * _var("", {@link AClassFactory#defType(type)}, true, para)
     * </p>
     * @param type
     * @param para
     * @return
     */
    public LocalVariable var(final Class<?> type, final Parameterized para);
    


    /**
     * create a local variable, this method equivalent to following code :
     * <p>
     * _var(name, type, false, para)
     * </p>
     * @param name
     * @param type
     * @param para
     * @return
     */
    public LocalVariable var(final String name, final AClass type, final Parameterized para);

    /**
     * create a local variable, this method equivalent to following code :
     * <p>
     * _var("", type, true, para)
     * </p>
     * @param type
     * @param para
     * @return
     */
    public LocalVariable var(final AClass type, final Parameterized para);
    
    /**
	 * create a local variable
	 * 
	 * @param name variable name.
	 * @param aClass variable type.
	 * @param anonymous true will not put the variable to "local variable table". in other world, the name will be invalid.
	 * @param para this variable initial value, set to null if you want the initial is null.
	 * @return the LocalVariable
	 */
	public LocalVariable var(final String name, final AClass aClass, boolean anonymous, final Parameterized para);
	
	/**
	 * 
	 * <p>创建数组变量，可分配数组空间大小</p>
	 * 
	 * <pre>
	 * arrayvar2dim("array", AClassFactory.deftype(String[][].class), false, null) --> String[][] array = null;
	 * arrayvar2dim("array", AClassFactory.deftype(String[][].class), false, Value.value(3)) --> String[][] array = new String[3][];
	 * arrayvar2dim("array", AClassFactory.deftype(String[][].class), false, Value.value(3), Value.value(2)) --> String[][] array = new String[3][2];
	 * <pre>
	 * 
	 * @param name variable name
	 * @param type type
	 * @param anonymous 是否匿名
	 * @param dim 预分配的数组空间
	 * @return
	 * @see #arrayvar2dim(ArrayClass, Parameterized...)
	 * @see #arrayvar2dim(Class, Parameterized...)
	 * @see #arrayvar2dim(String, ArrayClass, Parameterized...)
	 * @see #arrayvar2dim(String, Class, Parameterized...)
	 */
	@Deprecated
	public LocalVariable arrayvar2dim(final String name, final ArrayClass type, boolean anonymous, Parameterized... dim);

	/**
	 * Create an array variable with specify dimension.
	 * 
	 * @param name variable name.
	 * @param type variable type.
	 * @param dims the dimension.
	 * @return
	 */
	public LocalVariable arrayvar2dim(final String name, final ArrayClass type, Parameterized... dims);

	/**
	 * Create an array variable with specify dimension.
	 * 
	 * @param name array name
	 * @param type the array type, must be an array class
	 * @param dims
	 * @return
	 */
	public LocalVariable arrayvar2dim(final String name, Class<?> type, Parameterized... dims);
	
	/**
	 * Create an anonymous array variable with specify dimension.
	 * 
	 * @param type variable type.
	 * @param dims the dimension.
	 * @return
	 */
	public LocalVariable arrayvar2dim(ArrayClass type, Parameterized... dims);
	
	/**
	 * Create an anonymous array variable with specify dimension.
	 * 
	 * @param type variable type, must be an array class.
	 * @param dims the dimension.
	 * @return
	 */
	public LocalVariable arrayvar2dim(Class<?> arrayType, Parameterized... dims);

	/**
	 * Create an array variable with specify value reference.
	 * 
	 * @param name variable name. this argument available only argument anonymous is true
	 * @param type the variable type
	 * @param anonymous whether or not anonymous
	 * @param value the variable value.
	 * @return
	 */
	@Deprecated
	public LocalVariable arrayvar(final String name, final ArrayClass type, boolean anonymous, final Parameterized value);
	
	/**
	 * Create an array variable with specify value reference.
	 * 
	 * @param name
	 * @param type
	 * @param value
	 * @return
	 */
	public LocalVariable arrayvar(String name, ArrayClass type, Parameterized value);
	
	/**
	 * Create an array variable with specify value reference.
	 * 
	 * @param name
	 * @param type
	 * @param value
	 * @return
	 */
	public LocalVariable arrayvar(String name, Class<?> type, Parameterized value);

	
	/**
	 * Create an anonymous array variable with specify value reference.
	 * 
	 * @param name
	 * @param type
	 * @param value
	 * @return
	 */
	public LocalVariable arrayvar(ArrayClass type, Parameterized value);
	
	/**
	 * Create an anonymous array variable with specify value reference.
	 * 
	 * @param name
	 * @param type
	 * @param value
	 * @return
	 */
	public LocalVariable arrayvar(Class<?> type, Parameterized value);
	
	/**
	 * 
	 * Create an annoymous array variable with multiple value.
	 * 
	 * @param name
	 * @param aClass
	 * @param parameterizedArray
	 * @return
	 */
	public LocalVariable arrayvar(final String name, final ArrayClass aClass, boolean anonymous, final Object parameterizedArray);
	

	
	/**
	 * Create an array variable with specify value reference.
	 * 
	 * @param name
	 * @param type
	 * @param value
	 * @return
	 */
	public LocalVariable arrayvar(String name, ArrayClass type, Object parameterizedArray);
	
	/**
	 * Create an array variable with specify value reference.
	 * 
	 * @param name
	 * @param type
	 * @param value
	 * @return
	 */
	public LocalVariable arrayvar(String name, Class<?> type, Object parameterizedArray);

	
	/**
	 * Create an anonymous array variable with specify value reference.
	 * 
	 * @param name
	 * @param type
	 * @param value
	 * @return
	 */
	public LocalVariable arrayvar(ArrayClass type, Object parameterizedArray);
	
	/**
	 * Create an anonymous array variable with specify value reference.
	 * 
	 * @param name
	 * @param type
	 * @param value
	 * @return
	 */
	public LocalVariable arrayvar(Class<?> type, Object parameterizedArray);
	
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
	 * @return
	 */
	public Assigner assign(ExplicitVariable variable, Parameterized val);
}
