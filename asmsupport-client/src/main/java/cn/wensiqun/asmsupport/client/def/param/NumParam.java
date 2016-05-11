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

import cn.wensiqun.asmsupport.client.def.Param;
import cn.wensiqun.asmsupport.client.def.action.*;
import cn.wensiqun.asmsupport.client.def.behavior.NumBehavior;
import cn.wensiqun.asmsupport.client.def.var.Var;
import cn.wensiqun.asmsupport.core.utils.common.BlockTracker;


/**
 * Indicate a number parameter
 * 
 * @author WSQ
 *
 */
public class NumParam extends PriorityParam implements NumBehavior {

	public NumParam(BlockTracker tracker, Param param) {
        super(tracker, param);
    }

    public NumParam(BlockTracker tracker, OperatorAction action, Param... operands) {
        super(tracker, action, operands);
    }
    
    @Override
    public NumParam add(Param para) {
        priorityStack.pushAction(new AddAction(tracker), para);
        return this;
    }

    @Override
    public NumParam sub(Param para) {
        priorityStack.pushAction(new SubAction(tracker), para);
        return this;
    }

    @Override
    public NumParam mul(Param para) {
        priorityStack.pushAction(new MulAction(tracker), para);
        return this;
    }

    @Override
    public NumParam div(Param para) {
        priorityStack.pushAction(new DivAction(tracker), para);
        return this;
    }

    @Override
    public NumParam mod(Param para) {
        priorityStack.pushAction(new ModAction(tracker), para);
        return this;
    }

    @Override
    public NumParam band(Param para) {
        priorityStack.pushAction(new BandAction(tracker), para);
        return this;
    }

    @Override
    public NumParam bor(Param para) {
        priorityStack.pushAction(new BorAction(tracker), para);
        return this;
    }

    @Override
    public NumParam bxor(Param para) {
        priorityStack.pushAction(new BxorAction(tracker), para);
        return this;
    }

    @Override
    public NumParam shl(Param para) {
        priorityStack.pushAction(new ShiftLeftAction(tracker), para);
        return this;
    }

    @Override
    public NumParam shr(Param para) {
        priorityStack.pushAction(new ShiftRightAction(tracker), para);
        return this;
    }

    @Override
    public NumParam ushr(Param para) {
        priorityStack.pushAction(new UnsignedShiftRightAction(tracker), para);
        return this;
    }

    @Override
    public BoolParam gt(Param para) {
        return new BoolParam(tracker, new GreaterThanAction(tracker), this, para);
    }

    @Override
    public BoolParam ge(Param para) {
        return new BoolParam(tracker, new GreaterEqualAction(tracker), this, para);
    }

    @Override
    public BoolParam lt(Param para) {
        return new BoolParam(tracker, new LessThanAction(tracker), this, para);
    }

    @Override
    public BoolParam le(Param para) {
        return new BoolParam(tracker, new LessEqualAction(tracker), this, para);
    }

    @Override
    public BoolParam eq(Param para) {
        return new BoolParam(tracker, new EqualAction(tracker), this, para);
    }

    @Override
    public BoolParam ne(Param para) {
        return new BoolParam(tracker, new NotEqualAction(tracker), this, para);
    }

	@Override
	public NumParam assignTo(Var var) {
        priorityStack.pushAction(new AssignAction(tracker, var), this);
        return this;
	}

}
