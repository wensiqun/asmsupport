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
import cn.wensiqun.asmsupport.client.def.action.ArrayLengthAction;
import cn.wensiqun.asmsupport.client.def.action.AssignAction;
import cn.wensiqun.asmsupport.client.def.action.InstanceofAction;
import cn.wensiqun.asmsupport.client.def.behavior.ArrayBehavior;
import cn.wensiqun.asmsupport.client.def.var.Var;
import cn.wensiqun.asmsupport.core.definition.KernelParam;
import cn.wensiqun.asmsupport.standard.def.clazz.IClass;


/**
 * Array Parameter
 * 
 * @author WSQ
 *
 */
public class ArrayParam extends CommonParam implements ArrayBehavior{

    public ArrayParam(KernelProgramBlockCursor cursor, KernelParam arrayReference) {
        super(cursor, arrayReference);
    }

    @Override
    public UncertainParam load(Param firstDim, Param... dims) {
        return new UncertainParam(cursor, cursor.peek().arrayLoad(target, ParamPostern.getTarget(firstDim), ParamPostern.getTarget(dims)));
    }

    @Override
    public NumParam length(Param... dims) {
        Param[] operands = ParamPostern.unionParam(new DummyParam(cursor, target), dims);
        return new NumParam(cursor, new ArrayLengthAction(cursor, operands.length - 1), operands);
    }

    @Override
    public UncertainParam store(Param value, Param firstDim, Param... dims) {
        return new UncertainParam(cursor, cursor.peek().arrayStore(target, ParamPostern.getTarget(value),
                ParamPostern.getTarget(firstDim), ParamPostern.getTarget(dims)));
    }

    @Override
    public UncertainParam call(String methodName, Param... arguments) {
        return new UncertainParam(cursor, cursor.peek().call(target, methodName, ParamPostern.getTarget(arguments)));
    }

    @Override
    public UncertainParam cast(Class<?> type) {
        return new UncertainParam(cursor, cursor.peek().checkcast(target, type));
    }

    @Override
    public UncertainParam cast(IClass type) {
        return new UncertainParam(cursor, cursor.peek().checkcast(target, type));
    }

    @Override
    public BoolParam instanceof_(Class<?> type) {
        return new BoolParam(cursor, new InstanceofAction(cursor, cursor.peek().getType(type)), this);
    }

    @Override
    public BoolParam instanceof_(IClass type) {
        return new BoolParam(cursor, new InstanceofAction(cursor, type), this);
    }
    
    @Override
	public ArrayParam assignTo(Var var) {
    	return new ArrayParam(cursor, 
    			ParamPostern.getTarget(new AssignAction(cursor, var).doAction(var)));
	}

}
