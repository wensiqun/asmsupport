package example.create;

import java.lang.reflect.InvocationTargetException;

import cn.wensiqun.asmsupport.core.block.classes.method.clinit.ClinitBodyInternal;
import cn.wensiqun.asmsupport.core.block.classes.method.common.CommonMethodBodyInternal;
import cn.wensiqun.asmsupport.core.block.classes.method.common.StaticMethodBodyInternal;
import cn.wensiqun.asmsupport.core.block.classes.method.init.InitBodyInternal;
import cn.wensiqun.asmsupport.core.clazz.AClass;
import cn.wensiqun.asmsupport.core.clazz.AClassFactory;
import cn.wensiqun.asmsupport.core.creator.clazz.ClassCreatorInternal;
import cn.wensiqun.asmsupport.core.definition.value.Value;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.org.objectweb.asm.Opcodes;
import example.AbstractExample;


/**
 * 这个例子将学习到如何生成一个Class。并且在这个Class中添加局部变量和各种类型的方法
 * 最终将生产java代码如下:
 * 
 *public class CreateClassExample
 *{
 *  private static String staticGlobalVariable = "I'm a static global variable at class";
 *  public int globalVariable;
 *
 *  public CreateClassExample(int intVal)
 *  {
 *    this.globalVariable = intVal;
 *  }
 *
 *  private void commonMethod()
 *  {
 *    System.out.println("staticGlobalVariable : " + staticGlobalVariable);
 *    System.out.println("globalVariable : " + this.globalVariable);
 *  }
 *
 *  public static void main(String[] args)
 *  {
 *    new CreateClassExample(1024).commonMethod();
 *  }
 *}
 *
 */
public class CreateClass extends AbstractExample {

	/**
	 * @param args
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws SecurityException 
	 * @throws IllegalArgumentException 
	 */
	public static void main(String[] args) throws IllegalArgumentException, SecurityException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		
		/*
		 * ClassCreator: Class创建器，通过它可以创建一个新的Class。
		 * 构造它需要五个参数：
		 * 1: jdk version 
		 *    JDK的版本。可以用过org.objectweb.asm.Opcodes获取，比如：Opcodes.V1_5表示1.5版本的jdk
         * 2: class modifiers.
         *    表示创建的Class的修饰符。修饰符同样也是通过org.objectweb.asm.Opcodes获取，比如修饰符是"public abstract"则是 Opcodes.ACC_PUBLIC + Opcodes.ACC_ABSTRACT
         * 3: the class full name  
         *    Class的全名。
         * 4: super class
         *    父类,如果为null则是继承自Object.class
         * 5: interface 实现的接口, 可以是null,类型是Class[]
		 */
		ClassCreatorInternal creator = new ClassCreatorInternal(Opcodes.V1_5, Opcodes.ACC_PUBLIC , "generated.create.CreateClassExample", ParentCreateClassExample.class, null);
		
		/*
		 * 这部分是创建一个静态全局 变量。基本和CreateInterface.java中如何创建和赋值是一样的
		 * 唯一不同的是这时候全局变量的修饰符,和ClassCreator的modifiers类似，例如: Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC
		 * 
		 * java代码如下：
		 * private static String staticGlobalVariable;
		 *
		 */
		creator.createField("staticGlobalVariable", Opcodes.ACC_PRIVATE + Opcodes.ACC_STATIC, AClass.STRING_ACLASS);
		/*
		 * 对应java代码：
		 * static{
		 *     staticGlobalVariable = "I'm a static global variable at class"
		 * }
		 * 
		 */
		creator.createStaticBlock(new ClinitBodyInternal(){
			@Override
			public void body() {
				_assign(getMethodOwner().getGlobalVariable("staticGlobalVariable"), Value.value("I'm a static global variable at class"));
				_return();
			}
		});
		
		/*
		 * 创建个非静态的全局变量
		 * 我们这里仅仅值做一个全局变量的声明。我们并不能在这里给予它赋值。这里得区别于我们平时编写java代码，其实如果你对java字节码
		 * 熟悉的话也能知道，在java字节码中，全局变量的赋值是在static块中或者构造方法中实现的，前者是给静态的全局变量赋值。而后者
		 * 是给非静态的变量赋值。我们这里也一样。如果是static的全局变量我们将在createStaticBlock中赋值，否则我们在createConstructor
		 * 中赋值。
		 * 
		 * 对应java代码：
		 * public int globalVariable;
		 * 
		 */
		creator.createField("globalVariable", Opcodes.ACC_PUBLIC, AClass.INT_ACLASS);
		
