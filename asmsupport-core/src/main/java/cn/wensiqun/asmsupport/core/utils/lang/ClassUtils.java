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

import cn.wensiqun.asmsupport.core.clazz.AClassFactory;
import cn.wensiqun.asmsupport.core.definition.method.meta.AMethodMeta;
import cn.wensiqun.asmsupport.core.loader.ASMClassLoader;
import cn.wensiqun.asmsupport.core.utils.asm.ClassAdapter;
import cn.wensiqun.asmsupport.org.objectweb.asm.ClassReader;
import cn.wensiqun.asmsupport.org.objectweb.asm.MethodVisitor;
import cn.wensiqun.asmsupport.org.objectweb.asm.Type;
import cn.wensiqun.asmsupport.standard.def.clazz.AClass;
import cn.wensiqun.asmsupport.standard.error.ASMSupportException;

/**
 * Class Helper Class
 * 
 * @author wensiqun at 163.com(Joe Wen)
 */
public class ClassUtils { 

    /**
     * The inner class separator character: <code>'$' == {@value}</code>.
     */
    public static final char INNER_CLASS_SEPARATOR_CHAR = '$';
    
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
     * Check a class {@code innerClas} whether or not a inner class of an {@code owner}.
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
     * Get class from a class name, the name also support description, internal name in jvm,
     * and primitive name such int, char.
     * 
     * @param className class name, such java.lang.String, [java.lang.String, int, [java/lang/String etc... 
     * @return Class the java class
     * @throws ClassNotFoundException
     */
    public static Class<?> forName(String className) throws ClassNotFoundException {
        try {

            Class<?> clazz = primitiveToClass(className);
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
            Class<?> cls = (Class<?>) primitiveToClass(className);
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

    /**
     * Convert a primitive name or description to java class.
     * @param nameOrDesc primitive name or description 
     * @return Class java class
     */
    public static Class<?> primitiveToClass(String nameOrDesc) {
        if ("boolean".equals(nameOrDesc)) {
            return boolean.class;
        }
        if ("int".equals(nameOrDesc)) {
            return int.class;
        }
        if ("char".equals(nameOrDesc)) {
            return char.class;
        }
        if ("short".equals(nameOrDesc)) {
            return short.class;
        }
        if ("int".equals(nameOrDesc)) {
            return int.class;
        }
        if ("long".equals(nameOrDesc)) {
            return long.class;
        }
        if ("float".equals(nameOrDesc)) {
            return float.class;
        }
        if ("double".equals(nameOrDesc)) {
            return double.class;
        }
        if ("void".equals(nameOrDesc)) {
            return void.class;
        }

        /* class description */
        if ("Z".equals(nameOrDesc)) {
            return boolean.class;
        }
        if ("B".equals(nameOrDesc)) {
            return byte.class;
        }
        if ("C".equals(nameOrDesc)) {
            return char.class;
        }
        if ("S".equals(nameOrDesc)) {
            return short.class;
        }
        if ("I".equals(nameOrDesc)) {
            return int.class;
        }
        if ("J".equals(nameOrDesc)) {
            return long.class;
        }
        if ("F".equals(nameOrDesc)) {
            return float.class;
        }
        if ("D".equals(nameOrDesc)) {
            return double.class;
        }
        if ("V".equals(nameOrDesc)) {
            return void.class;
        }
        return null;
    }

    /**
     * According to a method name to find all method meta information 
     * from a class(whit out super class) 
     * 
     * @param clazz the method owner
     * @param methodName the method name
     * @return a list of {@link AMethodMeta}
     * @throws IOException
     */
    public static List<AMethodMeta> getAllMethod(Class<?> clazz, final String methodName) throws IOException {
        final AClass owner = AClassFactory.getType(clazz);
        InputStream classStream = ASMClassLoader.getInstance().getResourceAsStream(
                clazz.getName().replace('.', '/') + ".class");
        ClassReader cr = new ClassReader(classStream);
        final List<AMethodMeta> list = new ArrayList<AMethodMeta>();
        cr.accept(new ClassAdapter() {

            @Override
            public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
                if (name.equals(methodName)) {

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
     * get all interfaces from a class
     * 
     * @param clazz
     * @return a class list
     */
    public static List<Class<?>> getAllInterfaces(Class<?> clazz) {
        List<Class<?>> interfaceColl = new ArrayList<Class<?>>();
        getAllInterfaces(interfaceColl, clazz);
        return interfaceColl;
    }

    /**
     * Get all interface from a class and put the found classes to a list.
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

}
