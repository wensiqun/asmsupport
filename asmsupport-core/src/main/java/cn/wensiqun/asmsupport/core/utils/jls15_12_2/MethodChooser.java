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

import cn.wensiqun.asmsupport.core.build.SemiClass;
import cn.wensiqun.asmsupport.org.objectweb.asm.ClassReader;
import cn.wensiqun.asmsupport.org.objectweb.asm.ClassVisitor;
import cn.wensiqun.asmsupport.org.objectweb.asm.MethodVisitor;
import cn.wensiqun.asmsupport.org.objectweb.asm.Type;
import cn.wensiqun.asmsupport.standard.def.clazz.ClassHolder;
import cn.wensiqun.asmsupport.standard.def.clazz.IClass;
import cn.wensiqun.asmsupport.standard.def.clazz.ProductClass;
import cn.wensiqun.asmsupport.standard.def.method.AMethodMeta;
import cn.wensiqun.asmsupport.standard.error.ASMSupportException;
import cn.wensiqun.asmsupport.standard.utils.IClassUtils;
import cn.wensiqun.asmsupport.standard.utils.jls.TypeUtils;
import cn.wensiqun.asmsupport.standard.utils.jls15_12_2.ConversionsPromotionsUtils;
import cn.wensiqun.asmsupport.standard.utils.jls15_12_2.DetermineMethodSignature;
import cn.wensiqun.asmsupport.standard.utils.jls15_12_2.IMethodChooser;
import cn.wensiqun.asmsupport.utils.ASConstants;
import cn.wensiqun.asmsupport.utils.Modifiers;
import cn.wensiqun.asmsupport.utils.collections.CollectionUtils;
import cn.wensiqun.asmsupport.utils.collections.LinkedMultiValueMap;
import cn.wensiqun.asmsupport.utils.collections.MapLooper;
import cn.wensiqun.asmsupport.utils.collections.MultiValueMap;
import cn.wensiqun.asmsupport.utils.lang.ArrayUtils;
import cn.wensiqun.asmsupport.utils.lang.ClassUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;


public class MethodChooser implements IMethodChooser, DetermineMethodSignature {

	private ClassLoader classLoader;
	
	/**
	 * where is this method invoke operator
	 */
    protected IClass whereCall;
    
    /**
     * what class call
     */
    protected IClass directCallClass;
    
    /**
     * method name
     */
    protected String name;
    
    /**
     * 
     */
    protected IClass[] argumentTypes;
    
