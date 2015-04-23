package cn.wensiqun.asmsupport.client.operations.action;

import cn.wensiqun.asmsupport.client.def.Param;
import cn.wensiqun.asmsupport.client.def.ParamPostern;
import cn.wensiqun.asmsupport.client.def.param.DummyParam;
import cn.wensiqun.asmsupport.core.block.KernelProgramBlock;
import cn.wensiqun.asmsupport.core.operator.Operator;
import cn.wensiqun.asmsupport.org.apache.commons.collections.ArrayStack;

public class ArrayLengthAction extends OperatorAction {

    private int dimSize;
    
    public ArrayLengthAction(KernelProgramBlock block, int dimSize) {
        super(block, Operator.CONDITION_AND);
        this.dimSize = dimSize;
    }

    @Override
    public Param doAction(ArrayStack<Param> operands) {
        Param[] dims = new Param[dimSize];
        for(int i=0; i<dimSize; i++) {
            dims[dimSize - i - 1] = operands.pop();
        }
        return new DummyParam(block, block.arrayLength(ParamPostern.getTarget(operands.pop()), ParamPostern.getTarget(dims)));
    }

    @Override
    public Param doAction(Param... operands) {
        return new DummyParam(block, block.arrayLength(ParamPostern.getTarget(operands[0]), 
                ParamPostern.getTarget(operands, 1)));
    }

}
