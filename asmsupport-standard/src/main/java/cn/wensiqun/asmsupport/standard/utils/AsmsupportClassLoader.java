package cn.wensiqun.asmsupport.standard.utils;

import cn.wensiqun.asmsupport.standard.def.clazz.ClassHolder;
import cn.wensiqun.asmsupport.standard.def.clazz.IClass;

public abstract class AsmsupportClassLoader extends ClassLoader implements ClassHolder {

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

	
	@Override
	public Class<?> findClass(String name) throws ClassNotFoundException {
		if(name.startsWith("[")) {
			return Class.forName(name);
		}
		Class<?> clazz = doAsmsupportFindClass(name);
		if(clazz == null) {
			clazz = super.findClass(name);
		}
		return clazz;
	}
	
	public abstract Class<?> doAsmsupportFindClass(String name) throws ClassNotFoundException;

}
