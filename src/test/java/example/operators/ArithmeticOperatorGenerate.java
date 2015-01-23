package example.operators;

import java.util.Random;

import cn.wensiqun.asmsupport.core.block.classes.method.common.StaticMethodBodyInternal;
import cn.wensiqun.asmsupport.core.clazz.AClass;
import cn.wensiqun.asmsupport.core.clazz.AClassFactory;
import cn.wensiqun.asmsupport.core.creator.clazz.ClassCreatorInternal;
import cn.wensiqun.asmsupport.core.definition.value.Value;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.core.operator.method.MethodInvoker;
import cn.wensiqun.asmsupport.core.operator.numerical.arithmetic.Addition;
import cn.wensiqun.asmsupport.org.objectweb.asm.Opcodes;
import example.AbstractExample;

public class ArithmeticOperatorGenerate extends AbstractExample {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ClassCreatorInternal creator = new ClassCreatorInternal(Opcodes.V1_5, Opcodes.ACC_PUBLIC, "generated.operators.ArithmeticOperatorGenerateExample", null, null);

		//printIn方法
		creator.createStaticMethod(0, "printInt", new AClass[]{AClass.STRING_ACLASS, AClass.INT_ACLASS}, new String[]{"s", "i"}, null, null, new StaticMethodBodyInternal(){

			@Override
			public void body(LocalVariable... argus) {
				_invoke(systemOut, "println", _append(argus[0], Value.value(" = "), argus[1]));
				_return();
			}
			
		});
		
		//printIn方法
		creator.createStaticMethod(0, "printFloat", new AClass[]{AClass.STRING_ACLASS, AClass.FLOAT_ACLASS}, new String[]{"s", "f"}, null, null, new StaticMethodBodyInternal(){

			@Override
			public void body(LocalVariable... argus) {
				_invoke(systemOut, "println", _append(argus[0], Value.value(" = "), argus[1]));
				_return();
			}
			
		});		
		
		creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, "main", 
				new AClass[] { AClassFactory.getProductClass(String[].class) }, new String[] { "args" }, null, null, new StaticMethodBodyInternal() {

					@Override
					public void body(LocalVariable... argus) {
                        //Random rand = new Random();
						LocalVariable rand = _createVariable("rand", AClassFactory.getProductClass(Random.class), false, _new(AClassFactory.getProductClass(Random.class)));
						
						//rand.nextInt(100) + 1
						Addition add1 = _add(_invoke(rand, "nextInt", Value.value(100)), Value.value(1));
						
						//int j = rand.nextInt(100) + 1;
						LocalVariable j = _createVariable("j", AClass.INT_ACLASS, false, add1);
						
						//int k = rand.nextInt(100) + 1;
						LocalVariable k = _createVariable("k", AClass.INT_ACLASS, false, add1);
						
						//printInt("j", j);
						_invokeStatic(getMethodOwner(), "printInt", Value.value("j"), j);

						//printInt("k", k);
						_invokeStatic(getMethodOwner(), "printInt", Value.value("k"), k);
						
						//j + k
						Addition add2 = _add(j, k);
						//int i = j + k;
						LocalVariable i = _createVariable("i", AClass.INT_ACLASS, false, add2);
						
						//printInt("j + k", i);
						_invokeStatic(getMethodOwner(), "printInt", Value.value("j + k"), i);
						
						//i = j - k;
						_assign(i, _sub(j, k));
						
						//printInt("j - k", i);
						_invokeStatic(getMethodOwner(), "printInt", Value.value("j - k"), i);
						
                        //i = k / j;
						_assign(i, _div(k, j));
						
						//printInt("k / j", i);
						_invokeStatic(getMethodOwner(), "printInt", Value.value("k / j"), i);
						
						//i = k * j;
						_assign(i, _mul(k, j));
						
						//printInt("k * j", i);
						_invokeStatic(getMethodOwner(), "printInt", Value.value("k * j"), i);
						
						//i = k % j;
						_assign(i, _mod(k, j));
						
						//printInt("k % j", i);
						_invokeStatic(getMethodOwner(), "printInt", Value.value("k % j"), i);
						
						//j %= k;
						_assign(j, _mod(j, k));
						
						//printInt("j %= k", j);
						_invokeStatic(getMethodOwner(), "printInt", Value.value("j %= k"), j);
						
						
						//rand.nextFloat()
						MethodInvoker nextFloat = _invoke(rand, "nextFloat");
						
						//v = rand.nextFloat();
						LocalVariable v = _createVariable("v", AClass.FLOAT_ACLASS, false, nextFloat);
						
						//w = rand.nextFloat();
						LocalVariable w = _createVariable("w", AClass.FLOAT_ACLASS, false, nextFloat);
						
						//printFloat("v", v);
						_invokeStatic(getMethodOwner(), "printFloat", Value.value("v"), v);
						
						//printFloat("w", w);
						_invokeStatic(getMethodOwner(), "printFloat", Value.value("w"), w);
						
						//u = v + w;
						LocalVariable u = _createVariable("u", AClass.FLOAT_ACLASS, false, _add(v,w));
						
						//printFloat("v + w", u);
						_invokeStatic(getMethodOwner(), "printFloat", Value.value("v + w"), u);

						//u = v - w;
						_assign(u, _sub(v, w));
						
						//printFloat("v - w", u);
						_invokeStatic(getMethodOwner(), "printFloat", Value.value("v - w"), u);
						
						//u = v * w;
						_assign(u, _mul(v, w));
						
						//printFloat("v * w", u);
						_invokeStatic(getMethodOwner(), "printFloat", Value.value("v * w"), u);
						
						//u = v / w;
						_assign(u, _div(v, w));
						
						//printFloat("v / w", u);
						_invokeStatic(getMethodOwner(), "printFloat", Value.value("v / w"), u);
						
						//u += v;
						_assign(u, _add(u, v));
						
						//printFloat("u += v", u);
						_invokeStatic(getMethodOwner(), "printFloat", Value.value("u += v"), u);
						
						//u -= v;
						_assign(u, _sub(u, v));
						
						//printFloat("u -= v", u);
						_invokeStatic(getMethodOwner(), "printFloat", Value.value("u -= v"), u);
						
						//u *= v;
						_assign(u, _mul(u, v));
						
						//printFloat("u *= v", u);
						_invokeStatic(getMethodOwner(), "printFloat", Value.value("u *= v"), u);
						
						//u /= v;
						_assign(u, _div(u, v));
						
						//printFloat("u /= v", u);
						_invokeStatic(getMethodOwner(), "printFloat", Value.value("u /= v"), u);
						
						_return();
					}
				});
		generate(creator);
	}

	/*main函数的方法中将生成如下三个method。 main1换成main*/
	
	static void printInt(String s, int i) {
		System.out.println(s + " = " + i);
	}

	static void printFloat(String s, float f) {
		System.out.println(s + " = " + f);
	}

	public static void main1(String[] args) {
		Random rand = new Random();
		int i, j, k;
		j = rand.nextInt(100) + 1;
		k = rand.nextInt(100) + 1;
		printInt("j", j);
		printInt("k", k);
		i = j + k;
		printInt("j + k", i);
		i = j - k;
		printInt("j - k", i);
		i = k / j;
		printInt("k / j", i);
		i = k * j;
		printInt("k * j", i);
		i = k % j;
		printInt("k % j", i);
		j %= k;
		printInt("j %= k", j);
		// Floating-point number tests:
		float u, v, w; // applies to doubles, too
		v = rand.nextFloat();
		w = rand.nextFloat();
		printFloat("v", v);
		printFloat("w", w);
		u = v + w;
		printFloat("v + w", u);
		u = v - w;
		printFloat("v - w", u);
		u = v * w;
		printFloat("v * w", u);
		u = v / w;
		printFloat("v / w", u);
		// the following also works for
		// char, byte, short, int, long,
		// and double:
		u += v;
		printFloat("u += v", u);
		u -= v;
		printFloat("u -= v", u);
		u *= v;
		printFloat("u *= v", u);
		u /= v;
		printFloat("u /= v", u);
	}

}
