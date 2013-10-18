package cn.wensiqun.asmsupport.entity;

import cn.wensiqun.asmsupport.clazz.AClass;

/**
 * 
 * @author 温斯群(Joe Wen)
 *
 */
public class LocalVariableEntity extends VariableEntity {

    public LocalVariableEntity(String name, int modifiers, AClass declareClass) {
        super(name, modifiers, declareClass);
    }
}
