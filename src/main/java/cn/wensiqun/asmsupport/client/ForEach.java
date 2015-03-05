package cn.wensiqun.asmsupport.client;

import cn.wensiqun.asmsupport.core.block.control.loop.ForEachInternal;
import cn.wensiqun.asmsupport.core.clazz.AClass;
import cn.wensiqun.asmsupport.core.clazz.AClassFactory;
import cn.wensiqun.asmsupport.core.definition.variable.ExplicitVariable;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.standard.loop.IForEach;

public abstract class ForEach extends ProgramBlock<ForEachInternal> implements IForEach {

	public ForEach(ExplicitVariable iteratorVar) {
		target = new ForEachInternal(iteratorVar) {

			@Override
			public void body(LocalVariable e) {
				ForEach.this.body(e);
			}
			
		};
	}
	
    public ForEach(ExplicitVariable iteratorVar, AClass elementType) {
        target = new ForEachInternal(iteratorVar, elementType) {

            @Override
            public void body(LocalVariable e) {
                ForEach.this.body(e);
            }
            
        };
    }
    
    public ForEach(ExplicitVariable iteratorVar, Class<?> elementType) {
        target = new ForEachInternal(iteratorVar, AClassFactory.getProductClass(elementType)) {

            @Override
            public void body(LocalVariable e) {
                ForEach.this.body(e);
            }
            
        };
    }
	
}
