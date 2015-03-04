package cn.wensiqun.asmsupport.core.definition.variable.meta;

import cn.wensiqun.asmsupport.core.clazz.AClass;

/**
 * 
 * @author 温斯群(Joe Wen)
 *
 */
public class VariableMeta {

    private String name;
    private int modifiers;
    private AClass declareType;

    public VariableMeta(String name, int modifiers, AClass declareType) {
        super();
        this.name = name;
        this.modifiers = modifiers;
        this.declareType = declareType;
    }

    public String getName() {
        return name;
    }

    public int getModifiers() {
        return modifiers;
    }

    public AClass getDeclareType() {
        return declareType;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((declareType == null) ? 0 : declareType.hashCode());
        result = prime * result + modifiers;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        VariableMeta other = (VariableMeta) obj;
        if (declareType == null) {
            if (other.declareType != null)
                return false;
        } else if (!declareType.equals(other.declareType))
            return false;
        if (modifiers != other.modifiers)
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }
    
    

}
