package cn.wensiqun.asmsupport.entity;

import cn.wensiqun.asmsupport.clazz.AClass;

/**
 * 
 * @author 温斯群(Joe Wen)
 *
 */
public class GlobalVariableEntity extends VariableEntity{

    private AClass owner;
    
    /**
     * @param owner 变量所属的Class
     * @param variableClass 变量类型
     * @param modifiers 变量修饰符
     * @param name 变量名
     */
    public GlobalVariableEntity(AClass owner, AClass declareClass, int modifiers,
            String name) {
        super(name, modifiers, declareClass);
        this.owner = owner;
    }

    /**
     * getter of owner
     * @return
     */
    public AClass getOwner() {
        return owner;
    }

}
