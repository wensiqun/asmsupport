package cn.wensiqun.asmsupport.client;

import java.util.LinkedList;

import cn.wensiqun.asmsupport.core.creator.clazz.ClassModifier;
import cn.wensiqun.asmsupport.core.exception.ASMSupportException;
import cn.wensiqun.asmsupport.core.utils.ASConstant;

public class DummyModifiedClass {

    private Class<?> _original;

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
    
    /** All modify methods */
    private LinkedList<DummyModifiedMethod> modifyDummies = new LinkedList<DummyModifiedMethod>();
    

    public DummyModifiedClass(Class<?> original) {
        this._original = original;
    }
    
    /**
     * Set the classloader
     * 
     * @param cl
     * @return
     */
    public DummyModifiedClass _classLoader(ClassLoader cl) {
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
    public DummyModifiedClass _classOutPutPath(String path) {
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
    public DummyField newField() {
        fieldDummies.add(new DummyField());
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
    public DummyMethod newMethod() {
        methodDummies.add(new DummyMethod());
        return methodDummies.getLast();
    }

    /**
     * Create a static block.
     * 
     * @return
     */
    public DummyModifiedClass newStaticBlock(StaticBlockBody staticBlock) {
        if(staticBlock != null) {
            throw new ASMSupportException("Static Block is already existes.");
        }
        this.staticBlock = staticBlock;
        return this;
    }
    
    /**
     * Modify common method.
     * 
     * @param method
     * @param argTypes
     * @param body
     * @return
     */
    public DummyModifiedClass modify(String method, Class<?>[] argTypes, ModifiedMethodBody body){
        DummyModifiedMethod dmm = new DummyModifiedMethod(method, argTypes, body);
        this.modifyDummies.add(dmm);
        return this;
    }
    
    /**
     * Modify constructor
     * 
     * @param argTypes
     * @param body
     * @return
     */
    public DummyModifiedClass modifyConstructor(Class<?>[] argTypes, ModifiedMethodBody body) {
        return this.modify(ASConstant.INIT, argTypes, body);
    }
    
    /**
     * Modify static block.
     * 
     * @param body
     * @return
     */
    public DummyModifiedClass modifyStaticBlock(ModifiedMethodBody body) {
        return this.modify(ASConstant.CLINIT, null, body);
    }

    /**
     * Build class. 
     * 
     * @return
     */
    public Class<?> build() {
        ClassModifier cmi = new ClassModifier(_original);
        for(DummyConstructor dummy : constructorDummies) {
            if(dummy.getConstructorBody() != null) {
                cmi.createConstructor(dummy.getModifiers(), dummy.getArgumentTypes(), dummy.getArgumentNames(), dummy.getThrows(), dummy.getConstructorBody().target);    
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
            cmi.createField(dummy.getName(), dummy.getModifiers(), dummy.getType());
        }
        
        for(DummyMethod dummy : methodDummies) {
            cmi.createMethodForDummy(dummy.getModifiers(), dummy.getName(), dummy.getArgumentTypes(),
                    dummy.getArgumentNames(), dummy.getReturnType(), dummy.getThrows(), dummy.getMethodBody().target);
        }
        
        if(staticBlock != null) {
            cmi.createStaticBlock(staticBlock.target);
        }
        
        for(DummyModifiedMethod dummy : modifyDummies) {
            cmi.modifyMethod(dummy.getName(), dummy.getArgumentTypes(), dummy.getMethodBody().target);
        }
        
        cmi.setParentClassLoader(classLoader);
        cmi.setClassOutPutPath(classOutPutPath);
        return cmi.startup();
    }
}
