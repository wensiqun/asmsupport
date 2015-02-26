package cn.wensiqun.asmsupport.client.dummy;

import cn.wensiqun.asmsupport.client.StaticMethodBody;
import cn.wensiqun.asmsupport.core.clazz.AClass;

public interface StaticMethodDummy {

	StaticMethodDummy _private();
	
	StaticMethodDummy _public();
	
	StaticMethodDummy _protected();
	
	StaticMethodDummy _synchronized();

	StaticMethodDummy _static();
	
	StaticMethodDummy _final();
	
	StaticMethodDummy setReturnType(AClass ret);
	
	StaticMethodDummy setReturnType(Class<?> ret);

	StaticMethodDummy setName(String name);
	
	StaticMethodDummy setArgumentTypes(AClass... argus);

	StaticMethodDummy setArgumentTypes(Class<?>... argus);
	
	StaticMethodDummy setArgumentNames(String... argNames);
	
	StaticMethodDummy throwTypes(Class<?>... exceptionTypes);
	
	/**
	 * 
	 * @param exceptionTypes
	 * @return
	 */
	StaticMethodDummy throwTypes(AClass[] exceptionTypes);
	
	/**
	 * 
	 * @param body
	 * @return
	 */
	StaticMethodDummy body(StaticMethodBody body);
	
}
