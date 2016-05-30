package cn.wensiqun.asmsupport.issues;

import java.lang.reflect.InvocationTargetException;

import cn.wensiqun.asmsupport.core.build.BytecodeResolver;
import cn.wensiqun.asmsupport.core.build.resolver.ClassResolver;

public class Utils {

	public static Class<?> generate(BytecodeResolver creator) throws IllegalArgumentException, SecurityException, IllegalAccessException, InvocationTargetException, NoSuchMethodException{
		creator.setClassOutputPath(IssuesConstant.classOutPutPath);
		Class<?> cls = creator.resolve();
		if(creator instanceof ClassResolver){
		    cls.getMethod("main", String[].class).invoke(cls, new Object[]{null});
		}
		return cls;
	}
	
}
