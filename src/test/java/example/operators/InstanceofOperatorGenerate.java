package example.operators;


import cn.wensiqun.asmsupport.core.block.classes.method.common.StaticMethodBodyInternal;
import cn.wensiqun.asmsupport.core.clazz.AClass;
import cn.wensiqun.asmsupport.core.clazz.AClassFactory;
import cn.wensiqun.asmsupport.core.creator.clazz.ClassCreatorInternal;
import cn.wensiqun.asmsupport.core.definition.value.Value;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.org.objectweb.asm.Opcodes;
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
		ClassCreatorInternal ACreator = new ClassCreatorInternal(Opcodes.V1_5, Opcodes.ACC_PUBLIC , "A", null, null);
		ACreator.createField("i", 0, AClass.INT_ACLASS);
		ACreator.createField("j", 0, AClass.INT_ACLASS);
		final Class A = ACreator.startup();
		
        //create class B
		ClassCreatorInternal BCreator = new ClassCreatorInternal(Opcodes.V1_5, Opcodes.ACC_PUBLIC , "B", null, null);
		BCreator.createField("i", 0, AClass.INT_ACLASS);
		BCreator.createField("j", 0, AClass.INT_ACLASS);
		final Class B = BCreator.startup();
		
		//create class C
		ClassCreatorInternal CCreator = new ClassCreatorInternal(Opcodes.V1_5, Opcodes.ACC_PUBLIC , "C", A, null);
		CCreator.createField("k", 0, AClass.INT_ACLASS);
		final Class C = CCreator.startup();

		//create class D
		ClassCreatorInternal DCreator = new ClassCreatorInternal(Opcodes.V1_5, Opcodes.ACC_PUBLIC , "D", A, null);
		DCreator.createField("k", 0, AClass.INT_ACLASS);
		final Class D = DCreator.startup();
		
        ClassCreatorInternal creator = new ClassCreatorInternal(Opcodes.V1_5, Opcodes.ACC_PUBLIC , "generated.operators.InstanceofOperatorGenerateExample", null, null);
		
		/*
		 * 生成一个main方法
		 */
		creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC,  
				"main", new AClass[]{AClassFactory.getProductClass(String[].class)}, new String[]{"args"}, null, null,
				new StaticMethodBodyInternal(){

			@Override
			public void body(LocalVariable... argus) {
				AClass A_AClass = AClassFactory.getProductClass(A);
				AClass B_AClass = AClassFactory.getProductClass(B);
				AClass C_AClass = AClassFactory.getProductClass(C);
				AClass D_AClass = AClassFactory.getProductClass(D);
				
			    //A a = new A();
				LocalVariable a = _createVariable("a", A_AClass, false, _new(A_AClass));
				
				//B b = new B();
				LocalVariable b = _createVariable("b", B_AClass, false, _new(B_AClass));
				
			    //C c = new C();
				LocalVariable c = _createVariable("c", C_AClass, false, _new(C_AClass));
				
			    //D d = new D();
				LocalVariable d = _createVariable("d", D_AClass, false, _new(D_AClass));
				
				/*if (a instanceof A)
				      System.out.println("a is instance of A");
				ifthan(new If(instanceOf(a, A_AClass)){
					@Override
					public void body() {
						invoke(systemOut, "println", Value.value("a is instance of A"));
					}
				});*/
				
				/*if (b instanceof B)
				    System.out.println("b is instance of B");
				ifthan(new If(instanceOf(b, B_AClass)){
					@Override
					public void body() {
						invoke(systemOut, "println", Value.value("b is instance of B"));
					}
				});*/
				
				/*if (c instanceof C)
				    System.out.println("b is instance of B");
				ifthan(new If(instanceOf(c, C_AClass)){
					@Override
					public void body() {
						invoke(systemOut, "println", Value.value("c is instance of C"));
					}
				});*/

				
				/*if (c instanceof A)
				    System.out.println("c can be cast to A");
				ifthan(new If(instanceOf(c, A_AClass)){
					@Override
					public void body() {
						invoke(systemOut, "println", Value.value("c can be cast to A"));
					}
				});*/

				/*if (a instanceof C)
				    System.out.println("a can be cast to C");
				ifthan(new If(instanceOf(a, C_AClass)){
					@Override
					public void body() {
						invoke(systemOut, "println", Value.value("a can be cast to C"));
					}
				});
				invoke(systemOut, "println");*/
				
				/*A ob = d; // A reference to d
				  System.out.println("ob now refers to d");*/
				LocalVariable ob = _createVariable("ob", A_AClass, false, d);
				_invoke(systemOut, "println", Value.value("ob now refers to d"));
				
				/* if (ob instanceof D)
		               System.out.println("ob is instance of D");
		           System.out.println();
				ifthan(new If(instanceOf(ob, D_AClass)){
					@Override
					public void body() {
						invoke(systemOut, "println", Value.value("ob is instance of D"));
					}
				});
				invoke(systemOut, "println");*/
				
				/*     ob = c; // A reference to c
				 *     System.out.println("ob now refers to c");
				 *     if (ob instanceof D)
				 *     	   System.out.println("ob can be cast to D");
				 *     else
				 *     	   System.out.println("ob cannot be cast to D");
				 
				assign(ob, c);
				invoke(systemOut, "println", Value.value("ob now refers to c"));
				ifthan(new If(instanceOf(ob, D_AClass)){
					@Override
					public void body() {
						invoke(systemOut, "println", Value.value("ob can be cast to D"));
					}
				}).elsethan(new Else(){
					@Override
					public void body() {
						invoke(systemOut, "println", Value.value("ob cannot be cast to D"));
					}
					
				});*/
				
				/*     if (ob instanceof A)
				 *     	   System.out.println("ob can be cast to A");
				 *     System.out.println();
				 
				ifthan(new If(instanceOf(ob, A_AClass)){
					@Override
					public void body() {
						invoke(systemOut, "println", Value.value("ob can be cast to A"));
					}
				});
				invoke(systemOut, "println");*/
				
				
				/*     if (a instanceof Object)
				 *     	   System.out.println("a may be cast to Object");
				
				ifthan(new If(instanceOf(a, AClass.OBJECT_ACLASS)){
					@Override
					public void body() {
						invoke(systemOut, "println", Value.value("a may be cast to Object"));
					}
				}); */
				
				
				/*     if (b instanceof Object)
				 *     	   System.out.println("b may be cast to Object");
				 
				ifthan(new If(instanceOf(b, AClass.OBJECT_ACLASS)){
					@Override
					public void body() {
						invoke(systemOut, "println", Value.value("b may be cast to Object"));
					}
				});*/
				
				
				/*     if (c instanceof Object)
				 *     	   System.out.println("c may be cast to Object");
				
				ifthan(new If(instanceOf(c, AClass.OBJECT_ACLASS)){
					@Override
					public void body() {
						invoke(systemOut, "println", Value.value("c may be cast to Object"));
					}
				}); */
				
				
				/*     if (d instanceof Object)
				 *     	   System.out.println("d may be cast to Object");
				 
				ifthan(new If(instanceOf(d, AClass.OBJECT_ACLASS)){
					@Override
					public void body() {
						invoke(systemOut, "println", Value.value("d may be cast to Object"));
					}
				});*/
				_return();
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