package example.operators;

import java.util.Random;

import org.objectweb.asm.Opcodes;

import cn.wensiqun.asmsupport.block.method.common.StaticMethodBody;
import cn.wensiqun.asmsupport.clazz.AClass;
import cn.wensiqun.asmsupport.clazz.AClassFactory;
import cn.wensiqun.asmsupport.creator.ClassCreator;
import cn.wensiqun.asmsupport.definition.value.Value;
import cn.wensiqun.asmsupport.definition.variable.LocalVariable;
import example.AbstractExample;

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
		creator.createStaticMethod("main", new AClass[]{AClassFactory.getProductClass(String[].class)}, new String[]{"args"}, null, null,
				Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, new StaticMethodBody(){

			@Override
			public void body(LocalVariable... argus) {
				//Random rand = new Random();
				LocalVariable rand = createVariable("rand", AClassFactory.getProductClass(Random.class), false, invokeConstructor(AClassFactory.getProductClass(Random.class)));
				
				//int i = rand.nextInt(100);
				LocalVariable i = createVariable("i", AClass.INT_ACLASS, false, invoke(rand, "nextInt", Value.value(100)));
				
			    //int j = rand.nextInt(100);
				LocalVariable j = createVariable("j", AClass.INT_ACLASS, false, invoke(rand, "nextInt", Value.value(100)));
				
			    //System.out.println("i = " + i);
				invoke(systemOut, "println", append(Value.value("i = "), i));
				
			    //System.out.println("j = " + j);
				invoke(systemOut, "println", append(Value.value("j = "), j));
				
			    //System.out.println("i > j is " + (i > j));
				invoke(systemOut, "println", append(Value.value("i > j is "), greaterThan(i, j)));
				
			    //System.out.println("i < j is " + (i < j));
				invoke(systemOut, "println", append(Value.value("i < j is "), lessThan(i, j)));
				
			    //System.out.println("i >= j is " + (i >= j));
				invoke(systemOut, "println", append(Value.value("i >= j is "), greaterEqual(i, j)));
				
			    //System.out.println("i <= j is " + (i <= j));
				invoke(systemOut, "println", append(Value.value("i <= j is "), lessEqual(i, j)));
				
			    //System.out.println("i == j is " + (i == j));
				invoke(systemOut, "println", append(Value.value("i == j is "), equal(i, j)));
				
			    //System.out.println("i != j is " + (i != j));
				invoke(systemOut, "println", append(Value.value("i != j is "), notEqual(i, j)));
				
			    //System.out.println("(i < 10) && (j < 10) is " + ((i < 10) && (j < 10)));
				invoke(systemOut, "println", append(Value.value("(i < 10) && (j < 10) is "), conditionalAnd(lessThan(i, Value.value(10)), lessThan(j, Value.value(10)))));
				
			    //System.out.println("(i < 10) || (j < 10) is " + ((i < 10) || (j < 10)));
				invoke(systemOut, "println", append(Value.value("(i < 10) || (j < 10) is "), conditionalOr(lessThan(i, Value.value(10)), lessThan(j, Value.value(10)))));
				
				runReturn();
			}
        });
		generate(creator);
	}
	
}
