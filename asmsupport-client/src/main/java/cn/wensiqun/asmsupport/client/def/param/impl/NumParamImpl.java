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
import cn.wensiqun.asmsupport.client.def.action.*;
import cn.wensiqun.asmsupport.client.def.param.BoolParam;
import cn.wensiqun.asmsupport.client.def.param.NumParam;
import cn.wensiqun.asmsupport.client.def.param.basic.PriorityParam;
import cn.wensiqun.asmsupport.client.def.var.Var;
import cn.wensiqun.asmsupport.core.utils.common.BlockTracker;


/**
 * Indicate a number parameter
 * 
 * @author WSQ
 *
 */
public class NumParamImpl extends PriorityParam implements NumParam {

	public NumParamImpl(BlockTracker tracker, Param param) {
        super(tracker, param);
    }

    public NumParamImpl(BlockTracker tracker, OperatorAction action, Param... operands) {
        super(tracker, action, operands);
    }
    
    @Override
    public NumParam add(Param para) {
        priorityStack.pushAction(new AddAction(clientBridge), para);
        return this;
    }

    @Override
    public NumParam sub(Param para) {
        priorityStack.pushAction(new SubAction(clientBridge), para);
        return this;
    }

    @Override
    public NumParam mul(Param para) {
        priorityStack.pushAction(new MulAction(clientBridge), para);
        return this;
    }

    @Override
    public NumParam div(Param para) {
        priorityStack.pushAction(new DivAction(clientBridge), para);
        return this;
    }

    @Override
    public NumParam mod(Param para) {
        priorityStack.pushAction(new ModAction(clientBridge), para);
        return this;
    }

    @Override
    public NumParam band(Param para) {
        priorityStack.pushAction(new BandAction(clientBridge), para);
        return this;
    }

    @Override
    public NumParam bor(Param para) {
        priorityStack.pushAction(new BorAction(clientBridge), para);
        return this;
    }

    @Override
    public NumParam bxor(Param para) {
        priorityStack.pushAction(new BxorAction(clientBridge), para);
        return this;
    }

    @Override
    public NumParam shl(Param para) {
        priorityStack.pushAction(new ShiftLeftAction(clientBridge), para);
        return this;
    }

    @Override
    public NumParam shr(Param para) {
        priorityStack.pushAction(new ShiftRightAction(clientBridge), para);
        return this;
    }

    @Override
    public NumParam ushr(Param para) {
        priorityStack.pushAction(new UnsignedShiftRightAction(clientBridge), para);
        return this;
    }

    @Override
    public BoolParam gt(Param para) {
        return new BoolParamImpl(clientBridge, new GreaterThanAction(clientBridge), this, para);
    }

    @Override
    public BoolParam ge(Param para) {
        return new BoolParamImpl(clientBridge, new GreaterEqualAction(clientBridge), this, para);
    }

    @Override
    public BoolParam lt(Param para) {
        return new BoolParamImpl(clientBridge, new LessThanAction(clientBridge), this, para);
    }

    @Override
    public BoolParam le(Param para) {
        return new BoolParamImpl(clientBridge, new LessEqualAction(clientBridge), this, para);
    }

    @Override
    public BoolParam eq(Param para) {
        return new BoolParamImpl(clientBridge, new EqualAction(clientBridge), this, para);
    }

    @Override
    public BoolParam ne(Param para) {
        return new BoolParamImpl(clientBridge, new NotEqualAction(clientBridge), this, para);
    }

	@Override
	public NumParam assignTo(Var var) {
        priorityStack.pushAction(new AssignAction(clientBridge, var), this);
        return this;
	}

}
