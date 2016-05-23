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

import cn.wensiqun.asmsupport.client.ClientBridge;
import cn.wensiqun.asmsupport.client.def.Param;
import cn.wensiqun.asmsupport.client.def.ParamPostern;
import cn.wensiqun.asmsupport.client.def.action.OperatorAction;
import cn.wensiqun.asmsupport.client.def.behavior.CommonBehavior;
import cn.wensiqun.asmsupport.client.def.var.FieldVar;
import cn.wensiqun.asmsupport.client.def.var.LocVar;
import cn.wensiqun.asmsupport.core.definition.KernelParam;
import cn.wensiqun.asmsupport.core.utils.common.BlockTracker;
import cn.wensiqun.asmsupport.standard.def.clazz.IClass;


/**
 * Indicate a parameter that's support priority for each operator
 * 
 * @author WSQ
 *
 */
public abstract class PriorityParam extends Param implements CommonBehavior {

    protected ClientBridge clientBridge;
    protected ClientBridge.PriorityStack priorityStack;
    
    protected PriorityParam(BlockTracker tracker, Param result) {
    	this.clientBridge = (ClientBridge)tracker;
    	priorityStack = clientBridge.build(result);
    }
    
    public PriorityParam(BlockTracker tracker, OperatorAction action, Param... operands) {
    	this.clientBridge = (ClientBridge)tracker;
    	priorityStack = clientBridge.build(action, operands);
    }
    
    @Override
    public IClass getResultType() {
        return getTarget().getResultType();
    }

    @Override
    public KernelParam getTarget() {
        return priorityStack.execute();
    }

    @Override
    public final FieldVar field(String name) {
        return new FieldVar(clientBridge, getTarget().field(name));
    }
    
    @Override
    public final ObjectParam stradd(Param param) {
        return new ObjectParam(clientBridge, clientBridge.track().stradd(getTarget(), ParamPostern.getTarget(param)));
    }

	@Override
	public LocVar asVar() {
		return asVar(getResultType());
	}
	
	@Override
	public LocVar asVar(IClass type) {
		return new LocVar(clientBridge, clientBridge.track().var(type, getTarget()));
	}

	@Override
	public LocVar asVar(Class<?> type) {
		return new LocVar(clientBridge, clientBridge.track().var(type, getTarget()));
	}

	@Override
	public LocVar asVar(String varName) {
		return asVar(varName, getResultType());
	}

	@Override
	public LocVar asVar(String varName, IClass type) {
		return new LocVar(clientBridge, clientBridge.track().var(varName, type, getTarget()));
	}

	@Override
	public LocVar asVar(String varName, Class<?> type) {
		return new LocVar(clientBridge, clientBridge.track().var(varName, type, getTarget()));
	}
}
