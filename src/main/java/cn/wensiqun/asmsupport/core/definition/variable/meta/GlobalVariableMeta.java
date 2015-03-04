package cn.wensiqun.asmsupport.core.definition.variable.meta;

import cn.wensiqun.asmsupport.core.clazz.AClass;

/**
 * 
 * @author 温斯群(Joe Wen)
 *
 */
public class GlobalVariableMeta extends VariableMeta{

    private AClass owner;
    
    private AClass actuallyOwnerType;
    
    /**
     * @param owner 变量所属的Class
     * @param actuallyOwnerType 变量实际有用者
     * @param variableClass 变量类型
     * @param modifiers 变量修饰符
     * @param name 变量名
     */
    public GlobalVariableMeta(AClass owner, AClass actuallyOwnerType, AClass declareClass, int modifiers, String name) {
        super(name, modifiers, declareClass);
        this.owner = owner;
        this.actuallyOwnerType = actuallyOwnerType;
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
    public AClass getActuallyOwnerType(){
    	return actuallyOwnerType;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((actuallyOwnerType == null) ? 0 : actuallyOwnerType.hashCode());
        result = prime * result + ((owner == null) ? 0 : owner.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        GlobalVariableMeta other = (GlobalVariableMeta) obj;
        if (actuallyOwnerType == null) {
            if (other.actuallyOwnerType != null)
                return false;
        } else if (!actuallyOwnerType.equals(other.actuallyOwnerType))
            return false;
        if (owner == null) {
            if (other.owner != null)
                return false;
        } else if (!owner.equals(other.owner))
            return false;
        return true;
    }
    
    
}
