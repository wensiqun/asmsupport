package jw.asmsupport.utils.chooser.v2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jw.asmsupport.clazz.AClass;
import jw.asmsupport.clazz.ArrayClass;
import jw.asmsupport.clazz.ProductClass;
import jw.asmsupport.clazz.SemiClass;
import jw.asmsupport.definition.method.Method;
import jw.asmsupport.entity.MethodEntity;
import jw.asmsupport.utils.AClassUtils;
import jw.asmsupport.utils.ASConstant;
import jw.asmsupport.utils.ClassUtils;
import jw.asmsupport.utils.LinkedMultiValueMap;
import jw.asmsupport.utils.MapLooper;
import jw.asmsupport.utils.ModifierUtils;
import jw.asmsupport.utils.MultiValueMap;
import jw.asmsupport.utils.chooser.DetermineMethodSignature;
import jw.asmsupport.utils.chooser.IMethodChooser;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ArrayUtils;


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
	public MethodEntity chooseMethod() {
		
		potentially = identifyPotentiallyApplicableMethods();
        
        MethodEntity me = firstPhase();
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
	
	
	public Map<AClass, List<MethodEntity>> identifyPotentiallyApplicableMethods(){
		
		@SuppressWarnings("serial")
		MultiValueMap<AClass,MethodEntity> potentially = new LinkedMultiValueMap<AClass, MethodEntity>(){

			@Override
			public void add(AClass key, MethodEntity value) {
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
				if(value == null || !(value instanceof MethodEntity)){
					return false;
				}
				
				Set<Entry<AClass, List<MethodEntity>>> entrySet = entrySet();
				for(Entry<AClass, List<MethodEntity>> entry : entrySet){
					if(containsValueInList(entry.getValue(), (MethodEntity) value)){
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
			private boolean containsValueInList(List<MethodEntity> list, MethodEntity value){
				if(!CollectionUtils.isEmpty(list)){
					for(MethodEntity method : list){
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
			private boolean isOverrideMethod(MethodEntity child, MethodEntity super_){
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
		
		Class<?> reallyClass = null;
		if(directCallClass instanceof SemiClass){
			if(ASConstant.INIT.equals(name)){
				for(Method method : ((SemiClass)directCallClass).getConstructors()){
					potentially.add(directCallClass, method.getMethodEntity());
				}
			}else{
				for(Method method : ((SemiClass)directCallClass).getMethods()){
					potentially.add(directCallClass, method.getMethodEntity());
				}
			}
			reallyClass = directCallClass.getSuperClass();
		}else if(directCallClass instanceof ProductClass){
			if(ASConstant.INIT.equals(name)){
				for(Method method : ((ProductClass)directCallClass).getConstructors()){
					potentially.add(directCallClass, method.getMethodEntity());
				}
			}else{
				for(Method method : ((ProductClass)directCallClass).getMethods()){
					potentially.add(directCallClass, method.getMethodEntity());
				}
			}
			reallyClass = ((ProductClass) directCallClass).getReallyClass();
		}else{
			reallyClass = Object.class;
		}
		
		try {
			fetchMatchMethod(potentially, reallyClass, name);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return potentially;
	}
	
	/**
	 * 
	 * @param potentially
	 * @param where
	 * @param name
	 * @throws IOException
	 */
	private void fetchMatchMethod(MultiValueMap<AClass,MethodEntity> potentially, Class<?> where, String name) throws IOException{
		if(where == null){
			return;
		}
		
		List<MethodEntity> methods = ClassUtils.getAllMethod(where, name);
		for(MethodEntity me : methods){
			potentially.add(me.getActuallyOwner(), me);
		}
		
		if(name.equals(ASConstant.INIT) || name.equals(ASConstant.CLINIT)){
			return;
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
	private Map<AClass, List<MethodEntity>> potentially;

	private void removePotentiallyMethod(MethodEntity... entities){
		if(MapUtils.isNotEmpty(potentially) && ArrayUtils.isNotEmpty(entities)){
			for(MethodEntity e : entities){
				potentially.get(e.getActuallyOwner()).remove(e);
			}
		}
	}
	
	public MethodEntity firstPhase(){
		//final MultiValueMap<AClass, MethodEntity> map = new LinkedMultiValueMap<AClass, MethodEntity>();
		final List<MethodEntity> list = new ArrayList<MethodEntity>();
		new MapLooper<AClass, List<MethodEntity>>(potentially){
			@Override
			protected void process(AClass key, List<MethodEntity> value) {
				for(MethodEntity e : value){
					filter(key, e);
				}
			}
			
			private void filter(AClass key, MethodEntity entity){
				AClass[] potentialMethodArgs = entity.getArgClasses();
						
				if(ModifierUtils.isVarargs(entity.getModifier())){
					AClass lastActuallyArg = argumentTypes[ArrayUtils.getLength(argumentTypes) - 1];
					
					if(!lastActuallyArg.isArray() || 
						ArrayUtils.getLength(argumentTypes) != ArrayUtils.getLength(potentialMethodArgs)) {
						return;
					}
				}
				for(int i=0, len = ArrayUtils.getLength(potentialMethodArgs); i<len; i++){
					AClass actuallyArg = argumentTypes[i];
					AClass potentialArg = potentialMethodArgs[i];
					if(!AClassUtils.isSubOrEqualType(actuallyArg, potentialArg))
						return;
				}
				list.add(entity);
			}
			
		}.loop();
		removePotentiallyMethod(list.toArray(new MethodEntity[list.size()]));
		return choosingTheMostSpecificMethod(list);
	}

	
	public MethodEntity secondPhase(){
		final List<MethodEntity> list = new ArrayList<MethodEntity>();
		new MapLooper<AClass, List<MethodEntity>>(potentially){
			@Override
			protected void process(AClass key, List<MethodEntity> value) {
				for(MethodEntity e : value){
					filter(key, e);
				}
			}
			
			private void filter(AClass key, MethodEntity entity){
				if(ModifierUtils.isVarargs(entity.getModifier())){
					return;
				}
				AClass[] potentialMethodArgs = entity.getArgClasses();
				for(int i=0, len = ArrayUtils.getLength(potentialMethodArgs); i<len; i++){
					AClass actuallyArg = argumentTypes[i];
					AClass potentialArg = potentialMethodArgs[i];
					if(!canBeConvertedByMethodInvocationConversion(actuallyArg, potentialArg))
						return;
				}
				list.add(entity);
			}
			
		}.loop();
		removePotentiallyMethod(list.toArray(new MethodEntity[list.size()]));
		return choosingTheMostSpecificMethod(list);
	}

	
	public MethodEntity thirdPhase(){
		final List<MethodEntity> list = new ArrayList<MethodEntity>();
		new MapLooper<AClass, List<MethodEntity>>(potentially){
			@Override
			protected void process(AClass key, List<MethodEntity> value) {
				for(MethodEntity e : value){
					filter(key, e);
				}
			}
			
			private void filter(AClass key, MethodEntity entity){
				if(!ModifierUtils.isVarargs(entity.getModifier())){
					return;
				}
				
				AClass[] potentialMethodArgs = entity.getArgClasses();
				int potenMtdArgLen = ArrayUtils.getLength(potentialMethodArgs);
				for(int i=0; i < potenMtdArgLen - 1 ; i++){
					AClass actuallyArg = argumentTypes[i];
					AClass potentialArg = potentialMethodArgs[i];
					if(!AClassUtils.isSubOrEqualType(actuallyArg, potentialArg) && !canBeConvertedByMethodInvocationConversion(actuallyArg, potentialArg))
						return;
				}
				
				int actMtdArgLen = ArrayUtils.getLength(argumentTypes);
				if(actMtdArgLen >= potenMtdArgLen){
					AClass varargType = ((ArrayClass)potentialMethodArgs[potenMtdArgLen - 1]).getRootComponentClass();
					for(int i = potenMtdArgLen - 1; i<actMtdArgLen ; i++){
						AClass actuallyArg = argumentTypes[i];
						if(!AClassUtils.isSubOrEqualType(actuallyArg, varargType) && !canBeConvertedByMethodInvocationConversion(actuallyArg, varargType))
							return;
					}
				}
				
				list.add(entity);
			}
			
		}.loop();
		if(potentially != null) potentially.clear();
		return choosingTheMostSpecificMethod(list);
	}

	
	/**
	 * check if argument "from" can be converted by method invocation conversion (The Java™ Language Specification Third Edition 5.3) to argument "to".
	 * 
	 * @param from
	 * @param to
	 * @return
	 */
	private boolean canBeConvertedByMethodInvocationConversion(AClass from, AClass to){
		if(from.isPrimitive() && !to.isPrimitive()){
            AClassUtils.getPrimitiveWrapAClass(from);
            return true;
        }else if(!from.isPrimitive() && to.isPrimitive()){
            AClassUtils.getPrimitiveAClass(from);
            return true;
        }
        return false;
	}
	
	@Override
	public MethodEntity choosingTheMostSpecificMethod(List<MethodEntity> entities) {
		Set<MethodEntity> most = null;
		for(MethodEntity e : entities){
			if(CollectionUtils.isEmpty(most)){
				most = new HashSet<MethodEntity>();
				most.add(e);
			}else{
				Set<MethodEntity> newMost = new HashSet<MethodEntity>();
				for(MethodEntity mostE : most){
					CollectionUtils.addAll(newMost, mostSpecificMethod(mostE, e));
				}
				most = newMost;
			}
		}
		
		if(CollectionUtils.isNotEmpty(most)){
			if(most.size() == 1){
				return (MethodEntity) most.toArray()[0];
			}else{
				throw new IllegalArgumentException("The method invocation is ambiguous: " + name + "(" + ArrayUtils.toString(argumentTypes) + ")");
			}
		}
		return null;
	}
	
	private MethodEntity[] mostSpecificMethod(MethodEntity method1, MethodEntity method2){
		
		//fixed-arity method compare;
		if(!ModifierUtils.isVarargs(method1.getModifier()) && !ModifierUtils.isVarargs(method2.getModifier())){
			if(mostSpecificFixedArityMethod(method1, method2)){
				return new MethodEntity[]{method1};
			}else if(mostSpecificFixedArityMethod(method2, method1)){
				return new MethodEntity[]{method2};
			}else{
				return new MethodEntity[]{method1, method2};
			}
		}else if(ModifierUtils.isVarargs(method1.getModifier()) && ModifierUtils.isVarargs(method2.getModifier())){
			if(mostSpecificVarargMethod(method1, method2)){
				return new MethodEntity[]{method1};
			}else if(mostSpecificVarargMethod(method2, method1)){
				return new MethodEntity[]{method2};
			}else{
				return new MethodEntity[]{method1, method2};
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
	private boolean mostSpecificFixedArityMethod(MethodEntity method1, MethodEntity method2){
		AClass[] t = method1.getArgClasses();
		AClass[] u = method2.getArgClasses();
		int n = ArrayUtils.getLength(t);
		if(n > 0){
			for(int j=0; j<n; j++){
				if(!AClassUtils.isSubOrEqualType(t[j], u[j])){
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
	private boolean mostSpecificVarargMethod(MethodEntity m1, MethodEntity m2){
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
			if(!AClassUtils.isSubOrEqualType(t[j], u[j])){
				return false;
			}
		}
		
		
		if(ArrayUtils.getLength(t) >= ArrayUtils.getLength(u)){
			for(int j = k - 1; j < n; j++){
				if(!AClassUtils.isSubOrEqualType(t[j], u[k - 1])){
					return false;
				}
			}
		}else{
			for(int j = k - 1; j < n; j++){
				if(!AClassUtils.isSubOrEqualType(t[k - 1], u[j])){
					return false;
				}
			}
			
		}
		
		return true;
	}
	
	
}
