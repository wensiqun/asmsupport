package example.operators;


import org.objectweb.asm.Opcodes;

import cn.wensiqun.asmsupport.block.control.Else;
import cn.wensiqun.asmsupport.block.control.IF;
import cn.wensiqun.asmsupport.block.method.common.StaticMethodBody;
import cn.wensiqun.asmsupport.clazz.AClass;
import cn.wensiqun.asmsupport.clazz.AClassFactory;
import cn.wensiqun.asmsupport.creator.ClassCreator;
import cn.wensiqun.asmsupport.definition.value.Value;
import cn.wensiqun.asmsupport.definition.variable.LocalVariable;



import example.AbstractExample;

/**
 * 这个例子将实现instanceof操作的字节码生产
 * 
 * 首先会创建四个类如下： 
 * 
 * class A { int i, j; }
 * 
 * class B { int i, j; }
 * 
 * class C extends A { int k; }
 * 
 * class D extends A { int k; }
 * 
 * 然后再创建一个类generated.operators.InstanceofOperatorGenerateExample，类里面有个main方法。main方法的内容如下
 * public static void main(String args[]) {
 *     A a = new A();
 *     B b = new B();
 *     C c = new C();
 *     D d = new D();
 *     
 *     if (a instanceof A)
 *     	      System.out.println("a is instance of A");
 *     if (b instanceof B)
 *     	   System.out.println("b is instance of B");
 *     if (c instanceof C)
 *     	   System.out.println("c is instance of C");
 *     if (c instanceof A)
 *     	   System.out.println("c can be cast to A");
 *     if (a instanceof C)
 *     	   System.out.println("a can be cast to C");
 *     System.out.println();
 *     
 *     A ob = d; // A reference to d
 *     System.out.println("ob now refers to d");
 *     if (ob instanceof D)
 *     	   System.out.println("ob is instance of D");
 *     
 *     System.out.println();
 *     ob = c; // A reference to c
 *     System.out.println("ob now refers to c");
 *     if (ob instanceof D)
 *     	   System.out.println("ob can be cast to D");
 *     else
 *     	   System.out.println("ob cannot be cast to D");
 *     if (ob instanceof A)
 *     	   System.out.println("ob can be cast to A");
 *     System.out.println();
 *     // all objects can be cast to Object
 *     if (a instanceof Object)
 *     	   System.out.println("a may be cast to Object");
 *     if (b instanceof Object)
 *     	   System.out.println("b may be cast to Object");
 *     if (c instanceof Object)
 *     	   System.out.println("c may be cast to Object");
 *     if (d instanceof Object)
 *     	   System.out.println("d may be cast to Object");
 *  }
 */
public class InstanceofOperatorGenerate extends AbstractExample {

	

