package cn.wensiqun.asmsupport.issues;

import java.lang.reflect.InvocationTargetException;

import cn.wensiqun.asmsupport.core.creator.IClassContext;
import cn.wensiqun.asmsupport.core.creator.clazz.ClassCreator;

public class Utils {

	public static Class<?> generate(IClassContext creator) throws IllegalArgumentException, SecurityException, IllegalAccessException, InvocationTargetException, NoSuchMethodException{
		creator.setClassOutPutPath(IssuesConstant.classOutPutPath);
		Class<?> cls = creator.startup();
		if(creator instanceof ClassCreator){
		    cls.getMethod("main", String[].class).invoke(cls, new Object[]{null});
		}
		return cls;
	}
	
}
