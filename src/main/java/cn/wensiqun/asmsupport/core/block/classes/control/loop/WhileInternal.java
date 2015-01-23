package cn.wensiqun.asmsupport.core.block.classes.control.loop;

import cn.wensiqun.asmsupport.core.Parameterized;
import cn.wensiqun.asmsupport.generic.loop.IWhile;

/**
 * 
 * @author 温斯群(Joe Wen)
 *
 */
public abstract class WhileInternal extends DoWhileInternal implements IWhile  {

    public WhileInternal(Parameterized condition) {
        super(condition);
    }

	@Override
	public void doExecute() {
		insnHelper.goTo(conditionLbl);
		super.doExecute();
	}



	@Override
	public String toString() {
		return "Do While Block : " + super.toString();
	}
    
}
