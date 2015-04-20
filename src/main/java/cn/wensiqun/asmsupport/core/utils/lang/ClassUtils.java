/**    
 *  Asmsupport is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package cn.wensiqun.asmsupport.core.utils.lang;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import cn.wensiqun.asmsupport.core.clazz.AClass;
import cn.wensiqun.asmsupport.core.clazz.AClassFactory;
import cn.wensiqun.asmsupport.core.definition.method.meta.AMethodMeta;
import cn.wensiqun.asmsupport.core.exception.ASMSupportException;
import cn.wensiqun.asmsupport.core.loader.ASMClassLoader;
import cn.wensiqun.asmsupport.core.utils.AClassUtils;
import cn.wensiqun.asmsupport.core.utils.asm.ClassAdapter;
import cn.wensiqun.asmsupport.org.objectweb.asm.ClassReader;
import cn.wensiqun.asmsupport.org.objectweb.asm.MethodVisitor;
import cn.wensiqun.asmsupport.org.objectweb.asm.Type;

/**
 * 
 * @author 温斯群(Joe Wen)
 *
 */
public class ClassUtils { 

    /**
     * The inner class separator character: <code>'$' == {@value}</code>.
     */
    public static final char INNER_CLASS_SEPARATOR_CHAR = '$';

    /**
     * The package separator character: <code>'&#x2e;' == {@value}</code>.
     */
    public static final char PACKAGE_SEPARATOR_CHAR = '.';
    
    /**
     * 
     * @param cls
     * @return
     */
    public static Class<?> getRootComponentType(Class<?> cls) {
        if (cls.isArray()) {
            return getRootComponentType(cls.getComponentType());
        } else {
            return cls;
        }
    }

    /**
     * 
     * @param arrayClass
     * @return
     */
    public static int getDimension(Class<?> arrayClass) {
        if (arrayClass.isArray()) {
            return StringUtils.findAllIndexes(arrayClass.getName(), "[").length;
        } else {
            return 0;
        }
    }

    /**
     * determine cls1 is super of cls2
     * 
     * @param cls1
     * @param cls2
     * @return
     */
    public static boolean isSuper(Class<?> cls1, Class<?> cls2) {
        if (cls1.equals(cls2.getSuperclass())) {
            return true;
        } else {
            return isSuper(cls1, cls2.getSuperclass());
        }
    }

    public static Class<?> getMethodOwner(Class<?> owner, String name, Class<?> arguments) {
        for (; !owner.equals(Object.class); owner = owner.getSuperclass()) {
        }
        return owner;
    }

    /**
     * 
     * @param owner
     * @param innerCls
     * @return
     */
    public static boolean isDirectInnerClass(Class<?> owner, Class<?> innerCls) {

        String[] clses = StringUtils.split(innerCls.getName(), INNER_CLASS_SEPARATOR_CHAR);

        if (clses.length > 1) {
            if (clses[clses.length - 2].equals(owner.getName())) {
                return true;
            }
        }

        return false;
    }

    /**
     * 判断innerCls是否是owner的内部类
     * 
     * @param owner
     * @param innerCls
     * @return
     */
    public static boolean isInnerClass(Class<?> owner, Class<?> innerCls) {
        int ownerIndex = innerCls.getName().indexOf(owner.getName());

        int separatorIndex = innerCls.getName().indexOf(INNER_CLASS_SEPARATOR_CHAR);

        if (ownerIndex >= 0 && separatorIndex > 0 && ownerIndex < separatorIndex) {
            return true;
        }

        return false;
    }

    /**
     * 通过className获取class实例，这里的参数可以是int，char的那个基本类型
     * 
     * @param className
     * @return
     * @throws ClassNotFoundException
     */
    public static Class<?> forName(String className) throws ClassNotFoundException {
        try {

            Class<?> clazz = primitivesToClasses(className);
            if (clazz != null) {
                return clazz;
            }

            if (className.startsWith("[")) {
                className = StringUtils.replace(className, "/", ".");
            } else if (className.startsWith("L")) {
                className = className.substring(1, className.length() - 1);
                className = StringUtils.replace(className, "/", ".");
            }

            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            Class<?> cls = (Class<?>) primitivesToClasses(className);
            if (cls != null) {
                return cls;
            } else {
                // chech for internal name
                className = StringUtils.replace(className, "/", ".");
                try {
                    return Class.forName(className);
                } catch (ClassNotFoundException e1) {
                    throw e;
                }
            }
        }
    }

    public static Class<?> primitivesToClasses(String primNameOrDesc) {
        if ("boolean".equals(primNameOrDesc)) {
            return boolean.class;
        }
        if ("int".equals(primNameOrDesc)) {
            return int.class;
        }
        if ("char".equals(primNameOrDesc)) {
            return char.class;
        }
        if ("short".equals(primNameOrDesc)) {
            return short.class;
        }
        if ("int".equals(primNameOrDesc)) {
            return int.class;
        }
        if ("long".equals(primNameOrDesc)) {
            return long.class;
        }
        if ("float".equals(primNameOrDesc)) {
            return float.class;
        }
        if ("double".equals(primNameOrDesc)) {
            return double.class;
        }
        if ("void".equals(primNameOrDesc)) {
            return void.class;
        }

        /* class description */
        if ("Z".equals(primNameOrDesc)) {
            return boolean.class;
        }
        if ("B".equals(primNameOrDesc)) {
            return byte.class;
        }
        if ("C".equals(primNameOrDesc)) {
            return char.class;
        }
        if ("S".equals(primNameOrDesc)) {
            return short.class;
        }
        if ("I".equals(primNameOrDesc)) {
            return int.class;
        }
        if ("J".equals(primNameOrDesc)) {
            return long.class;
        }
        if ("F".equals(primNameOrDesc)) {
            return float.class;
        }
        if ("D".equals(primNameOrDesc)) {
            return double.class;
        }
        if ("V".equals(primNameOrDesc)) {
            return void.class;
        }
        return null;
    }

