package oldApi.value;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import cn.wensiqun.asmsupport.core.AbstractExample;
import cn.wensiqun.asmsupport.core.block.method.common.StaticMethodBodyInternal;
import cn.wensiqun.asmsupport.core.clazz.AClass;
import cn.wensiqun.asmsupport.core.clazz.AClassFactory;
import cn.wensiqun.asmsupport.core.creator.clazz.ClassCreator;
import cn.wensiqun.asmsupport.core.definition.value.Value;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.org.objectweb.asm.Opcodes;

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
		
		ClassCreator creator = new ClassCreator(Opcodes.V1_5, Opcodes.ACC_PUBLIC , "generated.value.BasicValueCreateExample", null, null);
		
		creator.createStaticMethod(Opcodes.ACC_PUBLIC, "main", new AClass[]{AClassFactory.getType(String[].class)}, new String[]{"args"}, null, null,
				new StaticMethodBodyInternal(){

	        @Override
			public void body(LocalVariable... argus) {

	        	/* ************************************************************************************************************** */
	        	/* 1.Value.value(Object obj): 获取基本类型值。该方法直接受的参数类型是String类型以及基本类型或者其封装类型.                 */
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
	        	call(systemOut, "println", stradd(Value.value("boolean value is : "), Value.value(boolVal)));
	        	call(systemOut, "println", stradd(Value.value("boolean default value is : "), Value.defaultValue(AClassFactory.getType(boolean.class))));
	        	
	        	
	        	call(systemOut, "println", stradd(Value.value("byte value is : "), Value.value(byteVal)));
	        	call(systemOut, "println", stradd(Value.value("byte default value is : "), Value.defaultValue(AClassFactory.getType(byte.class))));
	        	
	        	
	        	call(systemOut, "println", stradd(Value.value("short value is : "), Value.value(shortVal)));
	        	call(systemOut, "println", stradd(Value.value("short default value is : "), Value.defaultValue(AClassFactory.getType(short.class))));
	        	

	        	call(systemOut, "println", stradd(Value.value("char value is : "), Value.value(charVal)));
	        	call(systemOut, "println", stradd(Value.value("char default value is : "), Value.defaultValue(AClassFactory.getType(char.class))));


	        	call(systemOut, "println", stradd(Value.value("int value is : "), Value.value(intVal)));
	        	call(systemOut, "println", stradd(Value.value("int default value is : "), Value.defaultValue(AClassFactory.getType(int.class))));


	        	call(systemOut, "println", stradd(Value.value("float value is : "), Value.value(floatVal)));
	        	call(systemOut, "println", stradd(Value.value("float default value is : "), Value.defaultValue(AClassFactory.getType(float.class))));


	        	call(systemOut, "println", stradd(Value.value("long value is : "), Value.value(longVal)));
	        	call(systemOut, "println", stradd(Value.value("long default value is : "), Value.defaultValue(AClassFactory.getType(long.class))));


	        	call(systemOut, "println", stradd(Value.value("double value is : "), Value.value(doubleVal)));
	        	call(systemOut, "println", stradd(Value.value("double default value is : "), Value.defaultValue(AClassFactory.getType(double.class))));
	        	

	        	call(systemOut, "println", stradd(Value.value("String value is : "), Value.value(strVal)));
	        	call(systemOut, "println", stradd(Value.value("double default value is : "), Value.defaultValue(AClassFactory.getType(String.class))));

	        	//测试null的Value
	        	LocalVariable arrayListNullValue = var("arrayListNullValue", AClassFactory.getType(List.class), false, Value.getNullValue(AClassFactory.getType(ArrayList.class)));
	        	call(systemOut, "println", stradd(Value.value("I'm a null value and type is List: "), arrayListNullValue));
			    return_();
			}
		});
		generate(creator);
	}

}
