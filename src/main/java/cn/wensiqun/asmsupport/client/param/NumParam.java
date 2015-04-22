package cn.wensiqun.asmsupport.client.param;

import cn.wensiqun.asmsupport.client.Param;
import cn.wensiqun.asmsupport.client.action.AddAction;
import cn.wensiqun.asmsupport.client.action.BandAction;
import cn.wensiqun.asmsupport.client.action.BorAction;
import cn.wensiqun.asmsupport.client.action.BxorAction;
import cn.wensiqun.asmsupport.client.action.DivAction;
import cn.wensiqun.asmsupport.client.action.EqualAction;
import cn.wensiqun.asmsupport.client.action.GreaterEqualAction;
import cn.wensiqun.asmsupport.client.action.GreaterThanAction;
import cn.wensiqun.asmsupport.client.action.LessEqualAction;
import cn.wensiqun.asmsupport.client.action.LessThanAction;
import cn.wensiqun.asmsupport.client.action.ModAction;
import cn.wensiqun.asmsupport.client.action.MulAction;
import cn.wensiqun.asmsupport.client.action.NotEqualAction;
import cn.wensiqun.asmsupport.client.action.OperatorAction;
import cn.wensiqun.asmsupport.client.action.SubAction;
import cn.wensiqun.asmsupport.client.action.UnsignedShiftRightAction;
import cn.wensiqun.asmsupport.core.block.ProgramBlockInternal;

public class NumParam extends PriorityParam implements NumBehavior {

    public NumParam(ProgramBlockInternal block, OperatorAction action, Param... operands) {
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
