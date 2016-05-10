package cn.wensiqun.asmsupport.core.loader;

import cn.wensiqun.asmsupport.core.utils.log.Log;
import cn.wensiqun.asmsupport.core.utils.log.LogFactory;
import cn.wensiqun.asmsupport.org.objectweb.asm.Type;
import cn.wensiqun.asmsupport.standard.def.clazz.AnyException;
import cn.wensiqun.asmsupport.standard.def.clazz.ArrayClass;
import cn.wensiqun.asmsupport.standard.def.clazz.IClass;
import cn.wensiqun.asmsupport.standard.def.clazz.ProductClass;
import cn.wensiqun.asmsupport.standard.error.ASMSupportException;
import cn.wensiqun.asmsupport.standard.utils.ASMSupportClassLoader;
import cn.wensiqun.asmsupport.utils.lang.ClassUtils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CachedThreadLocalClassLoader extends ASMSupportClassLoader {

	private static final Log LOG = LogFactory.getLog(CachedThreadLocalClassLoader.class);
	
	private static ThreadLocal<CachedThreadLocalClassLoader> threadLocalClassLoader = new ThreadLocal<CachedThreadLocalClassLoader>();

	private volatile Map<BytecodeKey, BytecodeValue> classByteMap = new ConcurrentHashMap<BytecodeKey, BytecodeValue>();

	/**
	 * Key : description
	 */
	private volatile Map<String, Reference<IClass>> cacheASMSupportClass = new ConcurrentHashMap<String, Reference<IClass>>();

	private CachedThreadLocalClassLoader() {
	}

	private CachedThreadLocalClassLoader(ClassLoader parent) {
		super(parent);
	}
	
	@Override
	public Class<?> defineClass(String name, byte[] classBytes)
			throws Exception {
		BytecodeKey bytecodeKey = new BytecodeKey(name);
        if(!classByteMap.containsKey(bytecodeKey)) {
        	Class<?> clazz = null;
        	ClassLoader refCl = getReferenceClassLoader();
        	if(refCl == null) {
        		refCl = Thread.currentThread().getContextClassLoader();
        	}
        	try {
                if (refCl.getResource(bytecodeKey.getName()) == null) {
                    Method method = ClassLoader.class.getDeclaredMethod("defineClass", new Class[] { String.class, byte[].class, int.class, int.class });
                    boolean originalAccessible = method.isAccessible();
                    method.setAccessible(true);
                    clazz = (Class<?>) method.invoke(refCl, new Object[] { name, classBytes, 0, classBytes.length });
                    method.setAccessible(originalAccessible);
                } else {
            		if(LOG.isPrintEnabled()) {
            			LOG.print("The class " + name + " has alread exist in classloader '" + refCl + "', define it to asmsupport classloader instead of.");
            		}
                }
        	} catch (SecurityException e) {
        		if(LOG.isPrintEnabled()) {
        			LOG.print("Defineclass to classloader '" + refCl + "' error cause by " + e.getMessage() + " define it to asmsupport classloader instead of.");
        		}
        	}
        	if(clazz == null) {
        		clazz = defineClass(name, classBytes, 0, classBytes.length);
        	}
            classByteMap.put(bytecodeKey, new BytecodeValue(classBytes, clazz));
            return clazz;
        }
        throw new ASMSupportException("The class " + name + " has alread defined.");
	}

	@Override
    public Class<?> afterDefineClass(Class<?> result, IClass itype) throws Exception {
        cacheASMSupportClass.put(itype.getDescription(), new SoftReference<IClass>(itype));
        return result;
    }

    
    @Override
    public InputStream getResourceAsStream(String name) {
        BytecodeKey key = new BytecodeKey(name);
        BytecodeValue byteArray = classByteMap.get(key);
        InputStream stream = null;
        if (byteArray != null) {
            stream = new ByteArrayInputStream(byteArray.getBytecodes());
        }
        
        if(stream == null && getReferenceClassLoader() != null) {
        	stream = getReferenceClassLoader().getResourceAsStream(key.getName());
        }

        if (stream == null) {
            stream = super.getResourceAsStream(key.getName());
        }

        if (stream == null) {
            stream = ClassLoader.getSystemClassLoader().getResourceAsStream(key.getName());
        }
        
        if(stream == null) {
        	stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(key.getName());
        }
        
        if(stream == null) {
            throw new ASMSupportException("Class not found : " + name);
        }
        
        return stream;
    }
    
    @Override
	public Class<?> loadClass(String name) throws ClassNotFoundException {
    	if(name.startsWith("[")) {
            return Class.forName(name);
        }
    	
    	BytecodeValue bv = classByteMap.get(new BytecodeKey(name));
    	Class<?> clazz = bv == null ? null : bv.getClazz(); 
    	
    	if(clazz == null && getReferenceClassLoader() != null) {
    		try {
                clazz = getReferenceClassLoader().loadClass(name);
    		} catch (ClassNotFoundException e) {
    		}
    	}
    	
    	if(clazz == null) {
    		try {
                clazz = super.loadClass(name);
    		} catch (ClassNotFoundException e) {
    		}
        }
    	
    	if(clazz == null) {
    		try {
    			clazz = ClassLoader.getSystemClassLoader().loadClass(name);
    		} catch (ClassNotFoundException e) {
    		}
        }
    	
    	if(clazz == null) {
    		try {
    			clazz = Thread.currentThread().getContextClassLoader().loadClass(name);
    		} catch (ClassNotFoundException e) {
    		}
        }
    	
    	if(clazz == null) {
    		throw new ClassNotFoundException(name);
    	}
    	
    	return clazz;
	}
    
	private static class BytecodeKey {

		/**
		 * format like : a/b/c/D.class
		 */
		private String name;

		private BytecodeKey(Class<?> clazz) {
			this.name = translateClassNameToResource(clazz.getName());
		}

		/**
		 * name format like : a/b/c/D.class
		 * 
		 * @param name
		 */
		private BytecodeKey(String name) {
			if (name.endsWith(".class")) {
				this.name = name.substring(0, name.length() - 6).replace('.',
						'/')
						+ ".class";
			} else {
				this.name = translateClassNameToResource(name);
			}
		}

		private String translateClassNameToResource(String classQualifiedName) {
			return classQualifiedName.replace('.', '/') + ".class";
		}

		public String getName() {
			return name;
		}

		@Override
		public int hashCode() {
			return name.hashCode();
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			BytecodeKey other = (BytecodeKey) obj;
			if (name == null) {
				if (other.name != null)
					return false;
			} else if (!name.equals(other.name))
				return false;
			return true;
		}

	}

	private static class BytecodeValue {

		private byte[] bytecodes;

		private Class<?> clazz;

		public BytecodeValue(byte[] bytecodes, Class<?> clazz) {
			this.bytecodes = bytecodes;
			this.clazz = clazz;
		}

		public byte[] getBytecodes() {
			return bytecodes;
		}

		public Class<?> getClazz() {
			return clazz;
		}

	}

	public static CachedThreadLocalClassLoader getInstance() {
		return getInstance(null);
	}

	public static CachedThreadLocalClassLoader getInstance(ClassLoader parent) {
		CachedThreadLocalClassLoader ctcl = threadLocalClassLoader.get();
		if (ctcl == null) {
			ctcl = parent == null ? new CachedThreadLocalClassLoader()
					: new CachedThreadLocalClassLoader(parent);
			threadLocalClassLoader.set(ctcl);
		}
		return ctcl;
	}

	@Override
	public IClass getType(Class<?> javaClass) {
		String key = javaClass.getName();
		Reference<IClass> ref = cacheASMSupportClass.get(key);
		if (ref == null || ref.get() == null) {
			IClass clazz;
			if (AnyException.class.equals(javaClass)) {
				clazz = new AnyException(this);
			} else if (javaClass.isArray()) {
				clazz = getArrayType(
						ClassUtils.getRootComponentType(javaClass), Type
								.getType(javaClass).getDimensions());
			} else {
				clazz = new ProductClass(javaClass, this);
			}
			ref = new WeakReference<IClass>(clazz);
			cacheASMSupportClass.put(key, new WeakReference<IClass>(clazz));
		}
		return ref.get();
	}

	@Override
	public IClass getType(String possible) {
		String desc = ClassUtils.getDescription(possible);
		Reference<IClass> ref = cacheASMSupportClass.get(desc);
		if (ref == null || ref.get() == null) {
			IClass clazz;
			if ("E".equals(desc)) {
				clazz = new AnyException(this);
			} else {
				Class<?> reflexClazz = ClassUtils.primitiveToClass(desc);
				if (reflexClazz == null) {
					try {
						reflexClazz = loadClass(ClassUtils.getClassname(possible));
					} catch (ClassNotFoundException e) {
						throw new ASMSupportException(e);
					}
				}
				if (reflexClazz.isArray()) {
					clazz = getArrayType(
							ClassUtils.getRootComponentType(reflexClazz), Type
									.getType(reflexClazz).getDimensions());
				} else {
					clazz = new ProductClass(reflexClazz, this);
				}
			}
			ref = new WeakReference<IClass>(clazz); 
			cacheASMSupportClass.put(desc, ref);
		}
		return ref.get();
	}

	@Override
	public ArrayClass getArrayType(Class<?> root, int dim) {
		return getArrayType(getType(root), dim);
	}

	@Override
	public ArrayClass getArrayType(IClass root, int dim) {
		String nameKey = getDescription(root, dim);
		Reference<IClass> ref = cacheASMSupportClass.get(nameKey.toString());
		if (ref == null || ref.get() == null) {
			StringBuilder arrayClassDesc = new StringBuilder();
			int tmpDim = dim;
			while (tmpDim-- > 0) {
				arrayClassDesc.append("[");
			}
			arrayClassDesc.append(root.getDescription());
			ArrayClass arrayClass = new ArrayClass(root, dim, this);
			ref = new WeakReference<IClass>(arrayClass);
			cacheASMSupportClass.put(nameKey, ref);
		}
		return (ArrayClass) ref.get();
	}

	private String getDescription(IClass root, int dim) {
		StringBuilder nameKey = new StringBuilder();
		while (dim-- > 0) {
			nameKey.append('[');
		}
		if (root.isPrimitive()) {
			nameKey.append(root.getDescription());
		} else {
			nameKey.append(root.getDescription());
		}
		return nameKey.toString();
	}


}