    /**
     * 
     * @param clazz
     * @return
     * @throws IOException
     */
    public static List<AMethodMeta> getAllMethod(Class<?> clazz, final String findName) throws IOException {
        final AClass owner = AClassFactory.getType(clazz);
        InputStream classStream = ASMClassLoader.getInstance().getResourceAsStream(
                clazz.getName().replace('.', '/') + ".class");
        ClassReader cr = new ClassReader(classStream);
        final List<AMethodMeta> list = new ArrayList<AMethodMeta>();

        cr.accept(new ClassAdapter() {

            @Override
            public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
                if (name.equals(findName)) {

                    if (exceptions == null) {
                        exceptions = new String[0];
                    }

                    try {

                        Type[] types = Type.getArgumentTypes(desc);
                        AClass[] aclass = new AClass[types.length];
                        String[] args = new String[types.length];
                        for (int i = 0; i < types.length; i++) {
                            aclass[i] = AClassFactory.getType(forName(types[i].getDescriptor()));
                            args[i] = "arg" + i;
                        }

                        AClass returnType = AClassFactory.getType(forName(Type.getReturnType(desc).getDescriptor()));

                        AClass[] exceptionAclassArray = new AClass[exceptions.length];
                        for (int i = 0; i < exceptions.length; i++) {
                            exceptionAclassArray[i] = AClassFactory.getType(forName(exceptions[i]));
                        }

                        AMethodMeta me = new AMethodMeta(name, owner, owner, aclass, args, returnType,
                                exceptionAclassArray, access);
                        list.add(me);
                    } catch (ClassNotFoundException e) {
                        throw new ASMSupportException(e);
                    }
                }
                return super.visitMethod(access, name, desc, signature, exceptions);
            }

        }, 0);

        return list;
    }

    /**
     * get all interfaces
     * 
     * @param clazz
     * @return
     */
    public static List<Class<?>> getAllInterfaces(Class<?> clazz) {
        List<Class<?>> interfaceColl = new ArrayList<Class<?>>();
        getAllInterfaces(interfaceColl, clazz);
        return interfaceColl;
    }

    /**
     * 递归获取class的所有接口，并且保存到传入的List中
     */
    public static void getAllInterfaces(List<Class<?>> interfaceColl, Class<?> clazz) {
        if (clazz == null || Object.class.equals(clazz)) {
            return;
        }

        // get interface from super class
        getAllInterfaces(interfaceColl, clazz.getSuperclass());

        Class<?>[] interfaces = clazz.getInterfaces();
        if (ArrayUtils.isNotEmpty(interfaces)) {
            for (Class<?> inter : interfaces) {
                if (!interfaceColl.contains(inter)) {
                    interfaceColl.add(inter);
                }

                // get interface from current interface
                getAllInterfaces(interfaceColl, inter);
            }
        }

    }

    /**
     * 判断是否可见
     * 
     * @param invoker
     *            调用者所在的类
     * @param invoked
     *            被调用的方法或者field所在的类
     * @param actuallyInvoked
     *            被调用的方法或者field实际所在的类 actuallyInvoked必须是invoked或是其父类
     * @param mod
     *            被调用的方法或者field的修饰符
     * @return
     */
    public static boolean visible(Class<?> invoker, Class<?> invoked, Class<?> actuallyInvoked, int mod) {
        return AClassUtils.visible(AClassFactory.getType(invoker), AClassFactory.getType(invoked),
                AClassFactory.getType(actuallyInvoked), mod);
    }
    

    /**
     * <p>Gets the package name of an {@code Object}.</p>
     *
     * @param object  the class to get the package name for, may be null
     * @param valueIfNull  the value to return if null
     * @return the package name of the object, or the null value
     */
    public static String getPackageName(final Object object, final String valueIfNull) {
        if (object == null) {
            return valueIfNull;
        }
        return getPackageName(object.getClass());
    }

    /**
     * <p>Gets the package name of a {@code Class}.</p>
     *
     * @param cls  the class to get the package name for, may be {@code null}.
     * @return the package name or an empty string
     */
    public static String getPackageName(final Class<?> cls) {
        if (cls == null) {
            return StringUtils.EMPTY;
        }
        return getPackageName(cls.getName());
    }

    /**
     * <p>Gets the package name from a {@code String}.</p>
     *
     * <p>The string passed in is assumed to be a class name - it is not checked.</p>
     * <p>If the class is unpackaged, return an empty string.</p>
     *
     * @param className  the className to get the package name for, may be {@code null}
     * @return the package name or an empty string
     */
    public static String getPackageName(String className) {
        if (StringUtils.isEmpty(className)) {
            return StringUtils.EMPTY;
        }

        // Strip array encoding
        while (className.charAt(0) == '[') {
            className = className.substring(1);
        }
        // Strip Object type encoding
        if (className.charAt(0) == 'L' && className.charAt(className.length() - 1) == ';') {
            className = className.substring(1);
        }

        final int i = className.lastIndexOf(PACKAGE_SEPARATOR_CHAR);
        if (i == -1) {
            return StringUtils.EMPTY;
        }
        return className.substring(0, i);
    }

}
