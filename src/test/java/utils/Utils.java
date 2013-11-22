package utils;

import cn.wensiqun.asmsupport.creator.ClassCreator;
import cn.wensiqun.asmsupport.creator.IClassContext;

public class Utils {

	public static Class<?> generate(IClassContext creator){
		creator.setClassOutPutPath(".//target//");
		Class<?> cls = creator.startup();
		if(creator instanceof ClassCreator){
			try {
				cls.getMethod("main", String[].class).invoke(cls, new Object[]{null});
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
		return cls;
	}
	
}
