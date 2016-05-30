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
import cn.wensiqun.asmsupport.core.build.resolver.InterfaceResolver;
import cn.wensiqun.asmsupport.core.loader.CachedThreadLocalClassLoader;
import cn.wensiqun.asmsupport.core.utils.CommonUtils;
import cn.wensiqun.asmsupport.core.utils.log.LogFactory;
import cn.wensiqun.asmsupport.org.objectweb.asm.Opcodes;
import cn.wensiqun.asmsupport.standard.def.clazz.IClass;
import cn.wensiqun.asmsupport.standard.error.ASMSupportException;
import cn.wensiqun.asmsupport.standard.utils.ASMSupportClassLoader;
import cn.wensiqun.asmsupport.utils.lang.StringUtils;

import java.util.LinkedList;

public class DummyInterface extends AbstractDummy {

    /** Version of Class File Format */
    private int javaVersion = CommonUtils.getJDKVersion();

    /** Package name of class */
    private String packageName = StringUtils.EMPTY;

    /** Package name of class */
    private String name;

    /** Any interfaces in the class */
    private IClass[] interfaces;
    
    /** What's the class generate path of the class, use this for debug normally */
    private String classOutPutPath;
    
    /** Whether or not print log.*/
    private boolean printLog;
    
    /** The log file path.*/
    private String logFilePath;
    
    /** All fields */
    private LinkedList<DummyField> fieldDummies = new LinkedList<DummyField>();
    

    /** All methods */
    private LinkedList<DummyInterfaceMethod> methodDummies = new LinkedList<DummyInterfaceMethod>();
    
    /** class static block */
    private StaticBlockBody staticBlock;
    
    /**
     * The modifiers
     */
    protected int modifiers = Opcodes.ACC_ABSTRACT;
    
    public DummyInterface() {
    	this(null, CachedThreadLocalClassLoader.getInstance());
    }
    
    public DummyInterface(ASMSupportClassLoader classLoader) {
    	this(null, classLoader);
    }
    
    public DummyInterface(String qualifiedName) {
        this(qualifiedName, CachedThreadLocalClassLoader.getInstance());
    }
    
    /**
     * The interface qualified name.
     * 
     * @param qualifiedName
     */
    public DummyInterface(String qualifiedName, ASMSupportClassLoader classLoader) {
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
     * Set the access to private.
     * 
     * @return
     */
    public DummyInterface public_() {
        modifiers = (modifiers & ~Opcodes.ACC_PUBLIC) + Opcodes.ACC_PUBLIC;
        return this;
    }

    /**
     * Check the access whether or not public.
     * 
     * @return
     */
    public boolean isPublic() {
        return (modifiers & Opcodes.ACC_PUBLIC) != 0;
    }
    

    /**
     * Set the access to default.
     * 
     * @return
     */
    public DummyInterface default_() {
        modifiers = modifiers & ~ Opcodes.ACC_PUBLIC;
        return this;
    }
    
    /**
     * Check the access whether or not default.
     * 
     * @return
     */
    public boolean isDefault() {
        return !isPublic();
    }
    
    /**
     * Set the package name of class. 
     * 
     * @param packageName
     * @return
     */
    public DummyInterface package_(String packageName) {
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
    public DummyInterface name(String name) {
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
    public DummyInterface setJavaVersion(int version) {
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
     * Set the interfaces
     * 
     * @param interfaces
     * @return
     */
    public DummyInterface extends_(IClass... interfaces) {
        this.interfaces = interfaces;
        return this;
    }
    
    /**
     * Set the interfaces 
     * 
     * @param itfs
     * @return
     */
    public DummyInterface extends_(Class<?>... itfs) {
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
    public IClass[] getExtends() {
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
    public DummyInterface setClassOutPutPath(String path) {
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
    public DummyInterface setPrintLog(boolean print) {
        printLog = print;
        return this;
    }

    /**
     * The log information out put path. call the method will set {@code printLog} to {@code true.}
     * 
     * @param logFile
     * @return
     */
    public DummyInterface setLogFilePath(String logFile){
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
        return field;
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
        return field;
    }
    

    /**
     * Create a method.
     * 
     * @return
     */
    public DummyInterfaceMethod newMethod(String name) {
        DummyInterfaceMethod method = new DummyInterfaceMethod(getClassLoader());
        method.name(name);
        methodDummies.add(method);
        return method;
    }    


    /**
     * Create a static block.
     * 
     * @return
     */
    public DummyInterface newStaticBlock(StaticBlockBody staticBlock) {
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
        InterfaceResolver ici = new InterfaceResolver(javaVersion,
        		StringUtils.isBlank(packageName) ? name : packageName + "." + name, interfaces, getClassLoader());

        for(DummyField dummy : fieldDummies) {
            if(dummy.getType() == null) {
                throw new ASMSupportException("Not specify field type for field '" +  dummy.getName() + "'");
            }
            if(dummy.getName() == null) {
                throw new ASMSupportException("Not specify field name.");
            }
            ici.createField(dummy.getName(), dummy.getType(), dummy.getValue());
        }
        
        for(DummyInterfaceMethod dummy : methodDummies) {
            ici.declareMethod(dummy.getName(), dummy.getArgTypes(), dummy.getReturnType(), dummy.getThrows(), dummy.isVarargs());
        }
        
        if(staticBlock != null) {
            ici.createStaticBlock(staticBlock.getDelegate());
        }
        
        ici.setClassOutputPath(classOutPutPath);
        return ici.resolve();
    }
}
