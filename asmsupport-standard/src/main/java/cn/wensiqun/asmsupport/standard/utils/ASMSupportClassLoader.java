package cn.wensiqun.asmsupport.standard.utils;

import cn.wensiqun.asmsupport.standard.def.clazz.ClassHolder;
import cn.wensiqun.asmsupport.standard.def.clazz.IClass;

public abstract class ASMSupportClassLoader extends ClassLoader implements ClassHolder {
	
	private ClassLoader referenceClassLoader;
	
    public ASMSupportClassLoader() {
    }

    public ASMSupportClassLoader(ClassLoader parent) {
        super(parent);
        referenceClassLoader = parent;
    }

    /**
     * Define a class with a class name.
     * 
     * @param name
     * @param classBytes
     * @return
     * @throws Exception
     */
    public Class<?> defineClass(String name, byte[] classBytes, IClass type) throws Exception {
    	Class<?> result = defineClass(name, classBytes);
    	afterDefineClass(result, type);
    	return result;
    }

    protected abstract Class<?> afterDefineClass(Class<?> result, IClass type) throws Exception;
    
    public abstract Class<?> defineClass(String name, byte[] classBytes) throws Exception;

	public ClassLoader getReferenceClassLoader() {
		return referenceClassLoader;
	}
	
}
