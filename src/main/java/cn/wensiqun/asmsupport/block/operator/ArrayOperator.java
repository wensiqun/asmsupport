package cn.wensiqun.asmsupport.block.operator;

import cn.wensiqun.asmsupport.Parameterized;
import cn.wensiqun.asmsupport.definition.variable.IVariable;
import cn.wensiqun.asmsupport.operators.array.ArrayLength;
import cn.wensiqun.asmsupport.operators.array.ArrayLoader;
import cn.wensiqun.asmsupport.operators.array.ArrayStorer;
import cn.wensiqun.asmsupport.operators.array.ArrayValue;
import cn.wensiqun.asmsupport.operators.assign.Assigner;
import cn.wensiqun.asmsupport.operators.method.MethodInvoker;


/**
 * 数组操作
 *
 */
public interface ArrayOperator {
    
    
    /**
     * 
     * <p>
     * 根据传入的下标从数组中获取值,这里的数组是直接new出来的.对应下面的红色java代码
     * </p>
     * 
     * 
     * <p style="border:1px solid;width:400px;padding:10px;">
     * String value = <b style="color:#FF3300">{{"[0][0]","[0][1]"},{"[1][0]","[1][1]"}}[0][1]</b>;
     * </p>
     * 
     * 上面红色部分对应的asmsupport代码
     * <p style="border:1px solid;width:400px;padding:10px;">
     * arrayLoad(values, getValue(0), getValue(1));<br>
     * </p>
     * 
     * 
     * 
     * @param arrayReference
     * @param pardim
     * @param parDims
     * @return
     */
    public ArrayLoader arrayLoad(ArrayValue arrayReference, Parameterized pardim, Parameterized... parDims);
    
    
	
	/**
     * 根据传入的下标从数组中获取值,这里的数组是存储在一个变量中，对应下面的红色java代码
     * 
     * <p style="border:1px solid;width:300px;padding:10px;">
     * String value = <b style="color:#FF3300">values[0][1];</b>
     * </p>
     * 
     * 上面红色部分对应的asmsupport代码
     * <p style="border:1px solid;width:300px;padding:10px;">
     * arrayLoad(values, getValue(0), getValue(1))
     * </p>
     * 
     * @param arrayReference
     * @param pardim
     * @param parDims
     * @return
     * @see #arrayLoad(MethodInvoker, Parameterized, Parameterized...)
     * 
     * 
     */
    public ArrayLoader arrayLoad(IVariable arrayReference, Parameterized pardim, Parameterized... parDims);
    
    /**
     * <p>
     * 根据传入的下标从数组中获取值,这里的数组是直接通过方法调用获得的.对应下面的红色java代码
     * </p>
     * 
     * 
     * <p style="border:1px solid;width:300px;padding:10px;">
     * String value = <b style="color:#FF3300">getValues()[0][1];</b>
     * </p>
     * 
     * 上面红色部分对应的asmsupport代码
     * <p style="border:1px solid;width:300px;padding:10px;">
     * arrayLoad(getValuesMethodInvoker, getValue(0), getValue(1))
     * </p>
     * 
     * 
     * @param arrayReference
     * @param pardim
     * @param parDims
     * @return
     * @see #arrayLoad(IVariable, Parameterized, Parameterized...)
     * 
     */
    public ArrayLoader arrayLoad(MethodInvoker arrayReference, Parameterized pardim, Parameterized... parDims);
    
