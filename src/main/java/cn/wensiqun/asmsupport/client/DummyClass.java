package cn.wensiqun.asmsupport.client;

import java.util.LinkedList;

import cn.wensiqun.asmsupport.core.clazz.AClass;
import cn.wensiqun.asmsupport.core.creator.clazz.ClassCreator;
import cn.wensiqun.asmsupport.core.exception.ASMSupportException;
import cn.wensiqun.asmsupport.core.utils.CommonUtils;
import cn.wensiqun.asmsupport.org.apache.commons.lang3.StringUtils;
import cn.wensiqun.asmsupport.org.objectweb.asm.Opcodes;

public class DummyClass extends DummyAccessControl<DummyClass> {

    /** Version of Class File Format */
    private int javaVersion = CommonUtils.getSystemJDKVersion();

    /** Package name of class */
    private String packageName = StringUtils.EMPTY;

    /** Package name of class */
    private String name;

    /** The the super class */
    private Class<?> parent;

    /** Any interfaces in the class */
    private Class<?>[] interfaces;

    /** Specify the classloader */
    private ClassLoader classLoader;

    /** What's the class generate path of the class, use this for debug normally */
    private String classOutPutPath;

    /** All constructors */
    private LinkedList<DummyConstructor> constructorDummies = new LinkedList<DummyConstructor>();
    
    /** All fields */
    private LinkedList<DummyField> fieldDummies = new LinkedList<DummyField>();
    
    /** All methods */
    private LinkedList<DummyMethod> methodDummies = new LinkedList<DummyMethod>();
    
    /** class static block */
    private StaticBlockBody staticBlock;
    
    public DummyClass() {
    }
    
    public DummyClass(String qualifiedName) {
        this();
        if(StringUtils.isNotBlank(qualifiedName)) {
            int lastDot = qualifiedName.lastIndexOf('.');
            if(lastDot > 0) {
                packageName = qualifiedName.substring(0, lastDot);
                name = qualifiedName.substring(lastDot + 1);
            }
        }
    }

    /**
     * Set to static
     * 
     * @return
     */
    public DummyClass _static() {
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
    public DummyClass _package(String packageName) {
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
    public DummyClass _name(String name) {
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
    public DummyClass _javaVersion(int version) {
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
    public DummyClass _abstract() {
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
    public DummyClass _extends(Class<?> parent) {
        this.parent = parent;
        return this;
    }

    /**
     * Get the super class.
     * 
     * @return
     */
    public Class<?> getExtends() {
        return parent;
    }
    
    /**
     * Set the interfaces
     * 
     * @param interfaces
     * @return
     */
    public DummyClass _implements(Class<?>... interfaces) {
        this.interfaces = interfaces;
        return this;
    }
    
    
    /**
     * Get the interfaces.
     * 
     * @return
     */
    public Class<?>[] getImplements() {
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
    public DummyClass _classLoader(ClassLoader cl) {
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
    public DummyClass _classOutPutPath(String path) {
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
    public DummyField newField(AClass type, String name) {
        DummyField field = new DummyField(); 
        fieldDummies.add(field);
        field._type(type)._name(name);
        return fieldDummies.getLast();
    }

    /**
     * Create a field
     * 
     * @return
     */
    public DummyField newField(Class<?> type, String name) {
        DummyField field = new DummyField(); 
        fieldDummies.add(field);
        field._type(type)._name(name);
        return fieldDummies.getLast();
    }

    /**
     * Create Constructor.
     * 
     * @return
     */
    public DummyConstructor newConstructor() {
        constructorDummies.add(new DummyConstructor());
        return constructorDummies.getLast();
    }

    /**
     * Create a method.
     * 
     * @return
     */
    public DummyMethod newMethod(String name) {
        DummyMethod method = new DummyMethod();
        methodDummies.add(method);
        method._name(name);
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
        ClassCreator cci = new ClassCreator(javaVersion, modifiers, packageName + "." + name, parent,
                interfaces);
        for(DummyConstructor dummy : constructorDummies) {
            if(dummy.getConstructorBody() != null) {
                cci.createConstructor(dummy.getModifiers(), dummy.getArgumentTypes(), dummy.getArgumentNames(), dummy.getThrows(), dummy.getConstructorBody().target);    
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
            cci.createMethodForDummy(dummy.getModifiers(), dummy.getName(), dummy.getArgumentTypes(),
                    dummy.getArgumentNames(), dummy.getReturnType(), dummy.getThrows(), 
                    dummy.getMethodBody() == null ? null : dummy.getMethodBody().target);
        }
        
        if(staticBlock != null) {
            cci.createStaticBlock(staticBlock.target);
        }
        
        cci.setParentClassLoader(classLoader);
        cci.setClassOutPutPath(classOutPutPath);
        return cci.startup();
    }

}
