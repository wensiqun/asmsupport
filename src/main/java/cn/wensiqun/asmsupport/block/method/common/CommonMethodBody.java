/**
 * 
 */
package cn.wensiqun.asmsupport.block.method.common;

import cn.wensiqun.asmsupport.block.method.GenericMethodBody;
import cn.wensiqun.asmsupport.block.operator.ThisVariableable;
import cn.wensiqun.asmsupport.definition.variable.LocalVariable;

/**
 * @author 温斯群(Joe Wen)
 * 
 */
public abstract class CommonMethodBody extends GenericMethodBody implements ThisVariableable {
    
	@Override
    public final void generateBody() {
		generateBody(argments);
    }

    public abstract void generateBody(LocalVariable... argus);
}