	public MethodChooser(ClassLoader classLoader, IClass whereCall, IClass directCallClass,
			String name, IClass[] argumentTypes) {
		this.classLoader = classLoader;
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
	public Map<IClass, List<AMethodMeta>> identifyPotentiallyApplicableMethods(){
		
		@SuppressWarnings("serial")
		MultiValueMap<IClass,AMethodMeta> tempPotentially = new LinkedMultiValueMap<IClass, AMethodMeta>(){

			@Override
			public void add(IClass key, AMethodMeta value) {
				//1.check name
				//2.check accessible
				if(!name.equals(value.getName()) ||
				   !IClassUtils.visible(whereCall, directCallClass, value.getActuallyOwner(), value.getModifiers())){
				    return;	
				}
				
				int entityLength = ArrayUtils.getLength(value.getParameterTypes());
				int argumentLength = ArrayUtils.getLength(argumentTypes);
				
				//check arity of method
				if(Modifiers.isVarargs(value.getModifiers())){
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
				
				Set<Entry<IClass, List<AMethodMeta>>> entrySet = entrySet();
				for(Entry<IClass, List<AMethodMeta>> entry : entrySet){
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
				
				IClass[] childArgs = child.getParameterTypes();
				IClass[] superArgs = super_.getParameterTypes();
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
        	ClassHolder holder = directCallClass.getClassHolder();
		    if(directCallClass instanceof SemiClass){
	            if(ASConstants.INIT.equals(name)){
	                for(AMethodMeta method : directCallClass.getDeclaredConstructors()){
	                    tempPotentially.add(directCallClass, method);
	                }
	            }else{
	                for(AMethodMeta method : directCallClass.getDeclaredMethods()){
	                    tempPotentially.add(directCallClass, method);
	                }
	                fetchMatchMethod(tempPotentially, directCallClass.getSuperclass(), name);
	            }
	        }else if(directCallClass instanceof ProductClass){
	            if(ASConstants.INIT.equals(name)){
	                for(AMethodMeta method : directCallClass.getDeclaredConstructors()){
	                    tempPotentially.add(directCallClass, method);
	                }
	                
	                for(AMethodMeta me : getAllMethod(directCallClass, name)){
	                    tempPotentially.add(me.getActuallyOwner(), me);
	                }
	                
	            }else{
	                for(AMethodMeta method : directCallClass.getDeclaredMethods()){
	                    tempPotentially.add(directCallClass, method);
	                }
	                fetchMatchMethod(tempPotentially, directCallClass, name);
	            }
	        }else{
	            fetchMatchMethod(tempPotentially, holder.getType(Object.class), name);
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
	private void fetchMatchMethod(MultiValueMap<IClass,AMethodMeta> potentially, IClass where, String name) throws IOException{
		if(where == null){
			return;
		}
		
		for(AMethodMeta me : getAllMethod(where, name)){
			potentially.add(me.getActuallyOwner(), me);
		}
		
		fetchMatchMethod(potentially, where.getSuperclass(), name);
		IClass[] itfs = where.getInterfaces();
		if(ArrayUtils.isNotEmpty(itfs)){
			for(IClass itf : itfs){
				fetchMatchMethod(potentially, itf, name);
			}
		}
		
	}

	/** */
	private Map<IClass, List<AMethodMeta>> potentially;

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
		final List<AMethodMeta> list = new ArrayList<>();
		new MapLooper<IClass, List<AMethodMeta>>(potentially){
			@Override
			protected void process(IClass key, List<AMethodMeta> value) {
				for(AMethodMeta e : value){
					filter(key, e);
				}
			}
			
			private void filter(IClass key, AMethodMeta entity){
				
				if(Modifiers.isVarargs(entity.getModifiers())){
					return;
				}
				
				IClass[] potentialMethodArgs = entity.getParameterTypes();
				
				if(ArrayUtils.getLength(argumentTypes) != ArrayUtils.getLength(potentialMethodArgs)) {
                    return;
                }
				for(int i=0, len = ArrayUtils.getLength(potentialMethodArgs); i<len; i++){
					IClass actuallyArg = argumentTypes[i];
					IClass potentialArg = potentialMethodArgs[i];
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
		final List<AMethodMeta> list = new ArrayList<>();
		new MapLooper<IClass, List<AMethodMeta>>(potentially){
			@Override
			protected void process(IClass key, List<AMethodMeta> value) {
				for(AMethodMeta e : value){
					filter(key, e);
				}
			}
			
			private void filter(IClass key, AMethodMeta entity){
				if(Modifiers.isVarargs(entity.getModifiers())){
					return;
				}
				IClass[] potentialMethodArgs = entity.getParameterTypes();
				for(int i=0, len = ArrayUtils.getLength(potentialMethodArgs); i<len; i++){
					IClass actuallyArg = argumentTypes[i];
					IClass potentialArg = potentialMethodArgs[i];
					if(!ConversionsPromotionsUtils.checkMethodInvocationConversion(actuallyArg, potentialArg))
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
		final List<AMethodMeta> list = new ArrayList<>();
		new MapLooper<IClass, List<AMethodMeta>>(potentially){
			@Override
			protected void process(IClass key, List<AMethodMeta> value) {
				for(AMethodMeta e : value){
					filter(key, e);
				}
			}
			
			private void filter(IClass key, AMethodMeta entity){
				if(!Modifiers.isVarargs(entity.getModifiers())){
					return;
				}
				
				IClass[] potentialMethodArgs = entity.getParameterTypes();
				int potentialArgumentLength = ArrayUtils.getLength(potentialMethodArgs);
				for(int i=0; i < potentialArgumentLength - 1 ; i++){
					IClass actuallyArg = argumentTypes[i];
					IClass potentialArg = potentialMethodArgs[i];
					if(!TypeUtils.isSubtyping(actuallyArg, potentialArg) && 
					   !ConversionsPromotionsUtils.checkMethodInvocationConversion(actuallyArg, potentialArg))
						return;
				}
				
				int argumentLength = ArrayUtils.getLength(argumentTypes);
				if(argumentLength == potentialArgumentLength) {
					IClass variableArityType = argumentTypes[argumentLength - 1];
					IClass potentialVariableArityType = potentialMethodArgs[argumentLength - 1];
					
					if(TypeUtils.isSubtyping(variableArityType, potentialVariableArityType) || 
					   ConversionsPromotionsUtils.checkMethodInvocationConversion(variableArityType, potentialVariableArityType)) {
						list.add(entity);
						return;
					}
				}
				
				if(argumentLength >= potentialArgumentLength){
					IClass potentialVariableArityType = potentialMethodArgs[potentialArgumentLength - 1].getRootComponentClass();
					for(int i = potentialArgumentLength - 1; i<argumentLength ; i++){
						IClass variableArityType = argumentTypes[i];
						if(!TypeUtils.isSubtyping(variableArityType, potentialVariableArityType) && 
						   !ConversionsPromotionsUtils.checkMethodInvocationConversion(variableArityType, potentialVariableArityType))
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
				most = new HashSet<>();
				most.add(e);
			}else{
				Set<AMethodMeta> newMost = new HashSet<>();
				for(AMethodMeta mostE : most){
					CollectionUtils.addAll(newMost, mostSpecificMethod(mostE, e));
				}
				most = newMost;
			}
		}
		
		int mostLen = most == null ? 0 : most.size();
        if(mostLen > 1) {
            //first remove varargs method and re add to potentially
            //in order to third phase choose.
            Set<AMethodMeta> newMost = new HashSet<>();
            for(AMethodMeta m : most){
                if(!Modifiers.isVarargs(m.getModifiers())) {
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
		if(!Modifiers.isVarargs(method1.getModifiers()) && !Modifiers.isVarargs(method2.getModifiers())){
			if(mostSpecificFixedArityMethod(method1, method2)){
				return new AMethodMeta[]{method1};
			}else if(mostSpecificFixedArityMethod(method2, method1)){
				return new AMethodMeta[]{method2};
			}else{
				return new AMethodMeta[]{method1, method2};
			}
		}else if(Modifiers.isVarargs(method1.getModifiers()) && Modifiers.isVarargs(method2.getModifiers())){
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
		IClass[] t = method1.getParameterTypes();
		IClass[] u = method2.getParameterTypes();
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
		IClass[] t = m1.getParameterTypes();
		IClass[] u = m2.getParameterTypes();
		
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
	


    /**
     * According to a method name to find all method meta information 
     * from a class(whit out super class) 
     * 
     * @param owner the method owner
     * @param methodName the method name
     * @return a list of {@link AMethodMeta}
     * @throws IOException
     */
	@Deprecated
    public List<AMethodMeta> getAllMethod(final IClass owner, final String methodName) throws IOException {
        InputStream classStream = classLoader.getResourceAsStream(
        		owner.getName().replace('.', '/') + ".class");
        ClassReader cr = new ClassReader(classStream);
        final ClassHolder holder = owner.getClassHolder();
        final List<AMethodMeta> list = new ArrayList<>();
        cr.accept(new ClassVisitor(ASConstants.ASM_VERSION) {

            @Override
            public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
                if (name.equals(methodName)) {

                    if (exceptions == null) {
                        exceptions = new String[0];
                    }

                    try {

                        Type[] types = Type.getArgumentTypes(desc);
                        IClass[] aclass = new IClass[types.length];
                        String[] args = new String[types.length];
                        for (int i = 0; i < types.length; i++) {
                            aclass[i] = holder.getType(ClassUtils.forName(types[i].getDescriptor()));
                            args[i] = "arg" + i;
                        }

                        IClass returnType = holder.getType(ClassUtils.forName(Type.getReturnType(desc).getDescriptor()));

                        IClass[] exceptionAclassArray = new IClass[exceptions.length];
                        for (int i = 0; i < exceptions.length; i++) {
                            exceptionAclassArray[i] = holder.getType(ClassUtils.forName(exceptions[i]));
                        }

                        AMethodMeta me = new AMethodMeta(holder, name, owner, owner, aclass, args, returnType,
                                exceptionAclassArray, access);
                        list.add(me);
                    } catch (ClassNotFoundException e) {
                        throw new ASMSupportException(e);
                    }
                }
                return super.visitMethod(access, name, desc, signature, exceptions);
            }
        }, 0);
        return list;
    }
	
	
}
