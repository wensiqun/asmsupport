package cn.wensiqun.asmsupport.client;

import java.util.LinkedList;

import cn.wensiqun.asmsupport.core.clazz.AClass;
import cn.wensiqun.asmsupport.core.creator.clazz.InterfaceCreator;
import cn.wensiqun.asmsupport.core.exception.ASMSupportException;
import cn.wensiqun.asmsupport.core.utils.CommonUtils;
import cn.wensiqun.asmsupport.org.apache.commons.lang3.StringUtils;
import cn.wensiqun.asmsupport.org.objectweb.asm.Opcodes;

public class DummyInterface {

    /** Version of Class File Format */
    private int javaVersion = CommonUtils.getSystemJDKVersion();

    /** Package name of class */
    private String packageName = StringUtils.EMPTY;

    /** Package name of class */
    private String name;

    /** Any interfaces in the class */
    private Class<?>[] interfaces;
    
    /** Specify the classloader */
    private ClassLoader classLoader;

    /** What's the class generate path of the class, use this for debug normally */
    private String classOutPutPath;
    
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
    
    /**
     * Set the access to private.
     * 
     * @return
     */
    public DummyInterface _public() {
        modifiers = (modifiers | ~Opcodes.ACC_PUBLIC) + Opcodes.ACC_PUBLIC;
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
    public DummyInterface _default() {
        modifiers = modifiers | ~ Opcodes.ACC_PUBLIC;
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
    public DummyInterface _package(String packageName) {
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
    public DummyInterface _name(String name) {
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
    public DummyInterface _javaVersion(int version) {
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
    public DummyInterface _extends(Class<?>... interfaces) {
        this.interfaces = interfaces;
        return this;
    }
    
    /**
     * Get the interfaces.
     * 
     * @return
     */
    public Class<?>[] getExtends() {
        if(interfaces == null) {
            return new Class[0];
        }
        Class<?>[] copy = new Class[interfaces.length];
        System.arraycopy(interfaces, 0, copy, 0, copy.length);
        return copy;
    }
    
    /**
     * Set the classloader
     * 
     * @param cl
     * @return
     */
    public DummyInterface _classLoader(ClassLoader cl) {
        this.classLoader = cl;
        return this;
    }
    
    /**
     * Get the class loader
     * 
     * @return
     */
    public ClassLoader getClassLoader() {
        return classLoader;
    }

    /**
     * Set the class out put path.
     * 
     * @param path
     * @return
     */
    public DummyInterface _classOutPutPath(String path) {
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
     * Create a field
     * 
     * @return
     */
    public DummyInterface newField(AClass type, String name) {
        DummyField field = new DummyField(); 
        fieldDummies.add(field);
        field._type(type)._name(name);
        return this;
    }
    
    /**
     * Create a field
     * 
     * @return
     */
    public DummyInterface newField(Class<?> type, String name) {
        DummyField field = new DummyField(); 
        fieldDummies.add(field);
        field._type(type)._name(name);
        return this;
    }
    

    /**
     * Create a method.
     * 
     * @return
     */
    public DummyInterfaceMethod newMethod() {
        methodDummies.add(new DummyInterfaceMethod());
        return methodDummies.getLast();
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
        InterfaceCreator ici = new InterfaceCreator(javaVersion, packageName + "." + name, interfaces);
        
        for(DummyField dummy : fieldDummies) {
            if(dummy.getType() == null) {
                throw new ASMSupportException("Not specify field type for field '" +  dummy.getName() + "'");
            }
            if(dummy.getName() == null) {
                throw new ASMSupportException("Not specify field name.");
            }
            ici.createField(dummy.getName(), dummy.getType());
        }
        
        for(DummyInterfaceMethod dummy : methodDummies) {
            ici.createMethod(dummy.getName(), dummy.getArgumentTypes(), dummy.getReturnType(), dummy.getThrows());
        }
        
        if(staticBlock != null) {
            ici.createStaticBlock(staticBlock.target);
        }
        
        ici.setParentClassLoader(classLoader);
        ici.setClassOutPutPath(classOutPutPath);
        return ici.startup();
    }
}
