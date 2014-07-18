/**
 * 
 */
package demo.operators;


import org.objectweb.asm.Opcodes;

import cn.wensiqun.asmsupport.block.method.common.CommonMethodBody;
import cn.wensiqun.asmsupport.clazz.AClass;
import cn.wensiqun.asmsupport.creator.ClassCreator;
import cn.wensiqun.asmsupport.definition.value.Value;
import cn.wensiqun.asmsupport.definition.variable.LocalVariable;
import demo.CreateMethod;

/**
 * @author 温斯群(Joe Wen)
 * 
 */
public class TernaryOperators extends CreateMethod {

	public TernaryOperators(ClassCreator creator) {
		super(creator);
	}

	@Override
	public void createMethod() {
		creator.createMethod("ternaryOperators", null, null, null, null, Opcodes.ACC_PUBLIC,
		        new CommonMethodBody(){
	        @Override
            public void body(LocalVariable... argus) {
	        	LocalVariable i = createVariable("i", AClass.INT_ACLASS, false, Value.value(10));
	        	invoke(out, "println", Value.value("i = 10"));
	        	LocalVariable k = createVariable("k", AClass.INT_ACLASS, false, ternary(lessThan(i, Value.value(0)), neg(i), i));
	        	invoke(out, "println", Value.value("k = i < 0 ? -i : i"));
	        	invoke(out, "println", append(Value.value(" k = "), k));
	        	
	        	assign(i, Value.value(-10));
	        	invoke(out, "println", Value.value("i = -10"));
	        	invoke(out, "println", Value.value("k = i < 0 ? -i : i"));
	        	assign(k, ternary(lessThan(i, Value.value(0)), neg(i), i));
	        	invoke(out, "println", append(Value.value(" k = "), k));
			    runReturn();
    	    }
        });
	}

}
