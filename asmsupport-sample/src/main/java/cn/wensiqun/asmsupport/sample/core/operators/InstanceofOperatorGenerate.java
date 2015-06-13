package cn.wensiqun.asmsupport.sample.core.operators;


import cn.wensiqun.asmsupport.core.block.control.condition.KernelElse;
import cn.wensiqun.asmsupport.core.block.control.condition.KernelIF;
import cn.wensiqun.asmsupport.core.block.method.common.KernelStaticMethodBody;
import cn.wensiqun.asmsupport.core.builder.impl.ClassBuilderImpl;
import cn.wensiqun.asmsupport.core.definition.value.Value;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.org.objectweb.asm.Opcodes;
import cn.wensiqun.asmsupport.sample.core.AbstractExample;
import cn.wensiqun.asmsupport.standard.def.clazz.AClass;
import cn.wensiqun.asmsupport.standard.def.clazz.AClassFactory;

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
		ClassBuilderImpl ACreator = new ClassBuilderImpl(Opcodes.V1_5, Opcodes.ACC_PUBLIC , "A", null, null);
		ACreator.createField("i", 0, AClassFactory.getType(int.class));
		ACreator.createField("j", 0, AClassFactory.getType(int.class));
		final Class A = ACreator.startup();
		
        //create class B
		ClassBuilderImpl BCreator = new ClassBuilderImpl(Opcodes.V1_5, Opcodes.ACC_PUBLIC , "B", null, null);
		BCreator.createField("i", 0, AClassFactory.getType(int.class));
		BCreator.createField("j", 0, AClassFactory.getType(int.class));
		final Class B = BCreator.startup();
		
		//create class C
		ClassBuilderImpl CCreator = new ClassBuilderImpl(Opcodes.V1_5, Opcodes.ACC_PUBLIC , "C", A, null);
		CCreator.createField("k", 0, AClassFactory.getType(int.class));
		final Class C = CCreator.startup();

		//create class D
		ClassBuilderImpl DCreator = new ClassBuilderImpl(Opcodes.V1_5, Opcodes.ACC_PUBLIC , "D", A, null);
		DCreator.createField("k", 0, AClassFactory.getType(int.class));
		final Class D = DCreator.startup();
		
        ClassBuilderImpl creator = new ClassBuilderImpl(Opcodes.V1_5, Opcodes.ACC_PUBLIC , "generated.operators.InstanceofOperatorGenerateExample", null, null);
		
		/*
		 * 生成一个main方法
		 */
		creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC,  
				"main", new AClass[]{AClassFactory.getType(String[].class)}, new String[]{"args"}, null, null,
				new KernelStaticMethodBody(){

			@Override
			public void body(LocalVariable... argus) {
				AClass A_AClass = AClassFactory.getType(A);
				AClass B_AClass = AClassFactory.getType(B);
				AClass C_AClass = AClassFactory.getType(C);
				AClass D_AClass = AClassFactory.getType(D);
				
			    //A a = new A();
				LocalVariable a = var("a", A_AClass, new_(A_AClass));
				
				//B b = new B();
				LocalVariable b = var("b", B_AClass, new_(B_AClass));
				
			    //C c = new C();
				LocalVariable c = var("c", C_AClass, new_(C_AClass));
				
			    //D d = new D();
				LocalVariable d = var("d", D_AClass, new_(D_AClass));
				
				/*if (a instanceof A)
				      System.out.println("a is instance of A");*/
				if_(new KernelIF(instanceof_(a, A_AClass)){
					@Override
					public void body() {
						call(systemOut, "println", Value.value("a is instance of A"));
					}
				});
				
				/*if (b instanceof B)
				    System.out.println("b is instance of B");*/
				if_(new KernelIF(instanceof_(b, B_AClass)){
					@Override
					public void body() {
						call(systemOut, "println", Value.value("b is instance of B"));
					}
				});
				
				/*if (c instanceof C)
				    System.out.println("b is instance of B");*/
				if_(new KernelIF(instanceof_(c, C_AClass)){
					@Override
					public void body() {
						call(systemOut, "println", Value.value("c is instance of C"));
					}
				});

				
				/*if (c instanceof A)
				    System.out.println("c can be cast to A");*/
				if_(new KernelIF(instanceof_(c, A_AClass)){
					@Override
					public void body() {
						call(systemOut, "println", Value.value("c can be cast to A"));
					}
				});

				/*if (a instanceof C)
				    System.out.println("a can be cast to C");*/
				if_(new KernelIF(instanceof_(a, C_AClass)){
					@Override
					public void body() {
						call(systemOut, "println", Value.value("a can be cast to C"));
					}
				});
				call(systemOut, "println");
				
				/*A ob = d; // A reference to d
				  System.out.println("ob now refers to d");*/
				LocalVariable ob = var("ob", A_AClass, d);
				call(systemOut, "println", Value.value("ob now refers to d"));
				
				/* if (ob instanceof D)
		               System.out.println("ob is instance of D"); */
				if_(new KernelIF(instanceof_(ob, D_AClass)){
					@Override
					public void body() {
						call(systemOut, "println", Value.value("ob is instance of D"));
					}
				});
				call(systemOut, "println");
				
				/*     ob = c; // A reference to c
				 *     System.out.println("ob now refers to c");
				 *     if (ob instanceof D)
				 *     	   System.out.println("ob can be cast to D");
				 *     else
				 *     	   System.out.println("ob cannot be cast to D");
				 */
				assign(ob, c);
				call(systemOut, "println", Value.value("ob now refers to c"));
				if_(new KernelIF(instanceof_(ob, D_AClass)){
					@Override
					public void body() {
						call(systemOut, "println", Value.value("ob can be cast to D"));
					}
				}).else_(new KernelElse(){
					@Override
					public void body() {
						call(systemOut, "println", Value.value("ob cannot be cast to D"));
					}
					
				});
				
				/*     if (ob instanceof A)
				 *     	   System.out.println("ob can be cast to A");
				 *     System.out.println();
				 */
				if_(new KernelIF(instanceof_(ob, A_AClass)){
					@Override
					public void body() {
						call(systemOut, "println", Value.value("ob can be cast to A"));
					}
				});
				call(systemOut, "println");
				
				
				/*     if (a instanceof Object)
				 *     	   System.out.println("a may be cast to Object");
				 */
				if_(new KernelIF(instanceof_(a, AClassFactory.getType(Object.class))){
					@Override
					public void body() {
					    call(systemOut, "println", Value.value("a may be cast to Object"));
					}
				}); 
				
				
				/*     if (b instanceof Object)
				 *     	   System.out.println("b may be cast to Object");
				 */
				if_(new KernelIF(instanceof_(b, AClassFactory.getType(Object.class))){
					@Override
					public void body() {
					    call(systemOut, "println", Value.value("b may be cast to Object"));
					}
				});
				
				
				/*     if (c instanceof Object)
				 *     	   System.out.println("c may be cast to Object");
				 */
				if_(new KernelIF(instanceof_(c, AClassFactory.getType(Object.class))){
					@Override
					public void body() {
					    call(systemOut, "println", Value.value("c may be cast to Object"));
					}
				}); 
				
				
				/*     if (d instanceof Object)
				 *     	   System.out.println("d may be cast to Object");
				 */
				if_(new KernelIF(instanceof_(d, AClassFactory.getType(Object.class))){
					@Override
					public void body() {
						call(systemOut, "println", Value.value("d may be cast to Object"));
					}
				});
				return_();
			}
        });
		generate(creator);
	}

}

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