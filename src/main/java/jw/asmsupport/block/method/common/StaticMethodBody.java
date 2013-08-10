package jw.asmsupport.block.method.common;

import jw.asmsupport.block.method.SuperMethodBody;
import jw.asmsupport.definition.variable.LocalVariable;

public abstract class StaticMethodBody extends SuperMethodBody {
	
	@Override
    public void generateBody() {
		generateBody(argments);
    }

	
    @Override
	public void setReturned(boolean returned) {
		super.setReturned(returned);
	}



	public abstract void generateBody(LocalVariable... argus);

}
