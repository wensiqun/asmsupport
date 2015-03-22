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
package cn.wensiqun.asmsupport.core.creator.clazz;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.wensiqun.asmsupport.core.asm.adapter.VisitXInsnAdapter;
import cn.wensiqun.asmsupport.core.block.method.clinit.StaticBlockBodyInternal;
import cn.wensiqun.asmsupport.core.block.method.common.MethodBodyInternal;
import cn.wensiqun.asmsupport.core.block.method.common.ModifiedMethodBodyInternal;
import cn.wensiqun.asmsupport.core.block.method.common.StaticMethodBodyInternal;
import cn.wensiqun.asmsupport.core.block.method.init.ConstructorBodyInternal;
import cn.wensiqun.asmsupport.core.clazz.AClass;
import cn.wensiqun.asmsupport.core.clazz.AClassFactory;
import cn.wensiqun.asmsupport.core.clazz.ProductClass;
import cn.wensiqun.asmsupport.core.creator.FieldCreator;
import cn.wensiqun.asmsupport.core.creator.IFieldCreator;
import cn.wensiqun.asmsupport.core.creator.IMethodCreator;
import cn.wensiqun.asmsupport.core.creator.MethodCreator;
import cn.wensiqun.asmsupport.core.exception.ASMSupportException;
import cn.wensiqun.asmsupport.core.exception.NoSuchMethod;
import cn.wensiqun.asmsupport.core.loader.ClassModifierClassLoader;
import cn.wensiqun.asmsupport.core.utils.AClassUtils;
import cn.wensiqun.asmsupport.core.utils.ASConstant;
import cn.wensiqun.asmsupport.core.utils.lang.ClassFileUtils;
import cn.wensiqun.asmsupport.core.utils.lang.StringUtils;
import cn.wensiqun.asmsupport.org.objectweb.asm.ClassWriter;
import cn.wensiqun.asmsupport.org.objectweb.asm.Opcodes;
import cn.wensiqun.asmsupport.org.objectweb.asm.Type;


public class ClassModifier extends AbstractClassContext {
	
    private static Log LOG = LogFactory.getLog(ClassModifier.class);
	
    protected List<IMethodCreator> methodModifiers;
    
    private List<ModifiedMethodBodyInternal> modifyConstructorBodies;
    
	private ProductClass productClass;
	
	public ClassModifier(Class<?> clazz) {
		super();
		if(!clazz.isArray()){
			this.productClass = (ProductClass) AClassFactory.defType(clazz);
		}else{
			throw new ASMSupportException("cannot modify array type : " + clazz);
		}
		methodCreaters = new ArrayList<IMethodCreator>();
		methodModifiers = new ArrayList<IMethodCreator>();
		fieldCreators = new ArrayList<IFieldCreator>();
	}
	
	public final void modifyMethod(String name, Class<?>[] argClasses, ModifiedMethodBodyInternal mb){
		Class<?> clazz = productClass.getReallyClass();
		if(argClasses == null){
			argClasses = new Class<?>[0];
		}
		AClass[] argCls = new AClass[argClasses.length];
		String[] defaultArgNames = new String[argClasses.length];
		for(int i=0; i<argCls.length; i++){
			argCls[i] = defType(argClasses[i]);
			defaultArgNames[i] = "arg" + i;
		}
		try {
			
			MethodCreator methodCreator;
			if(name.equals(ASConstant.CLINIT)){
				methodCreator = MethodCreator.methodCreatorForModify(name, argCls, defaultArgNames, AClass.VOID_ACLASS, null, Opcodes.ACC_STATIC, mb);
			}else if(name.equals(ASConstant.INIT)){
				if(modifyConstructorBodies == null){
					modifyConstructorBodies = new ArrayList<ModifiedMethodBodyInternal>();
				}
				modifyConstructorBodies.add(mb);
				
				Constructor<?> constructor = clazz.getDeclaredConstructor(argClasses);
				methodCreator = MethodCreator.methodCreatorForModify(ASConstant.INIT, 
						argCls, 
						defaultArgNames, 
						AClass.VOID_ACLASS, 
						AClassUtils.convertToAClass(constructor.getExceptionTypes()),
						constructor.getModifiers(), mb);
			}else{
				Method method = clazz.getDeclaredMethod(name, argClasses);
				methodCreator = MethodCreator.methodCreatorForModify(name, 
						argCls, 
						defaultArgNames, 
						defType(method.getReturnType()), 
						AClassUtils.convertToAClass(method.getExceptionTypes()),
						method.getModifiers(), mb);
			}
			
			methodModifiers.add(methodCreator);
		} catch (NoSuchMethodException e) {
			throw new NoSuchMethod(productClass, name, argCls);
		}
	}
	
	/**
	 * Create a constructor.
	 * 
	 * @param access
	 * @param argTypes
	 * @param argNames
	 * @param exceptions
	 * @param body
	 * @return
	 */
    public IMethodCreator createConstructor(int access, AClass[] argTypes, String[] argNames, AClass[] exceptions, ConstructorBodyInternal body) {
        IMethodCreator creator = MethodCreator.methodCreatorForAdd(ASConstant.INIT, argTypes, argNames,
                null, exceptions, access, body);
        methodCreaters.add(creator);
        return creator;
    }
	
	/**
     * Crate method for dummy call, remove static check that different to {@link #createMethod} method
     * 
     * @param access
     * @param name
     * @param argTypes
     * @param argNames
     * @param returnClass
     * @param exceptions
     * @param body
     * @return
     */
    public IMethodCreator createMethodForDummy(int access, String name, AClass[] argTypes, String[] argNames,
            AClass returnClass, AClass[] exceptions, MethodBodyInternal body) {
        IMethodCreator creator = MethodCreator.methodCreatorForAdd(name, argTypes, argNames,
                returnClass, exceptions, access, body);
        methodCreaters.add(creator);
        return creator;
    }
	
