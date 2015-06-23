package cn.wensiqun.asmsupport.client;

import cn.wensiqun.asmsupport.standard.utils.AsmsupportClassLoader;

public abstract class AbstractDummy {

	private AsmsupportClassLoader classLoader;
	
	AbstractDummy (AsmsupportClassLoader classLoader) {
		this.classLoader = classLoader;
	}

	public final AsmsupportClassLoader getClassLoader() {
		return classLoader;
	}
	
}
