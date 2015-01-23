package cn.wensiqun.asmsupportclient.puppet;

import cn.wensiqun.asmsupport.core.clazz.AClass;

public interface FieldPuppet {

	FieldPuppet _private();
	
	FieldPuppet _public();
	
	FieldPuppet _protected();
	
	FieldPuppet setType(AClass ret);
	
	FieldPuppet setType(Class<?> ret);

	FieldPuppet setName(String name);
	
}
