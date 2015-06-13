package cn.wensiqun.asmsupport.core.loader;

import java.io.InputStream;


public abstract class AsmsupportClassLoader extends ClassLoader {

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
	public abstract Class<?> defineClass(String name, byte[] classBytes) throws Exception;

	/**
	 * Get class resource according to class name.
	 * 
	 * @return
	 */
	public abstract InputStream loadClassResource(String name);
}
