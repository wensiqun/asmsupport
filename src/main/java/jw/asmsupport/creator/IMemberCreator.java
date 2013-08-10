package jw.asmsupport.creator;

import jw.asmsupport.Executeable;
import jw.asmsupport.clazz.ProductClass;
import jw.asmsupport.clazz.SemiClass;

/**
 * 
 * @author 温斯群(Joe Wen)
 *
 */
public interface IMemberCreator extends Executeable{

    /**
     * 创建逻辑实体
     * @param cv
     * @param owner
     */
    public void create(IClassContext cv, SemiClass owner);

    /**
     * 创建逻辑实体
     * @param cv
     * @param owner
     */
    void create(IClassContext cv, ProductClass owner);
    
}
