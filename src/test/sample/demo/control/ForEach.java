package demo.control;

import java.util.ArrayList;
import java.util.List;

import org.objectweb.asm.Opcodes;

import cn.wensiqun.asmsupport.block.control.ForEachLoop;
import cn.wensiqun.asmsupport.block.control.IF;
import cn.wensiqun.asmsupport.block.method.common.CommonMethodBody;
import cn.wensiqun.asmsupport.clazz.AClass;
import cn.wensiqun.asmsupport.clazz.AClassFactory;
import cn.wensiqun.asmsupport.creator.ClassCreator;
import cn.wensiqun.asmsupport.definition.value.Value;
import cn.wensiqun.asmsupport.definition.variable.LocalVariable;
import demo.CreateMethod;

public class ForEach extends CreateMethod  {

	public ForEach(ClassCreator creator) {
		super(creator);
	}

	@Override
	public void createMethod() {

		creator.createMethod("foreach", null, null, null, null, Opcodes.ACC_PUBLIC,
		        new CommonMethodBody(){
					@Override
					public void body(LocalVariable... argus) {
						AClass listCls = AClassFactory.getProductClass(List.class);
						AClass arraylistCls = AClassFactory.getProductClass(ArrayList.class);
						LocalVariable list = createVariable("list", listCls, false, invokeConstructor(arraylistCls));
						invoke(list, "add", Value.value("one"));
						invoke(list, "add", Value.value("two"));
						invoke(list, "add", Value.value("three"));
						
						forEach(new ForEachLoop(list){
							@Override
							public void body(LocalVariable var) {
								invoke(out, "println", var);
							}
						});
						LocalVariable array = createArrayVariableWithAllocateDimension("array", AClassFactory.getArrayClass(int[].class), false, Value.value(3));
						arrayStore(array, Value.value(2), Value.value(0));
						arrayStore(array, Value.value(4), Value.value(1));
						arrayStore(array, Value.value(6), Value.value(2));
						
						forEach(new ForEachLoop(array){
							@Override
							public void body(final LocalVariable var) {
								ifthan(new IF(equal(var, Value.value(4))){
									@Override
									public void body() {
									    continueOut();
									}
								});
								invoke(out,  "println", var);
							}
							
						});
						
					    runReturn();
					}
		        }
		);
	}

}