		/* 
		 * 创建一个构造方法。对于ClassCreator来说。如果没有创建任何构造方法，将会自动创建一个无参的默认构造函数
		 * 这个构造方法有一个int类型参数，在构造函数中将该参数赋值给我们上面创建的globalVariable。
		 * 对应的java代码:
		 * public CreateClassExample(String intVal){
		 *     this.globalVariable = intVal;
		 * }
		 * 
		 * 这里有四个参数
		 * 1.构造方法的所有参数类型的数组
		 * 2.构造方法所有参数的名称
		 * 3.构造参数的方法体
		 * 4.构造方法的修饰符
		 */
		creator.createConstructor(Opcodes.ACC_PUBLIC, new AClass[]{AClassFactory.getProductClass(int.class)}, new String[]{"intVal"}, new InitBodyInternal(){

			/*
			 * 这个方法中的内容就是我们创建的构造方法里面需要执行的内容了，他有一个变元参数 argus。
			 * 这里的参数表示的是我们当前创建的构造方法的参数。这个数组中每个LocalVariable
			 * 和createConstructor方法的第一个参数中的AClass数组一一对应的。例如这里argus
			 * 只有一个元素它的类型是int类型，名字是intVal。
			 */
			@Override
			public void body(LocalVariable... argus) {
				/*
				 * 这里要注意的。 如果是创建构造方法，要显示的调用invokeSuperConstructor,他的作用
				 * 就是等同于我们编写java代码的时候调用super()一样。只不过我们在用java创建个构造函数的时候有时候
				 * 不需要调用super，是应为父类的存在没有参数的构造方法。但这并不代码jvm没有去调用super().其实编译器在编译
				 * java的时候已经将调用super的指令加上到了构造方法的字节码中去了。
				 * 
				 * 这个方法存在一个变元参数arguments。他表示我们调用super()的时候需要传递的参数
				 * 
				 */
				invokeSuperConstructor();
				
				/*
				 * 由于我们创建的globalVariable是非static的。所以通过getThis()获取globalVariable。
				 * getThis()对应于java代码中就是this关键字。
				 */
				_assign(_this().getGlobalVariable("globalVariable"), argus[0]);
				_return();
			}
			
		});
		
		/*
		 * 创建一个private的commonMethod方法。
		 * 在commonMethod中将打印出staticGlobalVariable和globalVariable的值
		 * 
		 * java代码如下:
		 * private void commonMethod(){
		 *     System.out.println("staticGlobalVariable : " + staticGlobalVariable);
		 *     System.out.println("globalVariable : " + globalVariable);
		 * }
		 * 
		 */
		creator.createMethod(Opcodes.ACC_PRIVATE, "commonMethod", null, null, null, null, new CommonMethodBodyInternal(){

			@Override
			public void body(LocalVariable... argus) {
				_invoke(systemOut, "println", _append(Value.value("staticGlobalVariable : "), getMethodOwner().getGlobalVariable("staticGlobalVariable")));
				_invoke(systemOut, "println", _append(Value.value("globalVariable : "), _this().getGlobalVariable("globalVariable")));
				_return();
			}
			
		});
		
		
		/*
		 * 在这里我们又要创建一个我们经常用到的静态方法main方法
		 * 在这个方法中我们首先new一个我们现在正在创建的class然后调用它的commonMethod方法。
		 * java代码如下：
		 * public static void main(String[] args){
		 *     new CreateClassExample(1024).commonMethod();
		 * }
		 */
		creator.createStaticMethod(Opcodes.ACC_PUBLIC, "main", new AClass[]{AClassFactory.getProductClass(String[].class)}, new String[]{"args"}, null, null,
				new StaticMethodBodyInternal(){

	        @Override
			public void body(LocalVariable... argus) {
	        	_invoke(_new(getMethodOwner(), Value.value(1024)), "commonMethod");
	        	
	        	_invoke(systemOut, "println", _append(Value.value("COMMON_PRE : "), getMethodOwner().getGlobalVariable("COMMON_PRE")));
	        	
	        	_invoke(systemOut, "println", _append(Value.value("COMMON_POST : "), getMethodOwner().getGlobalVariable("COMMON_POST")));
				
	        	//invoke(systemOut, "println", append(Value.value("COMMON_MIDDLE : "), getMethodOwner().getGlobalVariable("common_middle")));
				
			    _return();
			}
			
		});
		generate(creator);
	}

}
