/**
 * 
 */
package demo.operators;


import org.objectweb.asm.Opcodes;

import cn.wensiqun.asmsupport.block.method.common.CommonMethodBody;
import cn.wensiqun.asmsupport.clazz.AClass;
import cn.wensiqun.asmsupport.clazz.AClassFactory;
import cn.wensiqun.asmsupport.creator.ClassCreator;
import cn.wensiqun.asmsupport.definition.value.Value;
import cn.wensiqun.asmsupport.definition.variable.LocalVariable;
import demo.CreateMethod;

/**
 * @author 温斯群(Joe Wen)
 * 
 */
public class InstanceofOperators extends CreateMethod {

	public InstanceofOperators(ClassCreator creator) {
		super(creator);
	}

	@Override
	public void createMethod() {
		creator.createMethod("instanceofOperators", null, null, null, null, Opcodes.ACC_PUBLIC,
		        new CommonMethodBody(){
	        @Override
            public void body(LocalVariable... argus) {
	        	LocalVariable str = createVariable("str", AClass.STRING_ACLASS, false, Value.value("String"));
	        	invoke(out, "println", Value.value("String str = \"test string\" "));
	        	invoke(out, "println", append(Value.value(" str instanceof CharSequence = "), instanceOf(str, AClassFactory.getProductClass(CharSequence.class))));
			    runReturn();
    	    }
        });
	}

}
