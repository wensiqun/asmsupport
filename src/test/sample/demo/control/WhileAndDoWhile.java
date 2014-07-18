package demo.control;


import org.objectweb.asm.Opcodes;

import cn.wensiqun.asmsupport.block.control.DoWhileLoop;
import cn.wensiqun.asmsupport.block.control.IF;
import cn.wensiqun.asmsupport.block.control.WhileLoop;
import cn.wensiqun.asmsupport.block.method.common.CommonMethodBody;
import cn.wensiqun.asmsupport.clazz.AClass;
import cn.wensiqun.asmsupport.creator.ClassCreator;
import cn.wensiqun.asmsupport.definition.value.Value;
import cn.wensiqun.asmsupport.definition.variable.LocalVariable;
import demo.CreateMethod;

public class WhileAndDoWhile extends CreateMethod  {

	public WhileAndDoWhile(ClassCreator creator) {
		super(creator);
	}

	@Override
	public void createMethod() {

		creator.createMethod("whileAndDoWhile", null, null, null, null, Opcodes.ACC_PUBLIC,
		        new CommonMethodBody(){
					@Override
					public void body(LocalVariable... argus) {
						final LocalVariable i = createVariable("i", AClass.INT_ACLASS, false, Value.value(0));
			        	
			        	whileloop(new WhileLoop(lessThan(i, Value.value(5))){
							
			        		@Override
							public void body() {
			        			ifthan(new IF(equal(i, Value.value(3))){
									@Override
									public void body() {
										breakOut();
									}
			        			});
			        			invoke(out, "println", append(Value.value(" i = "), afterInc(i)));
						    }
			        	});
			        	
			        	assign(i, Value.value(0));
			        	
                        dowhile(new DoWhileLoop(lessThan(i, Value.value(5))){
							
			        		@Override
							public void body() {
			        			invoke(out, "println", append(Value.value(" i = "), afterInc(i)));
			        			ifthan(new IF(equal(i, Value.value(3))){
									@Override
									public void body() {
										afterInc(i);
										continueOut();
									}
			        			});
						}});
                        
					    runReturn();
					}
		        }
		);
	}

}
