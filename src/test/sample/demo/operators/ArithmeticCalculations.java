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
public class ArithmeticCalculations extends CreateMethod {

	public ArithmeticCalculations(ClassCreator creator) {
		super(creator);
	}

	@Override
	public void createMethod() {
		creator.createMethod("crementforfloat", null, null, null, null, Opcodes.ACC_PUBLIC,
		        new CommonMethodBody(){
	        @Override
            public void body(LocalVariable... argus) {
				LocalVariable a = createVariable("a", AClass.DOUBLE_ACLASS, false, Value.value(12.12D));  //double a = 12.12;
	        	invoke(out, "println", afterDec(a)); //System.out.println(a--);
	        	invoke(out, "println", afterInc(a)); //System.out.println(a++);
	        	invoke(out, "println", beforeDec(a)); //System.out.println(--a);
	        	invoke(out, "println", beforeInc(a)); //System.out.println(++a);
			    runReturn();//return
    	    }
        }
);
	}

}
