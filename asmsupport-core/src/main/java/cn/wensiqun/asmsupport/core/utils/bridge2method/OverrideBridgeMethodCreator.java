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
package cn.wensiqun.asmsupport.core.utils.bridge2method;

import java.util.ArrayList;
import java.util.List;

import cn.wensiqun.asmsupport.core.block.method.common.KernelMethodBody;
import cn.wensiqun.asmsupport.core.builder.impl.MethodBuilderImpl;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.core.utils.reflect.MethodUtils;
import cn.wensiqun.asmsupport.org.objectweb.asm.Opcodes;
import cn.wensiqun.asmsupport.org.objectweb.asm.Type;
import cn.wensiqun.asmsupport.standard.def.clazz.AClass;
import cn.wensiqun.asmsupport.standard.def.clazz.AClassFactory;
import cn.wensiqun.asmsupport.standard.def.method.AMethodMeta;
import cn.wensiqun.asmsupport.utils.lang.ArrayUtils;


/**
 * This class will convert a method to bridge method.
 *
 */
public class OverrideBridgeMethodCreator {
	
	private AMethodMeta validateMethod;
	
	public OverrideBridgeMethodCreator(AMethodMeta validateMethod) {
		this.validateMethod = validateMethod;
	}

	public List<MethodBuilderImpl> getList(){
		List<MethodBuilderImpl> creatorList = new ArrayList<MethodBuilderImpl>();
		List<java.lang.reflect.Method> parentMethods = foundParentMethod();
		for(java.lang.reflect.Method method : parentMethods){
			if(needBridge(validateMethod, method)){
				creatorList.add(createBridgeMethodCreator(validateMethod, method));
			}
		}
		return creatorList;
	}

	/**
	 * Get all super method
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
    
    private boolean needBridge(AMethodMeta method, java.lang.reflect.Method parent){
    	Type implReturnType = method.getReturnType();
    	Class<?> parentReturnClass = parent.getReturnType();
    	Type parentReturnType = parentReturnClass == null ? Type.VOID_TYPE : Type.getType(parentReturnClass);
    	if(parentReturnType == null){
    		parentReturnType = Type.VOID_TYPE;
    	}
    	return !parentReturnType.equals(implReturnType);
    }

	/**
     * Create bridge method
     * 
     * @param method the overide method
     * @param overriden the super method
     */
    private MethodBuilderImpl createBridgeMethodCreator(AMethodMeta method, java.lang.reflect.Method overriden){
    	final String name = method.getName();
    	
    	AClass[] argClasses = method.getArgClasses();
    	
    	String[] argNames = method.getArgNames();
    	
    	AClass returnClass = AClassFactory.getType(overriden.getReturnType());
    	
    	AClass[] exceptions = method.getExceptions();
    	
    	//remove abstract flag first and then add bridge flag.
    	int access = (overriden.getModifiers() & ~Opcodes.ACC_ABSTRACT) + Opcodes.ACC_BRIDGE;

    	return MethodBuilderImpl.methodCreatorForAdd(name, argClasses, argNames,
                returnClass, exceptions, access, new KernelMethodBody(){

					@Override
					public void body(LocalVariable... argus) {
						
						return_(call(this_(), name, argus));
						
					}
    		
    	});
    }
    
}