    /**
     * 
     * @param name
     * @param arguments
     * @param argNames
     * @param returnClass
     * @param exceptions
     * @param access
     * @param mb
     * @return
     */
    public final void createMethod(String name, AClass[] argClasses,
            String[] argNames, AClass returnClass, AClass[] exceptions,
            int access, MethodBodyInternal mb) {
    	if((access & Opcodes.ACC_STATIC) != 0){
    		access -= Opcodes.ACC_STATIC;
    	}
        methodCreaters.add(MethodCreator.methodCreatorForAdd(name, argClasses, argNames,
                returnClass, exceptions, access, mb));
    }
    
    /**
     * 
     * @param name
     * @param argClasses
     * @param argNames
     * @param returnClass
     * @param exceptions
     * @param access
     * @param mb
     * @return
     */
    public void createStaticMethod(String name, AClass[] argClasses,
            String[] argNames, AClass returnClass, AClass[] exceptions,
            int access, StaticMethodBodyInternal mb) {
    	if((access & Opcodes.ACC_STATIC) == 0){
    		access += Opcodes.ACC_STATIC;
    	}
        methodCreaters.add(MethodCreator.methodCreatorForAdd(name, argClasses, argNames,
                returnClass, exceptions, access, mb));
    }
    
    protected void checkStaticBlock(){
    	if(productClass.existStaticInitBlock()){
        	existedStaticBlock = true;
    	}
    	super.checkStaticBlock();
    }
    
    public void createStaticBlock(StaticBlockBodyInternal mb){
    	checkStaticBlock();
    	existedStaticBlock = true;
        methodCreaters.add(0, MethodCreator.methodCreatorForAdd(ASConstant.CLINIT, null, null, null, null,
                Opcodes.ACC_STATIC, mb));
    }
    
    
    /**
     * 
     * Create a field with null value.
     * 
     * @param name            the field name
     * @param modifiers       the field modifiers
     * @param type      the field type
     * @return
     */
    public IFieldCreator createField(String name, int modifiers, AClass type) {
        return createField(name, modifiers, type, null);
    }
    
    /**
     * 
     * Create a field with special value.
     * 
     * @param name
     * @param modifiers
     * @param type
     * @param value The initial value, this value is only support static field, 
     *              otherwise will ignored.This parameter, which may be null 
     *              if the field does not have an initial value, 
     *              must be an Integer, a Float, a Long, a Double or a 
     *              String (for int, float, long or String fields respectively). 
     *              This parameter is only used for static fields. Its value is 
     *              ignored for non static fields, which must be initialized 
     *              through bytecode instructions in constructors or methods.
     * @return
     */
    public IFieldCreator createField(String name, int modifiers, AClass type, Object value) {
        FieldCreator fc = new FieldCreator(name, modifiers, type, value);
        fieldCreators.add(fc);
        return fc;
    }

    public void modify(Map<String, List<VisitXInsnAdapter>> superConstructorMap){
        
        if(LOG.isDebugEnabled()){
            LOG.debug("Start modify class : " + productClass.getReallyClass());
        }
        
        // create field
        for (IFieldCreator ifc : fieldCreators) {
            ifc.create(this);
        }

        // create method
        for (IMethodCreator imc : methodCreaters) {
            imc.create(this);
        }
        
        // modify method
        for (IMethodCreator imc : methodModifiers) {
            imc.create(this);
        }

        if(modifyConstructorBodies != null){
            for(ModifiedMethodBodyInternal mbfm : modifyConstructorBodies){
                 Type[] argumentTypes = mbfm.getMethod().getMethodMeta().getArgTypes();
                 String desc = Type.getMethodDescriptor(Type.VOID_TYPE, argumentTypes);
                 mbfm.setSuperConstructorOperators(superConstructorMap.get(desc));
            }
        }
        
        for (IFieldCreator ifc : fieldCreators) {
            ifc.prepare();
        }

        for (IMethodCreator imc : methodCreaters) {
            imc.prepare();
        }
        
        for (IMethodCreator imc : methodModifiers) {
            imc.prepare();
        }

        for (IFieldCreator ifc : fieldCreators) {
            ifc.execute();
        }

        for (IMethodCreator imc : methodCreaters) {
            imc.execute();
        }
        
        for (IMethodCreator imc : methodModifiers) {
            imc.execute();
        }

        if(LOG.isDebugEnabled()){
            LOG.debug("End modify class : " + productClass.getReallyClass());
        }
    }

    @Override
    public Class<?> startup() {
        ClassModifierClassLoader loader = new ClassModifierClassLoader(this);
        
        try {
            loader.loadClass(productClass.getName());
            String proxyClassName = productClass.getName();
            byte[] modifiedBytes = loader.getModifiedClassBytes();
            if(StringUtils.isNotBlank(getClassOutPutPath())){
                ClassFileUtils.toLocal(modifiedBytes, getClassOutPutPath(), proxyClassName);
            }
            return loadClass(proxyClassName, modifiedBytes);
        } catch (ClassNotFoundException e) {
            throw new ASMSupportException("Class Not Found Exception");
        } finally{
            //cleanCach();
        }
    }
    
	@Override
	public ProductClass getCurrentClass() {
		return productClass;
	}
	
	public void setClassWriter(ClassWriter cw){
		if(this.cw == null){
			this.cw = cw;
		}
	}

	public List<IMethodCreator> getMethodModifiers() {
		return methodModifiers;
	}

}
