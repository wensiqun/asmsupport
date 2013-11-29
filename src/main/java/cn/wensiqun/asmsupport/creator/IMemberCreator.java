package cn.wensiqun.asmsupport.creator;

import cn.wensiqun.asmsupport.Executeable;

/**
 * 
 * @author 温斯群(Joe Wen)
 *
 */
public interface IMemberCreator extends Executeable{

    /**
     * 创建逻辑实体
     * @param cv
     */
    public void create(IClassContext cv);

}
