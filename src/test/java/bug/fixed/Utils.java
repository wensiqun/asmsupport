package bug.fixed;

import java.lang.reflect.InvocationTargetException;

import cn.wensiqun.asmsupport.creator.ClassCreator;
import cn.wensiqun.asmsupport.creator.IClassContext;

public class Utils {

	public static Class<?> generate(IClassContext creator) throws IllegalArgumentException, SecurityException, IllegalAccessException, InvocationTargetException, NoSuchMethodException{
		creator.setClassOutPutPath(".//target//");
		Class<?> cls = creator.startup();
		if(creator instanceof ClassCreator){
		    cls.getMethod("main", String[].class).invoke(cls, new Object[]{null});
		}
		return cls;
	}
	
}
