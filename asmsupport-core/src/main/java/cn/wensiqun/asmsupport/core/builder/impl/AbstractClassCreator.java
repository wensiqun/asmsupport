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

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import cn.wensiqun.asmsupport.core.builder.IFieldBuilder;
import cn.wensiqun.asmsupport.core.builder.IMethodBuilder;
import cn.wensiqun.asmsupport.core.exception.ClassException;
import cn.wensiqun.asmsupport.core.loader.AsmsupportClassLoader;
import cn.wensiqun.asmsupport.core.loader.CachedThreadLocalClassLoader;
import cn.wensiqun.asmsupport.core.utils.bridge2method.OverrideBridgeMethodCreator;
import cn.wensiqun.asmsupport.core.utils.log.Log;
import cn.wensiqun.asmsupport.core.utils.log.LogFactory;
import cn.wensiqun.asmsupport.core.utils.reflect.MethodUtils;
import cn.wensiqun.asmsupport.core.utils.reflect.ModifierUtils;
import cn.wensiqun.asmsupport.org.objectweb.asm.ClassWriter;
import cn.wensiqun.asmsupport.org.objectweb.asm.Opcodes;
import cn.wensiqun.asmsupport.org.objectweb.asm.Type;
import cn.wensiqun.asmsupport.standard.def.clazz.AClass;
import cn.wensiqun.asmsupport.standard.def.clazz.AClassFactory;
import cn.wensiqun.asmsupport.standard.def.clazz.IClass;
import cn.wensiqun.asmsupport.standard.def.clazz.MutableClass;
import cn.wensiqun.asmsupport.standard.def.method.AMethodMeta;
import cn.wensiqun.asmsupport.standard.def.var.meta.Field;
import cn.wensiqun.asmsupport.standard.error.ASMSupportException;
import cn.wensiqun.asmsupport.utils.ByteCodeConstant;
import cn.wensiqun.asmsupport.utils.collections.CollectionUtils;
import cn.wensiqun.asmsupport.utils.lang.ArrayUtils;
import cn.wensiqun.asmsupport.utils.lang.InterfaceLooper;

public abstract class AbstractClassCreator extends AbstractClassBuilder {
	
    private static final Log LOG = LogFactory.getLog(AbstractClassCreator.class);

    protected SemiClass sc;

    protected boolean haveInitMethod;

    public AbstractClassCreator(int version, int access, String name, Class<?> superCls, Class<?>[] interfaces) {
    	this(version, access, name, superCls, interfaces, CachedThreadLocalClassLoader.getInstance());
    }
    
    public AbstractClassCreator(int version, int access, String name, Class<?> superCls, Class<?>[] interfaces, AsmsupportClassLoader asmsupportClassLoader) {
    	super(asmsupportClassLoader);
        if (superCls == null) {
            superCls = Object.class;
        } else if (superCls.isInterface()) {
            throw new ClassException("the super class \"" + superCls.getName()
                    + "\" is an interface");
        }
        sc = new SemiClass(version, access, name, superCls, interfaces);
        cw = new ClassWriter(0);
    }
    

	@Override
	public void create() {
        String[] interfaceStrs;
        if(sc.getInterfaces() == null){
            interfaceStrs = new String[0];
        }else{
            interfaceStrs = new String[sc.getInterfaces().length];
        }
        for (int i = 0; i < interfaceStrs.length; i++) {
            interfaceStrs[i] = Type.getInternalName(sc.getInterfaces()[i]);
        }
        
        if(LOG.isPrintEnabled()){
        	LOG.print("Starting create class : " + sc.getName());
        }
        
        // create class
        cw.visit(sc.getVersion(), sc.getModifiers(),
                sc.getName().replace('.', '/'), null,
                Type.getInternalName(sc.getSuperClass()), interfaceStrs);

        //beforeCreate        
        this.beforeCreate();
        
        // create default constructor
        checkOrCreateDefaultConstructor();
        
        // create field
        for (IFieldBuilder ifc : fieldCreators) {
            ifc.create(this);
        }

        // create method
        for (IMethodBuilder imc : methodCreaters) {
            imc.create(this);
        }
	}


