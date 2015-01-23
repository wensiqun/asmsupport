package bug.fixed;

import java.lang.reflect.InvocationTargetException;

import cn.wensiqun.asmsupport.core.creator.clazz.ClassCreatorInternal;
import cn.wensiqun.asmsupport.generic.creator.IClassContext;

public class Utils {

	public static Class<?> generate(IClassContext creator) throws IllegalArgumentException, SecurityException, IllegalAccessException, InvocationTargetException, NoSuchMethodException{
		creator.setClassOutPutPath(".//target//");
		Class<?> cls = creator.startup();
		if(creator instanceof ClassCreatorInternal){
		    cls.getMethod("main", String[].class).invoke(cls, new Object[]{null});
		}
		return cls;
	}
	
}
