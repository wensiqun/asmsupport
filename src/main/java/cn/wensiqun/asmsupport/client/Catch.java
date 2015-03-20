package cn.wensiqun.asmsupport.client;

import cn.wensiqun.asmsupport.core.block.control.exception.CatchInternal;
import cn.wensiqun.asmsupport.core.clazz.AClass;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.standard.excep.ICatch;

public abstract class Catch extends ProgramBlock<CatchInternal> implements ICatch<Catch, Finally> {

	public Catch(AClass aclass) {
		target = new CatchInternal(aclass) {

			@Override
			public void body(LocalVariable e) {
				Catch.this.body(e);
			}
		};
	}
	
    public Catch catch_(Catch catchBlock)
    {
        target.catch_(catchBlock.target);
        return catchBlock;
    }
    
    public Finally finally_(Finally finallyClient) {
    	target.finally_(finallyClient.target);
    	return finallyClient;
    }
	
}
