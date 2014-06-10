package cn.wensiqun.asmsupport.creator;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Type;

import cn.wensiqun.asmsupport.clazz.NewMemberClass;
import cn.wensiqun.asmsupport.clazz.SemiClass;
import cn.wensiqun.asmsupport.definition.method.AMethod;
import cn.wensiqun.asmsupport.exception.ClassException;
import cn.wensiqun.asmsupport.utils.bridge2method.OverrideBridgeMethodCreator;
import cn.wensiqun.asmsupport.utils.lang.ClassFileUtils;
import cn.wensiqun.asmsupport.utils.lang.StringUtils;
import cn.wensiqun.asmsupport.utils.reflet.MethodUtils;
import cn.wensiqun.asmsupport.utils.reflet.ModifierUtils;

public abstract class AbstractClassCreatorContext extends AbstractClassContext {
	
    private static Log LOG = LogFactory.getLog(AbstractClassCreatorContext.class);

    protected SemiClass sc;

    protected boolean haveInitMethod;
    
    public AbstractClassCreatorContext(int version, int access, String name,
            Class<?> superCls, Class<?>[] interfaces) {
        if (superCls == null) {
            superCls = Object.class;
        } else if (superCls.isInterface()) {
            throw new ClassException("the super class \"" + superCls.getName()
                    + "\" is an interface");
        }
        sc = newSemiClass(version, access, name, superCls, interfaces);
        cw = new ClassWriter(0);
        methodCreaters = new ArrayList<IMethodCreator>();
        fieldCreators = new ArrayList<IGlobalVariableCreator>();
    }

    @Override
	public NewMemberClass getCurrentClass() {
		return sc;
	}

	@Override
    public Class<?> startup() {
        String[] interfaceStrs;
        if(sc.getInterfaces() == null){
            interfaceStrs = new String[0];
        }else{
            interfaceStrs = new String[sc.getInterfaces().length];
        }
        for (int i = 0; i < interfaceStrs.length; i++) {
            interfaceStrs[i] = Type.getInternalName(sc.getInterfaces()[i]);
        }
        
        if(LOG.isDebugEnabled()){
        	LOG.debug("Starting create class : " + sc.getName());
        }
        
        // create class
        cw.visit(sc.getVersion(), sc.getModifiers(),
                sc.getName().replace('.', '/'), null,
                Type.getInternalName(sc.getSuperClass()), interfaceStrs);

        //beforeCreate        
        this.beforeCreate();
        
        // create default constructor
        checkOrCreateDefaultConstructor();
        
        // create field
        for (IMemberCreator ifc : fieldCreators) {
            ifc.create(this);
        }

        // create method
        for (IMethodCreator imc : methodCreaters) {
            imc.create(this);
        }
        
        checkOverriedAndCreateBridgeMethod();
        
        checkUnImplementMethod();

        for (IMemberCreator ifc : fieldCreators) {
            ifc.prepare();
        }

        for (IMethodCreator imc : methodCreaters) {
            imc.prepare();
        }
        
        for (IMemberCreator ifc : fieldCreators) {
            ifc.execute();
        }

        for (IMethodCreator imc : methodCreaters) {
            imc.execute();
        }

        byte[] code = cw.toByteArray();
        
        if(StringUtils.isNotBlank(classOutPutPath)){
            ClassFileUtils.toLocal(code, classOutPutPath, sc.getName());
        }

        if(LOG.isDebugEnabled()){
        	LOG.debug("End create class : " + sc.getName().replace('.', '/'));
        }
        
        return loadClass(sc.getName(), code);
    }
    
    private void checkOrCreateDefaultConstructor(){
        if (!haveInitMethod) {
            createDefaultConstructor();
        }
    }
    
    protected abstract void createDefaultConstructor();

    protected void beforeCreate(){};
    
