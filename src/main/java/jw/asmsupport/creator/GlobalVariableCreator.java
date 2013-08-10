package jw.asmsupport.creator;

import jw.asmsupport.clazz.AClass;
import jw.asmsupport.clazz.NewMemberClass;
import jw.asmsupport.clazz.ProductClass;
import jw.asmsupport.clazz.SemiClass;
import jw.asmsupport.entity.GlobalVariableEntity;

/**
 * 
 * @author 温斯群(Joe Wen)
 *
 */
public class GlobalVariableCreator implements IGlobalVariableCreator {

    private String name;
    private int modifiers;
    private AClass fieldClass;

    private GlobalVariableEntity fe;
    private IClassContext context;
    
    /**
     * 
     * @param name
     * @param modifiers
     * @param fieldClass
     */
    public GlobalVariableCreator(String name, int modifiers, AClass fieldClass) {
        super();
        this.name = name;
        this.modifiers = modifiers;
        this.fieldClass = fieldClass;
    }

    @Override
    public void create(IClassContext cv, SemiClass owner) {
		create(cv, (NewMemberClass)owner);
    }

	@Override
	public void create(IClassContext cv, ProductClass owner) {
		create(cv, (NewMemberClass)owner);
	}
	
	private void create(IClassContext cv, NewMemberClass owner){
        this.context = cv;
        fe = new GlobalVariableEntity(owner, fieldClass, modifiers, name);
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
