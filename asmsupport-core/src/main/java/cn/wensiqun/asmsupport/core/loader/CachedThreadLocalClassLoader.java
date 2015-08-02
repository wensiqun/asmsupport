package cn.wensiqun.asmsupport.core.loader;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import cn.wensiqun.asmsupport.org.objectweb.asm.Type;
import cn.wensiqun.asmsupport.standard.def.clazz.AnyException;
import cn.wensiqun.asmsupport.standard.def.clazz.ArrayClass;
import cn.wensiqun.asmsupport.standard.def.clazz.IClass;
import cn.wensiqun.asmsupport.standard.def.clazz.ProductClass;
import cn.wensiqun.asmsupport.standard.error.ASMSupportException;
import cn.wensiqun.asmsupport.standard.utils.AsmsupportClassLoader;
import cn.wensiqun.asmsupport.utils.lang.ClassUtils;

public class CachedThreadLocalClassLoader extends AsmsupportClassLoader {

	private static ThreadLocal<CachedThreadLocalClassLoader> threadLocalClassLoader = new ThreadLocal<CachedThreadLocalClassLoader>();

	private volatile Map<BytecodeKey, BytecodeValue> classByteMap = new ConcurrentHashMap<BytecodeKey, BytecodeValue>();

	/**
	 * Key : description
	 */
	private volatile Map<String, IClass> cacheAsmsuportClass = new ConcurrentHashMap<String, IClass>();

	private CachedThreadLocalClassLoader() {
	}

	private CachedThreadLocalClassLoader(ClassLoader parent) {
		super(parent);
	}

	public Class<?> defineClass(String name, byte[] classBytes, IClass itype)
			throws Exception {
		BytecodeKey bytecodeKey = new BytecodeKey(name);
		if (!classByteMap.containsKey(bytecodeKey)) {
			Class<?> clazz;
			if (getParent().getResource(bytecodeKey.getName()) == null) {
				Method method = ClassLoader.class.getDeclaredMethod(
						"defineClass", new Class[] { String.class,
								byte[].class, int.class, int.class });
				boolean originalAccessible = method.isAccessible();
				method.setAccessible(true);
				clazz = (Class<?>) method.invoke(getParent(), new Object[] {
						name, classBytes, 0, classBytes.length });
				method.setAccessible(originalAccessible);
			} else {
				clazz = super.defineClass(name, classBytes, 0,
						classBytes.length);
			}
			cacheAsmsuportClass.put(name, itype);
			classByteMap.put(bytecodeKey, new BytecodeValue(classBytes, clazz));
			return clazz;
		}
		throw new ASMSupportException("The class " + name + "has alread exist.");
	}

	@Override
	public Class<?> doAsmsupportFindClass(String name)
			throws ClassNotFoundException {
		BytecodeValue bv = classByteMap.get(new BytecodeKey(name));
		if (bv != null) {
			return bv.getClazz();
		}
		return null;
	}

	/**
	 * format like : a/b/c/D.class
	 */
	@Override
	public InputStream getResourceAsStream(String name) {
		BytecodeKey key = new BytecodeKey(name);
		BytecodeValue byteArray = classByteMap.get(key);
		InputStream stream = null;
		if (byteArray != null) {
			stream = new ByteArrayInputStream(byteArray.getBytecodes());
		}

		if (stream == null) {
			stream = super.getResourceAsStream(key.getName());
		}

		if (stream == null) {
			stream = ClassLoader.getSystemClassLoader().getResourceAsStream(
					key.getName());
		}

		if (stream == null) {
			throw new ASMSupportException("Class not found : " + name);
		}
		return stream;
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
		IClass clazz = cacheAsmsuportClass.get(key);
		if (clazz == null) {
			if (AnyException.class.equals(javaClass)) {
				clazz = new AnyException(this);
			} else if (javaClass.isArray()) {
				clazz = getArrayType(
						ClassUtils.getRootComponentType(javaClass), Type
								.getType(javaClass).getDimensions());
			} else {
				clazz = new ProductClass(javaClass, this);
			}
			cacheAsmsuportClass.put(key, clazz);
		}
		return clazz;
	}

