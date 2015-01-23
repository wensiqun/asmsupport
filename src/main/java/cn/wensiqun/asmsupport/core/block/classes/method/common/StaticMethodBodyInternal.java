package cn.wensiqun.asmsupport.core.block.classes.method.common;

import cn.wensiqun.asmsupport.core.block.classes.method.AbstractMethodBody;
import cn.wensiqun.asmsupport.generic.method.IStaticMethodBody;

public abstract class StaticMethodBodyInternal extends AbstractMethodBody implements IStaticMethodBody {
    
	@Override
    public final void generateBody() {
	    body(argments);
    }
	

}
