package cn.wensiqun.asmsupport.client.def.action;

import cn.wensiqun.asmsupport.client.def.Param;
import cn.wensiqun.asmsupport.client.def.ParamPostern;
import cn.wensiqun.asmsupport.client.def.param.basic.DummyParam;
import cn.wensiqun.asmsupport.client.def.var.Var;
import cn.wensiqun.asmsupport.core.definition.variable.IVariable;
import cn.wensiqun.asmsupport.core.operator.Operator;
import cn.wensiqun.asmsupport.core.utils.common.BlockTracker;

public class AssignAction extends AbstractUnaryAction {

	private Var variable;
	
	public AssignAction(BlockTracker tracker, Var variable) {
		super(tracker, Operator.ASSIGN);
		this.variable = variable;
	}

	@Override
	public Param doAction(Param... operands) {
		return new DummyParam(tracker,
				tracker.track().assign((IVariable)ParamPostern.getTarget(variable),
						operands[0].getTarget()));
	}

}
