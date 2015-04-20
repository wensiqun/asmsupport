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
package cn.wensiqun.asmsupport.core.utils.jls15_12_2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.wensiqun.asmsupport.core.clazz.AClass;
import cn.wensiqun.asmsupport.core.clazz.ArrayClass;
import cn.wensiqun.asmsupport.core.clazz.ProductClass;
import cn.wensiqun.asmsupport.core.clazz.SemiClass;
import cn.wensiqun.asmsupport.core.definition.method.AMethod;
import cn.wensiqun.asmsupport.core.definition.method.meta.AMethodMeta;
import cn.wensiqun.asmsupport.core.utils.AClassUtils;
import cn.wensiqun.asmsupport.core.utils.ASConstant;
import cn.wensiqun.asmsupport.core.utils.collections.CollectionUtils;
import cn.wensiqun.asmsupport.core.utils.collections.LinkedMultiValueMap;
import cn.wensiqun.asmsupport.core.utils.collections.MapLooper;
import cn.wensiqun.asmsupport.core.utils.collections.MultiValueMap;
import cn.wensiqun.asmsupport.core.utils.jls.TypeUtils;
import cn.wensiqun.asmsupport.core.utils.lang.ArrayUtils;
import cn.wensiqun.asmsupport.core.utils.lang.ClassUtils;
import cn.wensiqun.asmsupport.core.utils.reflect.ModifierUtils;


public class MethodChooser implements IMethodChooser, DetermineMethodSignature {

	/**
	 * where is this method invoke operator
	 */
    protected AClass whereCall;
    
    /**
     * what class call
     */
    protected AClass directCallClass;
    
    /**
     * method name
     */
    protected String name;
    
    /**
     * 
     */
    protected AClass[] argumentTypes;
    
	public MethodChooser(AClass whereCall, AClass directCallClass,
			String name, AClass[] argumentTypes) {
		super();
		this.whereCall = whereCall;
		this.directCallClass = directCallClass;
		this.name = name;
		this.argumentTypes = argumentTypes;
	}

	@Override
	public AMethodMeta chooseMethod() {
		
		potentially = identifyPotentiallyApplicableMethods();
        
        AMethodMeta me = firstPhase();
        if(me != null){
            return me;
        }
        
        me = secondPhase();
        
        if(me != null){
            return me;
        }
        
        me = thirdPhase();
        
        return me;
	}
	
