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
import cn.wensiqun.asmsupport.client.def.behavior.BoolBehavior;
import cn.wensiqun.asmsupport.client.def.var.Var;
import cn.wensiqun.asmsupport.core.utils.common.BlockTracker;

/**
 * Indicate a boolean parameter
 * 
 * @author WSQ
 *
 */
public class BoolParam extends PriorityParam implements BoolBehavior {
	
    public BoolParam(BlockTracker blockTracker, OperatorAction action, Param... operands) {
        super(blockTracker, action, operands);
    }

    @Override
    public BoolParam eq(Param para) {
        priorityStack.pushAction(new EqualAction(clientBridge), para);
        return this;
    }

    @Override
    public BoolParam ne(Param para) {
        priorityStack.pushAction(new NotEqualAction(clientBridge), para);
        return this;
    }

    @Override
    public BoolParam and(Param param) {
        priorityStack.pushAction(new AndAction(clientBridge), param);
        return this;
    }

    @Override
    public BoolParam or(Param param) {
        priorityStack.pushAction(new OrAction(clientBridge), param);
        return this;
    }

    @Override
    public BoolParam logicAnd(Param param) {
        priorityStack.pushAction(new LogicAndAction(clientBridge), param);
        return this;
    }

    @Override
    public BoolParam logicOr(Param param) {
        priorityStack.pushAction(new LogicOrAction(clientBridge), param);
        return this;
    }

    @Override
    public BoolParam logicXor(Param param) {
        priorityStack.pushAction(new LogicXorAction(clientBridge), param);
        return this;
    }

    @Override
    public BoolParam and(boolean param) {
        return and(new DummyParam(clientBridge, clientBridge.track().val(param)));
    }

    @Override
    public BoolParam or(boolean param) {
        return or(new DummyParam(clientBridge, clientBridge.track().val(param)));
    }

    @Override
    public BoolParam logicAnd(boolean param) {
        return logicAnd(new DummyParam(clientBridge, clientBridge.track().val(param)));
    }

    @Override
    public BoolParam logicOr(boolean param) {
        return logicOr(new DummyParam(clientBridge, clientBridge.track().val(param)));
    }

    @Override
    public BoolParam logicXor(boolean param) {
        return logicXor(new DummyParam(clientBridge, clientBridge.track().val(param)));
    }

	@Override
	public BoolParam assignTo(Var var) {
        priorityStack.pushAction(new AssignAction(clientBridge, var), this);
        return this;
	}

}
