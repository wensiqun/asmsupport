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
/**
 * 
 */
package cn.wensiqun.asmsupport.core.utils;

import java.io.Serializable;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import cn.wensiqun.asmsupport.core.clazz.AClass;
import cn.wensiqun.asmsupport.core.clazz.AClassFactory;
import cn.wensiqun.asmsupport.core.clazz.ArrayClass;
import cn.wensiqun.asmsupport.core.clazz.ProductClass;
import cn.wensiqun.asmsupport.core.clazz.SemiClass;
import cn.wensiqun.asmsupport.core.definition.method.AMethod;
import cn.wensiqun.asmsupport.core.definition.method.meta.AMethodMeta;
import cn.wensiqun.asmsupport.core.exception.ASMSupportException;
import cn.wensiqun.asmsupport.core.utils.collections.CollectionUtils;
import cn.wensiqun.asmsupport.core.utils.lang.ArrayUtils;
import cn.wensiqun.asmsupport.core.utils.lang.ClassUtils;
import cn.wensiqun.asmsupport.core.utils.reflect.MethodUtils;
import cn.wensiqun.asmsupport.org.objectweb.asm.Opcodes;
import cn.wensiqun.asmsupport.org.objectweb.asm.Type;

/**
 * 
 * @author 温斯群(Joe Wen)
 *
 */
public class AClassUtils {
    /**
     * this class is a tools class. so don't support constructor
     */
    private AClassUtils() {
        throw new UnsupportedOperationException("cannot support new instance the utils class");
    }

    public static boolean isPrimitiveWrapAClass(AClass aclass) {
        if (aclass.getName().equals(Byte.class.getName()) || aclass.getName().equals(Short.class.getName())
                || aclass.getName().equals(Character.class.getName())
                || aclass.getName().equals(Integer.class.getName()) || aclass.getName().equals(Long.class.getName())
                || aclass.getName().equals(Float.class.getName()) || aclass.getName().equals(Double.class.getName())
                || aclass.getName().equals(Boolean.class.getName())) {
            return true;
        }
        return false;
    }

    public static AClass getPrimitiveAClass(AClass aclass) {
        if (aclass.equals(AClassFactory.defType(Boolean.class))) {
            return AClassFactory.defType(boolean.class);
        } else if (aclass.equals(AClassFactory.defType(Byte.class))) {
            return AClassFactory.defType(byte.class);
        } else if (aclass.equals(AClassFactory.defType(Short.class))) {
            return AClassFactory.defType(short.class);
        } else if (aclass.equals(AClassFactory.defType(Character.class))) {
            return AClassFactory.defType(char.class);
        } else if (aclass.equals(AClassFactory.defType(Integer.class))) {
            return AClassFactory.defType(int.class);
        } else if (aclass.equals(AClassFactory.defType(Long.class))) {
            return AClassFactory.defType(long.class);
        } else if (aclass.equals(AClassFactory.defType(Float.class))) {
            return AClassFactory.defType(float.class);
        } else if (aclass.equals(AClassFactory.defType(Double.class))) {
            return AClassFactory.defType(double.class);
        }
        return aclass;
    }

    public static AClass getPrimitiveWrapAClass(AClass aclass) {
        if (aclass.equals(AClassFactory.defType(boolean.class))) {
            return AClassFactory.defType(Boolean.class);
        } else if (aclass.equals(AClassFactory.defType(byte.class))) {
            return AClassFactory.defType(Byte.class);
        } else if (aclass.equals(AClassFactory.defType(short.class))) {
            return AClassFactory.defType(Short.class);
        } else if (aclass.equals(AClassFactory.defType(char.class))) {
            return AClassFactory.defType(Character.class);
        } else if (aclass.equals(AClassFactory.defType(int.class))) {
            return AClassFactory.defType(Integer.class);
        } else if (aclass.equals(AClassFactory.defType(long.class))) {
            return AClassFactory.defType(Long.class);
        } else if (aclass.equals(AClassFactory.defType(float.class))) {
            return AClassFactory.defType(Float.class);
        } else if (aclass.equals(AClassFactory.defType(double.class))) {
            return AClassFactory.defType(Double.class);
        }
        return aclass;
    }

