package cn.wensiqun.asmsupport.sample.core.operators;

import java.util.Random;

import cn.wensiqun.asmsupport.core.block.method.common.KernelStaticMethodBody;
import cn.wensiqun.asmsupport.core.builder.impl.ClassBuilderImpl;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.core.operator.method.MethodInvoker;
import cn.wensiqun.asmsupport.core.operator.numerical.arithmetic.KernelAdd;
import cn.wensiqun.asmsupport.org.objectweb.asm.Opcodes;
import cn.wensiqun.asmsupport.sample.core.AbstractExample;
import cn.wensiqun.asmsupport.standard.def.clazz.AClass;

public class ArithmeticOperatorGenerate extends AbstractExample {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ClassBuilderImpl creator = new ClassBuilderImpl(Opcodes.V1_5, Opcodes.ACC_PUBLIC, "generated.operators.ArithmeticOperatorGenerateExample", null, null);

		//printIn方法
		creator.createStaticMethod(0, "printInt", new AClass[]{classLoader.getType(String.class), classLoader.getType(int.class)}, new String[]{"s", "i"}, null, null, new KernelStaticMethodBody(){

			@Override
			public void body(LocalVariable... argus) {
				call(systemOut, "println", stradd(argus[0], val(" = "), argus[1]));
				return_();
			}
			
		});
		
		//printIn方法
		creator.createStaticMethod(0, "printFloat", new AClass[]{classLoader.getType(String.class), classLoader.getType(float.class)}, new String[]{"s", "f"}, null, null, new KernelStaticMethodBody(){

			@Override
			public void body(LocalVariable... argus) {
				call(systemOut, "println", stradd(argus[0], val(" = "), argus[1]));
				return_();
			}
			
		});		
		
		creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, "main", 
				new AClass[] { classLoader.getType(String[].class) }, new String[] { "args" }, null, null, new KernelStaticMethodBody() {

					@Override
					public void body(LocalVariable... argus) {
                        //Random rand = new Random();
						LocalVariable rand = var("rand", classLoader.getType(Random.class), new_(classLoader.getType(Random.class)));
						
						//rand.nextInt(100) + 1
						KernelAdd add1 = add(call(rand, "nextInt", val(100)), val(1));
						
						//int j = rand.nextInt(100) + 1;
						LocalVariable j = var("j", classLoader.getType(int.class), add1);
						
						//int k = rand.nextInt(100) + 1;
						LocalVariable k = var("k", classLoader.getType(int.class), add1);
						
						//printInt("j", j);
						call(getMethodDeclaringClass(), "printInt", val("j"), j);

						//printInt("k", k);
						call(getMethodDeclaringClass(), "printInt", val("k"), k);
						
						//j + k
						KernelAdd add2 = add(j, k);
						//int i = j + k;
						LocalVariable i = var("i", classLoader.getType(int.class), add2);
						
						//printInt("j + k", i);
						call(getMethodDeclaringClass(), "printInt", val("j + k"), i);
						
						//i = j - k;
						assign(i, sub(j, k));
						
						//printInt("j - k", i);
						call(getMethodDeclaringClass(), "printInt", val("j - k"), i);
						
                        //i = k / j;
						assign(i, div(k, j));
						
						//printInt("k / j", i);
						call(getMethodDeclaringClass(), "printInt", val("k / j"), i);
						
						//i = k * j;
						assign(i, mul(k, j));
						
						//printInt("k * j", i);
						call(getMethodDeclaringClass(), "printInt", val("k * j"), i);
						
						//i = k % j;
						assign(i, mod(k, j));
						
						//printInt("k % j", i);
						call(getMethodDeclaringClass(), "printInt", val("k % j"), i);
						
						//j %= k;
						assign(j, mod(j, k));
						
						//printInt("j %= k", j);
						call(getMethodDeclaringClass(), "printInt", val("j %= k"), j);
						
						
						//rand.nextFloat()
						MethodInvoker nextFloat = call(rand, "nextFloat");
						
						//v = rand.nextFloat();
						LocalVariable v = var("v", classLoader.getType(float.class), nextFloat);
						
						//w = rand.nextFloat();
						LocalVariable w = var("w", classLoader.getType(float.class), nextFloat);
						
						//printFloat("v", v);
						call(getMethodDeclaringClass(), "printFloat", val("v"), v);
						
						//printFloat("w", w);
						call(getMethodDeclaringClass(), "printFloat", val("w"), w);
						
						//u = v + w;
						LocalVariable u = var("u", classLoader.getType(float.class), add(v,w));
						
						//printFloat("v + w", u);
						call(getMethodDeclaringClass(), "printFloat", val("v + w"), u);

						//u = v - w;
						assign(u, sub(v, w));
						
						//printFloat("v - w", u);
						call(getMethodDeclaringClass(), "printFloat", val("v - w"), u);
						
						//u = v * w;
						assign(u, mul(v, w));
						
						//printFloat("v * w", u);
						call(getMethodDeclaringClass(), "printFloat", val("v * w"), u);
						
						//u = v / w;
						assign(u, div(v, w));
						
						//printFloat("v / w", u);
						call(getMethodDeclaringClass(), "printFloat", val("v / w"), u);
						
						//u += v;
						assign(u, add(u, v));
						
						//printFloat("u += v", u);
						call(getMethodDeclaringClass(), "printFloat", val("u += v"), u);
						
						//u -= v;
						assign(u, sub(u, v));
						
						//printFloat("u -= v", u);
						call(getMethodDeclaringClass(), "printFloat", val("u -= v"), u);
						
						//u *= v;
						assign(u, mul(u, v));
						
						//printFloat("u *= v", u);
						call(getMethodDeclaringClass(), "printFloat", val("u *= v"), u);
						
						//u /= v;
						assign(u, div(u, v));
						
						//printFloat("u /= v", u);
						call(getMethodDeclaringClass(), "printFloat", val("u /= v"), u);
						
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
