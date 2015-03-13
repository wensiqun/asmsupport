package sample.operators;

import java.util.Random;

import sample.AbstractExample;
import cn.wensiqun.asmsupport.core.block.method.common.StaticMethodBodyInternal;
import cn.wensiqun.asmsupport.core.clazz.AClass;
import cn.wensiqun.asmsupport.core.clazz.AClassFactory;
import cn.wensiqun.asmsupport.core.creator.clazz.ClassCreator;
import cn.wensiqun.asmsupport.core.definition.value.Value;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.org.objectweb.asm.Opcodes;

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
				"main", new AClass[]{AClassFactory.getProductClass(String[].class)}, new String[]{"args"}, null, null,
				new StaticMethodBodyInternal(){

			@Override
			public void body(LocalVariable... argus) {
				//Random rand = new Random();
				LocalVariable rand = _var("rand", AClassFactory.getProductClass(Random.class), false, _new(AClassFactory.getProductClass(Random.class)));
				
				//int i = rand.nextInt(100);
				LocalVariable i = _var("i", AClass.INT_ACLASS, false, _invoke(rand, "nextInt", Value.value(100)));
				
			    //int j = rand.nextInt(100);
				LocalVariable j = _var("j", AClass.INT_ACLASS, false, _invoke(rand, "nextInt", Value.value(100)));
				
			    //System.out.println("i = " + i);
				_invoke(systemOut, "println", _append(Value.value("i = "), i));
				
			    //System.out.println("j = " + j);
				_invoke(systemOut, "println", _append(Value.value("j = "), j));
				
			    //System.out.println("i > j is " + (i > j));
				_invoke(systemOut, "println", _append(Value.value("i > j is "), _gt(i, j)));
				
			    //System.out.println("i < j is " + (i < j));
				_invoke(systemOut, "println", _append(Value.value("i < j is "), _lt(i, j)));
				
			    //System.out.println("i >= j is " + (i >= j));
				_invoke(systemOut, "println", _append(Value.value("i >= j is "), _ge(i, j)));
				
			    //System.out.println("i <= j is " + (i <= j));
				_invoke(systemOut, "println", _append(Value.value("i <= j is "), _le(i, j)));
				
			    //System.out.println("i == j is " + (i == j));
				_invoke(systemOut, "println", _append(Value.value("i == j is "), _eq(i, j)));
				
			    //System.out.println("i != j is " + (i != j));
				_invoke(systemOut, "println", _append(Value.value("i != j is "), _ne(i, j)));
				
			    //System.out.println("(i < 10) && (j < 10) is " + ((i < 10) && (j < 10)));
				_invoke(systemOut, "println", _append(Value.value("(i < 10) && (j < 10) is "), _and(_lt(i, Value.value(10)), _lt(j, Value.value(10)))));
				
			    //System.out.println("(i < 10) || (j < 10) is " + ((i < 10) || (j < 10)));
				_invoke(systemOut, "println", _append(Value.value("(i < 10) || (j < 10) is "), _or(_lt(i, Value.value(10)), _lt(j, Value.value(10)))));
				
				_return();
			}
        });
		generate(creator);
	}
	
}
