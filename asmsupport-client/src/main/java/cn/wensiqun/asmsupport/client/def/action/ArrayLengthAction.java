package cn.wensiqun.asmsupport.client.def.action;

import cn.wensiqun.asmsupport.client.def.Param;
import cn.wensiqun.asmsupport.client.def.ParamPostern;
import cn.wensiqun.asmsupport.client.def.param.DummyParam;
import cn.wensiqun.asmsupport.core.operator.Operator;
import cn.wensiqun.asmsupport.core.utils.common.BlockTracker;
import cn.wensiqun.asmsupport.org.apache.commons.collections.ArrayStack;

/**
 * Get array length action
 * 
 * @author WSQ
 *
 */
public class ArrayLengthAction extends OperatorAction {

    private int dimSize;
    
    public ArrayLengthAction(BlockTracker tracker, int dimSize) {
        super(tracker, Operator.COMMON);
        this.dimSize = dimSize;
    }

    @Override
    public Param doAction(ArrayStack<Param> operands) {
        Param[] dims = new Param[dimSize];
        for(int i=0; i<dimSize; i++) {
            dims[dimSize - i - 1] = operands.pop();
        }
        return new DummyParam(tracker, tracker.track().arrayLength(ParamPostern.getTarget(operands.pop()), ParamPostern.getTarget(dims)));
    }

    @Override
    public Param doAction(Param... operands) {
        return new DummyParam(tracker, tracker.track().arrayLength(ParamPostern.getTarget(operands[0]),
                ParamPostern.getTarget(operands, 1)));
    }

}
