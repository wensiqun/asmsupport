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
package cn.wensiqun.asmsupport.core.utils.reflect;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import cn.wensiqun.asmsupport.standard.def.clazz.AClass;
import cn.wensiqun.asmsupport.standard.def.clazz.AClassFactory;
import cn.wensiqun.asmsupport.standard.def.clazz.ProductClass;
import cn.wensiqun.asmsupport.standard.def.method.AMethodMeta;
import cn.wensiqun.asmsupport.standard.utils.AClassUtils;

/**
 * 
 * @author wensiqun at 163.com(Joe Wen)
 *
 */
public class MethodUtils {

    /**
     * Return a method, that have overrided by passed method.
     * 
     * @param overrideMethod the override method
     */
    public static Method getOverriddenMethod(AMethodMeta entity) {
        Class<?> superClass = entity.getActuallyOwner().getSuperClass();
        String methodName = entity.getName();
        AClass[] argClasses = entity.getArgClasses() == null ? new AClass[0] : entity.getArgClasses();
        Class<?>[] argTypes = new Class[argClasses.length];
        for (int i = 0; i < argTypes.length; i++) {
            AClass argAclass = argClasses[i];
            if (argAclass instanceof ProductClass) {
                argTypes[i] = ((ProductClass) argAclass).getReallyClass();
            } else {
                return null;
            }
        }

        for (; superClass != null && !Object.class.equals(superClass);) {
            try {
                Method method = superClass.getDeclaredMethod(methodName, argTypes);

                AClass callerOwner = entity.getActuallyOwner();
                AClass calledOwner = AClassFactory.getType(method.getDeclaringClass());
                if (AClassUtils.visible(callerOwner, calledOwner, calledOwner, method.getModifiers())) {
                    return method;
                }

            } catch (NoSuchMethodException e) {
                superClass = superClass.getSuperclass();
            }
        }
        return null;
    }

    /**
     * 
     * 
     * @param implementMethod
     */
    public static Method[] getImplementedMethod(AMethodMeta entity) {
        String methodName = entity.getName();
        AClass[] argClasses = entity.getArgClasses() == null ? new AClass[0] : entity.getArgClasses();
        Class<?>[] argTypes = new Class[argClasses.length];
        for (int i = 0; i < argTypes.length; i++) {
            AClass argAclass = argClasses[i];
            if (argAclass instanceof ProductClass) {
                argTypes[i] = ((ProductClass) argAclass).getReallyClass();
            } else {
                return null;
            }
        }

        List<Method> foundList = new ArrayList<Method>();
        List<Class<?>> interfaces = AClassUtils.getAllInterfaces(entity.getActuallyOwner());

        for (Class<?> inter : interfaces) {
            try {
                Method method = inter.getDeclaredMethod(methodName, argTypes);
                if (!foundList.contains(method)) {
                    foundList.add(method);
                }
            } catch (NoSuchMethodException e) {
            }
        }

        return foundList.toArray(new Method[foundList.size()]);
    }

    /**
     * 
     * @param method1
     * @param method2
     * @return
     */
    public static boolean methodSignatureEqualWithoutOwner(Method method1, Method method2) {
        if (methodEqualWithoutOwner(method1, method2)) {
            return method1.getReturnType().equals(method2.getReturnType());
        }
        return false;
    }

    /**
     * 
     * @param me
     * @param method
     * @return
     */
    public static boolean methodEqualWithoutOwner(AMethodMeta me, Method method) {
        if (me.getName().equals(method.getName())) {
            AClass[] mePara = me.getArgClasses();
            Class<?>[] methodPara = method.getParameterTypes();
            if (mePara.length == methodPara.length) {
                for (int i = 0, len = mePara.length; i < len; i++) {
                    if (!mePara[i].getName().equals(methodPara[i].getName())) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    /**
     * 
     * @param m1
     * @param m2
     * @return
     */
    public static boolean methodEqualWithoutOwner(Method m1, Method m2) {
        if (m1.getName().equals(m2.getName())) {
            Class<?>[] params1 = m1.getParameterTypes();
            Class<?>[] params2 = m2.getParameterTypes();
            if (params1.length == params2.length) {
                for (int i = 0; i < params1.length; i++) {
                    if (!params1[i].equals(params2[i])) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }
}
