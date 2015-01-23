package cn.wensiqun.asmsupport.core.definition.variable.meta;

import cn.wensiqun.asmsupport.core.clazz.AClass;

/**
 * 
 * @author 温斯群(Joe Wen)
 *
 */
public class LocalVariableMeta extends VariableMeta {

    public LocalVariableMeta(String name, int modifiers, AClass declareClass) {
        super(name, modifiers, declareClass);
    }
}
