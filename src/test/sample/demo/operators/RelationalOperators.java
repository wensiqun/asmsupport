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
public class RelationalOperators extends CreateMethod {

	public RelationalOperators(ClassCreator creator) {
		super(creator);
	}

	@Override
	public void createMethod() {
		creator.createMethod("relationalOperators", null, null, null, null, Opcodes.ACC_PUBLIC,
		        new CommonMethodBody(){
	        @Override
            public void body(LocalVariable... argus) {
	        	LocalVariable int1 = createVariable("int1", AClass.INT_ACLASS, false, Value.value(2));
	        	LocalVariable long1 = createVariable("long1", AClass.LONG_ACLASS, false, Value.value(1L));
	        	invoke(out, "println", append(Value.value(" 2 > 1L = "), greaterThan(int1, long1)));
	        	invoke(out, "println", append(Value.value(" 2 >= 1L = "), greaterEqual(int1, long1)));
	        	invoke(out, "println", append(Value.value(" 2 < 1L = "), lessThan(int1, long1)));
	        	invoke(out, "println", append(Value.value(" 2 <= 1L = "), lessEqual(int1, long1)));
	        	invoke(out, "println", append(Value.value(" 2 == 1L = "), equal(Value.value("test"), Value.getNullValue(AClass.STRING_ACLASS))));
	        	invoke(out, "println", append(Value.value(" 2 != 1L = "), notEqual(int1, long1)));
			    runReturn();
    	    }
        });
	}

}
