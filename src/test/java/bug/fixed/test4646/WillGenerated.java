package bug.fixed.test4646;

import bug.fixed.test4646.entity.Child;
import bug.fixed.test4646.entity.ChildChild;
import bug.fixed.test4646.parent.AbstractClass;


/**
 * 
 *
 */
public class WillGenerated extends AbstractClass {

	@Override
	public ChildChild abstractClassAbstractMethod() {
		return new ChildChild();
	}

	@Override
	public ChildChild interfaceMethod() {
		return new ChildChild();
	}

	@Override
	public ChildChild abstractClassMethod() {
		return new ChildChild();
	}

	@Override
	public ChildChild interfaceReturnTypeIsChild() {
		return new ChildChild();
	}
	
	

}
