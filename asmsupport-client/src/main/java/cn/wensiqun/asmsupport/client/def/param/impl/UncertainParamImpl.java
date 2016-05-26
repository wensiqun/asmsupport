/**    
 *  Asmsupport is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package cn.wensiqun.asmsupport.client.def.param.impl;

import cn.wensiqun.asmsupport.client.def.Param;
import cn.wensiqun.asmsupport.client.def.ParamPostern;
import cn.wensiqun.asmsupport.client.def.action.*;
import cn.wensiqun.asmsupport.client.def.param.BoolParam;
import cn.wensiqun.asmsupport.client.def.param.NumParam;
import cn.wensiqun.asmsupport.client.def.param.UncertainParam;
import cn.wensiqun.asmsupport.client.def.param.basic.DummyParam;
import cn.wensiqun.asmsupport.client.def.var.Var;
import cn.wensiqun.asmsupport.core.definition.KernelParam;
import cn.wensiqun.asmsupport.core.utils.common.BlockTracker;
import cn.wensiqun.asmsupport.standard.def.clazz.IClass;

/**
 * If asmsupport can't know what's the parameter type, use this parameter
 * 
 * @author WSQ
 *
 */
public class UncertainParamImpl extends AbstractCommonParam implements UncertainParam {

    public UncertainParamImpl(BlockTracker tracker, KernelParam target) {
        super(tracker, target);
    }

    @Override
    public UncertainParam call(String methodName, Param... arguments) {
        return new UncertainParamImpl(tracker, tracker.track().call(target, methodName, ParamPostern.getTarget(arguments)));
    }

    @Override
    public UncertainParam cast(Class<?> type) {
        return new UncertainParamImpl(tracker, tracker.track().checkcast(target, type));
    }

    @Override
    public UncertainParam cast(IClass type) {
        return new UncertainParamImpl(tracker, tracker.track().checkcast(target, type));
    }

    @Override
    public BoolParam instanceof_(Class<?> type) {
        return new BoolParamImpl(tracker, new InstanceofAction(tracker, tracker.track().getType(type)), this);
    }

    @Override
    public BoolParam instanceof_(IClass type) {
        return new BoolParamImpl(tracker, new InstanceofAction(tracker, type), this);
    }

    @Override
    public NumParam add(Param para) {
        return new NumParamImpl(tracker, new AddAction(tracker), this, para);
    }

    @Override
    public NumParam sub(Param para) {
        return new NumParamImpl(tracker, new SubAction(tracker), this, para);
    }

    @Override
    public NumParam mul(Param para) {
        return new NumParamImpl(tracker, new MulAction(tracker), this, para);
    }

    @Override
    public NumParam div(Param para) {
        return new NumParamImpl(tracker, new DivAction(tracker), this, para);
    }

    @Override
    public NumParam mod(Param para) {
        return new NumParamImpl(tracker, new ModAction(tracker), this, para);
    }

    @Override
    public NumParam band(Param para) {
        return new NumParamImpl(tracker, new BandAction(tracker), this, para);
    }

    @Override
    public NumParam bor(Param para) {
        return new NumParamImpl(tracker, new BorAction(tracker), this, para);
    }

    @Override
    public NumParam bxor(Param para) {
        return new NumParamImpl(tracker, new BxorAction(tracker), this, para);
    }

    @Override
    public NumParam shl(Param para) {
        return new NumParamImpl(tracker, new ShiftLeftAction(tracker), this, para);
    }

    @Override
    public NumParam shr(Param para) {
        return new NumParamImpl(tracker, new ShiftRightAction(tracker), this, para);
    }

    @Override
    public NumParam ushr(Param para) {
        return new NumParamImpl(tracker, new UnsignedShiftRightAction(tracker), this, para);
    }

    @Override
    public BoolParam gt(Param para) {
        return new BoolParamImpl(tracker, new GreaterThanAction(tracker), this, para);
    }

    @Override
    public BoolParam ge(Param para) {
        return new BoolParamImpl(tracker, new GreaterEqualAction(tracker), this, para);
    }

    @Override
    public BoolParam lt(Param para) {
        return new BoolParamImpl(tracker, new LessThanAction(tracker), this, para);
    }

    @Override
    public BoolParam le(Param para) {
        return new BoolParamImpl(tracker, new LessEqualAction(tracker), this, para);
    }

    @Override
    public BoolParam and(Param param) {
        return new BoolParamImpl(tracker, new AndAction(tracker), this, param);
    }

    @Override
    public BoolParam or(Param param) {
        return new BoolParamImpl(tracker, new OrAction(tracker), this, param);
    }

    @Override
    public BoolParam logicAnd(Param param) {
        return new BoolParamImpl(tracker, new LogicAndAction(tracker), this, param);
    }

    @Override
    public BoolParam logicOr(Param param) {
        return new BoolParamImpl(tracker, new LogicOrAction(tracker), this, param);
    }

    @Override
    public BoolParam logicXor(Param param) {
        return new BoolParamImpl(tracker, new LogicXorAction(tracker), this, param);
    }

    @Override
    public BoolParam and(boolean param) {
        return and(new DummyParam(tracker, tracker.track().val(param)));
    }

    @Override
    public BoolParam or(boolean param) {
        return or(new DummyParam(tracker, tracker.track().val(param)));
    }

    @Override
    public BoolParam logicAnd(boolean param) {
        return logicAnd(new DummyParam(tracker, tracker.track().val(param)));
    }

    @Override
    public BoolParam logicOr(boolean param) {
        return logicOr(new DummyParam(tracker, tracker.track().val(param)));
    }

    @Override
    public BoolParam logicXor(boolean param) {
        return logicXor(new DummyParam(tracker, tracker.track().val(param)));
    }

    @Override
    public UncertainParam load(Param firstDim, Param... dims) {
        return new UncertainParamImpl(tracker, tracker.track().arrayLoad(target, firstDim.getTarget(), ParamPostern.getTarget(dims)));
    }

    @Override
    public NumParam length(Param... dims) {
        Param[] operands = ParamPostern.unionParam(new DummyParam(tracker, target), dims);
        return new NumParamImpl(tracker, new ArrayLengthAction(tracker, operands.length - 1), operands);
    }

    @Override
    public UncertainParam store(Param value, Param firstDim, Param... dims) {
        return new UncertainParamImpl(tracker, tracker.track().arrayStore(target, value.getTarget(),
                firstDim.getTarget(), ParamPostern.getTarget(dims)));
    }
    
    @Override
	public UncertainParam assignTo(Var var) {
		return new UncertainParamImpl(tracker,
                new AssignAction(tracker, var).doAction(this).getTarget());
	}
}
