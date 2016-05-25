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
import cn.wensiqun.asmsupport.client.def.ParamPostern;
import cn.wensiqun.asmsupport.client.def.action.AssignAction;
import cn.wensiqun.asmsupport.client.def.action.InstanceofAction;
import cn.wensiqun.asmsupport.client.def.behavior.ObjectBehavior;
import cn.wensiqun.asmsupport.client.def.var.Var;
import cn.wensiqun.asmsupport.core.definition.KernelParam;
import cn.wensiqun.asmsupport.core.definition.value.Value;
import cn.wensiqun.asmsupport.core.utils.common.BlockTracker;
import cn.wensiqun.asmsupport.standard.def.clazz.IClass;


/**
 * Indicate a object parameter.
 * 
 * @author WSQ
 *
 */
public class ObjectParam extends CommonParam implements ObjectBehavior {

    public ObjectParam(BlockTracker tracker, KernelParam target) {
        super(tracker, target);
    }

    @Override
    public UncertainParam call(String methodName, Param... arguments) {
    	if(target instanceof Value && ((Value)target).getValue() instanceof IClass) {
    		return new UncertainParam(tracker,
                    tracker.track().call(
    						(IClass)((Value)target).getValue(), 
    						methodName, ParamPostern.getTarget(arguments)));
    	}
        return new UncertainParam(tracker, tracker.track().call(target, methodName, ParamPostern.getTarget(arguments)));
    }

    @Override
    public UncertainParam cast(Class<?> type) {
        return new UncertainParam(tracker, tracker.track().checkcast(target, type));
    }

    @Override
    public UncertainParam cast(IClass type) {
        return new UncertainParam(tracker, tracker.track().checkcast(target, type));
    }

    @Override
    public BoolParam instanceof_(Class<?> type) {
        return new BoolParam(tracker, new InstanceofAction(tracker, tracker.track().getType(type)), this);
    }

    @Override
    public BoolParam instanceof_(IClass type) {
        return new BoolParam(tracker, new InstanceofAction(tracker, type), this);
    }

	@Override
	public ObjectParam assignTo(Var var) {
		return new ObjectParam(tracker,
                new AssignAction(tracker, var).doAction(this).getTarget());
	}

}
