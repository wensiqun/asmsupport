package example.operators;


import cn.wensiqun.asmsupport.core.block.classes.method.common.StaticMethodBodyInternal;
import cn.wensiqun.asmsupport.core.clazz.AClass;
import cn.wensiqun.asmsupport.core.clazz.AClassFactory;
import cn.wensiqun.asmsupport.core.creator.clazz.ClassCreatorInternal;
import cn.wensiqun.asmsupport.core.definition.value.Value;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.org.objectweb.asm.Opcodes;
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
		
		ClassCreatorInternal creator = new ClassCreatorInternal(Opcodes.V1_5, Opcodes.ACC_PUBLIC , "generated.operators.TernaryOperatorGenerateExample", null, null);
		
		/*
		 * 生成一个main方法，方法内容和willGenerate内容相同
		 */
		creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, 
				"main", new AClass[]{AClassFactory.getProductClass(String[].class)}, new String[]{"args"}, null, null,
				new StaticMethodBodyInternal(){

			@Override
			public void body(LocalVariable... argus) {
				//int i = 10;
				LocalVariable i = _createVariable("i", AClass.INT_ACLASS, false, Value.value(10));
				
				//ternary方法将生成三元操作
				//int k = i < 0 ? -i : i;
				LocalVariable k = _createVariable("k", AClass.INT_ACLASS, false, _ternary(_lessThan(i, Value.value(0)), _neg(i), i));
				
				//System.out.print("Absolute value of ");
				_invoke(systemOut, "print", Value.value("Absolute value of "));
				
				//System.out.println(i + " is " + k);
				_invoke(systemOut, "println", _append(i, Value.value(" is "), k));
				
				//i = -10;
				_assign(i, Value.value(-10));
				
				//k = i < 0 ? -i : i;
				_assign(k, _ternary(_lessThan(i, Value.value(0)), _neg(i), i));

				//System.out.print("Absolute value of ");
				_invoke(systemOut, "print", Value.value("Absolute value of "));
				
				//System.out.println(i + " is " + k);
				_invoke(systemOut, "println", _append(i, Value.value(" is "), k));
				
				_return();
			}
        });
		generate(creator);
	}

}
