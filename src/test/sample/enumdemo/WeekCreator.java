package enumdemo;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.objectweb.asm.Opcodes;

import cn.wensiqun.asmsupport.block.method.common.StaticMethodBody;
import cn.wensiqun.asmsupport.clazz.AClass;
import cn.wensiqun.asmsupport.clazz.AClassFactory;
import cn.wensiqun.asmsupport.creator.EnumCreator;
import cn.wensiqun.asmsupport.definition.variable.GlobalVariable;
import cn.wensiqun.asmsupport.definition.variable.LocalVariable;


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
					public void body(LocalVariable... argus) {
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
			
			System.out.println("==========================");
			
            m = c.getMethod("values");
            Object o = m.invoke(c);
            int len = Array.getLength(o);
            for(int i=0; i<len; i++)
            {
                System.out.println(Array.get(o, i));
            }
            
            m = c.getMethod("valueOf", String.class);
            System.out.println("==========================");
            
            System.out.println(m.invoke(c, "Monday"));
            System.out.println(m.invoke(c, "Tuesday"));
            
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
