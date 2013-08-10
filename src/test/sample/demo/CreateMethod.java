package demo;

import jw.asmsupport.clazz.AClassFactory;
import jw.asmsupport.creator.ClassCreator;
import jw.asmsupport.definition.variable.GlobalVariable;

/**
 * 
 * @author 温斯群(Joe Wen)
 *
 */
public abstract class CreateMethod {
	
	public ClassCreator creator;

	public static final GlobalVariable out = AClassFactory.getProductClass(System.class).getGlobalVariable("out");

	public CreateMethod(ClassCreator creator) {
		super();
		this.creator = creator;
	}
	
	public abstract void createMethod();

}
