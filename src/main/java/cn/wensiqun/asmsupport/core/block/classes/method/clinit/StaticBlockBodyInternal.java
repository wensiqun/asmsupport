package cn.wensiqun.asmsupport.core.block.classes.method.clinit;

import cn.wensiqun.asmsupport.core.block.classes.method.AbstractMethodBody;
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
