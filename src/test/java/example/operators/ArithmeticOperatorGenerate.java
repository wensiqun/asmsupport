package example.operators;

import java.util.Random;

import org.objectweb.asm.Opcodes;

import cn.wensiqun.asmsupport.block.method.common.StaticMethodBody;
import cn.wensiqun.asmsupport.clazz.AClass;
import cn.wensiqun.asmsupport.clazz.AClassFactory;
import cn.wensiqun.asmsupport.creator.ClassCreator;
import cn.wensiqun.asmsupport.definition.value.Value;
import cn.wensiqun.asmsupport.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.operators.method.MethodInvoker;
import cn.wensiqun.asmsupport.operators.numerical.arithmetic.Addition;
import example.AbstractExample;

public class ArithmeticOperatorGenerate extends AbstractExample {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ClassCreator creator = new ClassCreator(Opcodes.V1_5, Opcodes.ACC_PUBLIC, "generated.operators.ArithmeticOperatorGenerateExample", null, null);

		//printIn方法
		creator.createStaticMethod("printInt", new AClass[]{AClass.STRING_ACLASS, AClass.INT_ACLASS}, new String[]{"s", "i"}, null, null, 0, new StaticMethodBody(){

			@Override
			public void body(LocalVariable... argus) {
				invoke(systemOut, "println", append(argus[0], Value.value(" = "), argus[1]));
				runReturn();
			}
			
		});
		
		//printIn方法
		creator.createStaticMethod("printFloat", new AClass[]{AClass.STRING_ACLASS, AClass.FLOAT_ACLASS}, new String[]{"s", "f"}, null, null, 0, new StaticMethodBody(){

			@Override
			public void body(LocalVariable... argus) {
				invoke(systemOut, "println", append(argus[0], Value.value(" = "), argus[1]));
				runReturn();
			}
			
		});		
		
		creator.createStaticMethod("main", new AClass[] { AClassFactory.getProductClass(String[].class) }, new String[] { "args" }, null, null, Opcodes.ACC_PUBLIC +

				Opcodes.ACC_STATIC, new StaticMethodBody() {

					@Override
					public void body(LocalVariable... argus) {
                        //Random rand = new Random();
						LocalVariable rand = createVariable("rand", AClassFactory.getProductClass(Random.class), false, invokeConstructor(AClassFactory.getProductClass(Random.class)));
						
						//rand.nextInt(100) + 1
						Addition add1 = add(invoke(rand, "nextInt", Value.value(100)), Value.value(1));
						
						//int j = rand.nextInt(100) + 1;
						LocalVariable j = createVariable("j", AClass.INT_ACLASS, false, add1);
						
						//int k = rand.nextInt(100) + 1;
						LocalVariable k = createVariable("k", AClass.INT_ACLASS, false, add1);
						
						//printInt("j", j);
						invokeStatic(getMethodOwner(), "printInt", Value.value("j"), j);

						//printInt("k", k);
						invokeStatic(getMethodOwner(), "printInt", Value.value("k"), k);
						
						//j + k
						Addition add2 = add(j, k);
						//int i = j + k;
						LocalVariable i = createVariable("i", AClass.INT_ACLASS, false, add2);
						
						//printInt("j + k", i);
						invokeStatic(getMethodOwner(), "printInt", Value.value("j + k"), i);
						
						//i = j - k;
						assign(i, sub(j, k));
						
						//printInt("j - k", i);
						invokeStatic(getMethodOwner(), "printInt", Value.value("j - k"), i);
						
                        //i = k / j;
						assign(i, div(k, j));
						
						//printInt("k / j", i);
						invokeStatic(getMethodOwner(), "printInt", Value.value("k / j"), i);
						
						//i = k * j;
						assign(i, mul(k, j));
						
						//printInt("k * j", i);
						invokeStatic(getMethodOwner(), "printInt", Value.value("k * j"), i);
						
						//i = k % j;
						assign(i, mod(k, j));
						
						//printInt("k % j", i);
						invokeStatic(getMethodOwner(), "printInt", Value.value("k % j"), i);
						
						//j %= k;
						assign(j, mod(j, k));
						
						//printInt("j %= k", j);
						invokeStatic(getMethodOwner(), "printInt", Value.value("j %= k"), j);
						
						
						//rand.nextFloat()
						MethodInvoker nextFloat = invoke(rand, "nextFloat");
						
						//v = rand.nextFloat();
						LocalVariable v = createVariable("v", AClass.FLOAT_ACLASS, false, nextFloat);
						
						//w = rand.nextFloat();
						LocalVariable w = createVariable("w", AClass.FLOAT_ACLASS, false, nextFloat);
						
						//printFloat("v", v);
						invokeStatic(getMethodOwner(), "printFloat", Value.value("v"), v);
						
						//printFloat("w", w);
						invokeStatic(getMethodOwner(), "printFloat", Value.value("w"), w);
						
						//u = v + w;
						LocalVariable u = createVariable("u", AClass.FLOAT_ACLASS, false, add(v,w));
						
						//printFloat("v + w", u);
						invokeStatic(getMethodOwner(), "printFloat", Value.value("v + w"), u);

						//u = v - w;
						assign(u, sub(v, w));
						
						//printFloat("v - w", u);
						invokeStatic(getMethodOwner(), "printFloat", Value.value("v - w"), u);
						
						//u = v * w;
						assign(u, mul(v, w));
						
						//printFloat("v * w", u);
						invokeStatic(getMethodOwner(), "printFloat", Value.value("v * w"), u);
						
						//u = v / w;
						assign(u, div(v, w));
						
						//printFloat("v / w", u);
						invokeStatic(getMethodOwner(), "printFloat", Value.value("v / w"), u);
						
						//u += v;
						assign(u, add(u, v));
						
						//printFloat("u += v", u);
						invokeStatic(getMethodOwner(), "printFloat", Value.value("u += v"), u);
						
						//u -= v;
						assign(u, sub(u, v));
						
						//printFloat("u -= v", u);
						invokeStatic(getMethodOwner(), "printFloat", Value.value("u -= v"), u);
						
						//u *= v;
						assign(u, mul(u, v));
						
						//printFloat("u *= v", u);
						invokeStatic(getMethodOwner(), "printFloat", Value.value("u *= v"), u);
						
						//u /= v;
						assign(u, div(u, v));
						
						//printFloat("u /= v", u);
						invokeStatic(getMethodOwner(), "printFloat", Value.value("u /= v"), u);
						
						runReturn();
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
