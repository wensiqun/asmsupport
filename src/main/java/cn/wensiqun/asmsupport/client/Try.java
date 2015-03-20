package cn.wensiqun.asmsupport.client;

import cn.wensiqun.asmsupport.core.block.control.exception.TryInternal;
import cn.wensiqun.asmsupport.standard.excep.ITry;

public abstract class Try extends ProgramBlock<TryInternal> implements ITry<Catch, Finally> {

	public Try() {
		target = new TryInternal() {

			@Override
			public void body() {
				Try.this.body();
			}
			
		};
	}
	
	@Override
    public Catch catch_(Catch catchBlock)
    {
        target.catch_(catchBlock.target);
        return catchBlock;
    }

	@Override
    public Finally finally_(Finally finallyClient) {
    	target.finally_(finallyClient.target);
    	return finallyClient;
    }
	
}