	@Override
	public IClass getType(String possible) {
		String desc = getDescription(possible);
		IClass clazz = cacheAsmsuportClass.get(desc);
		if (clazz == null) {
			if ("E".equals(desc)) {
				clazz = new AnyException(this);
			} else {
				Class<?> reflexClazz = ClassUtils.primitiveToClass(desc);
				if (reflexClazz == null) {
					try {
						reflexClazz = loadClass(desc);
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
			cacheAsmsuportClass.put(desc, clazz);
		}
		return clazz;
	}

	@Override
	public ArrayClass getArrayType(Class<?> root, int dim) {
		return getArrayType(getType(root), dim);
	}

	@Override
	public ArrayClass getArrayType(IClass root, int dim) {
		String nameKey = getDescription(root, dim);
		ArrayClass arrayClass = (ArrayClass) cacheAsmsuportClass.get(nameKey
				.toString());
		if (arrayClass == null) {
			StringBuilder arrayClassDesc = new StringBuilder();
			int tmpDim = dim;
			while (tmpDim-- > 0) {
				arrayClassDesc.append("[");
			}
			arrayClassDesc.append(root.getDescription());
			arrayClass = new ArrayClass(root, dim, this);
			cacheAsmsuportClass.put(nameKey, arrayClass);
		}
		return arrayClass;
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

	/**
	 * The possible may be a class name, description.
	 * 
	 * <ul>
	 *     <li>Primitive type name : int</li>
	 *     <li>Primitive type description : I</li>
	 *     <li>Primitive array type name : [I</li>
	 *     <li>Primitive array type description : [I</li>
	 *     <li>Primitive array declared type name : int[][]</li>
	 *     <li>Object type name : java.lang.Object</li>
	 *     <li>Object type description : Ljava/lang/Object;</li>
	 *     <li>Object array type name : [Ljava.lang.Object;</li>
	 *     <li>Object array type description : [Ljava/lang/Object;</li>
	 *     <li>Object array declared type name : java.lang.Object[][]</li>
	 * </ul>
	 * 
	 * @param possible
	 * @return
	 */
	private String getDescription(String possible) {
		if (possible.endsWith(";") || "V".equals(possible)
				|| "Z".equals(possible) || "C".equals(possible)
				|| "B".equals(possible) || "S".equals(possible)
				|| "I".equals(possible) || "F".equals(possible)
				|| "J".equals(possible) || "D".equals(possible)
				|| "E".equals(possible)) {
			return possible;
		}

		if ("void".equals(possible)) {
			return "V";
		}

		if ("boolean".equals(possible)) {
			return "Z";
		}

		if ("char".equals(possible)) {
			return "C";
		}

		if ("byte".equals(possible)) {
			return "B";
		}

		if ("short".equals(possible)) {
			return "S";
		}

		if ("int".equals(possible)) {
			return "I";
		}

		if ("float".equals(possible)) {
			return "F";
		}

		if ("long".equals(possible)) {
			return "J";
		}

		if ("double".equals(possible)) {
			return "D";
		}

		if ("ANY_EXCEPTION".equals(possible)) {
			return "E";
		}

		if (possible.startsWith("[")) {
			return possible.replace('.', '/');
		}

		if (possible.endsWith("]")) {
			int splitIdx = possible.indexOf('[');
			String componentDesc = getDescription(possible.substring(0,
					splitIdx));
			String dimension = possible.substring(splitIdx).replace("]", "");
			return dimension + componentDesc;
		}

		return "L" + possible.replace('.', '/') + ";";
	}
	
	public static void main(String... args) {
		/*CachedThreadLocalClassLoader cc = new CachedThreadLocalClassLoader();
		System.out.println(cc.getDescription("int[][]"));
		System.out.println(Type.getDescriptor(String[][].class));*/
	}

}
