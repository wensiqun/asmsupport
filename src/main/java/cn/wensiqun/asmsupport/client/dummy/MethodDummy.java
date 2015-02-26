package cn.wensiqun.asmsupport.client.dummy;

import cn.wensiqun.asmsupport.client.MethodBody;
import cn.wensiqun.asmsupport.core.clazz.AClass;

public interface MethodDummy {

	MethodDummy _private();
	
	MethodDummy _public();
	
	MethodDummy _protected();
	
	MethodDummy _synchronized();

	MethodDummy _static();
	
	MethodDummy _abstract();
	
	MethodDummy _final();
	
	MethodDummy setReturnType(AClass ret);
	
	MethodDummy setReturnType(Class<?> ret);

	MethodDummy setName(String name);
	
	MethodDummy setArgumentTypes(AClass... argus);

	MethodDummy setArgumentTypes(Class<?>... argus);
	
	MethodDummy setArgumentNames(String... argNames);
	
	MethodDummy throwTypes(Class<?>... exceptionTypes);
	
	/**
	 * 
	 * @param exceptionTypes
	 * @return
	 */
	MethodDummy throwTypes(AClass[] exceptionTypes);
	
	/**
	 * 
	 * @param body
	 * @return
	 */
	MethodDummy body(MethodBody body);
	
}
