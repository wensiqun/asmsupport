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
import cn.wensiqun.asmsupport.client.def.action.AndAction;
import cn.wensiqun.asmsupport.client.def.action.AssignAction;
import cn.wensiqun.asmsupport.client.def.action.EqualAction;
import cn.wensiqun.asmsupport.client.def.action.LogicAndAction;
import cn.wensiqun.asmsupport.client.def.action.LogicOrAction;
import cn.wensiqun.asmsupport.client.def.action.LogicXorAction;
import cn.wensiqun.asmsupport.client.def.action.NotEqualAction;
import cn.wensiqun.asmsupport.client.def.action.OperatorAction;
import cn.wensiqun.asmsupport.client.def.action.OrAction;
import cn.wensiqun.asmsupport.client.def.behavior.BoolBehavior;
import cn.wensiqun.asmsupport.client.def.var.Var;

/**
 * Indicate a boolean parameter
 * 
 * @author WSQ
 *
 */
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
        return and(new DummyParam(cursor, cursor.peek().val(param)));
    }

    @Override
    public BoolParam or(boolean param) {
        return or(new DummyParam(cursor, cursor.peek().val(param)));
    }

    @Override
    public BoolParam logicAnd(boolean param) {
        return logicAnd(new DummyParam(cursor, cursor.peek().val(param)));
    }

    @Override
    public BoolParam logicOr(boolean param) {
        return logicOr(new DummyParam(cursor, cursor.peek().val(param)));
    }

    @Override
    public BoolParam logicXor(boolean param) {
        return logicXor(new DummyParam(cursor, cursor.peek().val(param)));
    }

	@Override
	public BoolParam assignTo(Var var) {
        priorityStack.pushAction(new AssignAction(cursor, var), this);
        return this;
	}

}
