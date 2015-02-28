package cn.wensiqun.asmsupport.client;

import cn.wensiqun.asmsupport.core.Parameterized;
import cn.wensiqun.asmsupport.core.block.method.init.ConstructorBodyInternal;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.core.operator.method.MethodInvoker;
import cn.wensiqun.asmsupport.standard.method.IContructorBody;

public abstract class ConstructorBody extends ProgramBlock<ConstructorBodyInternal> implements IContructorBody {

	public ConstructorBody() {
		target = new ConstructorBodyInternal(){

			@Override
			public void body(LocalVariable... args) {
				ConstructorBody.this.body(args);
			}
			
		};
	}

	@Override
	public MethodInvoker invokeSuperConstructor(Parameterized... arguments) {
    	return target.invokeSuperConstructor(arguments);
	}
}