    /**
     * 传入一组可以参与算术运算的类型，获取这些类型的值计算后的最终类型
     * 
     * @param type
     * @return
     */
    public static AClass getArithmeticalResultType(AClass... types) {
        AClass resultType = null;
        for (AClass type : types) {
            type = getPrimitiveAClass(type);

            if (isArithmetical(type)) {
                int typeSort = type.getType().getSort();
                if (resultType == null || typeSort > resultType.getType().getSort()) {
                    if (typeSort <= Type.INT) {
                        resultType = AClassFactory.defType(int.class);
                    } else {
                        resultType = type;
                    }
                }
            } else {
                throw new ASMSupportException(type + " dosn't support arithmetical operator.");
            }
        }
        return resultType;
    }

    /**
     * 判断传入的class是否可以参与算术运算操作
     * 
     * @param aclass
     * @return
     */
    public static boolean isArithmetical(AClass aclass) {
        if (aclass.isPrimitive() && !aclass.getName().equals(boolean.class.getName())) {
            return true;
        } else if (isPrimitiveWrapAClass(aclass) && !aclass.getName().equals(Boolean.class.getName())) {
            return true;
        }
        return false;
    }

    /**
     * 判断传入的class是否可以做unbox和box操作
     * 
     * @param aclass
     * @return
     */
    public static boolean boxUnboxable(AClass aclass) {
        if (aclass.isPrimitive() || isPrimitiveWrapAClass(aclass)) {
            return true;
        }
        return false;
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
    public static boolean visible(AClass invoker, AClass invoked, AClass actuallyInvoked, int mod) {
        // 只要是public就可见
        if (Modifier.isPublic(mod)) {
            return true;
        }

        // 如果invoked和 actuallyInvoked相同
        if (invoked.equals(actuallyInvoked)) {
            // 如果invoker和invoked相同
            if (invoker.equals(invoked)) {
                // 在同一个类中允许调用
                return true;
            } else {
                if (Modifier.isPrivate(mod)) {
                    return false;
                } else {
                    if (invoker.getPackage().equals(invoked.getPackage())) {
                        return true;
                    } else if (Modifier.isProtected(mod) && invoker.isChildOrEqual(invoked)){
                        return true;
                    }
                }
            }
        } else {
            // 先判断actuallyInvoked对invoked的可见性
            if (Modifier.isPrivate(mod)) {
                return false;
            }

            // 如果都在同一包下
            if (invoker.getPackage().equals(invoked.getPackage())
                    && invoker.getPackage().equals(actuallyInvoked.getPackage())) {
                return true;
            }

            if (Modifier.isProtected(mod)) {
                if (invoker.isChildOrEqual(invoked) && invoked.isChildOrEqual(actuallyInvoked)) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * get direct super type
     * 
     * @param as
     * @return
     */
    public static AClass[] getDirectSuperType(AClass as) {
        AClass[] a = null;

        if (as.isPrimitive()) {
            if (as.equals(AClassFactory.defType(byte.class))) {
                a = new AClass[] { AClassFactory.defType(short.class), AClassFactory.defType(Object.class) };

            } else if (as.equals(AClassFactory.defType(short.class))) {
                a = new AClass[] { AClassFactory.defType(int.class), AClassFactory.defType(Object.class) };

            } else if (as.equals(AClassFactory.defType(char.class))) {
                a = new AClass[] { AClassFactory.defType(int.class), AClassFactory.defType(Object.class) };

            } else if (as.equals(AClassFactory.defType(int.class))) {
                a = new AClass[] { AClassFactory.defType(long.class), AClassFactory.defType(Object.class) };

            } else if (as.equals(AClassFactory.defType(long.class))) {
                a = new AClass[] { AClassFactory.defType(float.class), AClassFactory.defType(Object.class) };

            } else if (as.equals(AClassFactory.defType(float.class))) {
                a = new AClass[] { AClassFactory.defType(double.class), AClassFactory.defType(Object.class) };
            }

        } else if (as.equals(AClassFactory.defType(Object.class))) {

        } else if (as.isInterface()) {
            Class<?>[] intfacs = as.getInterfaces();
            if (intfacs != null && intfacs.length > 0) {
                a = new AClass[intfacs.length];
                for (int i = 0; i < a.length; i++) {
                    a[i] = AClassFactory.defType(intfacs[i]);
                }
            } else {
                a = new AClass[] { AClassFactory.defType(Object.class) };
            }
        } else if (as.isArray()) {
            ArrayClass ac = (ArrayClass) as;
            AClass rootType = ac.getRootComponentClass();

            if (rootType.isPrimitive()) {
                a = new AClass[2];
                a[0] = AClassFactory.defType(Cloneable.class);
                a[1] = AClassFactory.defType(Serializable.class);
            } else {
                AClass[] rootSupers = getDirectSuperType(rootType);
                if (rootSupers != null) {
                    a = new AClass[rootSupers.length];
                    for (int i = 0; i < a.length; i++) {
                        a[i] = AClassFactory.defArrayType(rootSupers[i], ac.getDimension());
                    }
                } else {
                    a = new AClass[2];
                    a[0] = AClassFactory.defType(Cloneable.class);
                    a[1] = AClassFactory.defType(Serializable.class);
                }
            }
        } else {
            Class<?> sup = as.getSuperClass();
            Class<?>[] intefaces = as.getInterfaces();

            a = new AClass[intefaces.length + 1];
            a[0] = AClassFactory.defType(sup);

            for (int i = 1; i < a.length; i++) {
                a[i] = AClassFactory.defType(intefaces[i - 1]);
            }

        }

        return a;
    }

    public static boolean isSubOrEqualType(AClass subtype, AClass exceptSupertype) {
        if (subtype.equals(exceptSupertype)) {
            return true;
        }
        AClass[] actuallySupertypes = getDirectSuperType(subtype);
        if (ArrayUtils.isNotEmpty(actuallySupertypes)) {
            for (AClass actual : actuallySupertypes) {
                if (actual.equals(exceptSupertype)) {
                    return true;
                } else {
                    if (isSubOrEqualType(actual, exceptSupertype)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 
     * @param invoker
     * @param owner
     * @param name
     * @param actualArgLength
     * @return
     */
    public static List<AMethodMeta> allDeclareVariableArityMethod(AClass invoker, AClass owner, String name,
            int actualArgLength) {
        List<AMethodMeta> list = new ArrayList<AMethodMeta>();
        Class<?> reallyClass = null;
        if (owner instanceof SemiClass) {
            for (AMethod method : ((SemiClass) owner).getMethods()) {
                if ((method.getMethodMeta().getModifier() & Opcodes.ACC_VARARGS) != 0
                        && method.getMethodMeta().getName().equals(name)) {
                    list.add(method.getMethodMeta());
                }
            }
            reallyClass = owner.getSuperClass();
        } else if (owner instanceof ProductClass) {
            reallyClass = ((ProductClass) owner).getReallyClass();
        }

        Class<?> actuallyMethodOwner = reallyClass;
        AClass invoked = AClassFactory.defType(reallyClass);
        // ACC_VARARGS
        List<AMethodMeta> methods = new ArrayList<AMethodMeta>();
        java.lang.reflect.Method[] mes;
        for (; actuallyMethodOwner != null; actuallyMethodOwner = actuallyMethodOwner.getSuperclass()) {
            mes = actuallyMethodOwner.getDeclaredMethods();
            for (int i = 0; i < mes.length; i++) {
                if (mes[i].getName().equals(name) && mes[i].isVarArgs()) {
                    methods.add(AMethodMeta.methodToMethodEntity(invoked, mes[i]));
                }
            }
            addAndEliminateDupVariableArityMethod(invoker, invoked, name, actualArgLength, list, methods);
        }

        return list;
    }

    /**
     * 
     * @param invoker
     * @param invoked
     * @param name
     * @param actualArgLength
     * @param list
     * @param methods
     */
    private static void addAndEliminateDupVariableArityMethod(AClass invoker, AClass invoked, String name,
            int actualArgLength, List<AMethodMeta> list, List<AMethodMeta> methods) {
        boolean same;
        int length = list.size();
        for (AMethodMeta m1 : methods) {
            same = false;
            for (int i = 0; i < length; i++) {
                if (MethodUtils.methodEqualWithoutOwner(m1, list.get(i))) {
                    same = true;
                    break;
                }
            }

            if (!same && ((m1.getModifier() & Opcodes.ACC_VARARGS) != 0) && m1.getName().equals(name)) {

                if (AClassUtils.visible(invoker, invoked, m1.getActuallyOwner(), m1.getModifier())
                        && m1.getArgClasses().length <= actualArgLength + 1) {
                    list.add(m1);
                }
            }
        }
    }

    /**
     * 
     * @param aclasses
     * @return
     */
    public static int allArgumentWithBoxAndUnBoxCountExceptSelf(AClass[] aclasses) {
        int size = 0;
        for (AClass a : aclasses) {
            if (AClassUtils.boxUnboxable(a)) {
                if (size == 0) {
                    size = 1;
                }
                size = size << 1;
            }
        }
        return size == 0 ? 0 : size - 1;
    }

    public static int primitiveFlag(AClass[] aclasses) {
        int flagVal = 0;
        if (aclasses == null) {
            return flagVal;
        }

        for (int i = 0; i < aclasses.length; i++) {
            if (aclasses[i].isPrimitive()) {
                if (i == 0) {
                    flagVal++;
                } else {
                    flagVal += 2 << (i - 1);
                }
            }
        }
        return flagVal;
    }

    public static void allArgumentWithBoxAndUnBox(AClass[] orgi, int orgiFlagValue, int index, AClass[] newa,
            List<AClass[]> list) {

        newa[index] = AClassUtils.getPrimitiveAClass(orgi[index]);
        if (index == orgi.length - 1) {
            if (AClassUtils.boxUnboxable(orgi[index])) {
                AClass[] newb = new AClass[newa.length];
                System.arraycopy(newa, 0, newb, 0, newa.length);
                newb[index] = AClassUtils.getPrimitiveWrapAClass(orgi[index]);

                if (primitiveFlag(newb) != orgiFlagValue) {
                    list.add(newb);
                }
            }
            if (primitiveFlag(newa) != orgiFlagValue) {
                list.add(newa);
            }
        } else {
            if (AClassUtils.boxUnboxable(orgi[index])) {
                AClass[] newb = new AClass[newa.length];
                System.arraycopy(newa, 0, newb, 0, newa.length);
                newb[index] = AClassUtils.getPrimitiveWrapAClass(orgi[index]);

                allArgumentWithBoxAndUnBox(orgi, orgiFlagValue, index + 1, newb, list);
            }
            allArgumentWithBoxAndUnBox(orgi, orgiFlagValue, index + 1, newa, list);
        }
    }

    /**
     * 
     * @param from
     * @param to
     * @return
     */
    public static boolean checkAssignable(AClass from, AClass to) {

        if (from.isChildOrEqual(to)) {
            return true;
        } else if (from.isPrimitive() && to.equals(AClassFactory.defType(Object.class))) {
            return true;
        } else {
            AClass fromPrim = getPrimitiveAClass(from);
            AClass toPrim = getPrimitiveAClass(to);
            int fromSort = fromPrim.getType().getSort();
            int toSort = toPrim.getType().getSort();

            if (fromSort == toSort && fromSort < 9) {
                return true;
            }

            if (fromSort >= Type.CHAR && fromSort <= Type.DOUBLE && toSort >= Type.CHAR && toSort <= Type.DOUBLE) {
                if (fromSort < toSort) {
                    if (fromSort == Type.CHAR) {
                        if (toSort >= Type.INT) {
                            return true;
                        }
                    } else {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    /**
     * 
     * @param aclass
     * @return
     */
    public static List<Class<?>> getAllInterfaces(AClass aclass) {
        Class<?>[] interfaces = aclass.getInterfaces();
        Class<?> superClass = aclass.getSuperClass();
        List<Class<?>> interfaceColl = new ArrayList<Class<?>>();
        CollectionUtils.addAll(interfaceColl, interfaces);
        for (Class<?> inter : interfaces) {
            ClassUtils.getAllInterfaces(interfaceColl, inter);
        }
        ClassUtils.getAllInterfaces(interfaceColl, superClass);
        return interfaceColl;
    }

    /**
     * 
     * @param clses
     * @return
     */
    public static AClass[] convertToAClass(Class<?>[] clses) {
        if (clses == null) {
            return new AClass[0];
        }

        AClass[] aclasses = new AClass[clses.length];
        for (int i = 0; i < clses.length; i++) {
            aclasses[i] = AClassFactory.defType(clses[i]);
        }
        return aclasses;
    }
}
