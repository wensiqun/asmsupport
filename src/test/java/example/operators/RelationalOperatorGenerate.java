package example.operators;

import java.util.Random;

import cn.wensiqun.asmsupport.core.block.classes.method.common.StaticMethodBodyInternal;
import cn.wensiqun.asmsupport.core.clazz.AClass;
import cn.wensiqun.asmsupport.core.clazz.AClassFactory;
import cn.wensiqun.asmsupport.core.creator.clazz.ClassCreatorInternal;
import cn.wensiqun.asmsupport.core.definition.value.Value;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.org.objectweb.asm.Opcodes;
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
		ClassCreatorInternal creator = new ClassCreatorInternal(Opcodes.V1_5, Opcodes.ACC_PUBLIC , "generated.operators.RelationalOperatorGenerateExample", null, null);

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
				LocalVariable rand = _createVariable("rand", AClassFactory.getProductClass(Random.class), false, _new(AClassFactory.getProductClass(Random.class)));
				
				//int i = rand.nextInt(100);
				LocalVariable i = _createVariable("i", AClass.INT_ACLASS, false, _invoke(rand, "nextInt", Value.value(100)));
				
			    //int j = rand.nextInt(100);
				LocalVariable j = _createVariable("j", AClass.INT_ACLASS, false, _invoke(rand, "nextInt", Value.value(100)));
				
			    //System.out.println("i = " + i);
				_invoke(systemOut, "println", _append(Value.value("i = "), i));
				
			    //System.out.println("j = " + j);
				_invoke(systemOut, "println", _append(Value.value("j = "), j));
				
			    //System.out.println("i > j is " + (i > j));
				_invoke(systemOut, "println", _append(Value.value("i > j is "), _greaterThan(i, j)));
				
			    //System.out.println("i < j is " + (i < j));
				_invoke(systemOut, "println", _append(Value.value("i < j is "), _lessThan(i, j)));
				
			    //System.out.println("i >= j is " + (i >= j));
				_invoke(systemOut, "println", _append(Value.value("i >= j is "), _greaterEqual(i, j)));
				
			    //System.out.println("i <= j is " + (i <= j));
				_invoke(systemOut, "println", _append(Value.value("i <= j is "), _lessEqual(i, j)));
				
			    //System.out.println("i == j is " + (i == j));
				_invoke(systemOut, "println", _append(Value.value("i == j is "), _equals(i, j)));
				
			    //System.out.println("i != j is " + (i != j));
				_invoke(systemOut, "println", _append(Value.value("i != j is "), _notEquals(i, j)));
				
			    //System.out.println("(i < 10) && (j < 10) is " + ((i < 10) && (j < 10)));
				_invoke(systemOut, "println", _append(Value.value("(i < 10) && (j < 10) is "), _conditionalAnd(_lessThan(i, Value.value(10)), _lessThan(j, Value.value(10)))));
				
			    //System.out.println("(i < 10) || (j < 10) is " + ((i < 10) || (j < 10)));
				_invoke(systemOut, "println", _append(Value.value("(i < 10) || (j < 10) is "), _conditionalOr(_lessThan(i, Value.value(10)), _lessThan(j, Value.value(10)))));
				
				_return();
			}
        });
		generate(creator);
	}
	
}
