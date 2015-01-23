package cn.wensiqun.asmsupport.core.utils.lang;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import cn.wensiqun.asmsupport.core.clazz.AClass;
import cn.wensiqun.asmsupport.core.clazz.AClassFactory;
import cn.wensiqun.asmsupport.core.definition.method.meta.AMethodMeta;
import cn.wensiqun.asmsupport.core.loader.ASMClassLoader;
import cn.wensiqun.asmsupport.core.utils.AClassUtils;
import cn.wensiqun.asmsupport.core.utils.asm.ClassAdapter;
import cn.wensiqun.asmsupport.org.apache.commons.collections.CollectionUtils;
import cn.wensiqun.asmsupport.org.apache.commons.lang3.ArrayUtils;
import cn.wensiqun.asmsupport.org.objectweb.asm.ClassReader;
import cn.wensiqun.asmsupport.org.objectweb.asm.MethodVisitor;
import cn.wensiqun.asmsupport.org.objectweb.asm.Type;


/**
 * 
 * @author 温斯群(Joe Wen)
 *
 */
public class ClassUtils extends cn.wensiqun.asmsupport.org.apache.commons.lang3.ClassUtils {
	
    /**
     * 
     * @param cls
     * @return
     */
    public static Class<?> getRootComponentType(Class<?> cls){
        if(cls.isArray()){
            return getRootComponentType(cls.getComponentType());
        }else{
            return cls;
        }
    }
    
    /**
     * 
     * @param arrayClass
     * @return
     */
    public static int getDimension(Class<?> arrayClass){
    	if(arrayClass.isArray()){
    		return StringUtils.findAllIndexes(arrayClass.getName(), "[").length;
    	}else{
    		return 0;
    	}
    }
    
    /**
     * determine cls1 is super of cls2
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

    public static Class<?> getMethodOwner(Class<?> owner, String name,
            Class<?> arguments) {
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

        String[] clses = StringUtils.split(innerCls.getName(),
                INNER_CLASS_SEPARATOR_CHAR);

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
	 * @param className
	 * @return
	 * @throws ClassNotFoundException
	 */
	public static Class<?> forName(String className) throws ClassNotFoundException {
		try {
			
			Class<?> clazz = primitivesToClasses(className);
			if(clazz != null){
				return clazz;
			}
			
			if(className.startsWith("[")){
				className = StringUtils.replace(className, "/", ".");
			}else if(className.startsWith("L")){
				className = StringUtils.substring(className, 1, className.length() - 1);
				className = StringUtils.replace(className, "/", ".");
			}
			
			return Class.forName(className);
		} catch (ClassNotFoundException e) {
			Class<?> cls = (Class<?>) primitivesToClasses(className);
			if(cls != null){
				return cls;
			}else{
				//chech for internal name
				className = StringUtils.replace(className, "/", ".");
				try{
					return Class.forName(className);
				} catch (ClassNotFoundException e1) {
					throw e;
				}
			}
		}
	}
	
	public static Class<?> primitivesToClasses(String primNameOrDesc)
	{
		if("boolean".equals(primNameOrDesc)){return boolean.class;}
		if("int".equals(primNameOrDesc)){return int.class;}
		if("char".equals(primNameOrDesc)){return char.class;}
		if("short".equals(primNameOrDesc)){return short.class;}
		if("int".equals(primNameOrDesc)){return int.class;}
		if("long".equals(primNameOrDesc)){return long.class;}
		if("float".equals(primNameOrDesc)){return float.class;}
		if("double".equals(primNameOrDesc)){return double.class;}
		if("void".equals(primNameOrDesc)){return void.class;}
		
		/*class description*/
		if("Z".equals(primNameOrDesc)){return boolean.class;}
		if("B".equals(primNameOrDesc)){return byte.class;}
		if("C".equals(primNameOrDesc)){return char.class;}
		if("S".equals(primNameOrDesc)){return short.class;}
		if("I".equals(primNameOrDesc)){return int.class;}
		if("J".equals(primNameOrDesc)){return long.class;}
		if("F".equals(primNameOrDesc)){return float.class;}
		if("D".equals(primNameOrDesc)){return double.class;}
		if("V".equals(primNameOrDesc)){return void.class;}
		return null;
	}
	
	/**
	 * 
	 * @param sourceClass
	 * @return
	 */
	public static List<List<Class<?>>> getClassUpwardsRoute(Class<?> sourceClass){
		return getClassUpwardsRoute(sourceClass, null);
	}

	/**
	 * 
	 * @param sourceClass
	 * @param destionClass
	 * @return
	 */
	public static List<List<Class<?>>> getClassUpwardsRoute(Class<?> sourceClass, Class<?> destionClass){
		List<List<Class<?>>> list = new ArrayList<List<Class<?>>>();
		getClassUpwardsRoute(list, null, sourceClass, destionClass);
		
		List<List<Class<?>>> allRoute = new ArrayList<List<Class<?>>>();
		for(int i=0; i<list.size(); i++){
			List<Class<?>> route = list.get(i);
			if(destionClass == null || route.get(route.size() - 1).equals(destionClass)){
				allRoute.add(route);
			}
		}
		
		return allRoute;
	}
	
