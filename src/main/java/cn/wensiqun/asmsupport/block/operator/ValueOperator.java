package cn.wensiqun.asmsupport.block.operator;

import cn.wensiqun.asmsupport.Parameterized;
import cn.wensiqun.asmsupport.block.IBlockOperators;
import cn.wensiqun.asmsupport.clazz.ArrayClass;
import cn.wensiqun.asmsupport.operators.array.ArrayValue;

public interface ValueOperator {
	
    /*
     * got null value
     * @param aclass
     * @return
     */
    //public Value nullValue(AClass aclass);
    
    /*
     * get non null value
     * support is primitive type, class type and String type 
     * @param value
     * @return
     */
    //public Value getValue(Object value);
    

	
	/**
	 * @param aClass
	 * @param allocateDims
	 * @return
	 * @see IBlockOperators#createArrayVariable(String, ArrayClass, Parameterized, Parameterized...)
	 */
	public ArrayValue newArray(final ArrayClass aClass, final Parameterized... allocateDims);
	
	/**
	 * execute new array operator, cannot support more than 4 dim array, becuase we not need more than 4 dim array in really business service<br>
	 * for example:<br>
	 * <pre>
	 * java code:<br>
	 * new int[]{2}
	 * </pre>
	 * 
	 * asmsupport code:<br>
	 * <pre>
	 * newArray(AClassGetter.getArrayClass(int[].class), new Parameterized[]{getValue(1)});
	 * </pre>
	 * @param aClass
	 * @param values
	 * @return
	 */
	public ArrayValue newArrayWithValue(final ArrayClass aClass, final Parameterized[] values);

	/**
	 * create a two dim array
	 * @param aClass
	 * @param values
	 * @return
	 */
	public ArrayValue newArrayWithValue(final ArrayClass aClass, final Parameterized[][] values);

	/**
	 * create a three dim array
	 * @param aClass
	 * @param values
	 * @return
	 */
	public ArrayValue newArrayWithValue(final ArrayClass aClass, final Parameterized[][][] values);

	/**
	 * create a four dim array
	 * @param aClass
	 * @param values
	 * @return
	 */
	public ArrayValue newArrayWithValue(final ArrayClass aClass, final Parameterized[][][][] values);
	
	/**
	 * create a unknow dim array
	 * @param aClass
	 * @param arrayObject Parameterized array
	 * @return
	 */
	public ArrayValue newArrayWithValue(final ArrayClass aClass, final Object arrayObject);
}
