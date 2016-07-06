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

import cn.wensiqun.asmsupport.standard.def.clazz.IClass;
import cn.wensiqun.asmsupport.standard.def.method.AMethodMeta;
import cn.wensiqun.asmsupport.standard.utils.IClassUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author wensiqun at 163.com(Joe Wen)
 *
 */
public class MethodUtils {

    /**
     * Return a method, that have overrided by passed method.
     * 
     * @param entity the override method
     */
    public static AMethodMeta getOverriddenMethod(AMethodMeta entity) {
        IClass superClass = entity.getActuallyOwner().getSuperclass();
        String methodName = entity.getName();
        IClass[] argClasses = entity.getParameterTypes() == null ? new IClass[0] : entity.getParameterTypes();

        for (; superClass != null && !Object.class.equals(superClass);) {
        	AMethodMeta method = superClass.getDeclaredMethod(methodName, argClasses);
        	if(method != null) {
        		IClass callerOwner = entity.getActuallyOwner();
        		IClass calledOwner = method.getActuallyOwner();
                if (IClassUtils.visible(callerOwner, calledOwner, calledOwner, method.getModifiers())) {
                    return method;
                }
        	}
            superClass = superClass.getSuperclass();
        }
        return null;
    }

    /**
     * 
     * 
     * @param entity
     */
    public static AMethodMeta[] getImplementedMethod(AMethodMeta entity) {
        String methodName = entity.getName();
        IClass[] argClasses = entity.getParameterTypes() == null ? new IClass[0] : entity.getParameterTypes();
        List<AMethodMeta> foundList = new ArrayList<>();
        List<IClass> interfaces = IClassUtils.getAllInterfaces(entity.getActuallyOwner());
        for (IClass inter : interfaces) {
        	AMethodMeta method = inter.getDeclaredMethod(methodName, argClasses);
            if (method != null && !containsSameSignature(foundList, method)) {
                foundList.add(method);
            }
        }

        return foundList.toArray(new AMethodMeta[foundList.size()]);
    }
    
    //?????????????????????????????? NEED OPTIMIZE
    @Deprecated
    private static boolean containsSameSignature (List<AMethodMeta> foundList, AMethodMeta entity) {
    	for(AMethodMeta meta : foundList) {
    		if(entity.getName().equals(meta.getName()) && meta.getDescription().equals(entity.getDescription())) {
    			return true;
    		}
    	}
    	return false;
    }

    /**
     * 
     * @param method1
     * @param method2
     * @return
     */
    public static boolean methodSignatureEqualWithoutOwner(AMethodMeta method1, AMethodMeta method2) {
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
    public static boolean methodEqualWithoutOwner(AMethodMeta me, AMethodMeta method) {
        if (me.getName().equals(method.getName())) {
        	IClass[] mePara = me.getParameterTypes();
        	IClass[] methodPara = method.getParameterTypes();
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

    /*
     * 
     * @param m1
     * @param m2
     * @return
     */
    /*public static boolean methodEqualWithoutOwner(Method m1, Method m2) {
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
    }*/
}
