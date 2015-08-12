package cn.wensiqun.asmsupport.standard.utils;

import cn.wensiqun.asmsupport.standard.def.clazz.ClassHolder;
import cn.wensiqun.asmsupport.standard.def.clazz.IClass;

public abstract class AsmsupportClassLoader extends ClassLoader implements ClassHolder {
	
	private ClassLoader referenceClassLoader;

	public AsmsupportClassLoader() {
	}

	public AsmsupportClassLoader(ClassLoader parent) {
		super(parent);
	}

	/**
	 * Define a class with a class name.
	 * 
	 * @param name
	 * @param classBytes
	 * @return
	 * @throws Exception
	 */
	public abstract Class<?> defineClass(String name, byte[] classBytes, IClass itype) throws Exception;

	public ClassLoader getReferenceClassLoader() {
		return referenceClassLoader;
	}
	
}
