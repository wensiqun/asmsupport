package cn.wensiqun.asmsupport.client.action;

import cn.wensiqun.asmsupport.client.Param;
import cn.wensiqun.asmsupport.client.ParamPostern;
import cn.wensiqun.asmsupport.client.def.DummyParam;
import cn.wensiqun.asmsupport.core.block.ProgramBlockInternal;
import cn.wensiqun.asmsupport.core.operator.Operator;
import cn.wensiqun.asmsupport.org.apache.commons.collections.ArrayStack;

public class ArrayLengthAction extends OperatorAction {

    private int dimSize;
    
    public ArrayLengthAction(ProgramBlockInternal block, int dimSize) {
        super(block, Operator.CONDITION_AND);
        this.dimSize = dimSize;
    }

    @Override
    public Param doAction(ArrayStack<Param> operands) {
        Param[] dims = new Param[dimSize];
        for(int i=0; i<dimSize; i++) {
            dims[dimSize - i - 1] = operands.pop();
        }
        return new DummyParam(block.arrayLength(ParamPostern.getTarget(operands.pop()), ParamPostern.getTarget(dims)));
    }

}