	public static void main(String[] args) {
        //create class A
		ClassCreator ACreator = new ClassCreator(Opcodes.V1_5, Opcodes.ACC_PUBLIC , "A", null, null);
		ACreator.createGlobalVariable("i", 0, AClass.INT_ACLASS);
		ACreator.createGlobalVariable("j", 0, AClass.INT_ACLASS);
		final Class A = ACreator.startup();
		
        //create class B
		ClassCreator BCreator = new ClassCreator(Opcodes.V1_5, Opcodes.ACC_PUBLIC , "B", null, null);
		BCreator.createGlobalVariable("i", 0, AClass.INT_ACLASS);
		BCreator.createGlobalVariable("j", 0, AClass.INT_ACLASS);
		final Class B = BCreator.startup();
		
		//create class C
		ClassCreator CCreator = new ClassCreator(Opcodes.V1_5, Opcodes.ACC_PUBLIC , "C", A, null);
		CCreator.createGlobalVariable("k", 0, AClass.INT_ACLASS);
		final Class C = CCreator.startup();

		//create class D
		ClassCreator DCreator = new ClassCreator(Opcodes.V1_5, Opcodes.ACC_PUBLIC , "D", A, null);
		DCreator.createGlobalVariable("k", 0, AClass.INT_ACLASS);
		final Class D = DCreator.startup();
		
        ClassCreator creator = new ClassCreator(Opcodes.V1_5, Opcodes.ACC_PUBLIC , "generated.operators.InstanceofOperatorGenerateExample", null, null);
		
		/*
		 * 生成一个main方法
		 */
		creator.createStaticMethod("main", new AClass[]{AClassFactory.getProductClass(String[].class)}, new String[]{"args"}, null, null,
				Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, new StaticMethodBody(){

			@Override
			public void body(LocalVariable... argus) {
				AClass A_AClass = AClassFactory.getProductClass(A);
				AClass B_AClass = AClassFactory.getProductClass(B);
				AClass C_AClass = AClassFactory.getProductClass(C);
				AClass D_AClass = AClassFactory.getProductClass(D);
				
			    //A a = new A();
				LocalVariable a = createVariable("a", A_AClass, false, invokeConstructor(A_AClass));
				
				//B b = new B();
				LocalVariable b = createVariable("b", B_AClass, false, invokeConstructor(B_AClass));
				
			    //C c = new C();
				LocalVariable c = createVariable("c", C_AClass, false, invokeConstructor(C_AClass));
				
			    //D d = new D();
				LocalVariable d = createVariable("d", D_AClass, false, invokeConstructor(D_AClass));
				
				/*if (a instanceof A)
				      System.out.println("a is instance of A");*/
				ifthan(new IF(instanceOf(a, A_AClass)){
					@Override
					public void body() {
						invoke(systemOut, "println", Value.value("a is instance of A"));
					}
				});
				
				/*if (b instanceof B)
				    System.out.println("b is instance of B");*/
				ifthan(new IF(instanceOf(b, B_AClass)){
					@Override
					public void body() {
						invoke(systemOut, "println", Value.value("b is instance of B"));
					}
				});
				
				/*if (c instanceof C)
				    System.out.println("b is instance of B");*/
				ifthan(new IF(instanceOf(c, C_AClass)){
					@Override
					public void body() {
						invoke(systemOut, "println", Value.value("c is instance of C"));
					}
				});

				
				/*if (c instanceof A)
				    System.out.println("c can be cast to A");*/
				ifthan(new IF(instanceOf(c, A_AClass)){
					@Override
					public void body() {
						invoke(systemOut, "println", Value.value("c can be cast to A"));
					}
				});

				/*if (a instanceof C)
				    System.out.println("a can be cast to C");*/
				ifthan(new IF(instanceOf(a, C_AClass)){
					@Override
					public void body() {
						invoke(systemOut, "println", Value.value("a can be cast to C"));
					}
				});
				invoke(systemOut, "println");
				
				/*A ob = d; // A reference to d
				  System.out.println("ob now refers to d");*/
				LocalVariable ob = createVariable("ob", A_AClass, false, d);
				invoke(systemOut, "println", Value.value("ob now refers to d"));
				
				/* if (ob instanceof D)
		               System.out.println("ob is instance of D");
		           System.out.println();*/
				ifthan(new IF(instanceOf(ob, D_AClass)){
					@Override
					public void body() {
						invoke(systemOut, "println", Value.value("ob is instance of D"));
					}
				});
				invoke(systemOut, "println");
				
				/*     ob = c; // A reference to c
				 *     System.out.println("ob now refers to c");
				 *     if (ob instanceof D)
				 *     	   System.out.println("ob can be cast to D");
				 *     else
				 *     	   System.out.println("ob cannot be cast to D");
				 */
				assign(ob, c);
				invoke(systemOut, "println", Value.value("ob now refers to c"));
				ifthan(new IF(instanceOf(ob, D_AClass)){
					@Override
					public void body() {
						invoke(systemOut, "println", Value.value("ob can be cast to D"));
					}
				}).elsethan(new Else(){
					@Override
					public void body() {
						invoke(systemOut, "println", Value.value("ob cannot be cast to D"));
					}
					
				});
				
				/*     if (ob instanceof A)
				 *     	   System.out.println("ob can be cast to A");
				 *     System.out.println();
				 */
				ifthan(new IF(instanceOf(ob, A_AClass)){
					@Override
					public void body() {
						invoke(systemOut, "println", Value.value("ob can be cast to A"));
					}
				});
				invoke(systemOut, "println");
				
				
				/*     if (a instanceof Object)
				 *     	   System.out.println("a may be cast to Object");
				 */
				ifthan(new IF(instanceOf(a, AClass.OBJECT_ACLASS)){
					@Override
					public void body() {
						invoke(systemOut, "println", Value.value("a may be cast to Object"));
					}
				});
				
				
				/*     if (b instanceof Object)
				 *     	   System.out.println("b may be cast to Object");
				 */
				ifthan(new IF(instanceOf(b, AClass.OBJECT_ACLASS)){
					@Override
					public void body() {
						invoke(systemOut, "println", Value.value("b may be cast to Object"));
					}
				});
				
				
				/*     if (c instanceof Object)
				 *     	   System.out.println("c may be cast to Object");
				 */
				ifthan(new IF(instanceOf(c, AClass.OBJECT_ACLASS)){
					@Override
					public void body() {
						invoke(systemOut, "println", Value.value("c may be cast to Object"));
					}
				});
				
				
				/*     if (d instanceof Object)
				 *     	   System.out.println("d may be cast to Object");
				 */
				ifthan(new IF(instanceOf(d, AClass.OBJECT_ACLASS)){
					@Override
					public void body() {
						invoke(systemOut, "println", Value.value("d may be cast to Object"));
					}
				});
				runReturn();
			}
        });
		generate(creator);
	}

}

//上面代码将会生成类似如下内容
//不同的是访问修饰符不同

class A {
	int i, j;
}

class B {
	int i, j;
}

class C extends A {
	int k;
}

class D extends A {
	int k;
}

class InstanceOf {
	public static void main(String args[]) {
		A a = new A();
		B b = new B();
		C c = new C();
		D d = new D();

		if (a instanceof A)
			System.out.println("a is instance of A");
		if (b instanceof B)
			System.out.println("b is instance of B");
		if (c instanceof C)
			System.out.println("c is instance of C");
		if (c instanceof A)
			System.out.println("c can be cast to A");

		if (a instanceof C)
			System.out.println("a can be cast to C");

		System.out.println();

		A ob;

		ob = d; // A reference to d
		System.out.println("ob now refers to d");
		if (ob instanceof D)
			System.out.println("ob is instance of D");

		System.out.println();

		ob = c; // A reference to c
		System.out.println("ob now refers to c");

		if (ob instanceof D)
			System.out.println("ob can be cast to D");
		else
			System.out.println("ob cannot be cast to D");

		if (ob instanceof A)
			System.out.println("ob can be cast to A");

		System.out.println();

		// all objects can be cast to Object
		if (a instanceof Object)
			System.out.println("a may be cast to Object");
		if (b instanceof Object)
			System.out.println("b may be cast to Object");
		if (c instanceof Object)
			System.out.println("c may be cast to Object");
		if (d instanceof Object)
			System.out.println("d may be cast to Object");
	}
}