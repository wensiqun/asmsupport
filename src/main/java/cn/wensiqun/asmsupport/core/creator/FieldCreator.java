package cn.wensiqun.asmsupport.core.creator;

import cn.wensiqun.asmsupport.core.clazz.AClass;
import cn.wensiqun.asmsupport.core.clazz.NewMemberClass;
import cn.wensiqun.asmsupport.core.definition.value.Value;
import cn.wensiqun.asmsupport.core.definition.variable.meta.GlobalVariableMeta;

/**
 * 
 * @author 温斯群(Joe Wen)
 *
 */
public class FieldCreator implements IFieldCreator {

    private String name;
    private int modifiers;
    private AClass type;

    private GlobalVariableMeta fe;
    private IClassContext context;
    private Object value;
    
    /**
     * 
     * @param name
     * @param modifiers
     * @param type
     */
    public FieldCreator(String name, int modifiers, AClass type) {
        this(name, modifiers, type, null);
    }
    
    /**
     * @param name
     * @param modifiers
     * @param type
     * @param val the field's initial value. This parameter, 
     *            which may be null if the field does not have an initial value, 
     *            must be an Integer, a Float, a Long, a Double or a 
     *            String (for int, float, long or String fields respectively). 
     *            This parameter is only used for static fields. Its value is 
     *            ignored for non static fields, which must be initialized 
     *            through bytecode instructions in constructors or methods.
     */
    public FieldCreator(String name, int modifiers, AClass type, Object val) {
        this.name = name;
        this.modifiers = modifiers;
        this.type = type;
        this.value = val;
    }

    @Override
    public void create(IClassContext cv) {
    	this.context = cv;
    	NewMemberClass owner = cv.getCurrentClass();
        fe = new GlobalVariableMeta(owner, owner, type, modifiers, name);
        owner.addGlobalVariableEntity(fe);
    }
    
    @Override
    public void prepare() {
        
    }

    @Override
    public void execute() {
        context.getClassVisitor().visitField(fe.getModifiers(), name, fe.getDeclareClass().getDescription(), null, value).visitEnd();
    }

}
