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
package cn.wensiqun.asmsupport.core.builder.impl;

import cn.wensiqun.asmsupport.core.asm.adapter.ClassModifierClassAdapter;
import cn.wensiqun.asmsupport.core.asm.adapter.VisitXInsnAdapter;
import cn.wensiqun.asmsupport.core.block.method.clinit.KernelStaticBlockBody;
import cn.wensiqun.asmsupport.core.block.method.common.KernelMethodBody;
import cn.wensiqun.asmsupport.core.block.method.common.KernelModifiedMethodBody;
import cn.wensiqun.asmsupport.core.block.method.common.KernelStaticMethodBody;
import cn.wensiqun.asmsupport.core.block.method.init.KernelConstructorBody;
import cn.wensiqun.asmsupport.core.builder.IFieldBuilder;
import cn.wensiqun.asmsupport.core.builder.IMethodBuilder;
import cn.wensiqun.asmsupport.core.loader.CachedThreadLocalClassLoader;
import cn.wensiqun.asmsupport.core.utils.log.Log;
import cn.wensiqun.asmsupport.core.utils.log.LogFactory;
import cn.wensiqun.asmsupport.org.objectweb.asm.ClassReader;
import cn.wensiqun.asmsupport.org.objectweb.asm.ClassWriter;
import cn.wensiqun.asmsupport.org.objectweb.asm.Opcodes;
import cn.wensiqun.asmsupport.org.objectweb.asm.Type;
import cn.wensiqun.asmsupport.standard.def.clazz.IClass;
import cn.wensiqun.asmsupport.standard.def.clazz.ProductClass;
import cn.wensiqun.asmsupport.standard.def.method.AMethodMeta;
import cn.wensiqun.asmsupport.standard.error.ASMSupportException;
import cn.wensiqun.asmsupport.standard.utils.ASMSupportClassLoader;
import cn.wensiqun.asmsupport.standard.utils.IClassUtils;
import cn.wensiqun.asmsupport.utils.AsmsupportConstant;

