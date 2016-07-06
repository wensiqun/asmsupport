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
package cn.wensiqun.asmsupport.core.build.resolver;

import cn.wensiqun.asmsupport.core.build.FieldBuilder;
import cn.wensiqun.asmsupport.core.build.MethodBuilder;
import cn.wensiqun.asmsupport.core.build.SemiClass;
import cn.wensiqun.asmsupport.core.build.impl.DefaultMethodBuilder;
import cn.wensiqun.asmsupport.core.context.ClassExecuteContext;
import cn.wensiqun.asmsupport.core.exception.ClassException;
import cn.wensiqun.asmsupport.core.utils.CommonUtils;
import cn.wensiqun.asmsupport.core.utils.bridge2method.OverrideBridgeMethodCreator;
import cn.wensiqun.asmsupport.core.utils.log.Log;
import cn.wensiqun.asmsupport.core.utils.log.LogFactory;
import cn.wensiqun.asmsupport.core.utils.reflect.MethodUtils;
import cn.wensiqun.asmsupport.org.objectweb.asm.ClassWriter;
import cn.wensiqun.asmsupport.org.objectweb.asm.Type;
import cn.wensiqun.asmsupport.standard.def.clazz.IClass;
import cn.wensiqun.asmsupport.standard.def.clazz.MutableClass;
import cn.wensiqun.asmsupport.standard.def.method.AMethodMeta;
import cn.wensiqun.asmsupport.standard.utils.ASMSupportClassLoader;
import cn.wensiqun.asmsupport.utils.ASConstants;
import cn.wensiqun.asmsupport.utils.Modifiers;
import cn.wensiqun.asmsupport.utils.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

public abstract class ClassBuildResolver extends AbstractBytecodeResolver {
	
    private static final Log LOG = LogFactory.getLog(ClassBuildResolver.class);

    protected SemiClass sc;

    public ClassBuildResolver(int version, int access, String name, IClass superCls, IClass[] interfaces, ASMSupportClassLoader classLoader) {
    	super(classLoader);
    	CommonUtils.validateJavaClassName(name);
        if (superCls == null) {
            superCls = classLoader.getType(Object.class);
        } else if (superCls.isInterface()) {
            throw new ClassException("the super class \"" + superCls.getName()
                    + "\" is an interface");
        }
        sc = new SemiClass(classLoader, version, access, name, superCls, interfaces);
    }
    

	@Override
	public final void initialized(ClassExecuteContext context) {
        IClass[] interfaces = sc.getInterfaces();
        String[] interfaceStrings = new String[interfaces.length];
        for (int i = 0; i < interfaces.length; i++) {
        	interfaceStrings[i] = interfaces[i].getType().getInternalName();
        }
        
        if(LOG.isPrintEnabled()){
        	LOG.print("Starting create class : " + sc.getName());
        }
        
        // create class
		ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
        cw.visit(sc.getVersion(), sc.getModifiers(),
                sc.getName().replace('.', '/'), null,
                Type.getInternalName(sc.getSuperclass().getName()), interfaceStrings);
		context.setClassVisitor(cw);
		context.setOwner(sc);

        //beforeCreate        
        this.beforeCreate();
        
        // create default constructor
		boolean existInit = false;
		for(MethodBuilder methodBuilder : methods) {
			if(ASConstants.INIT.equals(methodBuilder.getName())) {
				existInit = true;
				break;
			}
		}
		if (!existInit) {
			createDefaultConstructor();
		}
        
        // create field
        for (FieldBuilder ifc : fields) {
            ifc.initialized(context);
        }

        // create method
        for (MethodBuilder imc : methods) {
            imc.initialized(context);
        }

		rebuildOverrideBridge(context);
	}


	@Override
	public final void prepare() {
        
        checkUnImplementMethod();

        for (FieldBuilder ifc : fields) {
            ifc.prepare();
        }

        for (MethodBuilder imc : methods) {
            imc.prepare();
        }
	}