    /**
     * 
     * <p>
     * 根据传入的下标从数组中获取值,这里的数组是直接new出来的.对应下面的红色java代码
     * </p>
     * 
     * 
     * <p style="border:1px solid;width:400px;padding:10px;">
     * String[][] values = null;<br>
     * String value = <b style="color:#FF3300">(values=getValues())[0]</b>;
     * </p>
     * 
     * 上面红色部分对应的asmsupport代码
     * <p style="border:1px solid;width:400px;padding:10px;">
     * arrayLoad(assignerOperator, getValue(0), getValue(1));<br>
     * </p>
     * 
     * @param arrayReference
     * @param pardim
     * @param parDims
     * @return
     */
    public ArrayLoader arrayLoad(Assigner arrayReference, Parameterized pardim, Parameterized... parDims);
    
    
    /**
     * <p>
     * 根据传入的下标从数组中获取值,这里的数组是从另一个数组中获得的，对于同一个数组其执行效果和其他的重载方法
     * {@link #arrayLoad(IVariable, Parameterized, Parameterized...)},
     * {@link #arrayLoad(MethodInvoker, Parameterized, Parameterized...)},
     * {@link #arrayLoad(ArrayValue, Parameterized, Parameterized...)},
     * {@link #arrayLoad(Assigner, Parameterized, Parameterized...)}一样。
     * 仅仅只是语义上有所不同，当前方法表示<span style="color=#00FF00">“获取数组的值(这个值同样也是个数组)的值，”</span>，而其他重载方法可能表示的
     * 是<span style="color=#00FF00">“获取多维数组的某一个元素的值”</span>.
     * </p>
     * 
     * <p>
     * 下面是将这个方法与其他从在方法在使用上做的比较(红色部分)。
     * </p>
     * 
     * <p style="border:1px solid;width:700px;padding:10px;">
     * String value = <b style="color:#FF3300">{{"[0][0]","[0][1]"},{"[1][0]","[1][1]"}}[0][1]</b>;//{@link #arrayLoad(ArrayValue, Parameterized, Parameterized...)}<br>
     * String value = <b style="color:#FF3300">values[0][1];</b>//{@link #arrayLoad(IVariable, Parameterized, Parameterized...)}<br>
     * String value = <b style="color:#FF3300">getValues()[1][2];</b>//{@link #arrayLoad(MethodInvoker, Parameterized, Parameterized...)}<br>
     * String[][] values = null;<br>
     * String value = <b style="color:#FF3300">(values=getValues())[0]</b>;//{@link #arrayLoad(Assigner, Parameterized, Parameterized...)}<br>
     * </p>
     * 
     * 上面红色部分对应其他重载方式的实现
     * <p style="border:1px solid;width:700px;padding:10px;">
     * arrayLoad(values, getValue(0), getValue(1));<br>
     * arrayLoad(values_variable, getValue(0), getValue(1));<br>
     * arrayLoad(getValuesMethodInvoker, getValue(0), getValue(1));<br>
     * arrayLoad(assignerOperator, getValue(0), getValue(1));<br>
     * </p>
     * 
     * 
     * 上面红色部分使用当前方法实现
     * 
     * <p style="border:1px solid;width:700px;padding:10px;">
     * arrayLoad(arrayLoad(values, getValue(0), getValue(1));<br>
     * arrayLoad(arrayLoad(values_variable, getValue(0)), getValue(1));<br>
     * arrayLoad(arrayLoad(getValuesMethodInvoker, getValue(0)), getValue(1));<br>
     * arrayLoad(arrayLoad(assignerOperator, getValue(0)), getValue(1));<br>
     * </p>
     * 
     * @param arrayReference
     * @param pardim
     * @param parDims
     * @return
     * 
     * 
     */
    public ArrayLoader arrayLoad(ArrayLoader arrayReference, Parameterized pardim, Parameterized... parDims);
    
    
    /**
     * set value from array according to index<br>
     * <p>
     * java code:<br>
     * i[1][2] = 10;
     * </p>
     * 
     * <p>
     * asmsupport code:<br>
     * arrayStore(i, getValue(10), getValue(1), getValue(2))
     * </p>
     * @param arrayReference
     * @param value
     * @param dim
     * @param dims
     * @return
     */
    public ArrayStorer arrayStore(IVariable arrayReference, Parameterized value, Parameterized dim, Parameterized... dims);
    

    /**
     * 
     * @param arrayReference
     * @param value
     * @param dim
     * @param dims
     * @return
     */
    public ArrayStorer arrayStore(MethodInvoker arrayReference, Parameterized value, Parameterized dim, Parameterized... dims);
    
    
    /**
     * 
     * @param arrayReference
     * @param value
     * @param dim
     * @param dims
     * @return
     */
    public ArrayStorer arrayStore(ArrayLoader arrayReference, Parameterized value, Parameterized dim, Parameterized... dims);
    

    /**
     * 
     * @param arrayReference
     * @param value
     * @param dim
     * @param dims
     * @return
     */
    public ArrayStorer arrayStore(ArrayValue arrayReference, Parameterized value, Parameterized dim, Parameterized... dims);
    
    
    /**
     * 
     * @param arrayReference
     * @param value
     * @param dim
     * @param dims
     * @return
     */
    public ArrayStorer arrayStore(Assigner arrayReference, Parameterized value, Parameterized dim, Parameterized... dims);
    
    /**
     * get length of array
     * <p>
     * java code:<br>
     * i[1].length<br>
     * note: i is int[][]{{1},{2}}
     * </p>
     * 
     * <p>
     * asmsupport code:<br>
     * arrayLength(i, getValue(1))
     * </p>
     * @param arrayReference
     * @param dims
     * @return
     */
    public ArrayLength arrayLength(IVariable arrayReference, Parameterized... dims);
    
    /**
     * 
     * @param arrayReference
     * @param dims
     * @return
     */
    public ArrayLength arrayLength(MethodInvoker arrayReference, Parameterized... dims);
    
    /**
     * 
     * @param arrayReference
     * @param dims
     * @return
     */
    public ArrayLength arrayLength(ArrayLoader arrayReference, Parameterized... dims);
    
    /**
     * 
     * @param arrayReference
     * @param dims
     * @return
     */
    public ArrayLength arrayLength(ArrayValue arrayReference, Parameterized... dims);
    

    /**
     * 
     * @param arrayReference
     * @param dims
     * @return
     */
    public ArrayLength arrayLength(Assigner arrayReference, Parameterized... dims);
    

}
