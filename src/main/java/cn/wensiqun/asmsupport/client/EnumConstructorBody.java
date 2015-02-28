package cn.wensiqun.asmsupport.client;

import cn.wensiqun.asmsupport.core.block.method.init.EnumConstructorBodyInternal;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.standard.method.IEnumContructorBody;

public abstract class EnumConstructorBody extends ProgramBlock<EnumConstructorBodyInternal> implements IEnumContructorBody {

	public EnumConstructorBody() {
		target = new EnumConstructorBodyInternal(){

			@Override
			public void body(LocalVariable... args) {
				EnumConstructorBody.this.body(args);
			}
			
		};
	}
}
