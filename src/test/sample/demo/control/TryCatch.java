package demo.control;


import org.objectweb.asm.Opcodes;

import jw.asmsupport.block.control.Catch;
import jw.asmsupport.block.control.Finally;
import jw.asmsupport.block.control.Try;
import jw.asmsupport.block.method.common.CommonMethodBody;
import jw.asmsupport.clazz.AClass;
import jw.asmsupport.clazz.AClassFactory;
import jw.asmsupport.creator.ClassCreator;
import jw.asmsupport.definition.value.Value;
import jw.asmsupport.definition.variable.LocalVariable;


import demo.CreateMethod;

public class TryCatch extends CreateMethod  {

	public TryCatch(ClassCreator creator) {
		super(creator);
	}
	
	@Override
	public void createMethod() {

		creator.createMethod("tryCatch", null, null, null, null, Opcodes.ACC_PUBLIC,
		        new CommonMethodBody(){
					@Override
					public void generateBody(LocalVariable... argus) {
						final AClass acls = AClassFactory.getProductClass(Class.class);
						tryDo(new Try(){
							@Override
							public void generateBody() {
								createVariable("stringClass", acls, false, invokeStatic(acls, "forName", Value.value("java.lang.String")));
								//runReturn();
							}
						}).catchException(new Catch(AClassFactory.getProductClass(ClassNotFoundException.class)){

							@Override
							public void catchBody(LocalVariable e) {
								invoke(e, "printStackTrace");
								throwException(invokeConstructor(AClassFactory.getProductClass(RuntimeException.class), Value.value("test run time exception")));
							}
							
						}).finallyThan(new Finally(){
							@Override
							public void generateInsn() {
							    invoke(out, "println", Value.value("hello..."));
							}
							
						});
					    runReturn();
					}
		        }
		);
	}

}
