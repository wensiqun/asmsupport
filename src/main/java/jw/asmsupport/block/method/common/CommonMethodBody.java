/**
 * 
 */
package jw.asmsupport.block.method.common;

import jw.asmsupport.block.method.SuperMethodBody;
import jw.asmsupport.block.operator.ThisVariableable;
import jw.asmsupport.definition.variable.LocalVariable;

/**
 * @author 温斯群(Joe Wen)
 * 
 */
public abstract class CommonMethodBody extends SuperMethodBody implements ThisVariableable {
    
	@Override
    public final void generateBody() {
		generateBody(argments);
    }

    public abstract void generateBody(LocalVariable... argus);
}
