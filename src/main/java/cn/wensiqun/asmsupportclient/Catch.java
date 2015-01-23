package cn.wensiqun.asmsupportclient;

import cn.wensiqun.asmsupport.core.block.classes.control.exception.CatchInternal;
import cn.wensiqun.asmsupport.core.clazz.AClass;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.generic.excep.ICatch;

public abstract class Catch extends ProgramBlock<CatchInternal> implements ICatch<Catch, Finally> {

	public Catch(AClass aclass) {
		target = new CatchInternal(aclass) {

			@Override
			public void body(LocalVariable e) {
				Catch.this.body(e);
			}
		};
	}
	
    public Catch _catch(Catch catchBlock)
    {
        target._catch(catchBlock.target);
        return catchBlock;
    }
    
    public Finally _finally(Finally finallyClient) {
    	target._finally(finallyClient.target);
    	return finallyClient;
    }
	
}
