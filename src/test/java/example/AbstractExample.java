package example;

import cn.wensiqun.asmsupport.core.clazz.AClassFactory;
import cn.wensiqun.asmsupport.core.creator.clazz.ClassCreatorInternal;
import cn.wensiqun.asmsupport.core.definition.variable.GlobalVariable;
import cn.wensiqun.asmsupport.generic.creator.IClassContext;

public abstract class AbstractExample {
    
	/**
	 * system.out global variable
	 */
	public static GlobalVariable systemOut = AClassFactory.getProductClass(System.class).getGlobalVariable("out");
	
	public static Class<?> generate(IClassContext creator){
		return generate(creator, true);
	}
	

	public static Class<?> generate(IClassContext creator, boolean callMain){
		//_这是Class的输出路径。主要为了调试作用。我们通过asmsupport生成的class将获输出到这个路径
		//你可以通过反编译软件看看我们生成的结果
		creator.setClassOutPutPath(".//target//");
		
		//这个就是个开关。前面我们把该创建的方法变量都放到了传送带上了。调用startup
		//启动传送带，将上面的东西一个个处理给我返回一个我们需要的成品（就是class了）
		Class<?> cls = creator.startup();
		
		//如果创建的是非枚举类型或者非接口类型则调用main方法
		if(callMain) {
			if(creator instanceof ClassCreatorInternal){
				try {
					cls.getMethod("main", String[].class).invoke(cls, new Object[]{null});
				} catch (Exception e) {
					e.printStackTrace();
				} 
			}
		}
		
		return cls;
	}
}
