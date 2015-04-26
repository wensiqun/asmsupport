package cn.wensiqun.asmsupport.client.def.param;

import cn.wensiqun.asmsupport.client.block.KernelProgramBlockCursor;
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
import cn.wensiqun.asmsupport.core.definition.value.Value;

public class BoolParam extends PriorityParam implements BoolBehavior {

	public BoolParam(KernelProgramBlockCursor cursor, Param param) {
        super(cursor, param);
    }
	
    public BoolParam(KernelProgramBlockCursor cursor, OperatorAction action, Param... operands) {
        super(cursor, action, operands);
    }

    @Override
    public BoolParam eq(Param para) {
        priorityStack.pushAction(new EqualAction(cursor), para);
        return this;
    }

    @Override
    public BoolParam ne(Param para) {
        priorityStack.pushAction(new NotEqualAction(cursor), para);
        return this;
    }

    @Override
    public BoolParam and(Param param) {
        priorityStack.pushAction(new AndAction(cursor), param);
        return this;
    }

    @Override
    public BoolParam or(Param param) {
        priorityStack.pushAction(new OrAction(cursor), param);
        return this;
    }

    @Override
    public BoolParam logicAnd(Param param) {
        priorityStack.pushAction(new LogicAndAction(cursor), param);
        return this;
    }

    @Override
    public BoolParam logicOr(Param param) {
        priorityStack.pushAction(new LogicOrAction(cursor), param);
        return this;
    }

    @Override
    public BoolParam logicXor(Param param) {
        priorityStack.pushAction(new LogicXorAction(cursor), param);
        return this;
    }

    @Override
    public BoolParam and(boolean param) {
        return and(new DummyParam(cursor, Value.value(param)));
    }

    @Override
    public BoolParam or(boolean param) {
        return or(new DummyParam(cursor, Value.value(param)));
    }

    @Override
    public BoolParam logicAnd(boolean param) {
        return logicAnd(new DummyParam(cursor, Value.value(param)));
    }

    @Override
    public BoolParam logicOr(boolean param) {
        return logicOr(new DummyParam(cursor, Value.value(param)));
    }

    @Override
    public BoolParam logicXor(boolean param) {
        return logicXor(new DummyParam(cursor, Value.value(param)));
    }

}
