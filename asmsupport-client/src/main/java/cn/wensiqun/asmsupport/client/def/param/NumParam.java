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
import cn.wensiqun.asmsupport.client.def.action.BandAction;
import cn.wensiqun.asmsupport.client.def.action.BorAction;
import cn.wensiqun.asmsupport.client.def.action.BxorAction;
import cn.wensiqun.asmsupport.client.def.action.DivAction;
import cn.wensiqun.asmsupport.client.def.action.EqualAction;
import cn.wensiqun.asmsupport.client.def.action.GreaterEqualAction;
import cn.wensiqun.asmsupport.client.def.action.GreaterThanAction;
import cn.wensiqun.asmsupport.client.def.action.LessEqualAction;
import cn.wensiqun.asmsupport.client.def.action.LessThanAction;
import cn.wensiqun.asmsupport.client.def.action.ModAction;
import cn.wensiqun.asmsupport.client.def.action.MulAction;
import cn.wensiqun.asmsupport.client.def.action.NotEqualAction;
import cn.wensiqun.asmsupport.client.def.action.OperatorAction;
import cn.wensiqun.asmsupport.client.def.action.ShiftLeftAction;
import cn.wensiqun.asmsupport.client.def.action.ShiftRightAction;
import cn.wensiqun.asmsupport.client.def.action.SubAction;
import cn.wensiqun.asmsupport.client.def.action.UnsignedShiftRightAction;
import cn.wensiqun.asmsupport.client.def.behavior.NumBehavior;
import cn.wensiqun.asmsupport.client.def.behavior.ObjectBehavior;

public class NumParam extends PriorityParam implements NumBehavior {

	public NumParam(KernelProgramBlockCursor cursor, Param param) {
        super(cursor, param);
    }

    public NumParam(KernelProgramBlockCursor cursor, OperatorAction action, Param... operands) {
        super(cursor, action, operands);
    }
    
    @Override
    public NumParam add(Param para) {
        priorityStack.pushAction(new AddAction(cursor), para);
        return this;
    }

    @Override
    public NumParam sub(Param para) {
        priorityStack.pushAction(new SubAction(cursor), para);
        return this;
    }

    @Override
    public NumParam mul(Param para) {
        priorityStack.pushAction(new MulAction(cursor), para);
        return this;
    }

    @Override
    public NumParam div(Param para) {
        priorityStack.pushAction(new DivAction(cursor), para);
        return this;
    }

    @Override
    public NumParam mod(Param para) {
        priorityStack.pushAction(new ModAction(cursor), para);
        return this;
    }

    @Override
    public NumParam band(Param para) {
        priorityStack.pushAction(new BandAction(cursor), para);
        return this;
    }

    @Override
    public NumParam bor(Param para) {
        priorityStack.pushAction(new BorAction(cursor), para);
        return this;
    }

    @Override
    public NumParam bxor(Param para) {
        priorityStack.pushAction(new BxorAction(cursor), para);
        return this;
    }

    @Override
    public NumParam shl(Param para) {
        priorityStack.pushAction(new ShiftLeftAction(cursor), para);
        return this;
    }

    @Override
    public NumParam shr(Param para) {
        priorityStack.pushAction(new ShiftRightAction(cursor), para);
        return this;
    }

    @Override
    public NumParam ushr(Param para) {
        priorityStack.pushAction(new UnsignedShiftRightAction(cursor), para);
        return this;
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
    public BoolParam eq(Param para) {
        return new BoolParam(cursor, new EqualAction(cursor), this, para);
    }

    @Override
    public BoolParam ne(Param para) {
        return new BoolParam(cursor, new NotEqualAction(cursor), this, para);
    }

    @Override
    public ObjectBehavior stradd(Param param) {
        return new ObjectParam(cursor, cursor.getPointer().stradd(target, ParamPostern.getTarget(param)));
    }
}
