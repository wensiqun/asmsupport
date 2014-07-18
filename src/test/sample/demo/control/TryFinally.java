package demo.control;


import org.objectweb.asm.Opcodes;

import cn.wensiqun.asmsupport.block.control.Finally;
import cn.wensiqun.asmsupport.block.control.Try;
import cn.wensiqun.asmsupport.block.method.common.CommonMethodBody;
import cn.wensiqun.asmsupport.clazz.AClass;
import cn.wensiqun.asmsupport.creator.ClassCreator;
import cn.wensiqun.asmsupport.definition.value.Value;
import cn.wensiqun.asmsupport.definition.variable.LocalVariable;
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
					public void body(LocalVariable... argus) {
						tryDo(new Try(){
							@Override
							public void body() {
								invoke(out, "println", getMethodOwner().getGlobalVariable("mtd_tryfinally_x"));
								//invoke(getMethodOwner().getGlobalVariable("mtd_tryfinally_x"),"toString");
							    runReturn();
							}
						}).finallyThan(new Finally(){
							@Override
							public void body() {
								invoke(out, "println", Value.value("finally code"));
							}
						});
					}
		        }
		);
	}

}
