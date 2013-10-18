package cn.wensiqun.asmsupport.block.method.common;

import cn.wensiqun.asmsupport.block.method.SuperMethodBody;
import cn.wensiqun.asmsupport.definition.variable.LocalVariable;

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
