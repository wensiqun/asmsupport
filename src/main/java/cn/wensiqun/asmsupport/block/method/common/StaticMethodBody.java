package cn.wensiqun.asmsupport.block.method.common;

import cn.wensiqun.asmsupport.block.body.ArgumentsBody;
import cn.wensiqun.asmsupport.block.method.GenericMethodBody;

public abstract class StaticMethodBody extends GenericMethodBody implements ArgumentsBody {
    
	@Override
    public void generateBody() {
	    body(argments);
    }
	
    @Override
	public void setReturned(boolean returned) {
		super.setReturned(returned);
	}




}
