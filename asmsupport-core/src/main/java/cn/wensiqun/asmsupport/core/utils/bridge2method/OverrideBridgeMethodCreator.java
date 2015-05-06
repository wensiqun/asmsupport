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
import cn.wensiqun.asmsupport.core.clazz.AClassFactory;
import cn.wensiqun.asmsupport.core.creator.MethodCreator;
import cn.wensiqun.asmsupport.core.definition.method.AMethod;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.core.utils.lang.ArrayUtils;
import cn.wensiqun.asmsupport.core.utils.reflect.MethodUtils;
import cn.wensiqun.asmsupport.org.objectweb.asm.Opcodes;
import cn.wensiqun.asmsupport.org.objectweb.asm.Type;
import cn.wensiqun.asmsupport.standard.def.clazz.AClass;


/**
 * This class will convert a method to bridge method.
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
    
    private boolean needBridge(AMethod method, java.lang.reflect.Method parent){
    	Type implReturnType = method.getMeta().getReturnType();
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
    private MethodCreator createBridgeMethodCreator(AMethod method, java.lang.reflect.Method overriden){
    	final String name = method.getMeta().getName();
    	
    	AClass[] argClasses = method.getMeta().getArgClasses();
    	
    	String[] argNames = method.getMeta().getArgNames();
    	
    	AClass returnClass = AClassFactory.getType(overriden.getReturnType());
    	
    	AClass[] exceptions = method.getMeta().getExceptions();
    	
    	//remove abstract flag first and then add bridge flag.
    	int access = (overriden.getModifiers() & ~Opcodes.ACC_ABSTRACT) + Opcodes.ACC_BRIDGE;

    	return MethodCreator.methodCreatorForAdd(name, argClasses, argNames,
                returnClass, exceptions, access, new KernelMethodBody(){

					@Override
					public void body(LocalVariable... argus) {
						
						return_(call(this_(), name, argus));
						
					}
    		
    	});
    }
    
}
