/**
 * 
 */
package cn.wensiqun.asmsupport.block.method.common;

import cn.wensiqun.asmsupport.block.body.ArgumentsBody;
import cn.wensiqun.asmsupport.block.method.GenericMethodBody;
import cn.wensiqun.asmsupport.block.operator.KeywordVariableable;

/**
 * @author 温斯群(Joe Wen)
 * 
 */
public abstract class CommonMethodBody extends GenericMethodBody implements KeywordVariableable, ArgumentsBody {
    
	@Override
    public void generateBody() {
		body(argments);
    }

}
