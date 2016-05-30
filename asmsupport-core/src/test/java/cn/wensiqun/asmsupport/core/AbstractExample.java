package cn.wensiqun.asmsupport.core;

import cn.wensiqun.asmsupport.core.build.BytecodeResolver;
import cn.wensiqun.asmsupport.core.build.resolver.ClassResolver;
import cn.wensiqun.asmsupport.core.definition.value.Value;
import cn.wensiqun.asmsupport.core.definition.variable.GlobalVariable;
import cn.wensiqun.asmsupport.core.loader.CachedThreadLocalClassLoader;
import cn.wensiqun.asmsupport.core.utils.log.LogFactory;

public abstract class AbstractExample {
    
	/**
	 * system.out global variable
	 */
	public static GlobalVariable systemOut;
	static {
		systemOut = Value.value(CachedThreadLocalClassLoader.getInstance(), System.class)
				.field("out");
		if("false".equalsIgnoreCase(System.getProperty("skiplog"))) {
			LogFactory.LOG_FACTORY_LOCAL.set(new LogFactory());
		}
	}
	public static Class<?> generate(BytecodeResolver creator){
		return generate(creator, true);
	}
	

	public static Class<?> generate(BytecodeResolver creator, boolean callMain){
		//_这是Class的输出路径。主要为了调试作用。我们通过asmsupport生成的class将获输出到这个路径
		//你可以通过反编译软件看看我们生成的结果
		creator.setClassOutputPath(".//target//asmsupport-test-generated");
		
		//这个就是个开关。前面我们把该创建的方法变量都放到了传送带上了。调用startup
		//启动传送带，将上面的东西一个个处理给我返回一个我们需要的成品（就是class了）
		Class<?> cls = creator.resolve();
		
		//如果创建的是非枚举类型或者非接口类型则调用main方法
		if(callMain) {
			if(creator instanceof ClassResolver){
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
