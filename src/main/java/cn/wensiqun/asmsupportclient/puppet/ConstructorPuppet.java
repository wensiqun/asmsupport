package cn.wensiqun.asmsupportclient.puppet;

import cn.wensiqun.asmsupport.core.clazz.AClass;
import cn.wensiqun.asmsupportclient.ConstructorBody;

public interface ConstructorPuppet {

	ConstructorPuppet _private();
	
	ConstructorPuppet _public();
	
	ConstructorPuppet _protected();
	
	ConstructorPuppet setArgumentTypes(AClass... argus);

	ConstructorPuppet setArgumentTypes(Class<?>... argus);
	
	ConstructorPuppet setArgumentNames(String... argNames);
	
	ConstructorPuppet throwTypes(Class<?>... exceptionTypes);
	
	/**
	 * 
	 * @param exceptionTypes
	 * @return
	 */
	ConstructorPuppet throwTypes(AClass[] exceptionTypes);
	
	/**
	 * 
	 * @param body
	 * @return
	 */
	ConstructorPuppet body(ConstructorBody body);
	
}
