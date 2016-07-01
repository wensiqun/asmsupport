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
package cn.wensiqun.asmsupport.client.block;

import cn.wensiqun.asmsupport.client.def.Param;
import cn.wensiqun.asmsupport.client.def.ParamPostern;
import cn.wensiqun.asmsupport.client.def.param.UncertainParam;
import cn.wensiqun.asmsupport.client.def.param.impl.UncertainParamImpl;
import cn.wensiqun.asmsupport.client.def.var.LocVar;
import cn.wensiqun.asmsupport.client.def.var.Var;
import cn.wensiqun.asmsupport.core.block.KernelProgramBlock;
import cn.wensiqun.asmsupport.core.definition.variable.IVariable;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.standard.action.ActionSet;
import cn.wensiqun.asmsupport.standard.def.clazz.IClass;

import java.util.HashMap;
import java.util.Map;

/**
 * A wrapper of {@link KernelProgramBlock}
 * 
 * @author WSQ
 *
 * @param <B>
 */
public class ProgramBlock<B extends KernelProgramBlock> extends OperationBlock<B> implements ActionSet<
Param, Var, IF, While, DoWhile, ForEach, Try, Sync> {
	
	ProgramBlock<? extends KernelProgramBlock> parent;
	
	Map<String, LocVar> locVarMap = new HashMap<>();

	/**
	 * get current method owner.
	 * 
	 * @return
	 */
	public IClass getMethodOwner() {
		return getGenerateTimeBlock().getMethodDeclaringClass();
	}

	@Override
	public LocVar var(String name, Class<?> type, Param para) {
		LocVar var = new LocVar(getClientBridge(), getGenerateTimeBlock().var(name, type, para.getTarget()));
		locVarMap.put(name, var);
		return var;
	}

	@Override
	public LocVar var(Class<?> type, Param para) {
		return new LocVar(getClientBridge(), getGenerateTimeBlock().var(type, para.getTarget()));
	}

	@Override
	public LocVar var(String name, IClass type, Param para) {
		LocVar var = new LocVar(getClientBridge(), getGenerateTimeBlock().var(name, type, para.getTarget()));
		locVarMap.put(name, var);
		return var;
	}

	@Override
	public LocVar var(IClass type, Param para) {
		return new LocVar(getClientBridge(), getGenerateTimeBlock().var(type, para.getTarget()));
	}

	@Override
	public UncertainParam assign(Var variable, Param val) {
		return new UncertainParamImpl(getClientBridge(), getGenerateTimeBlock().assign((IVariable) ParamPostern.getTarget(variable), val.getTarget()));
	}

	@Override
	public IF if_(IF ifBlock) {
	    ifBlock.parent = this;
		getGenerateTimeBlock().if_(ifBlock.getDelegate());
		return ifBlock;
	}

	@Override
	public While while_(While whileLoop) {
	    whileLoop.parent = this;
		getGenerateTimeBlock().while_(whileLoop.getDelegate());
		return whileLoop;
	}

	@Override
	public DoWhile dowhile(DoWhile doWhile) {
	    doWhile.parent = this;
		getGenerateTimeBlock().dowhile(doWhile.getDelegate());
		return doWhile;
	}

	@Override
	public ForEach for_(ForEach forEach) {
	    forEach.parent = this;
		getGenerateTimeBlock().for_(forEach.getDelegate());
		return forEach;
	}

	@Override
	public Try try_(Try tryClient) {
	    tryClient.parent = this;
		getGenerateTimeBlock().try_(tryClient.getDelegate());
		return tryClient;
	}

	@Override
	public Sync sync(Sync sync) {
	    sync.parent = this;
		getGenerateTimeBlock().sync(sync.getDelegate());
		return sync;
	}

	@Override
	public final void break_() {
		getGenerateTimeBlock().break_();
	}

	@Override
	public final void continue_() {
		getGenerateTimeBlock().continue_();
	}

	@Override
	public final void throw_(Param exception) {
		getGenerateTimeBlock().throw_(exception.getTarget());
	}

	@Override
	public final void return_() {
		getGenerateTimeBlock().return_();
	}

	@Override
	public final void return_(Param param) {
		getGenerateTimeBlock().return_(param.getTarget());
	}
	
    LocVar[] internalVar2ClientVar(LocalVariable... pars) {
        if(pars == null) {
            return null;
        }
        LocVar[] paras = new LocVar[pars.length];
        for(int i=0; i<pars.length; i++) {
            paras[i] = new LocVar(getClientBridge(), pars[i]);
        }
        return paras;
    }

}
