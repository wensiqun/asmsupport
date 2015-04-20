package oldApi.operators;


import cn.wensiqun.asmsupport.core.AbstractExample;
import cn.wensiqun.asmsupport.core.block.method.common.StaticMethodBodyInternal;
import cn.wensiqun.asmsupport.core.clazz.AClass;
import cn.wensiqun.asmsupport.core.clazz.AClassFactory;
import cn.wensiqun.asmsupport.core.creator.clazz.ClassCreator;
import cn.wensiqun.asmsupport.core.definition.value.Value;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.org.objectweb.asm.Opcodes;

public class TernaryOperatorGenerate extends AbstractExample {

	public static void willGenerate(String args[]) {
		int i = 10;
		int k = i < 0 ? -i : i; // get absolute value of i
		System.out.print("Absolute value of ");
		System.out.println(i + " is " + k);

		i = -10;
		k = i < 0 ? -i : i; // get absolute value of i
		System.out.print("Absolute value of ");
		System.out.println(i + " is " + k);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		ClassCreator creator = new ClassCreator(Opcodes.V1_5, Opcodes.ACC_PUBLIC , "generated.operators.TernaryOperatorGenerateExample", null, null);
		
		/*
		 * 生成一个main方法，方法内容和willGenerate内容相同
		 */
		creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, 
				"main", new AClass[]{AClassFactory.getType(String[].class)}, new String[]{"args"}, null, null,
				new StaticMethodBodyInternal(){

			@Override
			public void body(LocalVariable... argus) {
				//int i = 10;
				LocalVariable i = var("i", AClassFactory.getType(int.class), Value.value(10));
				
				//ternary方法将生成三元操作
				//int k = i < 0 ? -i : i;
				LocalVariable k = var("k", AClassFactory.getType(int.class), ternary(lt(i, Value.value(0)), neg(i), i));
				
				//System.out.print("Absolute value of ");
				call(systemOut, "print", Value.value("Absolute value of "));
				
				//System.out.println(i + " is " + k);
				call(systemOut, "println", stradd(i, Value.value(" is "), k));
				
				//i = -10;
				assign(i, Value.value(-10));
				
				//k = i < 0 ? -i : i;
				assign(k, ternary(lt(i, Value.value(0)), neg(i), i));

				//System.out.print("Absolute value of ");
				call(systemOut, "print", Value.value("Absolute value of "));
				
				//System.out.println(i + " is " + k);
				call(systemOut, "println", stradd(i, Value.value(" is "), k));
				
				return_();
			}
        });
		generate(creator);
	}

}
