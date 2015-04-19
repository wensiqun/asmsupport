package oldApi.operators;


import cn.wensiqun.asmsupport.core.AbstractExample;
import cn.wensiqun.asmsupport.core.block.method.common.MethodBodyInternal;
import cn.wensiqun.asmsupport.core.block.method.common.StaticMethodBodyInternal;
import cn.wensiqun.asmsupport.core.clazz.AClassFactory;
import cn.wensiqun.asmsupport.core.creator.clazz.ClassCreator;
import cn.wensiqun.asmsupport.core.definition.value.Value;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.org.objectweb.asm.Opcodes;
import cn.wensiqun.asmsupport.standard.clazz.AClass;

public class CrementOperatorGenerate extends AbstractExample {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		ClassCreator creator = new ClassCreator(Opcodes.V1_5, Opcodes.ACC_PUBLIC, "generated.operators.CrementOperatorGenerateExample", null, null);
		
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
		creator.createMethod(Opcodes.ACC_PUBLIC, "demonstrate", null, null, null, null, new MethodBodyInternal() {
			@Override
			public void body(LocalVariable... argus) {
				call(systemOut, "println", Value.value("******************************demonstrate***************************"));
				
				//int a = 1;
			    LocalVariable a = var("a", AClassFactory.getType(int.class), Value.value(1));
			    //int b = 2;
			    LocalVariable b = var("b", AClassFactory.getType(int.class), Value.value(2));
			    //int c = ++b;
			    LocalVariable c = var("c", AClassFactory.getType(int.class), preinc(b));
			    //d = a++;
			    LocalVariable d = var("d", AClassFactory.getType(int.class), postinc(a));
			    //c++;
			    postinc(c);
			    
			    //System.out.println("a = " + a);
				call(systemOut, "println", stradd(Value.value("a = "), a)); 
				call(systemOut, "println", stradd(Value.value("b = "), b)); 
				call(systemOut, "println", stradd(Value.value("c = "), c)); 
				call(systemOut, "println", stradd(Value.value("d = "), d)); 
				return_();
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
		creator.createMethod(Opcodes.ACC_PUBLIC, "incrementAndDecrement", null, null, null, null, new MethodBodyInternal() {
		    @Override
		    public void body(LocalVariable... argus) {
				call(systemOut, "println", Value.value("******************************incrementAndDecrement***************************"));
				//int count = 10;
				LocalVariable count = var("count", AClassFactory.getType(int.class), Value.value(10));
				// ++count
				preinc(count); 
				// --count;
				postdec(count); 
				// System.out.println("count = " +  count);
				call(systemOut, "println", stradd(Value.value("count = "), count));
				return_();
			}
		});
		
		creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, "main", 
				new AClass[]{AClassFactory.getType(String[].class)}, new String[]{"args"}, null, null,
				new StaticMethodBodyInternal(){

			@Override
			public void body(LocalVariable... argus) {
				LocalVariable currentObj = var("currentObj", getMethodOwner(), new_(getMethodOwner()));
				call(currentObj, "demonstrate");
				call(currentObj, "incrementAndDecrement");
				return_();
			}
        });
		generate(creator);
	}
}
