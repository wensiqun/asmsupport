package cn.wensiqun.asmsupport.creator;

import cn.wensiqun.asmsupport.clazz.AClass;

/**
 * 
 * @author 温斯群(Joe Wen)
 *
 */
public interface IMethodCreator extends IMemberCreator{
    
    public String getName();

    public AClass[] getArguments();
    
    public String getMethodString();
}
