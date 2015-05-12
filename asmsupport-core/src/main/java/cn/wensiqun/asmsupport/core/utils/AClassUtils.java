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

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import cn.wensiqun.asmsupport.core.clazz.AClassFactory;
import cn.wensiqun.asmsupport.core.utils.collections.CollectionUtils;
import cn.wensiqun.asmsupport.core.utils.lang.ClassUtils;
import cn.wensiqun.asmsupport.org.objectweb.asm.Type;
import cn.wensiqun.asmsupport.standard.def.clazz.AClass;
import cn.wensiqun.asmsupport.standard.error.ASMSupportException;

/**
 * The AClass Helper
 */
public class AClassUtils {
    
    /**
     * Check the specify type is primitive wrap type.
     * 
     * @param aclass
     */
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

    /**
     * Get the primitive type if the argument is wrap type, otherwise
     * return the argument self.
     */
    public static AClass getPrimitiveAClass(AClass aclass) {
        if (aclass.equals(AClassFactory.getType(Boolean.class))) {
            return AClassFactory.getType(boolean.class);
        } else if (aclass.equals(AClassFactory.getType(Byte.class))) {
            return AClassFactory.getType(byte.class);
        } else if (aclass.equals(AClassFactory.getType(Short.class))) {
            return AClassFactory.getType(short.class);
        } else if (aclass.equals(AClassFactory.getType(Character.class))) {
            return AClassFactory.getType(char.class);
        } else if (aclass.equals(AClassFactory.getType(Integer.class))) {
            return AClassFactory.getType(int.class);
        } else if (aclass.equals(AClassFactory.getType(Long.class))) {
            return AClassFactory.getType(long.class);
        } else if (aclass.equals(AClassFactory.getType(Float.class))) {
            return AClassFactory.getType(float.class);
        } else if (aclass.equals(AClassFactory.getType(Double.class))) {
            return AClassFactory.getType(double.class);
        }
        return aclass;
    }
    
    /**
     * Get the primitive wrap type if the argument is primitive type, otherwise
     * return the argument self.
     */
    public static AClass getPrimitiveWrapAClass(AClass aclass) {
        if (aclass.equals(AClassFactory.getType(boolean.class))) {
            return AClassFactory.getType(Boolean.class);
        } else if (aclass.equals(AClassFactory.getType(byte.class))) {
            return AClassFactory.getType(Byte.class);
        } else if (aclass.equals(AClassFactory.getType(short.class))) {
            return AClassFactory.getType(Short.class);
        } else if (aclass.equals(AClassFactory.getType(char.class))) {
            return AClassFactory.getType(Character.class);
        } else if (aclass.equals(AClassFactory.getType(int.class))) {
            return AClassFactory.getType(Integer.class);
        } else if (aclass.equals(AClassFactory.getType(long.class))) {
            return AClassFactory.getType(Long.class);
        } else if (aclass.equals(AClassFactory.getType(float.class))) {
            return AClassFactory.getType(Float.class);
        } else if (aclass.equals(AClassFactory.getType(double.class))) {
            return AClassFactory.getType(Double.class);
        }
        return aclass;
    }

    /**
     * according the passed type list, to figure out the finally result type
     */
    public static AClass getArithmeticalResultType(AClass... types) {
        AClass resultType = null;
        for (AClass type : types) {
            type = getPrimitiveAClass(type);

            if (isArithmetical(type)) {
                int typeSort = type.getType().getSort();
                if (resultType == null || typeSort > resultType.getType().getSort()) {
                    if (typeSort <= Type.INT) {
                        resultType = AClassFactory.getType(int.class);
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
     * Check the specify whether or not as an arithmetical operation factor.
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
     * Check the type support unbox or box.
     */
    public static boolean boxUnboxable(AClass aclass) {
        if (aclass.isPrimitive() || isPrimitiveWrapAClass(aclass)) {
            return true;
        }
        return false;
    }

    /**
     * <p>
     * Check the method call is visible, following is an example: 
     * <pre>
     * Class Super {
     *     public void call() {...}
     * }
     * Class Sub extends Super {
     * }
     * Class Main {
     *     public static void main(String... args) {
     *         Sub sub = new Sub();
     *         sub.call();
     *     }
     * }
     * </pre>
     * For code {@code sub.call()}, see parameter description for detail.
     * </p>
     * 
     * @param where the call occur in which class, in preceding code {@code sub.call()}, 
     *        the {@code where} is Main.class
     * @param callFrom where the call from, in preceding code {@code sub.call()}, 
     *        the {@code callFrom} is Sub.class
     * @param declaringClass the method declaring class, in preceding code {@code sub.call()}, 
     *        the {@code declaringClass} is Super.class
     * @param methodDescription method description
     * @param modifiers the modifier of method
     * @return
     */
    public static boolean visible(AClass where, AClass callFrom, AClass methodDescription, int modifiers) {
        // 只要是public就可见
        if (Modifier.isPublic(modifiers)) {
            return true;
        }

        // 如果invoked和 actuallyInvoked相同
        if (callFrom.equals(methodDescription)) {
            // 如果invoker和invoked相同
            if (where.equals(callFrom)) {
                // 在同一个类中允许调用
                return true;
            } else {
                if (Modifier.isPrivate(modifiers)) {
                    return false;
                } else {
                    if (where.getPackage().equals(callFrom.getPackage())) {
                        return true;
                    } else if (Modifier.isProtected(modifiers) && where.isChildOrEqual(callFrom)){
                        return true;
                    }
                }
            }
        } else {
            // 先判断actuallyInvoked对invoked的可见性
            if (Modifier.isPrivate(modifiers)) {
                return false;
            }

            // 如果都在同一包下
            if (where.getPackage().equals(callFrom.getPackage())
                    && where.getPackage().equals(methodDescription.getPackage())) {
                return true;
            }

            if (Modifier.isProtected(modifiers)) {
                if (where.isChildOrEqual(callFrom) && callFrom.isChildOrEqual(methodDescription)) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Check a type({@code from}) whether or not assign to other type({@code to}).
     */
    public static boolean checkAssignable(AClass from, AClass to) {
        if (from.isChildOrEqual(to)) {
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
     * Get all interface from an {@link AClass}
     * 
     * @return List<Class<?>> all interface list
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
     * Convert {@link Class} list to {@link AClass} list
     */
    public static AClass[] convertToAClass(Class<?>[] classes) {
        if (classes == null) {
            return new AClass[0];
        }

        AClass[] aclasses = new AClass[classes.length];
        for (int i = 0; i < classes.length; i++) {
            aclasses[i] = AClassFactory.getType(classes[i]);
        }
        return aclasses;
    }
}
