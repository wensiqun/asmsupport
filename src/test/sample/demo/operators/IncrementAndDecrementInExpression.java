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
public class IncrementAndDecrementInExpression extends CreateMethod {

	public IncrementAndDecrementInExpression(ClassCreator creator) {
		super(creator);
	}

	@Override
	public void createMethod() {
		creator.createMethod("incrementAndDecrementInExpression", null, null, AClass.INT_ACLASS, null, Opcodes.ACC_PUBLIC,
		        new CommonMethodBody(){
	        @Override
            public void body(LocalVariable... argus) {
				LocalVariable numA = createVariable("numA", AClass.INT_ACLASS, false, Value.value(5));  //int numA = 10;
				LocalVariable numB = createVariable("numB", AClass.INT_ACLASS, false, Value.value(10));  //int numB = 10;
				LocalVariable numC = createVariable("numC", AClass.INT_ACLASS, false, Value.value(0));  //int numC = 10;
				assign(numC, add(beforeInc(numA), numB)); //numC = ++numA + numB;
	        	invoke(out, "println", numA); //System.out.println(numA);
	        	invoke(out, "println", numC); //System.out.println(numC);
			    runReturn(afterInc(numA));//return numA++;
    	    }
        }
);
	}

}
