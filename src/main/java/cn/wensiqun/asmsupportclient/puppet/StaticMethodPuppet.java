package cn.wensiqun.asmsupportclient.puppet;

import cn.wensiqun.asmsupport.core.clazz.AClass;
import cn.wensiqun.asmsupportclient.StaticMethodBody;

public interface StaticMethodPuppet {

	StaticMethodPuppet _private();
	
	StaticMethodPuppet _public();
	
	StaticMethodPuppet _protected();
	
	StaticMethodPuppet _synchronized();

	StaticMethodPuppet _static();
	
	StaticMethodPuppet _final();
	
	StaticMethodPuppet setReturnType(AClass ret);
	
	StaticMethodPuppet setReturnType(Class<?> ret);

	StaticMethodPuppet setName(String name);
	
	StaticMethodPuppet setArgumentTypes(AClass... argus);

	StaticMethodPuppet setArgumentTypes(Class<?>... argus);
	
	StaticMethodPuppet setArgumentNames(String... argNames);
	
	StaticMethodPuppet throwTypes(Class<?>... exceptionTypes);
	
	/**
	 * 
	 * @param exceptionTypes
	 * @return
	 */
	StaticMethodPuppet throwTypes(AClass[] exceptionTypes);
	
	/**
	 * 
	 * @param body
	 * @return
	 */
	StaticMethodPuppet body(StaticMethodBody body);
	
}
