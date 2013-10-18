/**
 * 
 */
package cn.wensiqun.asmsupport.block.method.common;

import java.util.List;

import cn.wensiqun.asmsupport.asm.adapter.VisitXInsnAdapter;
import cn.wensiqun.asmsupport.block.operator.ThisVariableable;
import cn.wensiqun.asmsupport.clazz.AClass;
import cn.wensiqun.asmsupport.entity.MethodEntity;
import cn.wensiqun.asmsupport.utils.ASConstant;

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
			//如果是构造方法，将被修改的构造方法中调用父类构造方法的那段字节码转移到新的构造方法中。
			if(superConstructorOperators != null){
			    for(VisitXInsnAdapter visitXInsnAdapter : superConstructorOperators){
			    	visitXInsnAdapter.newVisitXInsnOperator(getExecuteBlock());
			    }
			}
		}
		super.generateBody();
    }
}
