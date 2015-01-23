package cn.wensiqun.asmsupportclient.puppet;

import cn.wensiqun.asmsupport.core.clazz.AClass;


public interface ClassPuppet {

	ClassPuppet _private();
	
	ClassPuppet _public();
	
	ClassPuppet _protected();
	
	ClassPuppet _static();
	
	ClassPuppet _abstract();
	
	ClassPuppet _final();
	
	ClassPuppet _extends(Class<?> parent);

	ClassPuppet _extends(AClass parent);
	
	ClassPuppet _implements(Class<?>... interfaces);

	ClassPuppet _implements(AClass... interfaces);
	
	FieldPuppet createField(); 
	
	ConstructorPuppet createConstructor();

	MethodPuppet createMethod();
	
	StaticMethodPuppet createStaticMethod();
}
