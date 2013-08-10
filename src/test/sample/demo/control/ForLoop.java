package demo.control;

import java.util.ArrayList;
import java.util.List;

import org.objectweb.asm.Opcodes;


import jw.asmsupport.block.control.WhileLoop;
import jw.asmsupport.block.method.common.CommonMethodBody;
import jw.asmsupport.clazz.AClass;
import jw.asmsupport.clazz.AClassFactory;
import jw.asmsupport.creator.ClassCreator;
import jw.asmsupport.definition.value.Value;
import jw.asmsupport.definition.variable.LocalVariable;


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
					public void generateBody(LocalVariable... argus) {
						AClass listCls = AClassFactory.getProductClass(List.class);
						AClass arraylistCls = AClassFactory.getProductClass(ArrayList.class);
						final LocalVariable list = createVariable("list", listCls, false, invokeConstructor(arraylistCls));
						invoke(list, "add", Value.value("one"));
						invoke(list, "add", Value.value("two"));
						invoke(list, "add", Value.value("three"));
						
						final LocalVariable i = createVariable("i", AClass.INT_ACLASS, false, Value.value(0));
						whileloop(new WhileLoop(lessThan(i, invoke(list, "size"))){

							@Override
							public void generateInsn() {
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
