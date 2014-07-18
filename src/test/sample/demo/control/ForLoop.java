package demo.control;

import java.util.ArrayList;
import java.util.List;

import org.objectweb.asm.Opcodes;

import cn.wensiqun.asmsupport.block.control.WhileLoop;
import cn.wensiqun.asmsupport.block.method.common.CommonMethodBody;
import cn.wensiqun.asmsupport.clazz.AClass;
import cn.wensiqun.asmsupport.clazz.AClassFactory;
import cn.wensiqun.asmsupport.creator.ClassCreator;
import cn.wensiqun.asmsupport.definition.value.Value;
import cn.wensiqun.asmsupport.definition.variable.LocalVariable;
import demo.CreateMethod;

public class ForLoop extends CreateMethod  {

	public ForLoop(ClassCreator creator) {
		super(creator);
	}

	@Override
	public void createMethod() {

		creator.createMethod("forloop", null, null, null, null, Opcodes.ACC_PUBLIC,
		        new CommonMethodBody(){
					@Override
					public void body(LocalVariable... argus) {
						AClass listCls = AClassFactory.getProductClass(List.class);
						AClass arraylistCls = AClassFactory.getProductClass(ArrayList.class);
						final LocalVariable list = createVariable("list", listCls, false, invokeConstructor(arraylistCls));
						invoke(list, "add", Value.value("one"));
						invoke(list, "add", Value.value("two"));
						invoke(list, "add", Value.value("three"));
						
						final LocalVariable i = createVariable("i", AClass.INT_ACLASS, false, Value.value(0));
						whileloop(new WhileLoop(lessThan(i, invoke(list, "size"))){

							@Override
							public void body() {
								invoke(out, "println", invoke(list, "get", i));
								afterInc(i);
							}
							
						});
						
					    runReturn();
					}
		        }
		);
	}

}
