package cn.wensiqun.asmsupport.core.utils.reflect;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import cn.wensiqun.asmsupport.core.clazz.AClass;
import cn.wensiqun.asmsupport.core.clazz.AClassFactory;
import cn.wensiqun.asmsupport.core.clazz.ProductClass;
import cn.wensiqun.asmsupport.core.definition.method.AMethod;
import cn.wensiqun.asmsupport.core.definition.method.meta.AMethodMeta;
import cn.wensiqun.asmsupport.core.utils.AClassUtils;
import cn.wensiqun.asmsupport.core.utils.lang.ClassUtils;
import cn.wensiqun.asmsupport.org.apache.commons.lang3.ArrayUtils;

/**
 * 
 * @author 温斯群(Joe Wen)
 *
 */
public class MethodUtils {
	
	/**
	 * 获取哪个方法被传入方法重写
	 * 
	 * @param overrideMethod
	 * @return
	 */
	public static Method getOverriddenMethod(AMethod overrideMethod){
		Class<?> superClass = overrideMethod.getMethodOwner().getSuperClass();
		AMethodMeta entity = overrideMethod.getMethodMeta();
		String methodName = entity.getName();
		AClass[] argClasses = entity.getArgClasses() == null ? new AClass[0] : entity.getArgClasses();
		Class<?>[] argTypes = new Class[argClasses.length];
		for(int i=0; i<argTypes.length; i++){
			AClass argAclass = argClasses[i];
			if(argAclass instanceof ProductClass){
				argTypes[i] = ((ProductClass)argAclass).getReallyClass();
			}else{
				return null;
			}
		}
		
		for(; superClass != null && !Object.class.equals(superClass);){
			try {
				Method method = superClass.getDeclaredMethod(methodName
						, argTypes);

				AClass callerOwner = overrideMethod.getMethodOwner();
				AClass calledOwner = AClassFactory.getProductClass(method.getDeclaringClass());
				if(AClassUtils.visible(callerOwner, calledOwner, calledOwner, method.getModifiers())){
					return method;
				}
				
			} catch (NoSuchMethodException e) {
				superClass = superClass.getSuperclass();
			}
		}
	    return null;	
	}
	
	
	/**
	 * 获取哪个方法被传入方法重写
	 * 
	 * @param overrideMethod
	 * @return
	 */
	public static Method getOverriddenMethod(Method overrideMethod){
		Class<?> superClass = overrideMethod.getDeclaringClass().getSuperclass();
		for(; superClass != null && !Object.class.equals(superClass);){
			try {
				Method method = superClass.getDeclaredMethod(overrideMethod.getName(), overrideMethod.getParameterTypes());
				if(visible(overrideMethod, method)){
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
	 * @param implementMethod
	 * @return
	 */
	public static Method[] getImplementedMethod(AMethod implementMethod){
		AMethodMeta entity = implementMethod.getMethodMeta();
		String methodName = entity.getName();
		AClass[] argClasses = entity.getArgClasses() == null ? new AClass[0] : entity.getArgClasses();
		Class<?>[] argTypes = new Class[argClasses.length];
		for(int i=0; i<argTypes.length; i++){
			AClass argAclass = argClasses[i];
			if(argAclass instanceof ProductClass){
				argTypes[i] = ((ProductClass)argAclass).getReallyClass();
			}else{
				return null;
			}
		}
		
		List<Method> foundList = new ArrayList<Method>();
		List<Class<?>> interfaces = AClassUtils.getAllInterfaces(implementMethod.getMethodOwner());
		
		for(Class<?> inter : interfaces)
		{
			try {
				Method method = inter.getDeclaredMethod(methodName, argTypes);
				if(!foundList.contains(method)){
					foundList.add(method);
				}
			} catch (NoSuchMethodException e) {
			}
		}
		
		return foundList.toArray(new Method[foundList.size()]);
	}
	
	
	/**
	 * 获取被实现的接口
	 * 
	 * @param implementMethod
	 * @return
	 */
	public static Method[] getImplementedMethod(Method implementMethod){
		List<Method> foundList = new ArrayList<Method>();
		List<Class<?>> interfaces = ClassUtils.getAllInterfaces(implementMethod.getDeclaringClass());
		
		for(Class<?> inter : interfaces)
		{
			try {
				Method method = inter.getDeclaredMethod(implementMethod.getName(), implementMethod.getParameterTypes());
				if(!foundList.contains(method)){
					foundList.add(method);
				}
			} catch (NoSuchMethodException e) {
			}
		}
		
		return foundList.toArray(new Method[foundList.size()]);
	}
	

	
	/**
	 * 在第一个参数所指的方法中能够调用第二个参数所指的方法。
	 * 
	 * @param caller
	 * @param called
	 * @return true表示可以调用
	 */
	public static boolean visible(Method caller, Method called){
		AClass callerOwner = AClassFactory.getProductClass(caller.getDeclaringClass());
		AClass calledOwner = AClassFactory.getProductClass(called.getDeclaringClass());
		return AClassUtils.visible(callerOwner, calledOwner, calledOwner, called.getModifiers());
	}
	
	/**
	 * 
	 * @param method1
	 * @param method2
	 * @return
	 */
	public static boolean methodSignatureEqualWithoutOwner(Method method1, Method method2)
	{
		if(methodEqualWithoutOwner(method1, method2)){
			return method1.getReturnType().equals(method2.getReturnType());
		}
		return false;
	}
    
	/**
     * 
     * @param m1
     * @param m2
     * @return
     */
    public static boolean methodEqualWithoutOwner(AMethodMeta m1, AMethodMeta m2){
        if(m1.getName().equals(m2.getName())){
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
    public static boolean methodEqualWithoutOwner(AMethodMeta me, Method method){
    	if(me.getName().equals(method.getName())){
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
    
    /**
     * 
     * @param m1
     * @param m2
     * @return
     */
    public static boolean methodEqualWithoutOwner(Method m1, Method m2){
        if(m1.getName().equals(m2.getName())){
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
    
    
    /**
     * 
     * @param type
     * @param name
     * @param parameterTypes
     * @return
     */
    public static List<Method> getMethod(Class<?> type, String name, Class<?>... parameterTypes){
    	List<Method> methodArray = new ArrayList<Method>();
    	getMethod(methodArray, type, name, parameterTypes);
    	return methodArray;
    }
    
    
    /**
     * 
     * @param foundMethods
     * @param type
     * @param name
     * @param parameterTypes
     * @throws SecurityException
     */
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
    
    /**
     * 
     * @param method
     * @return
     */
    public static String getMethodFullName(Method method){
    	String name = method.getName();
    	String owner = method.getDeclaringClass().getName();
    	return owner + "." + name;
    }
}