    private static void getClassUpwardsRoute(List<List<Class<?>>> allRoutes, List<Class<?>> currentRoute, Class<?> currentClass, Class<?> destionClass){
    	if(currentClass == null){
    		return;
    	}
    	
    	if(currentRoute == null){
    		currentRoute = new ArrayList<Class<?>>();
    		allRoutes.add(currentRoute);
    	}
    	
        currentRoute.add(currentClass);
    	
    	if(currentClass.equals(destionClass)){
    		return;
    	}
    	
        Class<?>[] interfaces = currentClass.getInterfaces();
    	
    	if(ArrayUtils.isNotEmpty(interfaces)){
    		for(Class<?> inter : interfaces){
    			List<Class<?>> newRoute = new ArrayList<Class<?>>();
    			CollectionUtils.addAll(newRoute, currentRoute.iterator());
    			allRoutes.add(newRoute);
    			getClassUpwardsRoute(allRoutes, newRoute, inter, destionClass);
    		}
    	}
		
    	getClassUpwardsRoute(allRoutes, currentRoute, currentClass.getSuperclass(), destionClass);
    	
	}
	
	/**
	 * 
	 * @param clazz
	 * @return
	 * @throws IOException
	 */
	public static List<AMethodMeta> getAllMethod(Class<?> clazz, final String findName) throws IOException{
		final AClass owner = AClassFactory.getProductClass(clazz);
		InputStream classStream = ASMClassLoader.getInstance().getResourceAsStream(clazz.getName().replace('.', '/') + ".class");
		ClassReader cr = new ClassReader(classStream);
		final List<AMethodMeta> list = new ArrayList<AMethodMeta>();
		
		cr.accept(new ClassAdapter(){

			@Override
			public MethodVisitor visitMethod(int access, String name,
					String desc, String signature, String[] exceptions) {
				if((StringUtils.isEmpty(findName) || name.equals(findName))){
					
					if(exceptions == null){
						exceptions = new String[0];
					}
					
					try {
						
						Type[] types = Type.getArgumentTypes(desc);
						AClass[] aclass = new AClass[types.length];
						String[] args = new String[types.length];
						for(int i=0; i<types.length; i++){
							aclass[i] = AClassFactory.getProductClass(forName(types[i].getDescriptor()));
							args[i] = "arg" + i;
						}
						
						AClass returnType = AClassFactory.getProductClass(
								forName(Type.getReturnType(desc).getDescriptor()));
						

						AClass[] exceptionAclassArray = new AClass[exceptions.length];
						for(int i=0; i<exceptions.length; i++){
							exceptionAclassArray[i] = AClassFactory.getProductClass(forName(exceptions[i]));
						}
						
						AMethodMeta me = new AMethodMeta(
					    		name, owner, owner, aclass, args, returnType, exceptionAclassArray, access);
						list.add(me);
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
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
	public static List<Class<?>> getAllInterfaces(Class<?> clazz){
		List<Class<?>> interfaceColl = new ArrayList<Class<?>>();
		getAllInterfaces(interfaceColl, clazz);
		return interfaceColl;
	}
	
	
	/**
	 * 递归获取class的所有接口，并且保存到传入的List中
	 */
	public static void getAllInterfaces(List<Class<?>> interfaceColl, Class<?> clazz){
		if(clazz == null || Object.class.equals(clazz)){
			return;
		}

		//get interface from super class
		getAllInterfaces(interfaceColl, clazz.getSuperclass());
		
		Class<?>[] interfaces =	clazz.getInterfaces();
		if(ArrayUtils.isNotEmpty(interfaces)){
			for(Class<?> inter : interfaces){
				if(!interfaceColl.contains(inter)){
					interfaceColl.add(inter);
				}

				//get interface from current interface
				getAllInterfaces(interfaceColl, inter);
			}
		}
		
	}
	
	
	/**
     * 判断是否可见
     * 
     * @param invoker 调用者所在的类
     * @param invoked 被调用的方法或者field所在的类
     * @param actuallyInvoked 被调用的方法或者field实际所在的类 actuallyInvoked必须是invoked或是其父类
     * @param mod 被调用的方法或者field的修饰符
     * @return
     */
    public static boolean visible(Class<?> invoker, Class<?> invoked, Class<?> actuallyInvoked, int mod){
        return AClassUtils.visible(
        		AClassFactory.getProductClass(invoker), 
        		AClassFactory.getProductClass(invoked), 
        		AClassFactory.getProductClass(actuallyInvoked), mod);
    }

}
