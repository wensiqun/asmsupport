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
public class BitwiseOperators extends CreateMethod {

	public BitwiseOperators(ClassCreator creator) {
		super(creator);
	}

	@Override
	public void createMethod() {
		creator.createMethod("bitwiseOperators", null, null, null, null, Opcodes.ACC_PUBLIC,
		        new CommonMethodBody(){
	        @Override
            public void body(LocalVariable... argus) {
	        	
	        	
	        	invoke(out, "println", append(Value.value(" 9 & 7 = "), bitAnd(Value.value(9), Value.value(7)))); //System.out.println(" 9 & 7 = " + 9 & 7);
	        	invoke(out, "println", append(Value.value(" 19 | 7 = "), bitOr(Value.value(19), Value.value(7)))); //System.out.println(" 19 & 7 = " + 19 & 7);
	        	invoke(out, "println", append(Value.value(" 9 ^ 7 = "), bitXor(Value.value(9), Value.value(7)))); //System.out.println(" 9 ^ 7 = " + 9 ^ 7);
	        	invoke(out, "println", append(Value.value(" 9 << 7 = "), leftShift(Value.value(9), Value.value(7)))); //System.out.println(" 9 << 7 = " + 9 << 7);
				LocalVariable i = createVariable("i", AClass.INT_ACLASS, false, Value.value(1));  //int i = 1;
				invoke(out, "println", i);//System.out.println(i);
				LocalVariable j = createVariable("j", AClass.INT_ACLASS, false, add(inverts(i), Value.value(1)));//int j = ~i + 1;
				invoke(out, "println", j);//System.out.println(j);
				assign(i, add(inverts(j), Value.value(1)));//i = ~j + 1;
				invoke(out, "println", i);//System.out.println(i);
			    runReturn();//return
    	    }
        }
);
	}

}
