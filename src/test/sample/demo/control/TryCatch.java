package demo.control;


import org.objectweb.asm.Opcodes;

import cn.wensiqun.asmsupport.block.control.Catch;
import cn.wensiqun.asmsupport.block.control.Finally;
import cn.wensiqun.asmsupport.block.control.Try;
import cn.wensiqun.asmsupport.block.method.common.CommonMethodBody;
import cn.wensiqun.asmsupport.clazz.AClass;
import cn.wensiqun.asmsupport.clazz.AClassFactory;
import cn.wensiqun.asmsupport.creator.ClassCreator;
import cn.wensiqun.asmsupport.definition.value.Value;
import cn.wensiqun.asmsupport.definition.variable.LocalVariable;
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
					public void body(LocalVariable... argus) {
						final AClass acls = AClassFactory.getProductClass(Class.class);
						tryDo(new Try(){
							@Override
							public void body() {
								createVariable("stringClass", acls, false, invokeStatic(acls, "forName", Value.value("java.lang.String")));
								//runReturn();
							}
						}).catchException(new Catch(AClassFactory.getProductClass(ClassNotFoundException.class)){

							@Override
							public void body(LocalVariable e) {
								invoke(e, "printStackTrace");
								throwException(invokeConstructor(AClassFactory.getProductClass(RuntimeException.class), Value.value("test run time exception")));
							}
							
						}).finallyThan(new Finally(){
							@Override
							public void body() {
							    invoke(out, "println", Value.value("hello..."));
							}
							
						});
					    runReturn();
					}
		        }
		);
	}

}
