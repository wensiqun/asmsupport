package cn.wensiqun.asmsupport.utils.lang;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

import cn.wensiqun.asmsupport.clazz.AClass;
import cn.wensiqun.asmsupport.clazz.AClassFactory;
import cn.wensiqun.asmsupport.entity.MethodEntity;
import cn.wensiqun.asmsupport.loader.ASMClassLoader;
import cn.wensiqun.asmsupport.utils.asm.ClassAdapter;


/**
 * 
 * @author 温斯群(Joe Wen)
 *
 */
public class ClassUtils extends org.apache.commons.lang3.ClassUtils {
	
	/** 基本类型Class */
	static Map<String, Class<?>> primitivesToClasses;

    static
    {
		HashMap<String, Class<?>> tmp = new HashMap<String, Class<?>>();
		tmp.put( "boolean", boolean.class );
		tmp.put( "int", int.class );
		tmp.put( "char", char.class );
		tmp.put( "short", short.class );
		tmp.put( "int", int.class );
		tmp.put( "long", long.class );
		tmp.put( "float", float.class );
		tmp.put( "double", double.class );
		tmp.put( "void", void.class );
		
		/*class description*/
		tmp.put( "Z", boolean.class );
		tmp.put( "B", byte.class );
		tmp.put( "C", char.class );
		tmp.put( "S", short.class );
		tmp.put( "I", int.class );
		tmp.put( "J", long.class );
		tmp.put( "F", float.class );
		tmp.put( "D", double.class );
		tmp.put( "V", void.class );

	    primitivesToClasses = Collections.unmodifiableMap( tmp );
    }
	
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
			
			Class<?> clazz = primitivesToClasses.get(className);
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
			Class<?> cls = (Class<?>) primitivesToClasses.get(className);
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
	public static List<MethodEntity> getAllMethod(Class<?> clazz, final String findName) throws IOException{
		final AClass owner = AClassFactory.getProductClass(clazz);
		InputStream classStream = ASMClassLoader.asmClassLoader.getResourceAsStream(clazz.getName().replace('.', '/') + ".class");
		ClassReader cr = new ClassReader(classStream);
		final List<MethodEntity> list = new ArrayList<MethodEntity>();
		
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
						

						AClass[] exceptionArray = new AClass[exceptions.length];
						for(int i=0; i<exceptions.length; i++){
							exceptionArray[i] = AClassFactory.getProductClass(forName(exceptions[i]));
						}
						
						MethodEntity me = new MethodEntity(
					    		name, owner, owner, aclass, args, returnType, exceptionArray, access);
						
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
	 * 递归获取class的所有接口
	 */
	private static void getAllInterfaces(List<Class<?>> interfaceColl, Class<?> clazz){
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

}
