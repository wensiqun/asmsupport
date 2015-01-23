package cn.wensiqun.asmsupport.core.utils.memory;

import cn.wensiqun.asmsupport.org.objectweb.asm.Type;

/**
 * 
 * @author 温斯群(Joe Wen)
 *
 */
public class ScopeThis implements Localable, Stackable {

    private Type owner;
    private String name;
    private static final int[] positions = {0};
    
    public ScopeThis(Class<?> owner, String name) {
        super();
        this.owner = Type.getType(owner);
        this.name = name;
    }

    @Override
    public int getSize() {
        return 1;
    }

    @Override
    public Type getDeclareType() {
        return owner;
    }

    @Override
    public Type getActuallyType() {
        return getDeclareType();
    }

    @Override
    public Type getType() {
        return getDeclareType();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int[] getPositions() {
        return positions;
    }

}
