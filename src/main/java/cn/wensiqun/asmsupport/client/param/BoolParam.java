package cn.wensiqun.asmsupport.client.param;

import cn.wensiqun.asmsupport.client.Param;
import cn.wensiqun.asmsupport.client.action.AndAction;
import cn.wensiqun.asmsupport.client.action.EqualAction;
import cn.wensiqun.asmsupport.client.action.LogicAndAction;
import cn.wensiqun.asmsupport.client.action.LogicOrAction;
import cn.wensiqun.asmsupport.client.action.LogicXorAction;
import cn.wensiqun.asmsupport.client.action.NotEqualAction;
import cn.wensiqun.asmsupport.client.action.OperatorAction;
import cn.wensiqun.asmsupport.client.action.OrAction;
import cn.wensiqun.asmsupport.core.block.ProgramBlockInternal;

public class BoolParam extends PriorityParam implements BoolBehavior {

    public BoolParam(ProgramBlockInternal block, OperatorAction action, Param... operands) {
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
