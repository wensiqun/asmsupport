package cn.wensiqun.asmsupport.client.def.param;

import cn.wensiqun.asmsupport.client.def.Param;
import cn.wensiqun.asmsupport.client.operations.action.AndAction;
import cn.wensiqun.asmsupport.client.operations.action.EqualAction;
import cn.wensiqun.asmsupport.client.operations.action.LogicAndAction;
import cn.wensiqun.asmsupport.client.operations.action.LogicOrAction;
import cn.wensiqun.asmsupport.client.operations.action.LogicXorAction;
import cn.wensiqun.asmsupport.client.operations.action.NotEqualAction;
import cn.wensiqun.asmsupport.client.operations.action.OperatorAction;
import cn.wensiqun.asmsupport.client.operations.action.OrAction;
import cn.wensiqun.asmsupport.client.operations.behavior.BoolBehavior;
import cn.wensiqun.asmsupport.core.block.KernelProgramBlock;

public class BoolParam extends PriorityParam implements BoolBehavior {

    public BoolParam(KernelProgramBlock block, OperatorAction action, Param... operands) {
        super(block, action, operands);
    }

    @Override
    public BoolParam eq(Param para) {
        priorityStack.pushAction(new EqualAction(block), para);
        return this;
    }

    @Override
    public BoolParam ne(Param para) {
        priorityStack.pushAction(new NotEqualAction(block), para);
        return this;
    }

    @Override
    public BoolParam and(Param param) {
        priorityStack.pushAction(new AndAction(block), param);
        return this;
    }

    @Override
    public BoolParam or(Param param) {
        priorityStack.pushAction(new OrAction(block), param);
        return this;
    }

    @Override
    public BoolParam logicAnd(Param param) {
        priorityStack.pushAction(new LogicAndAction(block), param);
        return this;
    }

    @Override
    public BoolParam logicOr(Param param) {
        priorityStack.pushAction(new LogicOrAction(block), param);
        return this;
    }

    @Override
    public BoolParam logicXor(Param param) {
        priorityStack.pushAction(new LogicXorAction(block), param);
        return this;
    }

}
