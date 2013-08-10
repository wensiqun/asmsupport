package jw.asmsupport.entity;

import jw.asmsupport.clazz.AClass;

/**
 * 
 * @author 温斯群(Joe Wen)
 *
 */
public class VariableEntity {

    private String name;
    private int modifiers;
    private AClass declareClass;

    public VariableEntity(String name, int modifiers, AClass declareClass) {
        super();
        this.name = name;
        this.modifiers = modifiers;
        this.declareClass = declareClass;
    }

    public String getName() {
        return name;
    }

    public int getModifiers() {
        return modifiers;
    }

    public AClass getDeclareClass() {
        return declareClass;
    }

}
