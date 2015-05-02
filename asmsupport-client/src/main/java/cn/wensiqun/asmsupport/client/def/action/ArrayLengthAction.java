package cn.wensiqun.asmsupport.client.def.action;

import cn.wensiqun.asmsupport.client.block.KernelProgramBlockCursor;
import cn.wensiqun.asmsupport.client.def.Param;
import cn.wensiqun.asmsupport.client.def.ParamPostern;
import cn.wensiqun.asmsupport.client.def.param.DummyParam;
import cn.wensiqun.asmsupport.core.operator.Operator;
import cn.wensiqun.asmsupport.org.apache.commons.collections.ArrayStack;

/**
 * Get array length action
 * 
 * @author WSQ
 *
 */
public class ArrayLengthAction extends OperatorAction {

    private int dimSize;
    
    public ArrayLengthAction(KernelProgramBlockCursor cursor, int dimSize) {
        super(cursor, Operator.COMMON);
        this.dimSize = dimSize;
    }

    @Override
    public Param doAction(ArrayStack<Param> operands) {
        Param[] dims = new Param[dimSize];
        for(int i=0; i<dimSize; i++) {
            dims[dimSize - i - 1] = operands.pop();
        }
        return new DummyParam(cursor, cursor.getPointer().arrayLength(ParamPostern.getTarget(operands.pop()), ParamPostern.getTarget(dims)));
    }

    @Override
    public Param doAction(Param... operands) {
        return new DummyParam(cursor, cursor.getPointer().arrayLength(ParamPostern.getTarget(operands[0]), 
                ParamPostern.getTarget(operands, 1)));
    }

}
