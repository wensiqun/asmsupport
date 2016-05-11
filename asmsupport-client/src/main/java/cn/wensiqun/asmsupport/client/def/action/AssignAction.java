package cn.wensiqun.asmsupport.client.def.action;

import cn.wensiqun.asmsupport.client.block.KernelProgramBlockCursor;
import cn.wensiqun.asmsupport.client.def.Param;
import cn.wensiqun.asmsupport.client.def.ParamPostern;
import cn.wensiqun.asmsupport.client.def.param.DummyParam;
import cn.wensiqun.asmsupport.client.def.var.Var;
import cn.wensiqun.asmsupport.core.definition.variable.IVariable;
import cn.wensiqun.asmsupport.core.operator.Operator;

public class AssignAction extends AbstractUnaryAction {

	private Var variable;
	
	public AssignAction(KernelProgramBlockCursor cursor, Var variable) {
		super(cursor, Operator.ASSIGN);
		this.variable = variable;
	}

	@Override
	public Param doAction(Param... operands) {
		return new DummyParam(cursor, 
				cursor.peek().assign((IVariable)ParamPostern.getTarget(variable),
						ParamPostern.getTarget(operands[0])));
	}

}
