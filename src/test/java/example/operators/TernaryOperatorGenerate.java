package example.operators;


import org.objectweb.asm.Opcodes;

import cn.wensiqun.asmsupport.block.method.common.StaticMethodBody;
import cn.wensiqun.asmsupport.clazz.AClass;
import cn.wensiqun.asmsupport.clazz.AClassFactory;
import cn.wensiqun.asmsupport.creator.ClassCreator;
import cn.wensiqun.asmsupport.definition.value.Value;
import cn.wensiqun.asmsupport.definition.variable.LocalVariable;
import example.AbstractExample;

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
		creator.createStaticMethod("main", new AClass[]{AClassFactory.getProductClass(String[].class)}, new String[]{"args"}, null, null,
				Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, new StaticMethodBody(){

			@Override
			public void body(LocalVariable... argus) {
				//int i = 10;
				LocalVariable i = createVariable("i", AClass.INT_ACLASS, false, Value.value(10));
				
				//ternary方法将生成三元操作
				//int k = i < 0 ? -i : i;
				LocalVariable k = createVariable("k", AClass.INT_ACLASS, false, ternary(lessThan(i, Value.value(0)), neg(i), i));
				
				//System.out.print("Absolute value of ");
				invoke(systemOut, "print", Value.value("Absolute value of "));
				
				//System.out.println(i + " is " + k);
				invoke(systemOut, "println", append(i, Value.value(" is "), k));
				
				//i = -10;
				assign(i, Value.value(-10));
				
				//k = i < 0 ? -i : i;
				assign(k, ternary(lessThan(i, Value.value(0)), neg(i), i));

				//System.out.print("Absolute value of ");
				invoke(systemOut, "print", Value.value("Absolute value of "));
				
				//System.out.println(i + " is " + k);
				invoke(systemOut, "println", append(i, Value.value(" is "), k));
				
				runReturn();
			}
        });
		generate(creator);
	}

}
