package cn.wensiqun.asmsupport.core.block.method.common;

import cn.wensiqun.asmsupport.core.block.method.AbstractMethodBody;
import cn.wensiqun.asmsupport.standard.method.IStaticMethodBody;

public abstract class StaticMethodBodyInternal extends AbstractMethodBody implements IStaticMethodBody {
    
	@Override
    public final void generateBody() {
	    body(argments);
    }
	

}
