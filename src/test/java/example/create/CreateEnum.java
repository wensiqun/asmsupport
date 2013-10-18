package example.create;

import java.lang.reflect.InvocationTargetException;

import org.objectweb.asm.Opcodes;




import cn.wensiqun.asmsupport.creator.EnumCreator;
import example.AbstractExample;

public class CreateEnum extends AbstractExample {

	/**
	 * @param args
	 * @throws NoSuchFieldException 
	 * @throws SecurityException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 */
	public static void main(String[] args) throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		
		/*
		 * 创建一个枚举类型。
		 * 这里只能指定三个参数(版本，类名，实现的接口)
		 * 枚举类型只允许是public的。
		 * 
		 */
		EnumCreator enumCreator = new EnumCreator(Opcodes.V1_6, "generated.create.CreateEnumExample", null);
		
		/*
		 * 创建每个枚举类别
		 */
		enumCreator.createEnumConstant("Monday");
		enumCreator.createEnumConstant("Tuesday");
		enumCreator.createEnumConstant("Wednesday");
		enumCreator.createEnumConstant("Thursday");
		enumCreator.createEnumConstant("Friday");
		enumCreator.createEnumConstant("Saturday");
		enumCreator.createEnumConstant("Sunday");
		
		/*
		 * 其余的部分和ClassCreate差不多。
		 */
		
		Class<? extends Enum> weekEnumCls = (Class<? extends Enum>) generate(enumCreator);
		Enum mo = (Enum) weekEnumCls.getField("Monday").get(weekEnumCls);
		Enum tu = (Enum) weekEnumCls.getField("Tuesday").get(weekEnumCls);
		
		System.out.println(mo);
		System.out.println(tu);
	}

}
