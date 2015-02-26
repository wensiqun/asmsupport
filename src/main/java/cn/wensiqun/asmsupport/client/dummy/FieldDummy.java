package cn.wensiqun.asmsupport.client.dummy;

import cn.wensiqun.asmsupport.core.clazz.AClass;

public interface FieldDummy {

	FieldDummy _private();
	
	FieldDummy _public();
	
	FieldDummy _protected();
	
	FieldDummy setType(AClass ret);
	
	FieldDummy setType(Class<?> ret);

	FieldDummy setName(String name);
	
}
