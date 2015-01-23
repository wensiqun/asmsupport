/**
 * 
 */
package cn.wensiqun.asmsupport.core.block.classes.method.common;

import cn.wensiqun.asmsupport.core.block.classes.method.AbstractMethodBody;
import cn.wensiqun.asmsupport.generic.method.IMethodBody;

/**
 * @author 温斯群(Joe Wen)
 * 
 */
public abstract class CommonMethodBodyInternal extends AbstractMethodBody implements IMethodBody {
    
	@Override
    public void generateBody() {
		body(argments);
    }

}
