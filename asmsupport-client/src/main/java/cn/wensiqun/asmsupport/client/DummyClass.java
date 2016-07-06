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
package cn.wensiqun.asmsupport.client;

import cn.wensiqun.asmsupport.client.block.StaticBlockBody;
import cn.wensiqun.asmsupport.core.build.resolver.ClassResolver;
import cn.wensiqun.asmsupport.core.loader.CachedThreadLocalClassLoader;
import cn.wensiqun.asmsupport.core.utils.CommonUtils;
import cn.wensiqun.asmsupport.core.utils.log.LogFactory;
import cn.wensiqun.asmsupport.org.objectweb.asm.Opcodes;
import cn.wensiqun.asmsupport.standard.def.clazz.IClass;
import cn.wensiqun.asmsupport.standard.error.ASMSupportException;
import cn.wensiqun.asmsupport.standard.utils.ASMSupportClassLoader;
import cn.wensiqun.asmsupport.utils.lang.StringUtils;

import java.util.LinkedList;

public class DummyClass extends DummyAccessControl<DummyClass> {

    /** Version of Class File Format */
    private int javaVersion = CommonUtils.getJDKVersion();

    /** Package name of class */
    private String packageName = StringUtils.EMPTY;

    /** Package name of class */
    private String name;

    /** The the super class */
    private IClass parent;

    /** Any interfaces in the class */
    private IClass[] interfaces;
    
    /** What's the class generate path of the class, use this for debug normally */
    private String classOutPutPath;
    
    /** Whether or not print log.*/
    private boolean printLog;
    
    /** The log file path.*/
    private String logFilePath;

    /** All constructors */
    private LinkedList<DummyConstructor> constructorDummies = new LinkedList<DummyConstructor>();
    
    /** All fields */
    private LinkedList<DummyField> fieldDummies = new LinkedList<>();
    
    /** All methods */
    private LinkedList<DummyMethod> methodDummies = new LinkedList<>();
    
    /** class static block */
    private StaticBlockBody staticBlock;
    
    public DummyClass() {
    	this(null, CachedThreadLocalClassLoader.getInstance());
    }
    
    public DummyClass(ASMSupportClassLoader classLoader) {
    	this(null, classLoader);
    }
    
    public DummyClass(String qualifiedName) {
        this(qualifiedName, CachedThreadLocalClassLoader.getInstance());
    }
    
    public DummyClass(String qualifiedName, ASMSupportClassLoader classLoader) {
    	super(classLoader);
        if(classLoader == null) {
        	throw new ASMSupportException("Class loader must be not null");
        }
        if(StringUtils.isNotBlank(qualifiedName)) {
            int lastDot = qualifiedName.lastIndexOf('.');
            if(lastDot > 0) {
                packageName = qualifiedName.substring(0, lastDot);
                name = qualifiedName.substring(lastDot + 1);
            } else {
            	name = qualifiedName;
            }
        }
    }
    
    

    /**
     * Set to static
     * 
     * @return
     */
    public DummyClass static_() {
        modifiers = (modifiers & ~Opcodes.ACC_STATIC) + Opcodes.ACC_STATIC;
        return this;
    }

    /**
     * Check the access whether or not static.
     * 
     * @return
     */
    public boolean isStatic() {
        return (modifiers & Opcodes.ACC_STATIC) != 0;
    }
    
    /**
     * Set the package name of class. 
     * 
     * @param packageName
     * @return
     */
    public DummyClass package_(String packageName) {
        this.packageName = packageName;
        return this;
    }

    /**
     * Get the class package name.
     * 
     * @return
     */
    public String getPackage() {
        return packageName;
    }

    /**
     * Set the class name.
     * 
     * @param name
     * @return
     */
    public DummyClass name(String name) {
        this.name = name;
        return this;
    }

    /**
     * Get the class name
     * 
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Set the jdk version of Class File Format
     * 
     * @param version
     * @return
     */
    public DummyClass setJavaVersion(int version) {
        javaVersion = version;
        return this;
    }

    /**
     * 
     * Get the jdk version of Class File Format
     * 
     * @return
     */
    public int getJavaVersion() {
        return javaVersion;
    }

    /**
     * Set the class to abstract.
     * 
     * @return
     */
    public DummyClass abstract_() {
        modifiers = (modifiers & ~Opcodes.ACC_ABSTRACT) + Opcodes.ACC_ABSTRACT;
        return this;
    }


    /**
     * Check the class access whether or not abstract.
     * 
     * @return
     */
    public boolean isAbstract() {
        return (modifiers & Opcodes.ACC_ABSTRACT) != 0;
    }

    /**
     * Set the super class
     * 
     * @param parent
     * @return
     */
    public DummyClass extends_(Class<?> parent) {
        this.parent = getClassLoader().getType(parent);
        return this;
    }
    
    public DummyClass extends_(IClass parent) {
    	this.parent = parent;
    	return this;
    }

    /**
     * Get the super class.
     * 
     * @return
     */
    public IClass getExtends() {
        return parent;
    }
    
