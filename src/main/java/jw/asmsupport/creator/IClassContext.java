/**1
 * 
 */
package jw.asmsupport.creator;


import org.objectweb.asm.ClassVisitor;

import jw.asmsupport.clazz.NewMemberClass;


/**
 * 方法创建或者修改的上下文环境
 * @author 温斯群(Joe Wen)
 *
 */
public interface IClassContext {
	
    /**
     * 获取Class的ClassVisitor
     * @return
     */
    ClassVisitor getClassVisitor();
    
/*    SuperVariable getSuperVariable();
    
    ThisVariable getThisVariable();*/
    
    Class<?> startup();
    
    NewMemberClass getCurrentClass();
    
	public void setClassOutPutPath(String classOutPutPath);
	
}