	@Override
	public Map<AClass, List<AMethodMeta>> identifyPotentiallyApplicableMethods(){
		
		@SuppressWarnings("serial")
		MultiValueMap<AClass,AMethodMeta> tempPotentially = new LinkedMultiValueMap<AClass, AMethodMeta>(){

			@Override
			public void add(AClass key, AMethodMeta value) {
				//1.check name
				//2.check accessible
				if(!name.equals(value.getName()) ||
				   !AClassUtils.visible(whereCall, directCallClass, value.getActuallyOwner(), value.getModifier())){
				    return;	
				}
				
				int entityLength = ArrayUtils.getLength(value.getArgClasses());
				int argumentLength = ArrayUtils.getLength(argumentTypes);
				
				//check arity of method
				if(ModifierUtils.isVarargs(value.getModifier())){
					if(argumentLength < entityLength - 1){
						return;
					}
				}else{
					if(argumentLength != entityLength){
						return;
					}
				}
				
				//to do generic later
				
				if(!containsValue(value)){
					super.add(key, value);
				}
			}

			@Override
			public boolean containsValue(Object value) {
				if(value == null || !(value instanceof AMethodMeta)){
					return false;
				}
				
				Set<Entry<AClass, List<AMethodMeta>>> entrySet = entrySet();
				for(Entry<AClass, List<AMethodMeta>> entry : entrySet){
					if(containsValueInList(entry.getValue(), (AMethodMeta) value)){
						return true;
					}
				}
				return false;
			}
			
			/**
			 * 
			 * @param list
			 * @param value
			 * @return
			 */
			private boolean containsValueInList(List<AMethodMeta> list, AMethodMeta value){
				if(!CollectionUtils.isEmpty(list)){
					for(AMethodMeta method : list){
						if(isOverrideMethod(method, value)){
							return true;
						}
					}
				}
				return false;
			}
			
			/**
			 * 
			 * @param child
			 * @param super_
			 * @return
			 */
			private boolean isOverrideMethod(AMethodMeta child, AMethodMeta super_){
				if(!child.getName().equals(super_.getName())){
					return false;
				}
				
				if(!child.getActuallyOwner().isChildOrEqual(super_.getActuallyOwner())){
					return false;
				}
				
				AClass[] childArgs = child.getArgClasses();
				AClass[] superArgs = super_.getArgClasses();
				if(ArrayUtils.isSameLength(childArgs, superArgs)){
					if(ArrayUtils.isEmpty(childArgs) && ArrayUtils.isEmpty(superArgs)){
						return true;
					}else{
						for(int i = 0; i<childArgs.length; i++){
							if(!childArgs[i].equals(superArgs[i])){
								return false;
							}
						}
						return true;
					}
				}
				return false;
			}
			
		};
		
		try {
		    if(directCallClass instanceof SemiClass){
	            if(ASConstant.INIT.equals(name)){
	                for(AMethod method : ((SemiClass)directCallClass).getConstructors()){
	                    tempPotentially.add(directCallClass, method.getMethodMeta());
	                }
	            }else{
	                for(AMethod method : ((SemiClass)directCallClass).getMethods()){
	                    tempPotentially.add(directCallClass, method.getMethodMeta());
	                }
	                fetchMatchMethod(tempPotentially, directCallClass.getSuperClass(), name);
	            }
	        }else if(directCallClass instanceof ProductClass){
	            if(ASConstant.INIT.equals(name)){
	                for(AMethod method : ((ProductClass)directCallClass).getConstructors()){
	                    tempPotentially.add(directCallClass, method.getMethodMeta());
	                }
	                
	                List<AMethodMeta> methods = ClassUtils.getAllMethod(((ProductClass) directCallClass).getReallyClass(), name);
	                for(AMethodMeta me : methods){
	                    tempPotentially.add(me.getActuallyOwner(), me);
	                }
	                
	            }else{
	                for(AMethod method : ((ProductClass)directCallClass).getMethods()){
	                    tempPotentially.add(directCallClass, method.getMethodMeta());
	                }
	                fetchMatchMethod(tempPotentially, ((ProductClass) directCallClass).getReallyClass(), name);
	            }
	        }else{
	            fetchMatchMethod(tempPotentially, Object.class, name);
	        }
		} catch (IOException e) {
		    throw new RuntimeException(e);
		}
		
		return tempPotentially;
	}
	
	/**
	 * 
	 * @param potentially
	 * @param where
	 * @param name
	 * @throws IOException
	 */
	private void fetchMatchMethod(MultiValueMap<AClass,AMethodMeta> potentially, Class<?> where, String name) throws IOException{
		if(where == null){
			return;
		}
		
		List<AMethodMeta> methods = ClassUtils.getAllMethod(where, name);
		for(AMethodMeta me : methods){
			potentially.add(me.getActuallyOwner(), me);
		}
		
		fetchMatchMethod(potentially, where.getSuperclass(), name);
		
		Class<?>[] interfaces = where.getInterfaces();
		if(ArrayUtils.isNotEmpty(interfaces)){
			for(Class<?> inter : interfaces){
				fetchMatchMethod(potentially, inter, name);
			}
		}
		
	}

	/** */
	private Map<AClass, List<AMethodMeta>> potentially;

	private void removePotentiallyMethod(AMethodMeta... entities){
		if(potentially != null && potentially.size() > 0 && ArrayUtils.isNotEmpty(entities)){
			for(AMethodMeta e : entities){
				potentially.get(e.getActuallyOwner()).remove(e);
			}
		}
	}
	
