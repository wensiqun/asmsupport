package cn.wensiqun.asmsupport.core.block.method.clinit;

import cn.wensiqun.asmsupport.core.block.method.AbstractMethodBody;
import cn.wensiqun.asmsupport.standard.method.IStaticBlockBody;

public abstract class StaticBlockBodyInternal extends AbstractMethodBody implements IStaticBlockBody {

	@Override
	protected void init() {
		return;
	}
    
    @Override
    public final void generateBody() {
        body();
    }
	
}
