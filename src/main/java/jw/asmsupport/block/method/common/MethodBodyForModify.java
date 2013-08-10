/**
 * 
 */
package jw.asmsupport.block.method.common;

import java.util.List;

import jw.asmsupport.asm.proxy.VisitXInsnAdapter;
import jw.asmsupport.block.operator.ThisVariableable;
import jw.asmsupport.clazz.AClass;
import jw.asmsupport.entity.MethodEntity;
import jw.asmsupport.utils.ASConstant;

/**
 * @author
 * 
 */
public abstract class MethodBodyForModify extends StaticMethodBody implements ThisVariableable {
    
	private List<VisitXInsnAdapter> superConstructorOperators;
	
    public AClass getOriginalMethodReturnClass(){
    	return method.getMethodEntity().getReturnClass();
    }
    
	public void setSuperConstructorOperators(
			List<VisitXInsnAdapter> superConstructorOperators) {
		this.superConstructorOperators = superConstructorOperators;
	}

	@Override
    public void generateBody() {
		MethodEntity me = method.getMethodEntity();
		if(me.getName().equals(ASConstant.INIT)){
			if(superConstructorOperators != null){
			    for(VisitXInsnAdapter visitXInsnAdapter : superConstructorOperators){
			    	visitXInsnAdapter.newVisitXInsnOperator(getExecuteBlock());
			    }
			}
		}
		super.generateBody();
    }
}