import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class ClassModifier extends AbstractClassBuilder {
	
    private static final Log LOG = LogFactory.getLog(ClassModifier.class);
	
    protected List<IMethodBuilder> methodModifiers = new ArrayList<IMethodBuilder>();
    
    private List<KernelModifiedMethodBody> modifyConstructorBodies;
    
	private ProductClass productClass;
	
	public ClassModifier(Class<?> clazz) {
		this(clazz, CachedThreadLocalClassLoader.getInstance());
	}
	
	public ClassModifier(Class<?> clazz, ASMSupportClassLoader classLoader) {
		super(classLoader);
		if(!clazz.isArray()){
			this.productClass = (ProductClass) classLoader.getType(clazz);
		}else{
			throw new ASMSupportException("cannot modify array type : " + clazz);
		}
	}
	
	public final void modifyMethod(String name, Class<?>[] argClasses, KernelModifiedMethodBody mb){
		Class<?> clazz = productClass.getReallyClass();
		if(argClasses == null){
			argClasses = new Class<?>[0];
		}
		IClass[] argCls = new IClass[argClasses.length];
		String[] defaultArgNames = new String[argClasses.length];
		for(int i=0; i<argCls.length; i++){
			argCls[i] = ASMSupportClassLoader.getType(argClasses[i]);
			defaultArgNames[i] = "arg" + i;
		}
		try {
			
			MethodBuilderImpl methodCreator;
			if(name.equals(AsmsupportConstant.CLINIT)){
				methodCreator = MethodBuilderImpl.methodCreatorForModify(name, argCls, defaultArgNames, ASMSupportClassLoader.getType(void.class), null, Opcodes.ACC_STATIC, mb);
			}else if(name.equals(AsmsupportConstant.INIT)){
				if(modifyConstructorBodies == null){
					modifyConstructorBodies = new ArrayList<KernelModifiedMethodBody>();
				}
				modifyConstructorBodies.add(mb);
				
				Constructor<?> constructor = clazz.getDeclaredConstructor(argClasses);
				methodCreator = MethodBuilderImpl.methodCreatorForModify(AsmsupportConstant.INIT, 
						argCls, 
						defaultArgNames, 
						ASMSupportClassLoader.getType(void.class),
						IClassUtils.convertToAClass(ASMSupportClassLoader, constructor.getExceptionTypes()),
						constructor.getModifiers(), mb);
			}else{
				Method method = clazz.getDeclaredMethod(name, argClasses);
				methodCreator = MethodBuilderImpl.methodCreatorForModify(name, 
						argCls, 
						defaultArgNames, 
						ASMSupportClassLoader.getType(method.getReturnType()),
						IClassUtils.convertToAClass(ASMSupportClassLoader, method.getExceptionTypes()),
						method.getModifiers(), mb);
			}
			
			methodModifiers.add(methodCreator);
		} catch (NoSuchMethodException e) {
			throw new ASMSupportException("No such method " + AMethodMeta.getMethodString(name, argCls) + " in " + productClass);
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
    public IMethodBuilder createConstructor(int access, IClass[] argTypes, String[] argNames, IClass[] exceptions, KernelConstructorBody body) {
        IMethodBuilder creator = MethodBuilderImpl.methodCreatorForAdd(AsmsupportConstant.INIT, argTypes, argNames,
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
    public IMethodBuilder createMethodForDummy(int access, String name, IClass[] argTypes, String[] argNames,
    		IClass returnClass, IClass[] exceptions, KernelMethodBody body) {
        IMethodBuilder creator = MethodBuilderImpl.methodCreatorForAdd(name, argTypes, argNames,
                returnClass, exceptions, access, body);
        methodCreaters.add(creator);
        return creator;
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
    public final void createMethod(String name, IClass[] argClasses,
            String[] argNames, IClass returnClass, IClass[] exceptions,
            int access, KernelMethodBody mb) {
    	if((access & Opcodes.ACC_STATIC) != 0){
    		access -= Opcodes.ACC_STATIC;
    	}
        methodCreaters.add(MethodBuilderImpl.methodCreatorForAdd(name, argClasses, argNames,
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
    public void createStaticMethod(String name, IClass[] argClasses,
            String[] argNames, IClass returnClass, IClass[] exceptions,
            int access, KernelStaticMethodBody mb) {
    	if((access & Opcodes.ACC_STATIC) == 0){
    		access += Opcodes.ACC_STATIC;
    	}
        methodCreaters.add(MethodBuilderImpl.methodCreatorForAdd(name, argClasses, argNames,
                returnClass, exceptions, access, mb));
    }
    
    protected void checkStaticBlock(){
    	if(productClass.existStaticInitBlock()){
        	existedStaticBlock = true;
    	}
    	super.checkStaticBlock();
    }
    
    public void createStaticBlock(KernelStaticBlockBody mb){
    	checkStaticBlock();
    	existedStaticBlock = true;
        methodCreaters.add(0, MethodBuilderImpl.methodCreatorForAdd(AsmsupportConstant.CLINIT, null, null, null, null,
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
    public IFieldBuilder createField(String name, int modifiers, IClass type) {
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
    public IFieldBuilder createField(String name, int modifiers, IClass type, Object value) {
        FieldBuildImpl fc = new FieldBuildImpl(name, modifiers, type, value);
        fieldCreators.add(fc);
        return fc;
    }

	@Override
	public void create() {
		InputStream is = ASMSupportClassLoader.getResourceAsStream(productClass.getName());
		try {
			//modify class
			ClassReader cr = new ClassReader(is);
			cw = new ClassWriter(0);
			ClassModifierClassAdapter adapter = new ClassModifierClassAdapter(cw, this);
			cr.accept(adapter, 0);
			
			if(LOG.isPrintEnabled()){
	            LOG.print("Start modify class : " + productClass.getReallyClass());
	        }
	        
	        // create field
	        for (IFieldBuilder ifc : fieldCreators) {
	            ifc.create(this);
	        }

	        // create method
	        for (IMethodBuilder imc : methodCreaters) {
	            imc.create(this);
	        }
	        
	        // modify method
	        for (IMethodBuilder imc : methodModifiers) {
	            imc.create(this);
	        }

	        Map<String, List<VisitXInsnAdapter>> superConstructorMap = adapter.getSuperConstructorMap();
	        
	        if(modifyConstructorBodies != null){
	            for(KernelModifiedMethodBody mbfm : modifyConstructorBodies){
	                 Type[] argumentTypes = mbfm.getMethod().getMeta().getParameterAsmTypes();
	                 String desc = Type.getMethodDescriptor(Type.VOID_TYPE, argumentTypes);
	                 mbfm.setSuperConstructorOperators(superConstructorMap.get(desc));
	            }
	        }
		} catch (Exception e) {
			throw new ASMSupportException(e.getMessage(), e);
		}
	}

	@Override
	public void prepare() {
		for (IFieldBuilder ifc : fieldCreators) {
			ifc.prepare();
		}

		for (IMethodBuilder imc : methodCreaters) {
			imc.prepare();
		}

		for (IMethodBuilder imc : methodModifiers) {
			imc.prepare();
		}

	}

	@Override
	public byte[] execute() {
	       
        for (IFieldBuilder ifc : fieldCreators) {
            ifc.execute();
        }

        for (IMethodBuilder imc : methodCreaters) {
            imc.execute();
        }
        
        for (IMethodBuilder imc : methodModifiers) {
            imc.execute();
        }

        if(LOG.isPrintEnabled()){
            LOG.print("End modify class : " + productClass.getReallyClass());
        }
		
		return cw.toByteArray();
	}
	
    /*@Override
    public Class<?> startup() {
    	InputStream is = asmsupportClassLoader.loadClassResource(productClass.getName());
    	try {
			//modify class
			ClassReader cr = new ClassReader(is);
			cw = new ClassWriter(0);
			ClassVisitor cv = new ClassModifierClassAdapter(cw, this);
			cr.accept(cv, 0);
			modifiedClassBytes = cw.toByteArray();
			
		} catch (Exception e) {
			throw new ASMSupportException(e.getMessage(), e);
		}
    	
    	
        ClassModifierClassLoader loader = new ClassModifierClassLoader(this);
        
        try {
            loader.loadClass(productClass.getName());
            String proxyClassName = productClass.getName();
            byte[] modifiedBytes = loader.getModifiedClassBytes();
            if(!StringUtils.isBlank(getClassOutPutPath())){
            	CommonUtils.toLocal(modifiedBytes, getClassOutPutPath(), proxyClassName);
            }
            return loadClass(proxyClassName, modifiedBytes);
        } catch (ClassNotFoundException e) {
            throw new ASMSupportException("Class Not Found Exception");
        } finally{
            //cleanCach();
        }
    }*/
    
	@Override
	public ProductClass getCurrentClass() {
		return productClass;
	}

	public List<IMethodBuilder> getMethodModifiers() {
		return methodModifiers;
	}

}
