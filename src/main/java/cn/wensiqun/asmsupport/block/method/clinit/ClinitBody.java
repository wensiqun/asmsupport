package cn.wensiqun.asmsupport.block.method.clinit;

import cn.wensiqun.asmsupport.block.body.Body;
import cn.wensiqun.asmsupport.block.method.GenericMethodBody;

public abstract class ClinitBody extends GenericMethodBody implements IClnitBody, Body {

	@Override
	protected void init() {
		return;
	}
    
    @Override
    public final void generateBody() {
        body();
    }
	
}
