package cn.wensiqun.asmsupport.client.operations.action;

import cn.wensiqun.asmsupport.client.def.Param;
import cn.wensiqun.asmsupport.core.block.KernelProgramBlock;
import cn.wensiqun.asmsupport.core.operator.Operator;
import cn.wensiqun.asmsupport.org.apache.commons.collections.ArrayStack;
import cn.wensiqun.asmsupport.standard.exception.ASMSupportException;

public abstract class AbstractBinaryAction extends OperatorAction {

    public AbstractBinaryAction(KernelProgramBlock block, Operator operator) {
        super(block, operator);
    }
    
    @Override
    public final Param doAction(ArrayStack<Param> operands) {
        try {
            Param fac2 = operands.pop();
            Param fac1 = operands.pop();
            return doAction(fac1, fac2);
        } catch (Exception e) {
            throw new ASMSupportException("Error while do operator : '" + getOperator().getSymbol() + "'");
        }
    }

}
