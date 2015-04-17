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
import cn.wensiqun.asmsupport.core.operator.array.ArrayLength;
import cn.wensiqun.asmsupport.core.operator.array.ArrayLoader;
import cn.wensiqun.asmsupport.core.operator.array.ArrayStorer;
import cn.wensiqun.asmsupport.core.operator.array.ArrayValue;


/**
 * The array action set.
 *
 *
 * @author wensiqun(at)163.com
 */
public interface ArrayAction {
    

	/**
     * <p>
     * Make an empty array value according an array type and the length of each dimensions call this method is seem like to 
     * following java code :
     * </p>
     * 
     * 
     * <p style="border:1px solid;width:550px;padding:10px;">
     * <b style="color:#FF3300">new String[2][3][4]</b>;
     * </p>
     * 
     * Following code is the implement of preceding code :
     * <p style="border:1px solid;width:550px;padding:10px;">
     * newArray(AClassFactory.getArrayClass(String[][][].class), Value.value(2), Value.value(3), Value.value(4));<br>
     * </p>
	 * 空间
	 * @param arraytype array value type. must be {@link ArrayClass}
	 * @param allocateDims length of each dimensions
	 * @return {@link ArrayValue}
	 */
	public ArrayValue makeArray(final AClass arraytype, final Parameterized... allocateDims);
	
	/**
	 * Same to {@link #makeArray(AClass, Parameterized...)}
	 * 
	 * 
	 * @param arraytype
	 * @param dimensions
	 * @return
	 */
	public ArrayValue makeArray(Class<?> arraytype, final Parameterized... dimensions);

	/**
	 * 
     * <p>
     * Make an array value according an array type and an array value that element type is 
     * {@link cn.wensiqun.asmsupport.core.Parameterized}, call this method is seem like to 
     * following java code :
     * </p>
     * 
     * <p style="border:1px solid;width:550px;padding:10px;">
     * <b style="color:#FF3300">new String[][]{{"00", "01"}, {"10", "11"}}</b>;
     * </p>
     * 
     * Following code is the implement of preceding code :
     * <pre style="border:1px solid;width:550px;padding:10px;">
     * Parameterized[][] values = new Parameterized[][]{
     *     {Value.value("00"), Value.value("01")},
     *     {Value.value("10"), Value.value("11")}
     * };
     * newArrayWithValue(AClassFactory.getArrayClass(String[][].class), values);
     * </pre>
	 * 
	 * @param arraytype array type you want to make. must be {@link ArrayClass}
	 * @param arrayobject the array value that you want to assign.
	 * @return {@link ArrayValue}
	 * @see ActionSet#arrayvar(String, AClass, Parameterized, Parameterized...)
	 * @see #makeArray(AClass, Parameterized...)
	 */
	public ArrayValue newarray(AClass arraytype, final Object arrayobject);
	
	/**
	 * 
	 * @param type
	 * @param arrayObject
	 * @return
	 */
	public ArrayValue newarray(Class<?> type, Object arrayObject);
    
    /**
     * 
     * <p>
     * Get array element according arguments. call this method is seem like to 
     * following java code :
     * </p>
     * 
     * <p style="border:1px solid;width:400px;padding:10px;">
     * String value = <b style="color:#FF3300">{{"[0][0]","[0][1]"},{"[1][0]","[1][1]"}}[0][1]</b>;
     * </p>
     * 
     * Following code is the implement of preceding code :
     * <p style="border:1px solid;width:400px;padding:10px;">
     * _arrayLoad(values, Value.value(0), Value.value(1));<br>
     * </p>
     * 
     * 
     * @param arrayReference the array value reference.
     * @param pardim the first dimension index.
     * @param parDims other dimensions index array.
     * @return {@link ArrayLoader}
     */
    public ArrayLoader arrayLoad(Parameterized arrayReference, Parameterized pardim, Parameterized... parDims);
    

    /**
     * 
     * <p>
     * Set element value to array according argument. call this method is seem like to 
     * following java code :
     * </p>
     * 
     * <p style="border:1px solid;width:400px;padding:10px;">
     * <b style="color:#FF3300">{{"[0][0]","[0][1]"},{"[1][0]","[1][1]"}}[0][1]=100</b>
     * </p>
     * 
     * Following code is the implement of preceding code :
     * <p style="border:1px solid;width:400px;padding:10px;">
     * _arrayStore(arrayValues, Value.value(100), Value.value(0), Value.value(1))
     * </p>
     * 
     * @param arrayReference array reference
     * @param value the value it's you want save to array.
     * @param dim the first dimension index
     * @param dims the other dimensions indexs
     * @return {@link ArrayStorer}
     */
    public ArrayStorer arrayStore(Parameterized arrayReference, Parameterized value, Parameterized dim, Parameterized... dims);
    
    
    /**
     * Get array length, call this method is seem like to 
     * following java code : 
     * 
     * <p style="border:1px solid;width:300px;padding:10px;">
     * <b style="color:#FF3300">{{"[0][0]","[0][1]"},{"[1][0]","[1][1]"}}[0].length</b>;
     * </p>
     * 
     * Following code is the implement of preceding code :
     * <p style="border:1px solid;width:300px;padding:10px;">
     * _arrayLength(values, Value.value(0))
     * </p>
     * 
     * 
     * @param arrayReference array reference
     * @param dims the dimensions index array that corresponding to each dimension
     * @return {@link ArrayLength}
     */
    public ArrayLength arrayLength(Parameterized arrayReference, Parameterized... dims);
    

}