	@Override
	public void prepare() {
        checkOverriedAndCreateBridgeMethod();
        
        checkUnImplementMethod();

        for (IFieldBuilder ifc : fieldCreators) {
            ifc.prepare();
        }

        for (IMethodBuilder imc : methodCreaters) {
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
        return cw.toByteArray();
	}

	/*@Override
    public Class<?> startup() {
		create();
		prepare();
		byte[] code = execute();
        if(!StringUtils.isBlank(classOutPutPath)){
        	CommonUtils.toLocal(code, classOutPutPath, sc.getName());
        }
        if(LOG.isPrintEnabled()){
        	LOG.print("End create class : " + sc.getName().replace('.', '/'));
        }
        return loadClass(sc.getName(), code);
    }*/

    @Override
	public MutableClass getCurrentClass() {
		return sc;
	}
    
    
    private void checkOrCreateDefaultConstructor(){
        if (!haveInitMethod) {
            createDefaultConstructor();
        }
    }
    protected abstract void createDefaultConstructor();

    protected void beforeCreate(){};
    
    //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<Start checkUnImplementMethod<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    
    /**
     * 
     */
    private void checkUnImplementMethod() {
    	if(sc.isAbstract() || sc.isInterface()){
    		return;
    	}
    	
    	List<Method> abstractMethods = new ArrayList<Method>();
    	List<Method> nonAbstractMethods = new ArrayList<Method>();
    	allMethodInClass(sc.getSuperClass(), abstractMethods, nonAbstractMethods);
    	
    	for(Class<?> inter : sc.getInterfaces()){
        	allMethodInClass(inter, abstractMethods, nonAbstractMethods);
    	}
    	
    	for(int i=0; i<abstractMethods.size();){
    		Method abstractMethod = abstractMethods.get(i);
    		boolean exist = false;
    		
    		for(int j=0; j<nonAbstractMethods.size(); j++){
        		Method nonAbstractMethod = nonAbstractMethods.get(j);
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
    	List<AMethodMeta> scImplMethods = 
    			new ArrayList<AMethodMeta>(sc.getMethods());
    	for(int i=0; i<abstractMethods.size(); ){
    		Method abstractMethod = abstractMethods.get(i);
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
    		for(Method m : abstractMethods){
    			sb.append(m.toString()).append(lineSeq);
    		}
    		throw new InternalError(sb.toString());
    	}
    	
    }
    
    /**
     * 
     * @param methods
     * @param method
     * @return
     */
    private boolean containMethod(List<Method> methods, Method method){
    	if(CollectionUtils.isNotEmpty(methods)){
    		for(Method m : methods){
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
    private void allMethodInClass(Class<?> clazz, List<Method> abstractMethods, List<Method> nonAbstractMethods){
        if(clazz == null || clazz.equals(Object.class)){
    		return;
    	}
        
        Method[] methods = clazz.getDeclaredMethods();
        if(ArrayUtils.isNotEmpty(methods)){
        	for(Method m : methods){
        		if(ModifierUtils.isAbstract(m.getModifiers())){
        			if(!containMethod(abstractMethods, m)){
        				abstractMethods.add(m);
        			}
        		}else{
        			if(!containMethod(nonAbstractMethods, m)){
        				nonAbstractMethods.add(m);
        			}
        		}
        	}
        }

    	allMethodInClass(clazz.getSuperclass(), abstractMethods, nonAbstractMethods);
        
    	Class<?>[] interfaces = clazz.getInterfaces();
    	if(ArrayUtils.isNotEmpty(interfaces)){
    		for(Class<?> interfaceClass : interfaces){
    			allMethodInClass(interfaceClass, abstractMethods, nonAbstractMethods);
    			
        	}
    	}
    }

    
   //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>End checkUnImplementMethod>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    
    

    //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<Start checkOverriedAndCreateBridgeMethod<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    /**
     * Check if the created method is override, than check the return type, 
     * throw exception or make it to bridge if return type if different to 
     * parent
     * 
     */
    private void checkOverriedAndCreateBridgeMethod(){
    	List<AMethodMeta> methods = 
    			new ArrayList<AMethodMeta>(sc.getMethods());
    	for(AMethodMeta validateMethod : methods){
    	    if(ByteCodeConstant.CLINIT.equals(validateMethod.getName())) {
    	        continue;
    	    }
    		OverrideBridgeMethodCreator obmc = new OverrideBridgeMethodCreator(validateMethod);
    		List<MethodBuilderImpl> creatorList = obmc.getList();
    		for(MethodBuilderImpl mc : creatorList){
    			mc.create(this);
    		}
    		this.methodCreaters.addAll(creatorList);
    	}
    }
    
    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>End checkOverriedAndCreateBridgeMethod>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    
    public static class SemiClass extends MutableClass {

    	SemiClass(int version, int access, String name, Class<?> superCls,
                Class<?>[] interfaces) {
            this.version = version;
            this.name = name;
            this.mod = access;
            this.superClass = superCls;
            this.interfaces = interfaces;

            if(!ModifierUtils.isInterface(mod)){
                this.mod += Opcodes.ACC_SUPER;
            }
            
        }

        @Override
        public String getDescription() {
            return new StringBuilder("L").append(getName().replace(".", "/"))
                    .append(";").toString();
        }
        
        @Override
        public boolean isPrimitive() {
            return false;
        }

        @Override
        public boolean isArray() {
            return false;
        }

        @Override
        public int getDimension() {
            return -1;
        }

        @Override
        public Field getField(final String name) {
            
            final LinkedList<Field> found = new LinkedList<Field>();
            
            for(Field gv : getFields()){
                if(gv.getName().equals(name)){
                    found.add(gv);
                }
            }
            
            if(found.isEmpty()) {
                Class<?> fieldOwner = getSuperClass();
                for(;!fieldOwner.equals(Object.class); fieldOwner = fieldOwner.getSuperclass()){
                    try {
                        java.lang.reflect.Field field = fieldOwner.getDeclaredField(name);
                        found.add(new Field(this,
                                AClassFactory.getType(fieldOwner),
                                AClassFactory.getType(field.getType()), field.getModifiers(), name));
                        break;
                    } catch (NoSuchFieldException e) {
                    }
                }
            }
            
            new InterfaceLooper() {
                @Override
                protected boolean process(Class<?> inter) {
                    try {
                        java.lang.reflect.Field f = inter.getDeclaredField(name);
                        found.add(new Field(SemiClass.this,
                                AClassFactory.getType(inter),
                                AClassFactory.getType(f.getType()), f.getModifiers(), name));
                        return true;
                    } catch (NoSuchFieldException e) {
                        return false;
                    }
                }
            }.loop(getInterfaces());
            
            if(found.size() == 0) {
                throw new ASMSupportException("Not found field " + name);
            } else if(found.size() == 1) {
                return found.getFirst();
            } 

            StringBuilder errorSuffix = new StringBuilder();
            for(Field field : found) {
                errorSuffix.append(field.getDeclaringClass()).append(',');
            }
            throw new ASMSupportException("The field '" + name + "' is ambiguous, found it in class [" + errorSuffix + "]");
        }

        @Override
        public AClass getNextDimType() {
            throw new UnsupportedOperationException();
        }

        @Override
        public IClass getRootComponentClass() {
            throw new UnsupportedOperationException();
        }
        
    }
}
