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
     * _var(name, {@link AClassFactory#getProductClass(type)}, false, para)
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
     * _var("", {@link AClassFactory#getProductClass(type)}, true, para)
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
	 * arrayVarWithDimension("array", AClassFactory.getArrayClass(String[][].class), false, null) --> String[][] array = null;
	 * arrayVarWithDimension("array", AClassFactory.getArrayClass(String[][].class), false, Value.value(3)) --> String[][] array = new String[3][];
	 * arrayVarWithDimension("array", AClassFactory.getArrayClass(String[][].class), false, Value.value(3), Value.value(2)) --> String[][] array = new String[3][2];
	 * <pre>
	 * 
	 * @param name   变量名
	 * @param aClass 数组类型
	 * @param anonymous 是否匿名
	 * @param allocateDim 预分配的数组空间
	 * @return
	 */
	public LocalVariable arrayVarWithDimension(final String name, final ArrayClass aClass, boolean anonymous, Parameterized... allocateDim);

	/**
	 * 
	 * @param name
	 * @param aClass
	 * @param value
	 * @return
	 */
	public LocalVariable arrayVar(final String name, final ArrayClass aClass, boolean anonymous, final Parameterized value);
	
	
	/**
	 * 
	 * @param name
	 * @param aClass
	 * @param parameterizedArray
	 * @return
	 */
	public LocalVariable arrayVar(final String name, final ArrayClass aClass, boolean anonymous, final Object parameterizedArray);
	
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
