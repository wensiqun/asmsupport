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
public class Increment extends CreateMethod {

	public Increment(ClassCreator creator) {
		super(creator);
	}

	@Override
	public void createMethod() {
		creator.createMethod("increment", null, null, null, null, Opcodes.ACC_PUBLIC,
		        new CommonMethodBody(){
	        @Override
            public void body(LocalVariable... argus) {
				LocalVariable a = createVariable("a", AClass.INT_ACLASS, false, Value.value(1));  //int a = 1;
	        	LocalVariable b = createVariable("b", AClass.INT_ACLASS, false, Value.value(2));  //int b = 2;
	        	LocalVariable c = createVariable("c", AClass.INT_ACLASS, false, beforeInc(b)); //int c = ++b;
				LocalVariable d = createVariable("d", AClass.INT_ACLASS, false, afterInc(a)); //int d = a++;
	        	afterInc(c); //c++;
	        	invoke(out, "println", append(Value.value("a = "), a)); //System.out.println("a = " + a);
	        	invoke(out, "println", append(Value.value("b = "), b)); //System.out.println("b = " + b);
	        	invoke(out, "println", append(Value.value("c = "), c)); //System.out.println("c = " + c);
	        	invoke(out, "println", append(Value.value("d = "), d)); //System.out.println("d = " + d);
			    runReturn();
    	    }
        }
);
	}
	
}
