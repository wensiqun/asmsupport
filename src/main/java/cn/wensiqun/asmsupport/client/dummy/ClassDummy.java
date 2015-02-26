package cn.wensiqun.asmsupport.client.dummy;

import cn.wensiqun.asmsupport.core.creator.clazz.ClassCreatorInternal;
import cn.wensiqun.asmsupport.org.apache.commons.lang3.StringUtils;
import cn.wensiqun.asmsupport.org.objectweb.asm.Opcodes;

public class ClassDummy {

    private int modifiers;

    private int javaVersion = Opcodes.V1_6;

    private String packageName = StringUtils.EMPTY;

    private String name;

    private Class<?> parent;

    private Class<?>[] interfaces;

    private ClassLoader classLoader;

    private String classOutPutPath;

    public ClassDummy _package(String packageName) {
        this.packageName = packageName;
        return this;
    }

    public String getPackage() {
        return packageName;
    }

    public ClassDummy _name(String name) {
        this.name = name;
        return this;
    }

    public String getName() {
        return name;
    }

    public ClassDummy _javaVersion(int version) {
        javaVersion = version;
        return this;
    }

    public int getJavaVersion() {
        return javaVersion;
    }

    public ClassDummy _private() {
        modifiers = (modifiers | ~(Opcodes.ACC_PRIVATE + Opcodes.ACC_PUBLIC + Opcodes.ACC_PROTECTED))
                + Opcodes.ACC_PRIVATE;
        return this;
    }

    public boolean isPrivate() {
        return (modifiers & Opcodes.ACC_PRIVATE) != 0;
    }

    public ClassDummy _public() {
        modifiers = (modifiers | ~(Opcodes.ACC_PRIVATE + Opcodes.ACC_PUBLIC + Opcodes.ACC_PROTECTED))
                + Opcodes.ACC_PUBLIC;
        return this;
    }

    public boolean isPublic() {
        return (modifiers & Opcodes.ACC_PUBLIC) != 0;
    }

    public ClassDummy _protected() {
        modifiers = (modifiers | ~(Opcodes.ACC_PRIVATE + Opcodes.ACC_PUBLIC + Opcodes.ACC_PROTECTED))
                + Opcodes.ACC_PROTECTED;
        return this;
    }

    public boolean isProtected() {
        return (modifiers & Opcodes.ACC_PROTECTED) != 0;
    }

    public ClassDummy _static() {
        modifiers = (modifiers | ~Opcodes.ACC_STATIC) + Opcodes.ACC_STATIC;
        return this;
    }

    public boolean isStatic() {
        return (modifiers & Opcodes.ACC_STATIC) != 0;
    }

    public ClassDummy _abstract() {
        modifiers = (modifiers | ~Opcodes.ACC_ABSTRACT) + Opcodes.ACC_ABSTRACT;
        return this;
    }

    public boolean isAbstract() {
        return (modifiers & Opcodes.ACC_ABSTRACT) != 0;
    }

    public ClassDummy _final() {
        modifiers = (modifiers | ~Opcodes.ACC_FINAL) + Opcodes.ACC_FINAL;
        return this;
    }

    public boolean isFinal() {
        return (modifiers & Opcodes.ACC_FINAL) != 0;
    }

    public ClassDummy _extends(Class<?> parent) {
        this.parent = parent;
        return this;
    }

    public ClassDummy _implements(Class<?>... interfaces) {
        this.interfaces = interfaces;
        return this;
    }

    public ClassDummy _classLoader(ClassLoader cl) {
        this.classLoader = cl;
        return this;
    }

    public ClassDummy _classOutPutPath(String path) {
        this.classOutPutPath = path;
        return this;
    }

    public FieldDummy field() {
        return null;
    }

    public ConstructorDummy constructor() {
        return null;
    }

    public MethodDummy method() {
        return null;
    }

    public StaticMethodDummy staticMethod() {
        return null;
    }

    public Class<?> build() {
        ClassCreatorInternal cci = new ClassCreatorInternal(javaVersion, modifiers, packageName + "." + name, parent,
                interfaces);
        cci.setParentClassLoader(classLoader);
        cci.setClassOutPutPath(classOutPutPath);
        return cci.startup();
    }

}
