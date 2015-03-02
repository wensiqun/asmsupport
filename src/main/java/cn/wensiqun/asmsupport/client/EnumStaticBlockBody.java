package cn.wensiqun.asmsupport.client;

import cn.wensiqun.asmsupport.core.Parameterized;
import cn.wensiqun.asmsupport.core.block.method.clinit.EnumStaticBlockBodyInternal;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.standard.method.IEnumStaticBlockBody;

public abstract class EnumStaticBlockBody extends ProgramBlock<EnumStaticBlockBodyInternal> implements IEnumStaticBlockBody {

	public EnumStaticBlockBody() {
		target = new EnumStaticBlockBodyInternal() {

			@Override
			public void body(LocalVariable... argus) {
				EnumStaticBlockBody.this.body(argus);
			}

			@Override
			public void constructEnumConsts() {
				EnumStaticBlockBody.this.constructEnumConsts();
			}
		};
	}
	
	@Override
	public void constructEnumConst(String name, Parameterized... argus) {
		target.constructEnumConst(name, argus);
	}
	
}
