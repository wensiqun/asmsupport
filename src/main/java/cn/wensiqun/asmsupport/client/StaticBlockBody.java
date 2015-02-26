package cn.wensiqun.asmsupport.client;

import cn.wensiqun.asmsupport.core.block.classes.method.clinit.StaticBlockBodyInternal;
import cn.wensiqun.asmsupport.standard.method.IStaticBlockBody;

public abstract class StaticBlockBody extends ProgramBlock<StaticBlockBodyInternal> implements IStaticBlockBody {

	public StaticBlockBody() {
		target = new StaticBlockBodyInternal() {
			@Override
			public void body() {
				StaticBlockBody.this.body();
			}
		};
	}
	
}
