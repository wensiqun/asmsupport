package cn.wensiqun.asmsupport.standard;

import cn.wensiqun.asmsupport.standard.def.var.IFieldVar;

/**
 * 
 * @author 温斯群(Joe Wen)
 *
 */
public interface FieldVarGetter<T extends IFieldVar> {
    
    /**
     * Get field
     * @param name
     * @return T the sub type of {@link IFieldVar}
     */
    T field(String name);
}
