package cn.wensiqun.asmsupport.core.operator.logical;


import cn.wensiqun.asmsupport.core.Parameterized;
import cn.wensiqun.asmsupport.core.block.classes.common.ProgramBlockInternal;
import cn.wensiqun.asmsupport.org.objectweb.asm.Label;


/**
 * 
 * @author 温斯群(Joe Wen)
 *
 */
public abstract class ConditionOperator extends BinaryLogical {

    protected Label trueLbl;
    protected Label falseLbl;
    
    protected ConditionOperator(ProgramBlockInternal block, Parameterized factor1, Parameterized factor2) {
        super(block, factor1, factor2);
        falseLbl = new Label();
        trueLbl = new Label();
    }
    
    @Override
    protected void doExecute() {
        executingProcess();
        block.getMethod().getStack().printState();
    }
}
