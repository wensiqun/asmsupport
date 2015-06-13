package cn.wensiqun.asmsupport.core.loader;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import cn.wensiqun.asmsupport.utils.collections.CollectionUtils;
import cn.wensiqun.asmsupport.utils.lang.StringUtils;

public class CachedThreadLocalClassLoader extends AsmsupportClassLoader {

	private static ThreadLocal<CachedThreadLocalClassLoader> threadLocalClassLoader = new ThreadLocal<CachedThreadLocalClassLoader>();
	
	private volatile Map<Key, byte[]> classByteMap = new ConcurrentHashMap<Key, byte[]>();

	private CachedThreadLocalClassLoader() {
	}

	private CachedThreadLocalClassLoader(ClassLoader parent) {
		super(parent);
	}

	@Override
	public Class<?> defineClass(String name, byte[] classBytes) throws Exception {
		String resource = name.replace('.', '/') + ".class";
		Class<?> clazz;
		if (getParent().getResource(resource) == null) {
			Method method = ClassLoader.class.getDeclaredMethod("defineClass", new Class[] { String.class, byte[].class, int.class, int.class });
			boolean originalAccessible = method.isAccessible();
			method.setAccessible(true);
			clazz = (Class<?>) method.invoke(getParent(), new Object[] { name, classBytes, 0, classBytes.length });
			method.setAccessible(originalAccessible);
		} else {
			clazz = super.defineClass(name, classBytes, 0, classBytes.length);
		}
		classByteMap.put(new Key(clazz), classBytes);
		return clazz;
	}

	@Override
	public Class<?> findClass(String name) throws ClassNotFoundException {
		Set<Key> keySet = classByteMap.keySet();
		if (CollectionUtils.isNotEmpty(keySet)) {
			for (Key k : keySet) {
				if (k.equals(new Key(translateClassName(name)))) {
					return k.clazz;
				}
			}
		}
		return super.findClass(name);
	}

	@Override
	public InputStream loadClassResource(String name) {
		// gets an input stream to read the bytecode of the class
		String resource = name.replace('.', '/') + ".class";
		InputStream is = getResourceAsStream(resource);
		return is;
	}
	
	/**
	 * format like : a/b/c/D.class
	 */
	@Override
	public InputStream getResourceAsStream(String name) {
		byte[] byteArray = classByteMap.get(new Key(name));
		InputStream stream = null;
		if (byteArray != null) {
			stream = new ByteArrayInputStream(byteArray);
		}

		if (stream == null) {
			stream = super.getResourceAsStream(name);
		}

		if (stream == null) {
			stream = ClassLoader.getSystemClassLoader().getResourceAsStream(
					name);
		}
		return stream;
	}

	/**
	 * e.g : "<b>jw.asmsupport.loader.ASMClassLoader<b>" convert to
	 * "<b>jw/asmsupport/loader/ASMClassLoader.class<b>"
	 * 
	 * @param classQualifiedName
	 * @return
	 */
	private String translateClassName(String classQualifiedName) {
		return classQualifiedName.replace('.', '/') + ".class";
	}

	private class Key {

		private Class<?> clazz;

		/**
		 * format like : a/b/c/D.class
		 */
		private String name;

		private Key(Class<?> clazz) {
			this.clazz = clazz;
		}

		/**
		 * name format like : a/b/c/D.class
		 * 
		 * @param name
		 */
		private Key(String name) {
			this.name = name;
		}

		@Override
		public int hashCode() {
			if (clazz != null) {
				return translateClassName(clazz.getName()).hashCode();
			} else {
				return name.hashCode();
			}
		}

		@Override
		public boolean equals(Object obj) {
			if (obj != null && obj instanceof Key) {
				Key k = (Key) obj;
				String currentName = clazz == null ? name
						: translateClassName(clazz.getName());
				String compareName = k.clazz == null ? k.name
						: translateClassName(k.clazz.getName());
				if (StringUtils.equals(currentName, compareName)) {
					return true;
				}
			}
			return false;
		}
	}
	
	public static CachedThreadLocalClassLoader getInstance() {
		return getInstance(null);
	}
	
	public static CachedThreadLocalClassLoader getInstance(ClassLoader parent) {
		CachedThreadLocalClassLoader ctcl = threadLocalClassLoader.get();
		if(ctcl == null) {
			ctcl = parent == null ? new CachedThreadLocalClassLoader() : new CachedThreadLocalClassLoader(parent);
			threadLocalClassLoader.set(ctcl);
		}
		return ctcl;
	}
	
}
