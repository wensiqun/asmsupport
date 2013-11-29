package bug.fixed.test4646.parent;

import bug.fixed.test4646.entity.Child;

public abstract class AbstractClass implements Interface {

	@Override
	public Child interfaceMethod() {
		return new Child();
	}
	

	public Child abstractClassMethod(){
		return new Child();
	};

	@Override
	public Child interfaceReturnTypeIsChild() {
		return new Child();
	}

	public abstract Child abstractClassAbstractMethod();
	
	
	
}
