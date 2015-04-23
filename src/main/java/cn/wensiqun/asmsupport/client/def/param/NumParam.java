package cn.wensiqun.asmsupport.client.def.param;

import cn.wensiqun.asmsupport.client.def.Param;
import cn.wensiqun.asmsupport.client.operations.action.AddAction;
import cn.wensiqun.asmsupport.client.operations.action.BandAction;
import cn.wensiqun.asmsupport.client.operations.action.BorAction;
import cn.wensiqun.asmsupport.client.operations.action.BxorAction;
import cn.wensiqun.asmsupport.client.operations.action.DivAction;
import cn.wensiqun.asmsupport.client.operations.action.EqualAction;
import cn.wensiqun.asmsupport.client.operations.action.GreaterEqualAction;
import cn.wensiqun.asmsupport.client.operations.action.GreaterThanAction;
import cn.wensiqun.asmsupport.client.operations.action.LessEqualAction;
import cn.wensiqun.asmsupport.client.operations.action.LessThanAction;
import cn.wensiqun.asmsupport.client.operations.action.ModAction;
import cn.wensiqun.asmsupport.client.operations.action.MulAction;
import cn.wensiqun.asmsupport.client.operations.action.NotEqualAction;
import cn.wensiqun.asmsupport.client.operations.action.OperatorAction;
import cn.wensiqun.asmsupport.client.operations.action.SubAction;
import cn.wensiqun.asmsupport.client.operations.action.UnsignedShiftRightAction;
import cn.wensiqun.asmsupport.client.operations.behavior.NumBehavior;
import cn.wensiqun.asmsupport.core.block.KernelProgramBlock;

public class NumParam extends PriorityParam implements NumBehavior {

    public NumParam(KernelProgramBlock block, OperatorAction action, Param... operands) {
        super(block, action, operands);
    }
    
    @Override
    public NumParam add(Param para) {
        priorityStack.pushAction(new AddAction(block), para);
        return this;
    }

    @Override
    public NumParam sub(Param para) {
        priorityStack.pushAction(new SubAction(block), para);
        return this;
    }

    @Override
    public NumParam mul(Param para) {
        priorityStack.pushAction(new MulAction(block), para);
        return this;
    }

    @Override
    public NumParam div(Param para) {
        priorityStack.pushAction(new DivAction(block), para);
        return this;
    }

    @Override
    public NumParam mod(Param para) {
        priorityStack.pushAction(new ModAction(block), para);
        return this;
    }

    @Override
    public NumParam band(Param para) {
        priorityStack.pushAction(new BandAction(block), para);
        return this;
    }

    @Override
    public NumParam bor(Param para) {
        priorityStack.pushAction(new BorAction(block), para);
        return this;
    }

    @Override
    public NumParam bxor(Param para) {
        priorityStack.pushAction(new BxorAction(block), para);
        return this;
    }

    @Override
    public NumParam shl(Param para) {
        priorityStack.pushAction(new BxorAction(block), para);
        return this;
    }

    @Override
    public NumParam shr(Param para) {
        priorityStack.pushAction(new BxorAction(block), para);
        return this;
    }

    @Override
    public NumParam ushr(Param para) {
        priorityStack.pushAction(new UnsignedShiftRightAction(block), para);
        return this;
    }

    @Override
    public BoolParam gt(Param para) {
        return new BoolParam(block, new GreaterThanAction(block), this, para);
    }

    @Override
    public BoolParam ge(Param para) {
        return new BoolParam(block, new GreaterEqualAction(block), this, para);
    }

    @Override
    public BoolParam lt(Param para) {
        return new BoolParam(block, new LessThanAction(block), this, para);
    }

    @Override
    public BoolParam le(Param para) {
        return new BoolParam(block, new LessEqualAction(block), this, para);
    }

    @Override
    public BoolParam eq(Param para) {
        return new BoolParam(block, new EqualAction(block), this, para);
    }

    @Override
    public BoolParam ne(Param para) {
        return new BoolParam(block, new NotEqualAction(block), this, para);
    }
}
