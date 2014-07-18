package example.operators;


import org.apache.commons.lang3.ArrayUtils;
import org.objectweb.asm.Opcodes;

import cn.wensiqun.asmsupport.Parameterized;
import cn.wensiqun.asmsupport.block.method.common.StaticMethodBody;
import cn.wensiqun.asmsupport.clazz.AClass;
import cn.wensiqun.asmsupport.clazz.AClassFactory;
import cn.wensiqun.asmsupport.creator.ClassCreator;
import cn.wensiqun.asmsupport.definition.value.Value;
import cn.wensiqun.asmsupport.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.operators.array.ArrayValue;
import example.AbstractExample;

/**
 * 在这个例子中我们将实现数组的相关操作
 *
 * @author 温斯群(Joe Wen)
 *
 */
public class ArrayOperatorGenerate extends AbstractExample {

	/**
	 * 我们将生成这个方法的内容
	 */
	public static void willGenerate(){
		//创建数组操作
		int[][] i1 = new int[2][2];
	    System.out.println("i1 = " + ArrayUtils.toString(i1));
	    
	    int[][] i2 = new int[2][];
	    System.out.println("i2 = " + ArrayUtils.toString(i2));
	    
	    String[] s1 = { "array \"s1\" first value", "array \"s1\" second value" };
	    System.out.println("s1 = " + ArrayUtils.toString(s1));
	    
	    String[][] s2 = { { "s2[0][0]", "s2[0][1]" }, { "s2[1][0]", "s2[1][1]" } };
	    System.out.println("s2 = " + ArrayUtils.toString(s2));
	    
	    //获取数组长度操作
	    System.out.println("length of s2 is " + s2.length);
	    System.out.println("length of s2[0] is " + s2[0].length);
	    
	    //获取数组内容的操作
	    System.out.println("value of s2[0] is " + ArrayUtils.toString(s2[0]));
	    System.out.println("value of s2[0][0] is " + ArrayUtils.toString(s2[0][0]));
	    
	    //为数组内容赋值的操作
	    s2[0] = new String[]{ "new s2[0][0]", "new s2[0][1]" };
	    s2[1][0] = "new s2[1][0]";
	    System.out.println("new value of s2 is : " + ArrayUtils.toString(s2));
		
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ClassCreator creator = new ClassCreator(Opcodes.V1_5, Opcodes.ACC_PUBLIC , "generated.operators.ArrayOperatorGenerateExample", null, null);
		
		creator.createStaticMethod("main", new AClass[]{AClassFactory.getProductClass(String[].class)}, new String[]{"args"}, null, null,
				Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, new StaticMethodBody(){

			@Override
			public void body(LocalVariable... argus) {
				/*
				 * 1.首先我们需要创建一个数组。我们有两只方式创建数组，第一种是在创建数组的时候
				 *   为其分配数组空间。第二种是创建数组的时候为其分配初试值
				 */
				//int[][] i1 = new int[1][2];
				//System.out.println(ArrayUtils.toString(i1));
				
				/*
				 * 
				 * 首先通过newArray方法创建一个数组值av，再创建一个i1变量并将av赋值给i1
				 * 再创建System.out.println(ArrayUtils.toString(i1))。这里我们组要介绍
				 * 如何创建数组值。
				 * 
				 * 这里创建数组值是通过newArray方法创建的，这个方法使我们上面所说的第一种创建数组
				 * 的方式。即在创建数组的时候 为其分配数组空间。这个方法是个变元方法，第一个参数表示
				 * 当前需要创建的数组值是什么类型的(参数类型是ArrayClass)，我们通过AClassFactory的
				 * getArrayClass方法获取其对应的AClass.这里介绍下getArrayClass的方法
				 * 
				 * getArrayClass有三个重载的方法
				 * 1.getArrayClass(Class<?> arrayCls)： 这里的参数类型是一个Array的类型。比如我们
				 *   需要获取一个int[]类型的ArrayClass，那么我们可以通过getArrayClass(int[].class)
				 *   获取
				 * 2.getArrayClass(Class<?> cls, int dim): 这里的第一参数是一个任意类型的Class。
				 *   第二个参数表示需要创建的ArrayClass的维数。通过这个方法得到的将是一个以第一个参数
				 *   为基本类型，维数为第二个参数的ArrayClass。比如：getArrayClass(int.class, 2)将
				 *   得到一个int[][]类型的ArrayClass；getArrayClass(int[].class, 2)将得到一个
				 *   int[][][]类型的ArrayClass。
				 * 3.getArrayClass(AClass cls, int dim)： 这个方法和上面那个方法类似，只是将第一个个参数
				 *   类型变成了AClass。其他相同
				 * 这里还可以通过getProductClass(Class<?> cls)方法获取ArrayClass，和
				 * getArrayClass(Class<?> arrayCls)用法相同。只是返回类型是AClass。我们
				 * 可以将其强制转换成ArrayClass。
				 * 
				 * 好了回到newArray方法。接下来是第二个参数。第二个参数是个变元参数，类型是Parameterized类型
				 * 表示我们为其每一个维分配的空间数
				 * 
				 * 下面一段代码将生成如下代码：
				 * int[][] i1 = new int[1][2];
				 * System.out.println("i1 = " + ArrayUtils.toString(i1));
				 */
				ArrayValue av = newArray(AClassFactory.getArrayClass(int[][].class), Value.value(2), Value.value(2));
				LocalVariable i1 = createArrayVariable("i1", AClassFactory.getArrayClass(int[][].class), false, av);
				invoke(systemOut, "println", append(Value.value("i1 = "), invokeStatic(AClassFactory.getProductClass(ArrayUtils.class), "toString", i1)));
				
				/*
				 * 下面一段代码将生成如下代码：
				 * int[][] i2 = new int[2][];
		         * System.out.println("i2 = " + ArrayUtils.toString(i2));
				 */
				av = newArray(AClassFactory.getArrayClass(int[][].class), Value.value(2));
				LocalVariable i2 = createArrayVariable("i2", AClassFactory.getArrayClass(int[][].class), false, av);
				invoke(systemOut, "println", append(Value.value("i2 = "), invokeStatic(AClassFactory.getProductClass(ArrayUtils.class), "toString", i2)));
				
				/*
				 * 接下来介绍创建数组的方式。
				 * 这种方式是为数组提供初始值，比如new int[]{1,2,3};
				 * 
				 * 
				 * 有五个方法可以实现这种方式
				 * 1.public ArrayValue newArrayWithValue(final ArrayClass aClass, final Object arrayObject);
				 * 2.public ArrayValue newArrayWithValue(final ArrayClass aClass, final Parameterized[] values)
	             * 3.public ArrayValue newArrayWithValue(final ArrayClass aClass, final Parameterized[][] values)
	             * 4.public ArrayValue newArrayWithValue(final ArrayClass aClass, final Parameterized[][][] values)
	             * 5.public ArrayValue newArrayWithValue(final ArrayClass aClass, final Parameterized[][][][] values)
				 * 上面的第2,3,4,5方法实际上是通过调用第一方法实现的。所以这里我们重点讨论第一个方法
				 * 这个方法有两个参数：
				 * 1.第一个参数是数组类型和newArray操作是一样的。
				 * 2.第二个参数是我们设置的默认值，虽然我们定义的是个Object类型的，但是在实际应用的时候必须得是Parameterized的数组类型
				 *   否则会抛异常。至于是几维数组，则根据我们所定义的类型而定。其实asmsupport始终保持着类似于java的编程方式。比如我们创建
				 *   一个二维数组那么我们可以说设置这个值为：new Parameterized[]{new Parameterized[]{p1, p2}}。
				 * 
				 * 下面一段代码将生成如下代码：
				 * String[] s1 = new String[]{"array \"s1\" first value", "array \"s1\" second value"};
				 * System.out.println("s1 = " + ArrayUtils.toString(s1));
				 */
				av = newArrayWithValue(AClassFactory.getArrayClass(String[].class), new Value[]{Value.value("array \"s1\" first value"), Value.value("array \"s1\" second value")});
				LocalVariable s1 = createArrayVariable("s1", AClassFactory.getArrayClass(String[].class), false, av);
				invoke(systemOut, "println", append(Value.value("s1 = "), invokeStatic(AClassFactory.getProductClass(ArrayUtils.class), "toString", s1)));

				/*
				 * 下面一段将生成如下代码：
				 * String[][] s2 = {{"s2[0][0]", "s2[0][1]"},{"s2[1][0]", "s2[1][1]"}};
		         * System.out.println("s2 = " + ArrayUtils.toString(s2));
				 */
				Value s200 = Value.value("s2[0][0]");
				Value s201 = Value.value("s2[0][1]");
				Value s210 = Value.value("s2[1][0]");
				Value s211 = Value.value("s2[1][1]");
				
				av = newArrayWithValue(AClassFactory.getArrayClass(String[][].class), 
						new Value[][]{new Value[]{s200, s201}, new Value[]{s210, s211}});
				LocalVariable s2 = createArrayVariable("s2", AClassFactory.getArrayClass(String[][].class), false, av);
				invoke(systemOut, "println", append(Value.value("s2 = "), invokeStatic(AClassFactory.getProductClass(ArrayUtils.class), "toString", s2)));

				/*
				 * 接下来我们将获取数组的长度：
				 * 代码如下：
				 * System.out.println("length of s2 is " + s2.length);
				 * System.out.println("length of s2[0] is " + s2[0].length);
				 */
				invoke(systemOut, "println", append(Value.value("length of s2 is "), arrayLength(s2)));
				invoke(systemOut, "println", append(Value.value("length of s2[0] is "), arrayLength(s2, Value.value(0))));

				/*
				 * 接下来我们将实现如何获取数组的值
				 * 代码如下：
				 * System.out.println("value of s2[0] is " + ArrayUtils.toString(s2[0]));
				 * System.out.println("value of s2[0][0] is " + s2[0][0]);
				 */
				//s2[0]
				Parameterized arrayLoader = arrayLoad(s2, Value.value(0));
				invoke(systemOut, "println", append(Value.value("value of s2[0] is "), invokeStatic(AClassFactory.getProductClass(ArrayUtils.class), "toString", arrayLoader)));
				
				//s2[0][0]
				arrayLoader = arrayLoad(s2, Value.value(0), Value.value(0));
				invoke(systemOut, "println", append(Value.value("value of s2[0][0] is "), invokeStatic(AClassFactory.getProductClass(ArrayUtils.class), "toString", arrayLoader)));
				
				/*
				 * 接下来是如何实现为数组单元赋值的操作
				 * 代码如下
				 * s2[0] = new String[]{"new s2[0][0]", "new s2[0][1]"};
				 * s2[1][0] = "new s2[1][0]"
				 * System.out.println("new value of s2 is : " + ArrayUtils.toString(s2));
				 */
				arrayStore(s2, newArrayWithValue(AClassFactory.getArrayClass(String[].class), new Parameterized[]{Value.value("new s2[0][0]"), Value.value("new s2[0][1]")}), Value.value(0));
				arrayStore(s2, Value.value("new s2[1][0]"), Value.value(1), Value.value(0));
				invoke(systemOut, "println", append(Value.value("new value of s2 is : "), invokeStatic(AClassFactory.getProductClass(ArrayUtils.class), "toString", s2)));
				
				runReturn();
			}
        });
		generate(creator);
	}
}
