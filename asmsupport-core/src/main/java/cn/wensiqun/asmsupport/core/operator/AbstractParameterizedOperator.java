package cn.wensiqun.asmsupport.core.operator;

import cn.wensiqun.asmsupport.core.block.KernelProgramBlock;
import cn.wensiqun.asmsupport.core.definition.KernelParameterized;
import cn.wensiqun.asmsupport.core.definition.variable.GlobalVariable;

public abstract class AbstractParameterizedOperator extends AbstractOperator implements KernelParameterized{

    protected AbstractParameterizedOperator(KernelProgramBlock block, Operator operatorSymbol) {
        super(block, operatorSymbol);
    }

    @Override
    public GlobalVariable field(String name) {
        throw new UnsupportedOperationException();
    }


}
