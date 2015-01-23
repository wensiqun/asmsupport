package cn.wensiqun.asmsupportclient;

import cn.wensiqun.asmsupport.core.Parameterized;
import cn.wensiqun.asmsupport.core.block.classes.method.init.InitBodyInternal;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.core.operator.method.MethodInvoker;
import cn.wensiqun.asmsupport.generic.method.IContructorBody;

public abstract class ConstructorBody extends ProgramBlock<InitBodyInternal> implements IContructorBody {

	public ConstructorBody() {
		target = new InitBodyInternal(){

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
