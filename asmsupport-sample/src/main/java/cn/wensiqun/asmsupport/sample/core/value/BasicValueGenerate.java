package cn.wensiqun.asmsupport.sample.core.value;

import cn.wensiqun.asmsupport.core.block.method.common.KernelMethodBody;
import cn.wensiqun.asmsupport.core.builder.impl.ClassBuilderImpl;
import cn.wensiqun.asmsupport.core.definition.value.Value;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.org.objectweb.asm.Opcodes;
import cn.wensiqun.asmsupport.sample.core.AbstractExample;
import cn.wensiqun.asmsupport.standard.def.clazz.IClass;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class BasicValueGenerate extends AbstractExample {

	/**
	 * @param args
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws SecurityException 
	 * @throws IllegalArgumentException 
	 */
	public static void main(String[] args) {
		
		ClassBuilderImpl creator = new ClassBuilderImpl(Opcodes.V1_5, Opcodes.ACC_PUBLIC , "generated.value.BasicValueCreateExample", null, null);
		
		creator.createMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, "main", new IClass[]{classLoader.getType(String[].class)}, new String[]{"args"}, null, null,
				new KernelMethodBody(){

	        @Override
			public void body(LocalVariable... argus) {

	        	/* ************************************************************************************************************** */
	        	/* 1.val(Object obj): 获取基本类型值。该方法直接受的参数类型是String类型以及基本类型或者其封装类型.                 */
	        	/* 2.Value.defaultValue(AClass type) 该方法是获取某一类型的默认值。例如Object类型的默认值为null，int类型的默认值为0        */
	        	/* 3.Value.nullValue(AClass type) 该方法获取null值，并且指定其类型.                                                   */
	        	/*      例如我们需要给一个String类型的变量a赋值null。则需要调用Value.nullValue(AClass of String)                        */
	        	/* ************************************************************************************************************** */
	        	boolean boolVal = true;
	        	byte byteVal = 5;
	        	short shortVal = 6;
	        	char charVal = 'a';
	        	int intVal = 7;
	        	float floatVal = 8;
	        	long longVal = 9;
	        	double doubleVal = 10;
	        	String strVal = "I'm a string value";
	        	//Value.nullValue(type)
	        	call(systemOut, "println", stradd(val("boolean value is : "), val(boolVal)));
	        	call(systemOut, "println", stradd(val("boolean default value is : "), Value.defaultValue(getType(boolean.class))));
	        	
	        	
	        	call(systemOut, "println", stradd(val("byte value is : "), val(byteVal)));
	        	call(systemOut, "println", stradd(val("byte default value is : "), Value.defaultValue(getType(byte.class))));
	        	
	        	
	        	call(systemOut, "println", stradd(val("short value is : "), val(shortVal)));
	        	call(systemOut, "println", stradd(val("short default value is : "), Value.defaultValue(getType(short.class))));
	        	

	        	call(systemOut, "println", stradd(val("char value is : "), val(charVal)));
	        	call(systemOut, "println", stradd(val("char default value is : "), Value.defaultValue(getType(char.class))));


	        	call(systemOut, "println", stradd(val("int value is : "), val(intVal)));
	        	call(systemOut, "println", stradd(val("int default value is : "), Value.defaultValue(getType(int.class))));


	        	call(systemOut, "println", stradd(val("float value is : "), val(floatVal)));
	        	call(systemOut, "println", stradd(val("float default value is : "), Value.defaultValue(getType(float.class))));


	        	call(systemOut, "println", stradd(val("long value is : "), val(longVal)));
	        	call(systemOut, "println", stradd(val("long default value is : "), Value.defaultValue(getType(long.class))));


	        	call(systemOut, "println", stradd(val("double value is : "), val(doubleVal)));
	        	call(systemOut, "println", stradd(val("double default value is : "), Value.defaultValue(getType(double.class))));
	        	

	        	call(systemOut, "println", stradd(val("String value is : "), val(strVal)));
	        	call(systemOut, "println", stradd(val("double default value is : "), Value.defaultValue(getType(String.class))));

	        	//测试null的Value
	        	LocalVariable arrayListNullValue = var("arrayListNullValue", getType(List.class), Value.getNullValue(getType(ArrayList.class)));
	        	call(systemOut, "println", stradd(val("I'm a null value and type is List: "), arrayListNullValue));
			    return_();
			}
		});
		generate(creator);
	}

}
