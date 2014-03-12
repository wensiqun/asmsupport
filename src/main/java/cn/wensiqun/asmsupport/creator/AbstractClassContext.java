package cn.wensiqun.asmsupport.creator;

import java.util.List;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

import cn.wensiqun.asmsupport.clazz.AClassFactory;
import cn.wensiqun.asmsupport.loader.ASMClassLoader;


public abstract class AbstractClassContext extends AClassFactory implements IClassContext{

    protected List<IMethodCreator> methodCreaters;

    protected List<IGlobalVariableCreator> fieldCreators;

    protected String classOutPutPath;
	
    protected boolean existedStaticBlock;
	
    protected ClassWriter cw;
    
	protected void checkStaticBlock(){
    	if(existedStaticBlock){
    		throw new UnsupportedOperationException("the static block has alreay exist this method!");
    	}
    }

    @Override
    public final ClassVisitor getClassVisitor() {
        return cw;
    }

	public String getClassOutPutPath() {
		return classOutPutPath;
	}
	
    public final void setClassOutPutPath(String classOutPutPath) {
        this.classOutPutPath = classOutPutPath;
    }

    protected Class<?> loadClass(String name, byte[] b) {
        Class<?> clazz = null;
        try {
        	ASMClassLoader loader = ASMClassLoader.getInstance();
        	clazz = loader.defineClass(name, b);
        } catch (Exception e) {
            System.exit(1);
            throw new RuntimeException("Error on define class." + e);
        }
        return clazz;
    }
    
}