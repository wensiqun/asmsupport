package cn.wensiqun.asmsupport.sample.core.operators;


import cn.wensiqun.asmsupport.core.block.method.common.KernelStaticMethodBody;
import cn.wensiqun.asmsupport.core.builder.impl.ClassBuilderImpl;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.org.objectweb.asm.Opcodes;
import cn.wensiqun.asmsupport.sample.core.AbstractExample;
import cn.wensiqun.asmsupport.standard.def.clazz.IClass;

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
		
		ClassBuilderImpl creator = new ClassBuilderImpl(Opcodes.V1_5, Opcodes.ACC_PUBLIC , "generated.operators.TernaryOperatorGenerateExample", null, null);
		
		/*
		 * 生成一个main方法，方法内容和willGenerate内容相同
		 */
		creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, 
				"main", new IClass[]{classLoader.getType(String[].class)}, new String[]{"args"}, null, null,
				new KernelStaticMethodBody(){

			@Override
			public void body(LocalVariable... argus) {
				//int i = 10;
				LocalVariable i = var("i", classLoader.getType(int.class), val(10));
				
				//ternary方法将生成三元操作
				//int k = i < 0 ? -i : i;
				LocalVariable k = var("k", classLoader.getType(int.class), ternary(lt(i, val(0)), neg(i), i));
				
				//System.out.print("Absolute value of ");
				call(systemOut, "print", val("Absolute value of "));
				
				//System.out.println(i + " is " + k);
				call(systemOut, "println", stradd(i, val(" is "), k));
				
				//i = -10;
				assign(i, val(-10));
				
				//k = i < 0 ? -i : i;
				assign(k, ternary(lt(i, val(0)), neg(i), i));

				//System.out.print("Absolute value of ");
				call(systemOut, "print", val("Absolute value of "));
				
				//System.out.println(i + " is " + k);
				call(systemOut, "println", stradd(i, val(" is "), k));
				
				return_();
			}
        });
		generate(creator);
	}

}
