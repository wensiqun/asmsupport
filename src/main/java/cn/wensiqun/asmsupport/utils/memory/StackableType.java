package cn.wensiqun.asmsupport.utils.memory;


import org.objectweb.asm.Type;

/**
 * 
 * @author 温斯群(Joe Wen)
 *
 */
public class StackableType implements Stackable {
    
    private Type type;

    public StackableType(Type type) {
        super();
        this.type = type;
    }

    @Override
    public Type getType(){
        return type;
    }

    @Override
    public int getSize() {
        return type.getSize();
    }
}
