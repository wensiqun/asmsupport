package cn.wensiqun.asmsupport.client;

import cn.wensiqun.asmsupport.core.block.method.common.ModifiedMethodBodyInternal;
import cn.wensiqun.asmsupport.core.clazz.AClass;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.standard.method.IModifiedMethodBody;

public abstract class ModifiedMethodBody extends ProgramBlock<ModifiedMethodBodyInternal> implements
    IModifiedMethodBody {

	private ModifiedMethodBody() {
	     target = new ModifiedMethodBodyInternal() {

			@Override
			public void body(LocalVariable... args) {
				ModifiedMethodBody.this.body(args);
			}
	    	 
	     };
	}

	public AClass getOriginalMethodReturnClass(){
		return target.getOriginalMethodReturnClass();
	}

}