	@Override
	public AMethodMeta firstPhase(){
		//final MultiValueMap<AClass, MethodEntity> map = new LinkedMultiValueMap<AClass, MethodEntity>();
		final List<AMethodMeta> list = new ArrayList<AMethodMeta>();
		new MapLooper<AClass, List<AMethodMeta>>(potentially){
			@Override
			protected void process(AClass key, List<AMethodMeta> value) {
				for(AMethodMeta e : value){
					filter(key, e);
				}
			}
			
			private void filter(AClass key, AMethodMeta entity){
				AClass[] potentialMethodArgs = entity.getArgClasses();
						
				if(ModifierUtils.isVarargs(entity.getModifier())){
					return;
				}
				if(ArrayUtils.getLength(argumentTypes) != ArrayUtils.getLength(potentialMethodArgs)) {
                    return;
                }
				for(int i=0, len = ArrayUtils.getLength(potentialMethodArgs); i<len; i++){
					AClass actuallyArg = argumentTypes[i];
					AClass potentialArg = potentialMethodArgs[i];
					if(!TypeUtils.isSubtyping(actuallyArg, potentialArg))
						return;
				}
				list.add(entity);
			}
			
		}.loop();
		removePotentiallyMethod(list.toArray(new AMethodMeta[list.size()]));
		return choosingTheMostSpecificMethod(list);
	}

	@Override
	public AMethodMeta secondPhase(){
		final List<AMethodMeta> list = new ArrayList<AMethodMeta>();
		new MapLooper<AClass, List<AMethodMeta>>(potentially){
			@Override
			protected void process(AClass key, List<AMethodMeta> value) {
				for(AMethodMeta e : value){
					filter(key, e);
				}
			}
			
			private void filter(AClass key, AMethodMeta entity){
				if(ModifierUtils.isVarargs(entity.getModifier())){
					return;
				}
				AClass[] potentialMethodArgs = entity.getArgClasses();
				for(int i=0, len = ArrayUtils.getLength(potentialMethodArgs); i<len; i++){
					AClass actuallyArg = argumentTypes[i];
					AClass potentialArg = potentialMethodArgs[i];
					if(!ConversionsPromotionsUtils.checkMethodInvocatioConversion(actuallyArg, potentialArg))
						return;
				}
				list.add(entity);
			}
			
		}.loop();
		removePotentiallyMethod(list.toArray(new AMethodMeta[list.size()]));
		return choosingTheMostSpecificMethod(list);
	}

	@Override
	public AMethodMeta thirdPhase(){
		final List<AMethodMeta> list = new ArrayList<AMethodMeta>();
		new MapLooper<AClass, List<AMethodMeta>>(potentially){
			@Override
			protected void process(AClass key, List<AMethodMeta> value) {
				for(AMethodMeta e : value){
					filter(key, e);
				}
			}
			
			private void filter(AClass key, AMethodMeta entity){
				if(!ModifierUtils.isVarargs(entity.getModifier())){
					return;
				}
				
				AClass[] potentialMethodArgs = entity.getArgClasses();
				int potenMtdArgLen = ArrayUtils.getLength(potentialMethodArgs);
				for(int i=0; i < potenMtdArgLen - 1 ; i++){
					AClass actuallyArg = argumentTypes[i];
					AClass potentialArg = potentialMethodArgs[i];
					if(!TypeUtils.isSubtyping(actuallyArg, potentialArg) && 
					   !ConversionsPromotionsUtils.checkMethodInvocatioConversion(actuallyArg, potentialArg))
						return;
				}
				
				int actMtdArgLen = ArrayUtils.getLength(argumentTypes);
				if(actMtdArgLen >= potenMtdArgLen){
					AClass varargType = ((ArrayClass)potentialMethodArgs[potenMtdArgLen - 1]).getRootComponentClass();
					for(int i = potenMtdArgLen - 1; i<actMtdArgLen ; i++){
						AClass actuallyArg = argumentTypes[i];
						if(!TypeUtils.isSubtyping(actuallyArg, varargType) && 
						   !ConversionsPromotionsUtils.checkMethodInvocatioConversion(actuallyArg, varargType))
							return;
					}
				}
				
				list.add(entity);
			}
			
		}.loop();
		if(potentially != null) potentially.clear();
		return choosingTheMostSpecificMethod(list);
	}

