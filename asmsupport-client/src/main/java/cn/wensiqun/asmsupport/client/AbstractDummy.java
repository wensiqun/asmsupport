package cn.wensiqun.asmsupport.client;

import cn.wensiqun.asmsupport.standard.utils.ASMSupportClassLoader;

public abstract class AbstractDummy {

	private ASMSupportClassLoader classLoader;
	
	AbstractDummy (ASMSupportClassLoader classLoader) {
		this.classLoader = classLoader;
	}

	public final ASMSupportClassLoader getClassLoader() {
		return classLoader;
	}
	
}