    /**
     * Set the interfaces
     * 
     * @param interfaces
     * @return
     */
    public DummyClass implements_(IClass... interfaces) {
        this.interfaces = interfaces;
        return this;
    }
    
    /**
     * Set the interfaces 
     * 
     * @param itfs
     * @return
     */
    public DummyClass implements_(Class<?>... itfs) {
    	if(itfs != null) {
    		this.interfaces = new IClass[itfs.length];
    		for(int i=0; i<itfs.length; i++) {
    			this.interfaces[i] = getClassLoader().getType(itfs[i]);
    		}
    	}
    	return this;
    }
    
    
    /**
     * Get the interfaces.
     * 
     * @return
     */
    public IClass[] getImplements() {
        if(interfaces == null) {
            return new IClass[0];
        }
        IClass[] copy = new IClass[interfaces.length];
        System.arraycopy(interfaces, 0, copy, 0, copy.length);
        return copy;
    }

    /**
     * Set the class out put path.
     * 
     * @param path
     * @return
     */
    public DummyClass setClassOutPutPath(String path) {
        this.classOutPutPath = path;
        return this;
    }
    
    /**
     * Get the class out put path.
     * 
     * @return
     */
    public String getClassOutPutPath() {
        return classOutPutPath;
    }
    
    /**
     * Whether or not print log information. if set {@code true} than print it to console.
     * Also you can print to file by method {@link DummyClass#setLogFilePath(String)}
     * 
     * @param print
     * @return
     */
    public DummyClass setPrintLog(boolean print) {
        printLog = print;
        return this;
    }

    /**
     * The log information out put path. call the method will set {@code printLog} to {@code true.}
     * 
     * @param logFile
     * @return
     */
    public DummyClass setLogFilePath(String logFile){
        logFilePath = logFile;
        printLog = true;
        return this;
    }
    
    /**
     * Create a field
     * 
     * @return
     */
    public DummyField newField(IClass type, String name) {
        DummyField field = new DummyField(getClassLoader()); 
        fieldDummies.add(field);
        field.type(type).name(name);
        return fieldDummies.getLast();
    }

    /**
     * Create a field
     * 
     * @return
     */
    public DummyField newField(Class<?> type, String name) {
        DummyField field = new DummyField(getClassLoader()); 
        fieldDummies.add(field);
        field.type(type).name(name);
        return fieldDummies.getLast();
    }

    /**
     * Create Constructor.
     * 
     * @return
     */
    public DummyConstructor newConstructor() {
        constructorDummies.add(new DummyConstructor(getClassLoader()));
        return constructorDummies.getLast();
    }

    /**
     * Create a method.
     * 
     * @return
     */
    public DummyMethod newMethod(String name) {
        DummyMethod method = new DummyMethod(getClassLoader());
        methodDummies.add(method);
        method.name(name);
        return method;
    }
    
    /**
     * Create a static block.
     * 
     * @return
     */
    public DummyClass newStaticBlock(StaticBlockBody staticBlock) {
        if(this.staticBlock != null) {
            throw new ASMSupportException("Static Block is already existes.");
        }
        this.staticBlock = staticBlock;
        return this;
    }

    /**
     * Build class. 
     * 
     * @return
     */
    public Class<?> build() {
    	LogFactory.LOG_FACTORY_LOCAL.remove();
        if(StringUtils.isNotBlank(logFilePath)) {
        	LogFactory.LOG_FACTORY_LOCAL.set(new LogFactory(logFilePath)); 
        } else if(printLog) {
        	LogFactory.LOG_FACTORY_LOCAL.set(new LogFactory()); 
        }
        
        ClassResolver cci = new ClassResolver(javaVersion, modifiers,
        		StringUtils.isBlank(packageName) ? name : packageName + "." + name, 
        				parent, interfaces, getClassLoader());
        
        for(DummyConstructor dummy : constructorDummies) {
            if(dummy.getConstructorBody() != null) {
                cci.createConstructor(dummy.getModifiers(), dummy.getArgumentTypes(), dummy.getArgumentNames(), dummy.getThrows(), dummy.getConstructorBody().getDelegate());
            } else {
                throw new ASMSupportException("Not found body...");
            }
        }
        
        for(DummyField dummy : fieldDummies) {
            if(dummy.getType() == null) {
                throw new ASMSupportException("Not specify field type for field '" +  dummy.getName() + "'");
            }
            if(dummy.getName() == null) {
                throw new ASMSupportException("Not specify field name.");
            }
            cci.createField(dummy.getName(), dummy.getModifiers(), dummy.getType(), dummy.getValue());
        }
        
        for(DummyMethod dummy : methodDummies) {
            cci.createMethod(dummy.getModifiers(), dummy.getName(), dummy.getArgTypes(),
                    dummy.getArgNames(), dummy.getReturnType(), dummy.getThrows(), 
                    dummy.getMethodBody() == null ? null : dummy.getMethodBody().getDelegate());
        }
        
        if(staticBlock != null) {
            cci.createStaticBlock(staticBlock.getDelegate());
        }
        
        cci.setClassOutputPath(classOutPutPath);
        return cci.resolve();
    }

}
