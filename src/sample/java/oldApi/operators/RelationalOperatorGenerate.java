package oldApi.operators;

import java.util.Random;

import cn.wensiqun.asmsupport.core.AbstractExample;
import cn.wensiqun.asmsupport.core.block.method.common.KernelStaticMethodBody;
import cn.wensiqun.asmsupport.core.clazz.AClassFactory;
import cn.wensiqun.asmsupport.core.creator.clazz.ClassCreator;
import cn.wensiqun.asmsupport.core.definition.value.Value;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.org.objectweb.asm.Opcodes;
import cn.wensiqun.asmsupport.standard.def.clazz.AClass;

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
		ClassCreator creator = new ClassCreator(Opcodes.V1_5, Opcodes.ACC_PUBLIC , "generated.operators.RelationalOperatorGenerateExample", null, null);

		/*
		 * 生成一个main方法，方法内容和willGenerate内容相同 
		 * 
		 */
		creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, 
				"main", new AClass[]{AClassFactory.getType(String[].class)}, new String[]{"args"}, null, null,
				new KernelStaticMethodBody(){

			@Override
			public void body(LocalVariable... argus) {
				//Random rand = new Random();
				LocalVariable rand = var("rand", AClassFactory.getType(Random.class), new_(AClassFactory.getType(Random.class)));
				
				//int i = rand.nextInt(100);
				LocalVariable i = var("i", AClassFactory.getType(int.class), call(rand, "nextInt", Value.value(100)));
				
			    //int j = rand.nextInt(100);
				LocalVariable j = var("j", AClassFactory.getType(int.class), call(rand, "nextInt", Value.value(100)));
				
			    //System.out.println("i = " + i);
				call(systemOut, "println", stradd(Value.value("i = "), i));
				
			    //System.out.println("j = " + j);
				call(systemOut, "println", stradd(Value.value("j = "), j));
				
			    //System.out.println("i > j is " + (i > j));
				call(systemOut, "println", stradd(Value.value("i > j is "), gt(i, j)));
				
			    //System.out.println("i < j is " + (i < j));
				call(systemOut, "println", stradd(Value.value("i < j is "), lt(i, j)));
				
			    //System.out.println("i >= j is " + (i >= j));
				call(systemOut, "println", stradd(Value.value("i >= j is "), ge(i, j)));
				
			    //System.out.println("i <= j is " + (i <= j));
				call(systemOut, "println", stradd(Value.value("i <= j is "), le(i, j)));
				
			    //System.out.println("i == j is " + (i == j));
				call(systemOut, "println", stradd(Value.value("i == j is "), eq(i, j)));
				
			    //System.out.println("i != j is " + (i != j));
				call(systemOut, "println", stradd(Value.value("i != j is "), ne(i, j)));
				
			    //System.out.println("(i < 10) && (j < 10) is " + ((i < 10) && (j < 10)));
				call(systemOut, "println", stradd(Value.value("(i < 10) && (j < 10) is "), and(lt(i, Value.value(10)), lt(j, Value.value(10)))));
				
			    //System.out.println("(i < 10) || (j < 10) is " + ((i < 10) || (j < 10)));
				call(systemOut, "println", stradd(Value.value("(i < 10) || (j < 10) is "), or(lt(i, Value.value(10)), lt(j, Value.value(10)))));
				
				return_();
			}
        });
		generate(creator);
	}
	
}
