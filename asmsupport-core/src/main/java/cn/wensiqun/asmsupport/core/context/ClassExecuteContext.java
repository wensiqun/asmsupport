package cn.wensiqun.asmsupport.core.context;

import cn.wensiqun.asmsupport.org.objectweb.asm.ClassWriter;
import cn.wensiqun.asmsupport.standard.def.clazz.IClass;
import cn.wensiqun.asmsupport.standard.utils.ASMSupportClassLoader;

/**
 * Created by sqwen on 2016/7/6.
 */
public class ClassExecuteContext implements Context {

    private IClass owner;

    private ClassWriter classVisitor;

    protected ASMSupportClassLoader classLoader;

    private String classOutPutPath;

    public IClass getOwner() {
        return owner;
    }

    public void setOwner(IClass owner) {
        this.owner = owner;
    }

    public ClassWriter getClassVisitor() {
        return classVisitor;
    }

    public void setClassVisitor(ClassWriter classVisitor) {
        this.classVisitor = classVisitor;
    }

    public ASMSupportClassLoader getClassLoader() {
        return classLoader;
    }

    public void setClassLoader(ASMSupportClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    /**
     *
     * @see #setClassOutPutPath
     */
    public String getClassOutPutPath() {
        return classOutPutPath;
    }

    /**
     * If you want's the generated class save to local,
     * call this method set the output path.
     *
     * @param classOutPutPath
     */
    public void setClassOutPutPath(String classOutPutPath) {
        this.classOutPutPath = classOutPutPath;
    }
}