	@Override
	public final void execute(ClassExecuteContext context) {
        for (FieldBuilder ifc : fields) {
            ifc.execute(context);
        }

        for (MethodBuilder imc : methods) {
            imc.execute(context);
        }
	}

    @Override
	public MutableClass getCurrentClass() {
		return sc;
	}

	/**
	 * Create a default constructor, if not create any
	 * constructor.
	 */
    protected abstract void createDefaultConstructor();

	/**
	 * Call this method before call {@link #initialized(ClassExecuteContext)}
	 */
    protected void beforeCreate(){}

    private void checkUnImplementMethod() {
    	if(sc.isAbstract() || sc.isInterface()){
    		return;
    	}
    	
    	List<AMethodMeta> abstractMethods = new ArrayList<>();
    	List<AMethodMeta> nonAbstractMethods = new ArrayList<>();
    	allMethodInClass(sc.getSuperclass(), abstractMethods, nonAbstractMethods);
    	
    	for(IClass itf : sc.getInterfaces()){
        	allMethodInClass(itf, abstractMethods, nonAbstractMethods);
    	}
    	
    	for(int i=0; i<abstractMethods.size();){
    		AMethodMeta abstractMethod = abstractMethods.get(i);
    		boolean exist = false;
    		
    		for(int j=0; j<nonAbstractMethods.size(); j++){
        		AMethodMeta nonAbstractMethod = nonAbstractMethods.get(j);
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
    	
    	//#30205 [BUG]
    	List<AMethodMeta> scImplMethods = new ArrayList<>(sc.getDeclaredMethods());
    	for(int i=0; i<abstractMethods.size(); ){
    		AMethodMeta abstractMethod = abstractMethods.get(i);
    		boolean exist = false;
    		
    		for(int j=0; j<scImplMethods.size(); j++ ) {
    			if(MethodUtils.methodEqualWithoutOwner(scImplMethods.get(j), abstractMethod)){
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
    		for(AMethodMeta m : abstractMethods){
    			sb.append(m.toString()).append(lineSeq);
    		}
    		throw new InternalError(sb.toString());
    	}
    	
    }

    private boolean containMethod(List<AMethodMeta> methods, AMethodMeta method){
    	if(CollectionUtils.isNotEmpty(methods)){
    		for(AMethodMeta m : methods){
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
    private void allMethodInClass(IClass clazz, List<AMethodMeta> abstractMethods, List<AMethodMeta> nonAbstractMethods){
        if(clazz == null || clazz.equals(Object.class)){
    		return;
    	}
        
        for(AMethodMeta method : clazz.getDeclaredMethods()){
    		if(Modifiers.isAbstract(method.getModifiers())){
    			if(!containMethod(abstractMethods, method)){
    				abstractMethods.add(method);
    			}
    		}else{
    			if(!containMethod(nonAbstractMethods, method)){
    				nonAbstractMethods.add(method);
    			}
    		}
    	}
        
        if(clazz instanceof MutableClass) {
        	for(AMethodMeta method : ((MutableClass)clazz).getBridgeMethod()) {
        		if(!containMethod(nonAbstractMethods, method)){
    				nonAbstractMethods.add(method);
    			}
        	}
        }

    	allMethodInClass(clazz.getSuperclass(), abstractMethods, nonAbstractMethods);
        
    	for(IClass itf : clazz.getInterfaces()){
			allMethodInClass(itf, abstractMethods, nonAbstractMethods);
    	}
    }

    /**
     * Check if the created method is override, than check the return type, 
     * throw exception or make it to bridge if return type if different to 
     * parent
     * @param context
     */
    private void rebuildOverrideBridge(ClassExecuteContext context){
    	for(AMethodMeta validateMethod : sc.getDeclaredMethods()){
    		OverrideBridgeMethodCreator obmc = new OverrideBridgeMethodCreator(validateMethod);
    		List<DefaultMethodBuilder> creatorList = obmc.getList();
    		for(DefaultMethodBuilder mc : creatorList){
    			mc.initialized(context);
    		}
    		this.methods.addAll(creatorList);
    	}
    }
}
