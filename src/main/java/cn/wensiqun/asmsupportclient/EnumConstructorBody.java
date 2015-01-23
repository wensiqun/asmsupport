package cn.wensiqun.asmsupportclient;

import cn.wensiqun.asmsupport.core.block.classes.method.init.EnumInitBodyInternal;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.generic.body.LocalVariablesBody;
import cn.wensiqun.asmsupport.generic.method.IEnumContructorBody;

public abstract class EnumConstructorBody extends ProgramBlock<EnumInitBodyInternal> implements IEnumContructorBody {

	public EnumConstructorBody() {
		target = new EnumInitBodyInternal(){

			@Override
			public void body(LocalVariable... args) {
				EnumConstructorBody.this.body(args);
			}
			
		};
	}
}
