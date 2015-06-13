package cn.wensiqun.asmsupport.issues;

import java.lang.reflect.InvocationTargetException;

import cn.wensiqun.asmsupport.core.builder.IClassBuilder;
import cn.wensiqun.asmsupport.core.builder.impl.ClassBuilderImpl;

public class Utils {

	public static Class<?> generate(IClassBuilder creator) throws IllegalArgumentException, SecurityException, IllegalAccessException, InvocationTargetException, NoSuchMethodException{
		creator.setClassOutPutPath(IssuesConstant.classOutPutPath);
		Class<?> cls = creator.startup();
		if(creator instanceof ClassBuilderImpl){
		    cls.getMethod("main", String[].class).invoke(cls, new Object[]{null});
		}
		return cls;
	}
	
}