    //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<Start checkUnImplementMethod<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    
    /**
     * 
     */
    private void checkUnImplementMethod() {
    	if(sc.isAbstract() || sc.isInterface()){
    		return;
    	}
    	
    	List<Method> abstractMethods = new ArrayList<Method>();
    	List<Method> nonAbstractMethods = new ArrayList<Method>();
    	allMethodInClass(sc.getSuperClass(), abstractMethods, nonAbstractMethods);
    	
    	for(Class<?> inter : sc.getInterfaces()){
        	allMethodInClass(inter, abstractMethods, nonAbstractMethods);
    	}
    	
    	for(int i=0; i<abstractMethods.size();){
    		Method abstractMethod = abstractMethods.get(i);
    		boolean exist = false;
    		
    		for(int j=0; j<nonAbstractMethods.size(); j++){
        		Method nonAbstractMethod = nonAbstractMethods.get(j);
    			if(MethodUtils.methodEqualWithoutOwner(abstractMethod, nonAbstractMethod)){
    				abstractMethods.remove(i);
    				nonAbstractMethods.remove(j);
    				exist = true;
    				break;
    			}
    		}
    		if(!exist){
    			i++;
    		}
    	}
    	
    	//#30205 [BUG] 新建方法中无法调用重写的方法 
    	List<AMethod> scImplMethods = 
    			new ArrayList<AMethod>(sc.getMethods());
    	for(int i=0; i<abstractMethods.size(); ){
    		Method abstractMethod = abstractMethods.get(i);
    		boolean exist = false;
    		
    		for(int j=0; j<scImplMethods.size(); j++ ) {
    			AMethod nonAbstractMethod = scImplMethods.get(j);
    			
    			if(MethodUtils.methodEqualWithoutOwner(nonAbstractMethod.getMethodMeta(), abstractMethod)){
    				abstractMethods.remove(i);
    				scImplMethods.remove(j);
    				exist = true;
    				break;
    			}
    		}
    		
    		if(!exist){
    			i++;
    		}
    	}
    	
    	if(!abstractMethods.isEmpty()){
            String lineSeq = System.getProperty("line.separator");
    		StringBuilder sb = new StringBuilder("The type ").append(sc)
    		.append(" must implement the inherited abstract method :").append(lineSeq);
    		for(Method m : abstractMethods){
    			sb.append(m.toString()).append(lineSeq);
    		}
    		throw new InternalError(sb.toString());
    	}
    	
    }
    
    /**
     * 
     * @param methods
     * @param method
     * @return
     */
    private boolean containMethod(List<Method> methods, Method method){
    	if(CollectionUtils.isNotEmpty(methods)){
    		for(Method m : methods){
    			if(MethodUtils.methodEqualWithoutOwner(m, method)){
    				return true;
    			}
    		}
    	}
    	return false;
    }
    
    
    /**
     * 
     * @param clazz
     * @param abstractMethods
     * @param nonAbstractMethods
     */
    private void allMethodInClass(Class<?> clazz, List<Method> abstractMethods, List<Method> nonAbstractMethods){
        if(clazz == null || clazz.equals(Object.class)){
    		return;
    	}
        
        Method[] methods = clazz.getDeclaredMethods();
        if(ArrayUtils.isNotEmpty(methods)){
        	for(Method m : methods){
        		if(ModifierUtils.isAbstract(m.getModifiers())){
        			if(!containMethod(abstractMethods, m)){
        				abstractMethods.add(m);
        			}
        		}else{
        			if(!containMethod(nonAbstractMethods, m)){
        				nonAbstractMethods.add(m);
        			}
        		}
        	}
        }

    	allMethodInClass(clazz.getSuperclass(), abstractMethods, nonAbstractMethods);
        
    	Class<?>[] interfaces = clazz.getInterfaces();
    	if(ArrayUtils.isNotEmpty(interfaces)){
    		for(Class<?> interfaceClass : interfaces){
    			allMethodInClass(interfaceClass, abstractMethods, nonAbstractMethods);
    			
        	}
    	}
    }

    
   //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>End checkUnImplementMethod>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    
    

    //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<Start checkOverriedAndCreateBridgeMethod<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    /**
     * 检测那些新建的方法为重写方法，如果存在返回类型不同，则抛异常或者创建bridge方法
     * 
     */
    private void checkOverriedAndCreateBridgeMethod(){
    	List<AMethod> methods = 
    			new ArrayList<AMethod>(sc.getMethods());
    	for(AMethod validateMethod : methods){
    		OverrideBridgeMethodCreator obmc = new OverrideBridgeMethodCreator(validateMethod);
    		List<MethodCreator> creatorList = obmc.getList();
    		for(MethodCreator mc : creatorList){
    			mc.create(this);
    		}
    		this.methodCreaters.addAll(creatorList);
    	}
    }
    
    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>End checkOverriedAndCreateBridgeMethod>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
}
