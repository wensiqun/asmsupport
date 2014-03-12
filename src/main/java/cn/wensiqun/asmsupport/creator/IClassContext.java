/**1
 * 
 */
package cn.wensiqun.asmsupport.creator;


import org.objectweb.asm.ClassVisitor;

import cn.wensiqun.asmsupport.clazz.NewMemberClass;


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
    
    
    /**
     * 启动Creator开始生成class，注意，调用完这个参数以后将不不会触发生成的class的static程序块
     * 
     * @return
     */
    Class<?> startup();
    
    NewMemberClass getCurrentClass();
    
	public void setClassOutPutPath(String classOutPutPath);
	
}
