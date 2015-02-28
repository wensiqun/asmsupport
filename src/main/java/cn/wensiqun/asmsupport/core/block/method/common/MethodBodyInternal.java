/**
 * 
 */
package cn.wensiqun.asmsupport.core.block.method.common;

import cn.wensiqun.asmsupport.core.block.method.AbstractMethodBody;
import cn.wensiqun.asmsupport.standard.method.IMethodBody;

/**
 * @author 温斯群(Joe Wen)
 * 
 */
public abstract class MethodBodyInternal extends AbstractMethodBody implements IMethodBody {
    
	@Override
    public void generateBody() {
		body(argments);
    }

}