	@Override
	public AMethodMeta choosingTheMostSpecificMethod(List<AMethodMeta> entities) {
		Set<AMethodMeta> most = null;
		for(AMethodMeta e : entities){
			if(CollectionUtils.isEmpty(most)){
				most = new HashSet<AMethodMeta>();
				most.add(e);
			}else{
				Set<AMethodMeta> newMost = new HashSet<AMethodMeta>();
				for(AMethodMeta mostE : most){
					CollectionUtils.addAll(newMost, mostSpecificMethod(mostE, e));
				}
				most = newMost;
			}
		}
		
		int mostLen = most == null ? 0 : most.size();
        if(mostLen > 1) {
            //first remove varargs method and readd to potentially
            //in order to third phase choose.
            Set<AMethodMeta> newMost = new HashSet<AMethodMeta>();
            for(AMethodMeta m : most){
                if(!ModifierUtils.isVarargs(m.getModifier())) {
                    newMost.add(m);
                }
            }
            most = newMost;
        }
		
		if(CollectionUtils.isNotEmpty(most)){
			if(most.size() == 1){
				return (AMethodMeta) most.toArray()[0];
			}else{
				String argTypes = ArrayUtils.toString(argumentTypes);
				throw new IllegalArgumentException("The method invocation is ambiguous: " + name + "(" + argTypes.substring(1, argTypes.length() - 1) + ")");
			}
		}
		return null;
	}
	
	private AMethodMeta[] mostSpecificMethod(AMethodMeta method1, AMethodMeta method2){
		
		//fixed-arity method compare;
		if(!ModifierUtils.isVarargs(method1.getModifier()) && !ModifierUtils.isVarargs(method2.getModifier())){
			if(mostSpecificFixedArityMethod(method1, method2)){
				return new AMethodMeta[]{method1};
			}else if(mostSpecificFixedArityMethod(method2, method1)){
				return new AMethodMeta[]{method2};
			}else{
				return new AMethodMeta[]{method1, method2};
			}
		}else if(ModifierUtils.isVarargs(method1.getModifier()) && ModifierUtils.isVarargs(method2.getModifier())){
			if(mostSpecificVarargMethod(method1, method2)){
				return new AMethodMeta[]{method1};
			}else if(mostSpecificVarargMethod(method2, method1)){
				return new AMethodMeta[]{method2};
			}else{
				return new AMethodMeta[]{method1, method2};
			}
		}
		return null;
	};
	
	/**
	 * check method1 is most specific than method2 for fixed-arity member method
	 * 
	 * @param method1
	 * @param method2
	 * @return
	 */
	private boolean mostSpecificFixedArityMethod(AMethodMeta method1, AMethodMeta method2){
		AClass[] t = method1.getArgClasses();
		AClass[] u = method2.getArgClasses();
		int n = ArrayUtils.getLength(t);
		if(n > 0){
			for(int j=0; j<n; j++){
				if(!TypeUtils.isSubtyping(t[j], u[j])){
					return false;
				}
			}
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * <p>
	 * check method1 is most specific than method2 for variable arity member method
	 * </p>
	 * @param m1
	 * @param m2
	 * @return
	 */
	private boolean mostSpecificVarargMethod(AMethodMeta m1, AMethodMeta m2){
		AClass[] t = m1.getArgClasses();
		AClass[] u = m2.getArgClasses();
		
		int n;
		int k;
		if(ArrayUtils.getLength(t) >= ArrayUtils.getLength(u)){
			n = ArrayUtils.getLength(t);
			k = ArrayUtils.getLength(u);
		}else{
			n = ArrayUtils.getLength(u);
			k = ArrayUtils.getLength(t);
		}
		
		for(int j = 0, l = k - 1; j < l ; j++){
			if(!TypeUtils.isSubtyping(t[j], u[j])){
				return false;
			}
		}
		
		
		if(ArrayUtils.getLength(t) >= ArrayUtils.getLength(u)){
			for(int j = k - 1; j < n; j++){
				if(!TypeUtils.isSubtyping(t[j], u[k - 1])){
					return false;
				}
			}
		}else{
			for(int j = k - 1; j < n; j++){
				if(!TypeUtils.isSubtyping(t[k - 1], u[j])){
					return false;
				}
			}
			
		}
		
		return true;
	}
	
	
}
