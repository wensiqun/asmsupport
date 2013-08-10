package demo.control;


import org.objectweb.asm.Opcodes;

import jw.asmsupport.block.control.Finally;
import jw.asmsupport.block.control.Try;
import jw.asmsupport.block.method.common.CommonMethodBody;
import jw.asmsupport.clazz.AClass;
import jw.asmsupport.creator.ClassCreator;
import jw.asmsupport.definition.value.Value;
import jw.asmsupport.definition.variable.LocalVariable;


import demo.CreateMethod;

public class TryFinally extends CreateMethod  {

	public TryFinally(ClassCreator creator) {
		super(creator);
	}

	@Override
	public void createMethod() {
		creator.createGlobalVariable("mtd_tryfinally_x", Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, AClass.STRING_ACLASS);
		creator.createGlobalVariable("mtd_tryfinally_y", Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, AClass.STRING_ACLASS);
		
		creator.createMethod("tryFinally", null, null, null, null, Opcodes.ACC_PUBLIC,
		        new CommonMethodBody(){
					@Override
					public void generateBody(LocalVariable... argus) {
						tryDo(new Try(){
							@Override
							public void generateBody() {
								invoke(out, "println", getMethodOwner().getGlobalVariable("mtd_tryfinally_x"));
								//invoke(getMethodOwner().getGlobalVariable("mtd_tryfinally_x"),"toString");
							    runReturn();
							}
						}).finallyThan(new Finally(){
							@Override
							public void generateInsn() {
								invoke(out, "println", Value.value("finally code"));
							}
						});
					}
		        }
		);
	}

}
