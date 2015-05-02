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
import cn.wensiqun.asmsupport.standard.def.clazz.AClass;


/**
 * The array action set.
 *
 *
 * @author wensiqun(at)163.com
 */
public interface ArrayAction<_P extends IParam> {
    

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
     * newArray(AClassFactory.getArrayClass(String[][][].class), val(2), val(3), val(4));<br>
     * </p>
	 * 空间
	 * @param arraytype array value type. must be an array type
	 * @param allocateDims length of each dimensions
	 * @return {@link _P}
	 */
	_P makeArray(final AClass arraytype, final _P... allocateDims);
	
	/**
	 * Same to {@link #makeArray(AClass, _P...)}
	 * 
	 * 
	 * @param arraytype
	 * @param dimensions
	 * @return {@link _P}
	 */
	_P makeArray(Class<?> arraytype, final _P... dimensions);

	/**
	 * 
     * <p>
     * Make an array value according an array type and an array value that element type is 
     * {@link cn.wensiqun.asmsupport.standard.def.IParam}, call this method is seem like to 
     * following java code :
     * </p>
     * 
     * <p style="border:1px solid;width:550px;padding:10px;">
     * <b style="color:#FF3300">new String[][]{{"00", "01"}, {"10", "11"}}</b>;
     * </p>
     * 
     * Following code is the implement of preceding code :
     * <pre style="border:1px solid;width:550px;padding:10px;">
     * {@link _P}[][] values = {@link _P}[][]{
     *     {val("00"), val("01")},
     *     {val("10"), val("11")}
     * };
     * newarray(AClassFactory.getType(String[][].class), values);
     * </pre>
	 * 
	 * @param arraytype array type you want to make. must be an array type
	 * @param arrayobject the array value that you want to assign.
	 * @return {@link _P}
	 * @see ActionSet#arrayvar(String, AClass, _P, _P...)
	 * @see #makeArray(AClass, _P...)
	 */
	_P newarray(AClass arraytype, final Object arrayobject);
	
	/**
	 * It's seem like {@link #newarray(AClass, Object)}
	 * 
	 * @param type
	 * @param arrayObject
	 * @return {@link _P}
	 */
	_P newarray(Class<?> type, Object arrayObject);
    
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
     * _arrayLoad(values, val(0), val(1));<br>
     * </p>
     * 
     * 
     * @param arrayReference the array value reference.
     * @param pardim the first dimension index.
     * @param parDims other dimensions index array.
     * @return {@link _P}
     */
    _P arrayLoad(_P arrayReference, _P pardim, _P... parDims);
    

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
     * _arrayStore(arrayValues, val(100), val(0), val(1))
     * </p>
     * 
     * @param arrayReference array reference
     * @param value the value it's you want save to array.
     * @param dim the first dimension index
     * @param dims the other dimensions indexs
     * @return {@link _P}
     */
    _P arrayStore(_P arrayReference, _P value, _P dim, _P... dims);
    
    
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
     * _arrayLength(values, val(0))
     * </p>
     * 
     * 
     * @param arrayReference array reference
     * @param dims the dimensions index array that corresponding to each dimension
     * @return {@link _P}
     */
    _P arrayLength(_P arrayReference, _P... dims);
    

}
