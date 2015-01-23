package cn.wensiqun.asmsupport.core.creator.clazz;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.wensiqun.asmsupport.core.asm.adapter.VisitXInsnAdapter;
import cn.wensiqun.asmsupport.core.block.classes.method.clinit.ClinitBodyInternal;
import cn.wensiqun.asmsupport.core.block.classes.method.common.CommonMethodBodyInternal;
import cn.wensiqun.asmsupport.core.block.classes.method.common.ModifiedMethodBodyInternal;
import cn.wensiqun.asmsupport.core.block.classes.method.common.StaticMethodBodyInternal;
import cn.wensiqun.asmsupport.core.clazz.AClass;
import cn.wensiqun.asmsupport.core.clazz.AClassFactory;
import cn.wensiqun.asmsupport.core.clazz.ProductClass;
import cn.wensiqun.asmsupport.core.creator.FieldCreatorIternel;
import cn.wensiqun.asmsupport.core.creator.IFieldCreator;
import cn.wensiqun.asmsupport.core.creator.IMethodCreator;
import cn.wensiqun.asmsupport.core.creator.MethodCreatorInternal;
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


public class ClassModifierInternal extends AbstractClassContext {
	
    private static Log LOG = LogFactory.getLog(ClassModifierInternal.class);
	
    protected List<IMethodCreator> methodModifiers;
    
    private List<ModifiedMethodBodyInternal> modifyConstructorBodies;
    
	private ProductClass productClass;
	
	public ClassModifierInternal(Class<?> clazz) {
		super();
		if(!clazz.isArray()){
			this.productClass = (ProductClass) AClassFactory.getProductClass(clazz);
		}else{
			throw new ASMSupportException("cannot modify array type : " + clazz);
		}
		methodCreaters = new ArrayList<IMethodCreator>();
		methodModifiers = new ArrayList<IMethodCreator>();
		fieldCreators = new ArrayList<IFieldCreator>();
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
			
			/*ClassReader nameRefactorReader = new ClassReader(loader.getModifiedClassBytes());
			ClassWriter ernameRefactorWrit = new ClassWriter(0);
			ClassNameRefactorAdapter nameRefactorAdapter = new ClassNameRefactorAdapter(ernameRefactorWrit);
			nameRefactorReader.accept(nameRefactorAdapter, 0);
			
			String proxyClassName = nameRefactorAdapter.getJVMProxyClassName().replace("/", ".");
            byte[] modifiedBytes = ernameRefactorWrit.toByteArray();*/
	        
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
	
	public final void modifyMethod(String name, Class<?>[] argClasses, ModifiedMethodBodyInternal mb){
		Class<?> clazz = productClass.getReallyClass();
		if(argClasses == null){
			argClasses = new Class<?>[0];
		}
		AClass[] argCls = new AClass[argClasses.length];
		String[] defaultArgNames = new String[argClasses.length];
		for(int i=0; i<argCls.length; i++){
			argCls[i] = getProductClass(argClasses[i]);
			defaultArgNames[i] = "arg" + i;
		}
		try {
			
			MethodCreatorInternal methodCreator;
			if(name.equals(ASConstant.CLINIT)){
				methodCreator = MethodCreatorInternal.methodCreatorForModify(name, argCls, defaultArgNames, AClass.VOID_ACLASS, null, Opcodes.ACC_STATIC, mb);
			}else if(name.equals(ASConstant.INIT)){
				if(modifyConstructorBodies == null){
					modifyConstructorBodies = new ArrayList<ModifiedMethodBodyInternal>();
				}
				modifyConstructorBodies.add(mb);
				
				Constructor<?> constructor = clazz.getDeclaredConstructor(argClasses);
				methodCreator = MethodCreatorInternal.methodCreatorForModify(ASConstant.INIT, 
						argCls, 
						defaultArgNames, 
						AClass.VOID_ACLASS, 
						AClassUtils.convertToAClass(constructor.getExceptionTypes()),
						constructor.getModifiers(), mb);
			}else{
				Method method = clazz.getDeclaredMethod(name, argClasses);
				methodCreator = MethodCreatorInternal.methodCreatorForModify(name, 
						argCls, 
						defaultArgNames, 
						getProductClass(method.getReturnType()), 
						AClassUtils.convertToAClass(method.getExceptionTypes()),
						method.getModifiers(), mb);
			}
			
			methodModifiers.add(methodCreator);
		} catch (NoSuchMethodException e) {
			throw new NoSuchMethod(productClass, name, argCls);
		}
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
            int access, CommonMethodBodyInternal mb) {
    	if((access & Opcodes.ACC_STATIC) != 0){
    		access -= Opcodes.ACC_STATIC;
    	}
        methodCreaters.add(MethodCreatorInternal.methodCreatorForAdd(name, argClasses, argNames,
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
        methodCreaters.add(MethodCreatorInternal.methodCreatorForAdd(name, argClasses, argNames,
                returnClass, exceptions, access, mb));
    }
    
    protected void checkStaticBlock(){
    	if(productClass.existStaticInitBlock()){
        	existedStaticBlock = true;
    	}
    	super.checkStaticBlock();
    }
    
    public void createStaticBlock(ClinitBodyInternal mb){
    	checkStaticBlock();
    	existedStaticBlock = true;
        methodCreaters.add(0, MethodCreatorInternal.methodCreatorForAdd(ASConstant.CLINIT, null, null, null, null,
                Opcodes.ACC_STATIC, mb));
    }

    /**
     * 
     * @param name
     * @param modifiers
     * @param fieldClass
     * @param value
     * @return
     */
    public void createGlobalVariable(String name, int modifiers,
            AClass fieldClass) {
        FieldCreatorIternel fc = new FieldCreatorIternel(name, modifiers,
                fieldClass);
        fieldCreators.add(fc);
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
