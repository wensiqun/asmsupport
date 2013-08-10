package jw.asmsupport.utils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;


import jw.asmsupport.clazz.AClass;
import jw.asmsupport.entity.MethodEntity;

/**
 * 
 * @author 温斯群(Joe Wen)
 *
 */
public class MethodUtils {
    /**
     * 
     * @param m1
     * @param m2
     * @return
     */
    public static boolean methodEqualInHierarchy(MethodEntity m1, MethodEntity m2){
        if(m1.getName().equals(m2.getName()) &&
           m1.getReturnType().equals(m2.getReturnType())){
                    
           /* Avoid unnecessary cloning */
            AClass[] params1 = m1.getArgClasses();
            AClass[] params2 = m2.getArgClasses();
            if (params1.length == params2.length) {
                for (int i = 0; i < params1.length; i++) {
                    if (!params1[i].equals(params2[i])){
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
     * @param me
     * @param method
     * @return
     */
    public static boolean methodEqual(MethodEntity me, Method method){
    	if(me.getName().equals(method.getName()) &&
    	   me.getReturnClass().getName().equals(method.getReturnType().getName())){
    		AClass[] mePara = me.getArgClasses();
    		Class<?>[] methodPara = method.getParameterTypes();
    		if(mePara.length == methodPara.length){
    			for(int i=0, len = mePara.length; i<len ; i++){
    				if(!mePara[i].getName().equals(methodPara[i].getName())){
    					return false;
    				}
    			}
    			return true;
    		}
    	}
    	return false;
    }
    
    public static boolean methodEqualWithoutOwner(Method m1, Method m2){
        if(m1.getName().equals(m2.getName()) &&
           m1.getReturnType().equals(m2.getReturnType())){
            /* Avoid unnecessary cloning */
            Class<?>[] params1 = m1.getParameterTypes();
            Class<?>[] params2 = m2.getParameterTypes();
            if (params1.length == params2.length) {
                for (int i = 0; i < params1.length; i++) {
                    if (!params1[i].equals(params2[i])){
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }
    
    public static List<Method> getMethod(Class<?> type, String name, Class<?>... parameterTypes){
    	List<Method> methodArray = new ArrayList<Method>();
    	getMethod(methodArray, type, name, parameterTypes);
    	return methodArray;
    }
    
    private static void getMethod(List<Method> foundMethods, Class<?> type, String name, Class<?>... parameterTypes) throws SecurityException{
    	if(type == null){
    		return;
    	}
    	
    	try {
    		Method sm = type.getDeclaredMethod(name, parameterTypes);
    		boolean exist = false;
    		for(Method m : foundMethods){
    			if(methodEqualWithoutOwner(sm, m)){
    				exist = true;
    				break;
    			}
    		}
    		if(!exist){
    			foundMethods.add(sm);
    		}
		} catch (NoSuchMethodException e) {
		}
    	
    	Class<?>[] interfaces = type.getInterfaces();
    	if(ArrayUtils.isNotEmpty(interfaces)){
    		for(Class<?> inter : interfaces){
    			getMethod(foundMethods, inter, name, parameterTypes);
    		}
    	}
    	
    	getMethod(foundMethods, type.getSuperclass(), name, parameterTypes);
    }
}
