package sample.operators;

import java.util.Random;

import sample.AbstractExample;
import cn.wensiqun.asmsupport.core.block.method.common.StaticMethodBodyInternal;
import cn.wensiqun.asmsupport.core.clazz.AClass;
import cn.wensiqun.asmsupport.core.clazz.AClassFactory;
import cn.wensiqun.asmsupport.core.creator.clazz.ClassCreator;
import cn.wensiqun.asmsupport.core.definition.value.Value;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.core.operator.method.MethodInvoker;
import cn.wensiqun.asmsupport.core.operator.numerical.arithmetic.Addition;
import cn.wensiqun.asmsupport.org.objectweb.asm.Opcodes;

public class ArithmeticOperatorGenerate extends AbstractExample {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ClassCreator creator = new ClassCreator(Opcodes.V1_5, Opcodes.ACC_PUBLIC, "generated.operators.ArithmeticOperatorGenerateExample", null, null);

		//printIn方法
		creator.createStaticMethod(0, "printInt", new AClass[]{AClass.STRING_ACLASS, AClass.INT_ACLASS}, new String[]{"s", "i"}, null, null, new StaticMethodBodyInternal(){

			@Override
			public void body(LocalVariable... argus) {
				call(systemOut, "println", stradd(argus[0], Value.value(" = "), argus[1]));
				return_();
			}
			
		});
		
		//printIn方法
		creator.createStaticMethod(0, "printFloat", new AClass[]{AClass.STRING_ACLASS, AClass.FLOAT_ACLASS}, new String[]{"s", "f"}, null, null, new StaticMethodBodyInternal(){

			@Override
			public void body(LocalVariable... argus) {
				call(systemOut, "println", stradd(argus[0], Value.value(" = "), argus[1]));
				return_();
			}
			
		});		
		
		creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, "main", 
				new AClass[] { AClassFactory.defType(String[].class) }, new String[] { "args" }, null, null, new StaticMethodBodyInternal() {

					@Override
					public void body(LocalVariable... argus) {
                        //Random rand = new Random();
						LocalVariable rand = var("rand", AClassFactory.defType(Random.class), false, new_(AClassFactory.defType(Random.class)));
						
						//rand.nextInt(100) + 1
						Addition add1 = add(call(rand, "nextInt", Value.value(100)), Value.value(1));
						
						//int j = rand.nextInt(100) + 1;
						LocalVariable j = var("j", AClass.INT_ACLASS, false, add1);
						
						//int k = rand.nextInt(100) + 1;
						LocalVariable k = var("k", AClass.INT_ACLASS, false, add1);
						
						//printInt("j", j);
						call(getMethodOwner(), "printInt", Value.value("j"), j);

						//printInt("k", k);
						call(getMethodOwner(), "printInt", Value.value("k"), k);
						
						//j + k
						Addition add2 = add(j, k);
						//int i = j + k;
						LocalVariable i = var("i", AClass.INT_ACLASS, false, add2);
						
						//printInt("j + k", i);
						call(getMethodOwner(), "printInt", Value.value("j + k"), i);
						
						//i = j - k;
						assign(i, sub(j, k));
						
						//printInt("j - k", i);
						call(getMethodOwner(), "printInt", Value.value("j - k"), i);
						
                        //i = k / j;
						assign(i, div(k, j));
						
						//printInt("k / j", i);
						call(getMethodOwner(), "printInt", Value.value("k / j"), i);
						
						//i = k * j;
						assign(i, mul(k, j));
						
						//printInt("k * j", i);
						call(getMethodOwner(), "printInt", Value.value("k * j"), i);
						
						//i = k % j;
						assign(i, mod(k, j));
						
						//printInt("k % j", i);
						call(getMethodOwner(), "printInt", Value.value("k % j"), i);
						
						//j %= k;
						assign(j, mod(j, k));
						
						//printInt("j %= k", j);
						call(getMethodOwner(), "printInt", Value.value("j %= k"), j);
						
						
						//rand.nextFloat()
						MethodInvoker nextFloat = call(rand, "nextFloat");
						
						//v = rand.nextFloat();
						LocalVariable v = var("v", AClass.FLOAT_ACLASS, false, nextFloat);
						
						//w = rand.nextFloat();
						LocalVariable w = var("w", AClass.FLOAT_ACLASS, false, nextFloat);
						
						//printFloat("v", v);
						call(getMethodOwner(), "printFloat", Value.value("v"), v);
						
						//printFloat("w", w);
						call(getMethodOwner(), "printFloat", Value.value("w"), w);
						
						//u = v + w;
						LocalVariable u = var("u", AClass.FLOAT_ACLASS, false, add(v,w));
						
						//printFloat("v + w", u);
						call(getMethodOwner(), "printFloat", Value.value("v + w"), u);

						//u = v - w;
						assign(u, sub(v, w));
						
						//printFloat("v - w", u);
						call(getMethodOwner(), "printFloat", Value.value("v - w"), u);
						
						//u = v * w;
						assign(u, mul(v, w));
						
						//printFloat("v * w", u);
						call(getMethodOwner(), "printFloat", Value.value("v * w"), u);
						
						//u = v / w;
						assign(u, div(v, w));
						
						//printFloat("v / w", u);
						call(getMethodOwner(), "printFloat", Value.value("v / w"), u);
						
						//u += v;
						assign(u, add(u, v));
						
						//printFloat("u += v", u);
						call(getMethodOwner(), "printFloat", Value.value("u += v"), u);
						
						//u -= v;
						assign(u, sub(u, v));
						
						//printFloat("u -= v", u);
						call(getMethodOwner(), "printFloat", Value.value("u -= v"), u);
						
						//u *= v;
						assign(u, mul(u, v));
						
						//printFloat("u *= v", u);
						call(getMethodOwner(), "printFloat", Value.value("u *= v"), u);
						
						//u /= v;
						assign(u, div(u, v));
						
						//printFloat("u /= v", u);
						call(getMethodOwner(), "printFloat", Value.value("u /= v"), u);
						
						return_();
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
