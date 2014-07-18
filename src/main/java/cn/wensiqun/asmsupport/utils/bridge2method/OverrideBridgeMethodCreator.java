package cn.wensiqun.asmsupport.utils.bridge2method;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import cn.wensiqun.asmsupport.block.method.common.CommonMethodBody;
import cn.wensiqun.asmsupport.clazz.AClass;
import cn.wensiqun.asmsupport.clazz.AClassFactory;
import cn.wensiqun.asmsupport.creator.MethodCreator;
import cn.wensiqun.asmsupport.definition.method.AMethod;
import cn.wensiqun.asmsupport.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.utils.reflet.MethodUtils;


/**
 * 
 *
 */
public class OverrideBridgeMethodCreator {
	
	private AMethod validateMethod;
	
	public OverrideBridgeMethodCreator(AMethod validateMethod) {
		this.validateMethod = validateMethod;
	}

	public List<MethodCreator> getList(){
		List<MethodCreator> creatorList = new ArrayList<MethodCreator>();
		List<java.lang.reflect.Method> parentMethods = foundParentMethod();
		for(java.lang.reflect.Method method : parentMethods){
			if(needBridge(validateMethod, method)){
				creatorList.add(createBridgeMethodCreator(validateMethod, method));
			}
		}
		return creatorList;
	}

	/**
	 * 获取指定方法的父类方法或者接口中的方法
	 * 
	 * @return
	 */
    private List<java.lang.reflect.Method> foundParentMethod(){
    	List<java.lang.reflect.Method> found = new ArrayList<java.lang.reflect.Method>();
    	java.lang.reflect.Method overriden = MethodUtils.getOverriddenMethod(validateMethod);
    	if(overriden != null){
    		found.add(overriden);
    	}
    	
    	java.lang.reflect.Method[] interMethods = MethodUtils.getImplementedMethod(validateMethod);
    	if(ArrayUtils.isEmpty(interMethods)){
    		return found;
    	}
    	
    	for(java.lang.reflect.Method m : interMethods){
    		if(!containSameSignature(found, m)){
    			found.add(m);
    		}
    	}
    	return found;
    }
    
    private boolean containSameSignature(List<java.lang.reflect.Method> list, java.lang.reflect.Method comp){
    	for(java.lang.reflect.Method m : list){
    		if(MethodUtils.methodSignatureEqualWithoutOwner(m, comp)){
    			return true;
    		}
    	}
    	return false;
    }
    
    private boolean needBridge(AMethod method, java.lang.reflect.Method parent){
    	Type implReturnType = method.getMethodMeta().getReturnType();
    	Class<?> parentReturnClass = parent.getReturnType();
    	Type parentReturnType = parentReturnClass == null ? Type.VOID_TYPE : Type.getType(parentReturnClass);
    	if(parentReturnType == null){
    		parentReturnType = Type.VOID_TYPE;
    	}
    	return !parentReturnType.equals(implReturnType);
    }

	/**
     * 创建bridge方法
     * 
     * @param method 新创建重写的方法
     * @param overriden 被重写的方法
     */
    private MethodCreator createBridgeMethodCreator(AMethod method, java.lang.reflect.Method overriden){
    	final String name = method.getMethodMeta().getName();
    	
    	AClass[] argClasses = method.getMethodMeta().getArgClasses();
    	
    	String[] argNames = method.getMethodMeta().getArgNames();
    	
    	AClass returnClass = AClassFactory.getProductClass(overriden.getReturnType());
    	
    	AClass[] exceptions = method.getMethodMeta().getExceptions();
    	
    	//先消除abstract的flag，再加入bridge的flag
    	int access = (overriden.getModifiers() & ~Opcodes.ACC_ABSTRACT) + Opcodes.ACC_BRIDGE;

    	return MethodCreator.methodCreatorForAdd(name, argClasses, argNames,
                returnClass, exceptions, access, new CommonMethodBody(){

					@Override
					public void body(LocalVariable... argus) {
						
						runReturn(invoke(getThis(), name, argus));
						
					}
    		
    	});
    }
    
}
