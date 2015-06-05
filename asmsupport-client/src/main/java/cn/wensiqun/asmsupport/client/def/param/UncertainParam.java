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
package cn.wensiqun.asmsupport.client.def.param;

import cn.wensiqun.asmsupport.client.block.KernelProgramBlockCursor;
import cn.wensiqun.asmsupport.client.def.Param;
import cn.wensiqun.asmsupport.client.def.ParamPostern;
import cn.wensiqun.asmsupport.client.def.action.AddAction;
import cn.wensiqun.asmsupport.client.def.action.AndAction;
import cn.wensiqun.asmsupport.client.def.action.ArrayLengthAction;
import cn.wensiqun.asmsupport.client.def.action.AssignAction;
import cn.wensiqun.asmsupport.client.def.action.BandAction;
import cn.wensiqun.asmsupport.client.def.action.BorAction;
import cn.wensiqun.asmsupport.client.def.action.BxorAction;
import cn.wensiqun.asmsupport.client.def.action.DivAction;
import cn.wensiqun.asmsupport.client.def.action.GreaterEqualAction;
import cn.wensiqun.asmsupport.client.def.action.GreaterThanAction;
import cn.wensiqun.asmsupport.client.def.action.InstanceofAction;
import cn.wensiqun.asmsupport.client.def.action.LessEqualAction;
import cn.wensiqun.asmsupport.client.def.action.LessThanAction;
import cn.wensiqun.asmsupport.client.def.action.LogicAndAction;
import cn.wensiqun.asmsupport.client.def.action.LogicOrAction;
import cn.wensiqun.asmsupport.client.def.action.LogicXorAction;
import cn.wensiqun.asmsupport.client.def.action.ModAction;
import cn.wensiqun.asmsupport.client.def.action.MulAction;
import cn.wensiqun.asmsupport.client.def.action.OrAction;
import cn.wensiqun.asmsupport.client.def.action.ShiftLeftAction;
import cn.wensiqun.asmsupport.client.def.action.ShiftRightAction;
import cn.wensiqun.asmsupport.client.def.action.SubAction;
import cn.wensiqun.asmsupport.client.def.action.UnsignedShiftRightAction;
import cn.wensiqun.asmsupport.client.def.behavior.UncertainBehavior;
import cn.wensiqun.asmsupport.client.def.var.Var;
import cn.wensiqun.asmsupport.core.definition.KernelParam;
import cn.wensiqun.asmsupport.core.definition.value.Value;
import cn.wensiqun.asmsupport.standard.def.clazz.AClass;

/**
 * If asmsupport can't know what's the parameter type, use this parameter
 * 
 * @author WSQ
 *
 */
public class UncertainParam extends CommonParam implements UncertainBehavior {

    public UncertainParam(KernelProgramBlockCursor cursor, KernelParam target) {
        super(cursor, target);
    }

    @Override
    public UncertainParam call(String methodName, Param... arguments) {
        return new UncertainParam(cursor, cursor.getPointer().call(target, methodName, ParamPostern.getTarget(arguments)));
    }

    @Override
    public UncertainParam cast(Class<?> type) {
        return new UncertainParam(cursor, cursor.getPointer().checkcast(target, type));
    }

    @Override
    public UncertainParam cast(AClass type) {
        return new UncertainParam(cursor, cursor.getPointer().checkcast(target, type));
    }

    @Override
    public BoolParam instanceof_(Class<?> type) {
        return new BoolParam(cursor, new InstanceofAction(cursor, cursor.getPointer().getType(type)), this);
    }

    @Override
    public BoolParam instanceof_(AClass type) {
        return new BoolParam(cursor, new InstanceofAction(cursor, type), this);
    }

    @Override
    public NumParam add(Param para) {
        return new NumParam(cursor, new AddAction(cursor), this, para);
    }

    @Override
    public NumParam sub(Param para) {
        return new NumParam(cursor, new SubAction(cursor), this, para);
    }

    @Override
    public NumParam mul(Param para) {
        return new NumParam(cursor, new MulAction(cursor), this, para);
    }

    @Override
    public NumParam div(Param para) {
        return new NumParam(cursor, new DivAction(cursor), this, para);
    }

    @Override
    public NumParam mod(Param para) {
        return new NumParam(cursor, new ModAction(cursor), this, para);
    }

    @Override
    public NumParam band(Param para) {
        return new NumParam(cursor, new BandAction(cursor), this, para);
    }

    @Override
    public NumParam bor(Param para) {
        return new NumParam(cursor, new BorAction(cursor), this, para);
    }

    @Override
    public NumParam bxor(Param para) {
        return new NumParam(cursor, new BxorAction(cursor), this, para);
    }

    @Override
    public NumParam shl(Param para) {
        return new NumParam(cursor, new ShiftLeftAction(cursor), this, para);
    }

    @Override
    public NumParam shr(Param para) {
        return new NumParam(cursor, new ShiftRightAction(cursor), this, para);
    }

    @Override
    public NumParam ushr(Param para) {
        return new NumParam(cursor, new UnsignedShiftRightAction(cursor), this, para);
    }

    @Override
    public BoolParam gt(Param para) {
        return new BoolParam(cursor, new GreaterThanAction(cursor), this, para);
    }

    @Override
    public BoolParam ge(Param para) {
        return new BoolParam(cursor, new GreaterEqualAction(cursor), this, para);
    }

    @Override
    public BoolParam lt(Param para) {
        return new BoolParam(cursor, new LessThanAction(cursor), this, para);
    }

    @Override
    public BoolParam le(Param para) {
        return new BoolParam(cursor, new LessEqualAction(cursor), this, para);
    }

    @Override
    public BoolParam and(Param param) {
        return new BoolParam(cursor, new AndAction(cursor), this, param);
    }

    @Override
    public BoolParam or(Param param) {
        return new BoolParam(cursor, new OrAction(cursor), this, param);
    }

    @Override
    public BoolParam logicAnd(Param param) {
        return new BoolParam(cursor, new LogicAndAction(cursor), this, param);
    }

    @Override
    public BoolParam logicOr(Param param) {
        return new BoolParam(cursor, new LogicOrAction(cursor), this, param);
    }

    @Override
    public BoolParam logicXor(Param param) {
        return new BoolParam(cursor, new LogicXorAction(cursor), this, param);
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

    @Override
    public UncertainParam load(Param firstDim, Param... dims) {
        return new UncertainParam(cursor, cursor.getPointer().arrayLoad(target, ParamPostern.getTarget(firstDim), ParamPostern.getTarget(dims)));
    }

    @Override
    public NumParam length(Param... dims) {
        Param[] operands = ParamPostern.unionParam(new DummyParam(cursor, target), dims);
        return new NumParam(cursor, new ArrayLengthAction(cursor, operands.length - 1), operands);
    }

    @Override
    public UncertainParam store(Param value, Param firstDim, Param... dims) {
        return new UncertainParam(cursor, cursor.getPointer().arrayStore(target, ParamPostern.getTarget(value), 
                ParamPostern.getTarget(firstDim), ParamPostern.getTarget(dims)));
    }
    
    @Override
	public UncertainParam assign(Var var) {
		return new UncertainParam(cursor, 
				ParamPostern.getTarget(new AssignAction(cursor, var).doAction(this)));
	}
}
