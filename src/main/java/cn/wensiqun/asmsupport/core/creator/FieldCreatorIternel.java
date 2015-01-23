package cn.wensiqun.asmsupport.core.creator;

import cn.wensiqun.asmsupport.core.clazz.AClass;
import cn.wensiqun.asmsupport.core.clazz.NewMemberClass;
import cn.wensiqun.asmsupport.core.definition.variable.meta.GlobalVariableMeta;
import cn.wensiqun.asmsupport.generic.creator.IClassContext;

/**
 * 
 * @author 温斯群(Joe Wen)
 *
 */
public class FieldCreatorIternel implements IFieldCreator {

    private String name;
    private int modifiers;
    private AClass fieldClass;

    private GlobalVariableMeta fe;
    private IClassContext context;
    
    /**
     * 
     * @param name
     * @param modifiers
     * @param fieldClass
     */
    public FieldCreatorIternel(String name, int modifiers, AClass fieldClass) {
        super();
        this.name = name;
        this.modifiers = modifiers;
        this.fieldClass = fieldClass;
    }

    @Override
    public void create(IClassContext cv) {
    	this.context = cv;
    	NewMemberClass owner = cv.getCurrentClass();
        fe = new GlobalVariableMeta(owner, owner, fieldClass, modifiers, name);
        owner.addGlobalVariableEntity(fe);
    }
    
    @Override
    public void prepare() {
        
    }

    @Override
    public void execute() {
        context.getClassVisitor().visitField(fe.getModifiers(), name, fe.getDeclareClass().getDescription(), null, null).visitEnd();
    }

}
