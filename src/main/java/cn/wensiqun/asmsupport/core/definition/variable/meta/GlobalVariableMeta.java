package cn.wensiqun.asmsupport.core.definition.variable.meta;

import cn.wensiqun.asmsupport.core.clazz.AClass;

/**
 * 
 * @author 温斯群(Joe Wen)
 *
 */
public class GlobalVariableMeta extends VariableMeta{

    private AClass owner;
    
    private AClass actuallyOwner;
    
    /**
     * @param owner 变量所属的Class
     * @param actuallyOwner 变量实际有用者
     * @param variableClass 变量类型
     * @param modifiers 变量修饰符
     * @param name 变量名
     */
    public GlobalVariableMeta(AClass owner, AClass actuallyOwner, AClass declareClass, int modifiers,
            String name) {
        super(name, modifiers, declareClass);
        this.owner = owner;
        this.actuallyOwner = actuallyOwner;
    }

    /**
     * getter of owner
     * @return
     */
    public AClass getOwner() {
        return owner;
    }

    /**
     * 
     * @return
     */
    public AClass getActuallyOwner(){
    	return actuallyOwner;
    }
    
}
