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
package cn.wensiqun.asmsupport.standard.utils;

import cn.wensiqun.asmsupport.org.objectweb.asm.Type;
import cn.wensiqun.asmsupport.standard.def.clazz.ClassHolder;
import cn.wensiqun.asmsupport.standard.def.clazz.IClass;
import cn.wensiqun.asmsupport.standard.error.ASMSupportException;
import cn.wensiqun.asmsupport.utils.lang.ArrayUtils;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

/**
 * The AClass Helper
 */
public class IClassUtils {
    
    /**
     * Check the specify type is primitive wrap type.
     * 
     * @param type
     */
    public static boolean isPrimitiveWrapAClass(IClass type) {
        if (type.getName().equals(Byte.class.getName()) || type.getName().equals(Short.class.getName())
                || type.getName().equals(Character.class.getName())
                || type.getName().equals(Integer.class.getName()) || type.getName().equals(Long.class.getName())
                || type.getName().equals(Float.class.getName()) || type.getName().equals(Double.class.getName())
                || type.getName().equals(Boolean.class.getName())) {
            return true;
        }
        return false;
    }

    /**
     * Get the primitive type if the argument is wrap type, otherwise
     * return the argument self.
     */
    public static IClass getPrimitiveAClass(IClass type) {
    	ClassHolder ch = type.getClassHolder();
        if (type.equals(ch.getType(Boolean.class))) {
            return ch.getType(boolean.class);
        } else if (type.equals(ch.getType(Byte.class))) {
            return ch.getType(byte.class);
        } else if (type.equals(ch.getType(Short.class))) {
            return ch.getType(short.class);
        } else if (type.equals(ch.getType(Character.class))) {
            return ch.getType(char.class);
        } else if (type.equals(ch.getType(Integer.class))) {
            return ch.getType(int.class);
        } else if (type.equals(ch.getType(Long.class))) {
            return ch.getType(long.class);
        } else if (type.equals(ch.getType(Float.class))) {
            return ch.getType(float.class);
        } else if (type.equals(ch.getType(Double.class))) {
            return ch.getType(double.class);
        }
        return type;
    }
    
    /**
     * Get the primitive wrap type if the argument is primitive type, otherwise
     * return the argument self.
     */
    public static IClass getPrimitiveWrapAClass(IClass type) {
    	ClassHolder ch = type.getClassHolder();
        if (type.equals(ch.getType(boolean.class))) {
            return ch.getType(Boolean.class);
        } else if (type.equals(ch.getType(byte.class))) {
            return ch.getType(Byte.class);
        } else if (type.equals(ch.getType(short.class))) {
            return ch.getType(Short.class);
        } else if (type.equals(ch.getType(char.class))) {
            return ch.getType(Character.class);
        } else if (type.equals(ch.getType(int.class))) {
            return ch.getType(Integer.class);
        } else if (type.equals(ch.getType(long.class))) {
            return ch.getType(Long.class);
        } else if (type.equals(ch.getType(float.class))) {
            return ch.getType(Float.class);
        } else if (type.equals(ch.getType(double.class))) {
            return ch.getType(Double.class);
        }
        return type;
    }

    /**
     * according the passed type list, to figure out the finally result type
     */
    public static IClass getArithmeticalResultType(IClass... types) {
    	IClass resultType = null;
        for (IClass type : types) {
            type = getPrimitiveAClass(type);

            if (isArithmetical(type)) {
                int typeSort = type.getType().getSort();
                if (resultType == null || typeSort > resultType.getType().getSort()) {
                    if (typeSort <= Type.INT) {
                        resultType = type.getClassHolder().getType(int.class);
                    } else {
                        resultType = type;
                    }
                }
            } else {
                throw new ASMSupportException(type + " dons't support arithmetical operator.");
            }
        }
        return resultType;
    }

    /**
     * Check the specify whether or not as an arithmetical operation factor.
     */
    public static boolean isArithmetical(IClass type) {
        if (type.isPrimitive() && !type.getName().equals(boolean.class.getName())) {
            return true;
        } else if (isPrimitiveWrapAClass(type) && !type.getName().equals(Boolean.class.getName())) {
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
     * @param methodDescription method description
     * @param modifiers the modifier of method
     * @return boolean
     */
    public static boolean visible(IClass where, IClass callFrom, IClass methodDescription, int modifiers) {
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
    public static boolean checkAssignable(IClass from, IClass to) {
        if (from.isChildOrEqual(to)) {
            return true;
        } else {
        	IClass fromPrim = getPrimitiveAClass(from);
        	IClass toPrim = getPrimitiveAClass(to);
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
     * @return List<Class<?>> all interface list
     */
    public static List<IClass> getAllInterfaces(IClass types) {
        IClass[] interfaces = types.getInterfaces();
        IClass superClass = types.getSuperclass();
        List<IClass> interfaceColl = new ArrayList<>();
        for (IClass itf : interfaces) {
        	interfaceColl.add(itf);
            getAllInterfaces(interfaceColl, itf);
        }
        getAllInterfaces(interfaceColl, superClass);
        return interfaceColl;
    }

    /**
     * Convert {@link Class} list to {@link IClass} list
     */
    public static IClass[] convertToAClass(ClassHolder classHolder, Class<?>[] classes) {
        if (classes == null) {
            return new IClass[0];
        }

        IClass[] types = new IClass[classes.length];
        for (int i = 0; i < classes.length; i++) {
            types[i] = classHolder.getType(classes[i]);
        }
        return types;
    }
    
    /**
     * Get all interface from a class and put the found classes to a list.
     * 
     * @param itfList
     * @param clazz
     */
    public static void getAllInterfaces(List<IClass> itfList, IClass clazz) {
        if (clazz == null || Object.class.getName().endsWith(clazz.getName())) {
            return;
        }

        // get interface from super class
        getAllInterfaces(itfList, clazz.getSuperclass());

        IClass[] interfaces = clazz.getInterfaces();
        if (ArrayUtils.isNotEmpty(interfaces)) {
            for (IClass itf : interfaces) {
                if (!itfList.contains(itf)) {
                    itfList.add(itf);
                }
                // get interface from current interface
                getAllInterfaces(itfList, itf);
            }
        }

    }
}
