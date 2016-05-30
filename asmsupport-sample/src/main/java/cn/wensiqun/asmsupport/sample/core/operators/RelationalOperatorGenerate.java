package cn.wensiqun.asmsupport.sample.core.operators;

import cn.wensiqun.asmsupport.core.block.method.common.KernelMethodBody;
import cn.wensiqun.asmsupport.core.build.resolver.ClassResolver;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.org.objectweb.asm.Opcodes;
import cn.wensiqun.asmsupport.sample.core.AbstractExample;
import cn.wensiqun.asmsupport.standard.def.clazz.IClass;

import java.util.Random;

public class RelationalOperatorGenerate extends AbstractExample {

	/*
	 * 
	 */
	public static void willGenerate(String[] args) {
	    Random rand = new Random();
	    int i = rand.nextInt(100);
	    int j = rand.nextInt(100);
	    System.out.println("i = " + i);
	    System.out.println("j = " + j);
	    System.out.println("i > j is " + (i > j));
	    System.out.println("i < j is " + (i < j));
	    System.out.println("i >= j is " + (i >= j));
	    System.out.println("i <= j is " + (i <= j));
	    System.out.println("i == j is " + (i == j));
	    System.out.println("i != j is " + (i != j));

	    System.out.println("(i < 10) && (j < 10) is " + ((i < 10) && (j < 10)));
	    System.out.println("(i < 10) || (j < 10) is " + ((i < 10) || (j < 10)));
	}
	
	public static void main(String[] args) {
		ClassResolver creator = new ClassResolver(Opcodes.V1_5, Opcodes.ACC_PUBLIC , "generated.operators.RelationalOperatorGenerateExample", null, null);

		/*
		 * 生成一个main方法，方法内容和willGenerate内容相同 
		 * 
		 */
		creator.createMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC,
				"main", new IClass[]{classLoader.getType(String[].class)}, new String[]{"args"}, null, null,
				new KernelMethodBody(){

			@Override
			public void body(LocalVariable... argus) {
				//Random rand = new Random();
				LocalVariable rand = var("rand", classLoader.getType(Random.class), new_(classLoader.getType(Random.class)));
				
				//int i = rand.nextInt(100);
				LocalVariable i = var("i", classLoader.getType(int.class), call(rand, "nextInt", val(100)));
				
			    //int j = rand.nextInt(100);
				LocalVariable j = var("j", classLoader.getType(int.class), call(rand, "nextInt", val(100)));
				
			    //System.out.println("i = " + i);
				call(systemOut, "println", stradd(val("i = "), i));
				
			    //System.out.println("j = " + j);
				call(systemOut, "println", stradd(val("j = "), j));
				
			    //System.out.println("i > j is " + (i > j));
				call(systemOut, "println", stradd(val("i > j is "), gt(i, j)));
				
			    //System.out.println("i < j is " + (i < j));
				call(systemOut, "println", stradd(val("i < j is "), lt(i, j)));
				
			    //System.out.println("i >= j is " + (i >= j));
				call(systemOut, "println", stradd(val("i >= j is "), ge(i, j)));
				
			    //System.out.println("i <= j is " + (i <= j));
				call(systemOut, "println", stradd(val("i <= j is "), le(i, j)));
				
			    //System.out.println("i == j is " + (i == j));
				call(systemOut, "println", stradd(val("i == j is "), eq(i, j)));
				
			    //System.out.println("i != j is " + (i != j));
				call(systemOut, "println", stradd(val("i != j is "), ne(i, j)));
				
			    //System.out.println("(i < 10) && (j < 10) is " + ((i < 10) && (j < 10)));
				call(systemOut, "println", stradd(val("(i < 10) && (j < 10) is "), and(lt(i, val(10)), lt(j, val(10)))));
				
			    //System.out.println("(i < 10) || (j < 10) is " + ((i < 10) || (j < 10)));
				call(systemOut, "println", stradd(val("(i < 10) || (j < 10) is "), or(lt(i, val(10)), lt(j, val(10)))));
				
				return_();
			}
        });
		generate(creator);
	}
	
}
