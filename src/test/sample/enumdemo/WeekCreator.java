package enumdemo;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.objectweb.asm.Opcodes;


import jw.asmsupport.block.method.common.StaticMethodBody;
import jw.asmsupport.clazz.AClass;
import jw.asmsupport.clazz.AClassFactory;
import jw.asmsupport.creator.EnumCreator;
import jw.asmsupport.definition.variable.GlobalVariable;
import jw.asmsupport.definition.variable.LocalVariable;


@org.junit.Ignore
public class WeekCreator {
	
	static GlobalVariable out = AClassFactory.getProductClass(System.class).getGlobalVariable("out");
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		EnumCreator creator = new EnumCreator(Opcodes.V1_6 , "demo.Week", null);
		creator.createEnumConstant("Monday");
		creator.createEnumConstant("Tuesday");
		creator.createStaticMethod("main", new AClass[]{AClassFactory.getProductClass(String[].class)}, new String[]{"args"}, null, null, 
				Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, 
			    new StaticMethodBody(){
					@Override
					public void generateBody(LocalVariable... argus) {
					    invoke(out, "println", getMethodOwner().getGlobalVariable("Monday"));
					    invoke(out, "println", getMethodOwner().getGlobalVariable("Tuesday"));
						runReturn();
					}
		        }
		);
		creator.setClassOutPutPath(".\\target\\generate\\");
		Class<?> c = creator.startup();
        try {
			Method m = c.getMethod("main", String[].class);
			m.invoke(c, new Object[]{null});
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
	}

}
