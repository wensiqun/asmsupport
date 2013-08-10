package demo.block;



import org.objectweb.asm.Opcodes;

import jw.asmsupport.Parameterized;
import jw.asmsupport.block.method.common.CommonMethodBody;
import jw.asmsupport.creator.ClassCreator;
import jw.asmsupport.definition.value.Value;
import jw.asmsupport.definition.variable.GlobalVariable;
import jw.asmsupport.definition.variable.LocalVariable;
import demo.CreateMethod;

public class Synchronized extends CreateMethod   {

	public Synchronized(ClassCreator creator) {
		super(creator);
	}

	@Override
	public void createMethod() {
		creator.createMethod("synchronizedMethod", null, null, null, null, Opcodes.ACC_PUBLIC, new CommonMethodBody(){
			@Override
			public void generateBody(LocalVariable... argus) {
				final GlobalVariable gv = getMethodOwner().getGlobalVariable("testString");
			    this.syn(new jw.asmsupport.block.Synchronized(gv){
					@Override
					public void generateBody(Parameterized synObj) {
						invoke(out, "println", append(Value.value("testString has been synchronized : "), gv));
					}
			    });
				runReturn();
			}
		});
	}

}
