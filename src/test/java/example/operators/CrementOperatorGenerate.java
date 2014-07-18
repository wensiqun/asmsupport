package example.operators;


import org.objectweb.asm.Opcodes;

import cn.wensiqun.asmsupport.block.method.common.CommonMethodBody;
import cn.wensiqun.asmsupport.block.method.common.StaticMethodBody;
import cn.wensiqun.asmsupport.clazz.AClass;
import cn.wensiqun.asmsupport.clazz.AClassFactory;
import cn.wensiqun.asmsupport.creator.ClassCreator;
import cn.wensiqun.asmsupport.definition.value.Value;
import cn.wensiqun.asmsupport.definition.variable.LocalVariable;
import example.AbstractExample;

public class CrementOperatorGenerate extends AbstractExample {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		ClassCreator creator = new ClassCreator(Opcodes.V1_5, Opcodes.ACC_PUBLIC , "generated.operators.CrementOperatorGenerateExample", null, null);
		
		/*
		 * 对应java代码
         * public void demonstrate() {
         *     System.out.println("******************************demonstrate***************************");
         *     int a = 1;
         *     int b = 2;
         *     int c;
         *     int d;
         *     c = ++b;
         *     d = a++;
         *     c++;
         *     System.out.println("a = " + a);
         *     System.out.println("b = " + b);
         *     System.out.println("c = " + c);
         *     System.out.println("d = " + d);
         * }
		 */
		creator.createMethod("demonstrate", null, null, null, null, Opcodes.ACC_PUBLIC, new CommonMethodBody() {
			@Override
			public void body(LocalVariable... argus) {
				invoke(systemOut, "println", Value.value("******************************demonstrate***************************"));
				
				//int a = 1;
			    LocalVariable a = createVariable("a", AClass.INT_ACLASS, false, Value.value(1));
			    //int b = 2;
			    LocalVariable b = createVariable("b", AClass.INT_ACLASS, false, Value.value(2));
			    //int c = ++b;
			    LocalVariable c = createVariable("c", AClass.INT_ACLASS, false, beforeInc(b));
			    //d = a++;
			    LocalVariable d = createVariable("d", AClass.INT_ACLASS, false, afterInc(a));
			    //c++;
			    afterInc(c);
			    
			    //System.out.println("a = " + a);
				invoke(systemOut, "println", append(Value.value("a = "), a)); 
				invoke(systemOut, "println", append(Value.value("b = "), b)); 
				invoke(systemOut, "println", append(Value.value("c = "), c)); 
				invoke(systemOut, "println", append(Value.value("d = "), d)); 
				runReturn();
			}
		});
		
		/*
		 * java code:
		 * public void incrementAndDecrement(String[] argv) {
		 *    System.out.println("******************************incrementAndDecrement***************************");
         *    int count = 10;
         *    ++count;
         *    --count;
         *    System.out.println(count);
         * }
		 */
		creator.createMethod("incrementAndDecrement", null, null, null, null, Opcodes.ACC_PUBLIC, new CommonMethodBody() {
		    @Override
		    public void body(LocalVariable... argus) {
				invoke(systemOut, "println", Value.value("******************************incrementAndDecrement***************************"));
				//int count = 10;
				LocalVariable count = createVariable("count", AClass.INT_ACLASS, false, Value.value(10));
				// ++count
				beforeInc(count); 
				// --count;
				beforeDec(count); 
				// System.out.println("count = " +  count);
				invoke(systemOut, "println", append(Value.value("count = "), count));
				runReturn();
			}
		});
		
		creator.createStaticMethod("main", new AClass[]{AClassFactory.getProductClass(String[].class)}, new String[]{"args"}, null, null,
				Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, new StaticMethodBody(){

			@Override
			public void body(LocalVariable... argus) {
				LocalVariable currentObj = createVariable("currentObj", getMethodOwner(), false, invokeConstructor(getMethodOwner()));
				invoke(currentObj, "demonstrate");
				invoke(currentObj, "incrementAndDecrement");
				runReturn();
			}
        });
		generate(creator);
	}
}
