package demo.block;



import org.objectweb.asm.Opcodes;

import cn.wensiqun.asmsupport.Parameterized;
import cn.wensiqun.asmsupport.block.method.common.CommonMethodBody;
import cn.wensiqun.asmsupport.creator.ClassCreator;
import cn.wensiqun.asmsupport.definition.value.Value;
import cn.wensiqun.asmsupport.definition.variable.GlobalVariable;
import cn.wensiqun.asmsupport.definition.variable.LocalVariable;
import demo.CreateMethod;

public class Synchronized extends CreateMethod   {

	public Synchronized(ClassCreator creator) {
		super(creator);
	}

	@Override
	public void createMethod() {
		creator.createMethod("synchronizedMethod", null, null, null, null, Opcodes.ACC_PUBLIC, new CommonMethodBody(){
			@Override
			public void body(LocalVariable... argus) {
				final GlobalVariable gv = getMethodOwner().getGlobalVariable("testString");
			    this.syn(new cn.wensiqun.asmsupport.block.Synchronized(gv){
					@Override
					public void body(Parameterized synObj) {
						invoke(out, "println", append(Value.value("testString has been synchronized : "), gv));
					}
			    });
				runReturn();
			}
		});
	}

}
