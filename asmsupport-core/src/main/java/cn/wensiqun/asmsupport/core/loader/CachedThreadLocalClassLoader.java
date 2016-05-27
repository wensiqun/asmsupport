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
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CachedThreadLocalClassLoader extends ASMSupportClassLoader {

    private static final Log LOG = LogFactory.getLog(CachedThreadLocalClassLoader.class);

    private static ThreadLocal<Map<Object, CachedThreadLocalClassLoader>> threadLocalClassLoader = new ThreadLocal<>();

    private volatile Map<BytecodeKey, BytecodeValue> classByteMap = new ConcurrentHashMap<>();

    private static final Object NULL_PARENT = new Object();

    /**
     * Key : description
     */
    private volatile Map<String, Reference<IClass>> cache = new ConcurrentHashMap<>();

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
                        LOG.print("The class " + name + " has already exist in classloader '" + refCl + "', define it to asmsupport classloader instead of.");
                    }
                }
            } catch (SecurityException e) {
                if(LOG.isPrintEnabled()) {
                    LOG.print("define class to classloader '" + refCl + "' error cause by " + e.getMessage() + " define it to asmsupport classloader instead of.");
                }
            }
            if(clazz == null) {
                clazz = defineClass(name, classBytes, 0, classBytes.length);
            }
            classByteMap.put(bytecodeKey, new BytecodeValue(classBytes, clazz));
            return clazz;
        }
        throw new ASMSupportException("The class " + name + " has already defined.");
    }

    @Override
    public Class<?> afterDefineClass(Class<?> result, IClass type) throws Exception {
        cache.put(type.getDescription(), new SoftReference<>(type));
        return result;
    }


    @Override
    public InputStream getResourceAsStream(String name) {
        BytecodeKey key = new BytecodeKey(name);
        BytecodeValue byteArray = classByteMap.get(key);
        InputStream stream = null;
        if (byteArray != null) {
            stream = new ByteArrayInputStream(byteArray.getBytes());
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

        /**
         * name format like : a/b/c/D.class
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

        private byte[] bytes;

        private Class<?> clazz;

        private BytecodeValue(byte[] bytes, Class<?> clazz) {
            this.bytes = bytes;
            this.clazz = clazz;
        }

        private byte[] getBytes() {
            return bytes;
        }

        private Class<?> getClazz() {
            return clazz;
        }

    }

    public static CachedThreadLocalClassLoader getInstance() {
        return getInstance(null);
    }

    public static CachedThreadLocalClassLoader getInstance(ClassLoader parent) {
        Map<Object, CachedThreadLocalClassLoader> instance = threadLocalClassLoader.get();
        if (instance == null) {
            threadLocalClassLoader.set(instance = new HashMap<>());
        }
        Object key = parent == null ? NULL_PARENT : parent;
        CachedThreadLocalClassLoader classLoader = instance.get(key);
        if(classLoader == null) {
            instance.put(key, classLoader = parent == null ? new CachedThreadLocalClassLoader()
                    : new CachedThreadLocalClassLoader(parent));
        }
        return classLoader;
    }

    @Override
    public IClass getType(Class<?> javaClass) {
        String desc = Type.getDescriptor(javaClass);
        Reference<IClass> ref = cache.get(desc);
        IClass type = ref == null ? null : ref.get();
        if (type == null) {
            if (AnyException.class.equals(javaClass)) {
                type = new AnyException(this);
            } else if (javaClass.isArray()) {
                type = getArrayType(
                        ClassUtils.getRootComponentType(javaClass), Type
                                .getType(javaClass).getDimensions());
            } else {
                type = new ProductClass(javaClass, this);
            }
            cache.put(desc, new WeakReference<>(type));
        }
        return type;
    }

    @Override
    public IClass getType(String possible) {
        String desc = ClassUtils.getDescription(possible);
        Reference<IClass> ref = cache.get(desc);
        IClass type = ref == null ? null : ref.get();
        if (type == null) {
            if ("E".equals(desc)) {
                type = new AnyException(this);
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
                    type = getArrayType(
                            ClassUtils.getRootComponentType(reflexClazz), Type
                                    .getType(reflexClazz).getDimensions());
                } else {
                    type = new ProductClass(reflexClazz, this);
                }
            }
            cache.put(desc, new WeakReference<>(type));
        }
        return type;
    }

    @Override
    public ArrayClass getArrayType(Class<?> root, int dim) {
        return getArrayType(getType(root), dim);
    }

    @Override
    public ArrayClass getArrayType(IClass root, int dim) {
        String nameKey = getDescription(root, dim);
        Reference<IClass> ref = cache.get(nameKey);
        if (ref == null || ref.get() == null) {
            ArrayClass arrayClass = new ArrayClass(root, dim, this);
            ref = new WeakReference<IClass>(arrayClass);
            cache.put(nameKey, ref);
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
