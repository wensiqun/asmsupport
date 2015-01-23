/**1
 * 
 */
package cn.wensiqun.asmsupport.generic.creator;


import cn.wensiqun.asmsupport.core.clazz.NewMemberClass;
import cn.wensiqun.asmsupport.org.objectweb.asm.ClassVisitor;


/**
 * The class create or modify context
 * 
 * @author 温斯群(Joe Wen)
 *
 */
public interface IClassContext {
	
    /**
     * get class visitor
     * 
     * @return
     */
    ClassVisitor getClassVisitor();
    
    
    /**
     * 
     * @return
     */
    NewMemberClass getCurrentClass();
    
    /**
     * 
     * @param classOutPutPath
     */
	public void setClassOutPutPath(String classOutPutPath);
    
    
    /**
     * start create/modify class
     * 
     * @return
     */
    Class<?> startup();
	
}
