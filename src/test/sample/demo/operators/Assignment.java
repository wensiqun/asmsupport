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
public class Assignment extends CreateMethod {

	public Assignment(ClassCreator creator) {
		super(creator);
	}

	@Override
	public void createMethod() {
		creator.createMethod("assignment", null, null, null, null, Opcodes.ACC_PUBLIC,
		        new CommonMethodBody(){
	        @Override
            public void body(LocalVariable... argus) {
	        	LocalVariable a = createVariable("a", AClass.INT_ACLASS, false, Value.value(1)); //int a = 1;
	        	LocalVariable b = createVariable("b", AClass.INT_ACLASS, false, Value.value(2)); //int b = 2;
	        	LocalVariable c = createVariable("c", AClass.INT_ACLASS, false, Value.value(3)); //int c = 3;
	        	invoke(out, "println", append(Value.value("a = "), assign(a, add(a, Value.value(5))))); //System.out.println(a += 5);
	        	assign(b, mul(b, Value.value(4))); //b *= 4;
	        	assign(c, add(c, mul(a, b)));   //c += a * b;
	            assign(c, mod(c, Value.value(6))); //c %= 6;
	        	invoke(out, "println", append(Value.value("a = "), a)); //System.out.println("a = " + a);
	        	invoke(out, "println", append(Value.value("b = "), b)); //System.out.println("b = " + b);
	        	invoke(out, "println", append(Value.value("c = "), c)); //System.out.println("c = " + c);
				runReturn();
    	    }
        }
       );
	}
	
}
