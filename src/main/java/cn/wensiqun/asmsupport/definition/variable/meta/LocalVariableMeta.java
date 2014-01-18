package cn.wensiqun.asmsupport.definition.variable.meta;

import cn.wensiqun.asmsupport.clazz.AClass;

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
