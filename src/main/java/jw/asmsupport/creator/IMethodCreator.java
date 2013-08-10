package jw.asmsupport.creator;

import jw.asmsupport.clazz.AClass;

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
