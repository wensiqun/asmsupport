package cn.wensiqun.asmsupportclient.puppet;

import cn.wensiqun.asmsupport.core.clazz.AClass;
import cn.wensiqun.asmsupportclient.MethodBody;
import cn.wensiqun.asmsupportclient.ProgramBlock;

public interface MethodPuppet {

	MethodPuppet _private();
	
	MethodPuppet _public();
	
	MethodPuppet _protected();
	
	MethodPuppet _synchronized();

	MethodPuppet _static();
	
	MethodPuppet _abstract();
	
	MethodPuppet _final();
	
	MethodPuppet setReturnType(AClass ret);
	
	MethodPuppet setReturnType(Class<?> ret);

	MethodPuppet setName(String name);
	
	MethodPuppet setArgumentTypes(AClass... argus);

	MethodPuppet setArgumentTypes(Class<?>... argus);
	
	MethodPuppet setArgumentNames(String... argNames);
	
	MethodPuppet throwTypes(Class<?>... exceptionTypes);
	
	/**
	 * 
	 * @param exceptionTypes
	 * @return
	 */
	MethodPuppet throwTypes(AClass[] exceptionTypes);
	
	/**
	 * 
	 * @param body
	 * @return
	 */
	MethodPuppet body(MethodBody body);
	
}
