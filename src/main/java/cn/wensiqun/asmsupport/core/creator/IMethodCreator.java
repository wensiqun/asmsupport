package cn.wensiqun.asmsupport.core.creator;

import cn.wensiqun.asmsupport.core.Executable;
import cn.wensiqun.asmsupport.core.clazz.AClass;
import cn.wensiqun.asmsupport.generic.creator.IClassContext;

/**
 * 
 * @author 温斯群(Joe Wen)
 *
 */
public interface IMethodCreator extends Executable {
    
    /**
     * 创建逻辑实体
     * @param cv
     */
    public void create(IClassContext cv);
	
    public String getName();

    public AClass[] getArguments();
    
    public String getMethodString();
}
