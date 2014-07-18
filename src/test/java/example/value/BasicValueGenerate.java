package example.value;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.objectweb.asm.Opcodes;

import cn.wensiqun.asmsupport.block.method.common.StaticMethodBody;
import cn.wensiqun.asmsupport.clazz.AClass;
import cn.wensiqun.asmsupport.clazz.AClassFactory;
import cn.wensiqun.asmsupport.creator.ClassCreator;
import cn.wensiqun.asmsupport.definition.value.Value;
import cn.wensiqun.asmsupport.definition.variable.LocalVariable;
import example.AbstractExample;

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
		
		creator.createStaticMethod("main", new AClass[]{AClassFactory.getProductClass(String[].class)}, new String[]{"args"}, null, null,
				Opcodes.ACC_PUBLIC, new StaticMethodBody(){

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
	        	invoke(systemOut, "println", append(Value.value("boolean value is : "), Value.value(boolVal)));
	        	invoke(systemOut, "println", append(Value.value("boolean default value is : "), Value.defaultValue(AClass.BOOLEAN_ACLASS)));
	        	
	        	
	        	invoke(systemOut, "println", append(Value.value("byte value is : "), Value.value(byteVal)));
	        	invoke(systemOut, "println", append(Value.value("byte default value is : "), Value.defaultValue(AClass.BYTE_ACLASS)));
	        	
	        	
	        	invoke(systemOut, "println", append(Value.value("short value is : "), Value.value(shortVal)));
	        	invoke(systemOut, "println", append(Value.value("short default value is : "), Value.defaultValue(AClass.SHORT_ACLASS)));
	        	

	        	invoke(systemOut, "println", append(Value.value("char value is : "), Value.value(charVal)));
	        	invoke(systemOut, "println", append(Value.value("char default value is : "), Value.defaultValue(AClass.CHAR_ACLASS)));


	        	invoke(systemOut, "println", append(Value.value("int value is : "), Value.value(intVal)));
	        	invoke(systemOut, "println", append(Value.value("int default value is : "), Value.defaultValue(AClass.INT_ACLASS)));


	        	invoke(systemOut, "println", append(Value.value("float value is : "), Value.value(floatVal)));
	        	invoke(systemOut, "println", append(Value.value("float default value is : "), Value.defaultValue(AClass.FLOAT_ACLASS)));


	        	invoke(systemOut, "println", append(Value.value("long value is : "), Value.value(longVal)));
	        	invoke(systemOut, "println", append(Value.value("long default value is : "), Value.defaultValue(AClass.LONG_ACLASS)));


	        	invoke(systemOut, "println", append(Value.value("double value is : "), Value.value(doubleVal)));
	        	invoke(systemOut, "println", append(Value.value("double default value is : "), Value.defaultValue(AClass.DOUBLE_ACLASS)));
	        	

	        	invoke(systemOut, "println", append(Value.value("String value is : "), Value.value(strVal)));
	        	invoke(systemOut, "println", append(Value.value("double default value is : "), Value.defaultValue(AClass.STRING_ACLASS)));

	        	//测试null的Value
	        	LocalVariable arrayListNullValue = createVariable("arrayListNullValue", AClassFactory.getProductClass(List.class), false, Value.getNullValue(AClassFactory.getProductClass(ArrayList.class)));
	        	invoke(systemOut, "println", append(Value.value("I'm a null value and type is List: "), arrayListNullValue));
			    runReturn();
			}
		});
		generate(creator);
	}

}
